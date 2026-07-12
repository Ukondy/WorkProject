package com.ukondy.utils;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfConverter {
    private static final String prefix = "image_";
    private static final String extension = ".jpg";
    private static final Pattern pattern = Pattern.compile(prefix + "(\\d+)" + extension);

    public static void downloadPdf(String dest) throws Exception {
        List<FileNumberPair> filePairs = findFiles(new File("src/main/resources/com/ukondy/imgs/"));
        if (filePairs == null || filePairs.isEmpty()) {
            System.out.println("Нет файлов для конвертации.");
            return;
        }

        filePairs.sort(Comparator.comparingInt(pair -> pair.number));
        System.out.println("Начало сборки бесшовного PDF в: " + dest);

        // 1. Предварительно загружаем картинки, чтобы рассчитать общую высоту ленты
        List<Image> loadedImages = new ArrayList<>();
        float totalHeight = 0;
        float maxWidth = 0;

        for (FileNumberPair pair : filePairs) {
            Image image = Image.getInstance(pair.file.getAbsolutePath());
            loadedImages.add(image);
            totalHeight += image.getHeight();
            if (image.getWidth() > maxWidth) {
                maxWidth = image.getWidth(); // Берем максимальную ширину за основу
            }
        }

        // 2. Создаем документ с ОДНОЙ длинной страницей под размер всей ленты комикса
        // Обнуляем все отступы (margins)
        Document document = new Document(new Rectangle(maxWidth, totalHeight), 0, 0, 0, 0);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest + "/title.pdf"));
        writer.setCompressionLevel(9);
        document.open();

        // 3. Размещаем картинки на холсте сверху вниз
        // В PDF координата Y=0 находится СНИЗУ, поэтому начинаем сверху и вычитаем высоту каждой картинки
        float currentY = totalHeight;

        for (Image img : loadedImages) {
            float imgWidth = img.getWidth();
            float imgHeight = img.getHeight();

            // Если картинки разной ширины, подгоняем их под общую ширину холста
            img.scaleAbsolute(maxWidth, imgHeight);

            // Сдвигаем координату Y вниз на высоту текущей картинки
            currentY -= imgHeight;

            // Устанавливаем точную позицию (X = 0, Y = текущий уровень)
            img.setAbsolutePosition(0, currentY);

            document.add(img);
        }

        document.close();
        System.out.println("Бесшовный PDF успешно создан: " + dest);
    }

    private static List<FileNumberPair> findFiles(File dir) {
        if (!dir.exists()) {
            System.out.println("Папка не найдена: " + dir);
            return null;
        }

        List<FileNumberPair> filePairs = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files == null) return filePairs;

        for (File f : files) {
            Matcher m = pattern.matcher(f.getName());
            if (m.matches()) {
                int number = Integer.parseInt(m.group(1));
                filePairs.add(new FileNumberPair(f, number));
            }
        }
        return filePairs;
    }

    static class FileNumberPair {
        File file;
        int number;

        FileNumberPair(File file, int number) {
            this.file = file;
            this.number = number;
        }
    }
}

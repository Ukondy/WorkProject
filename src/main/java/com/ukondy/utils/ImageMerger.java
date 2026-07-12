package com.ukondy.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageMerger {
    private static final String prefix = "image_";
    private static final String extension = ".jpg";
    private static final Pattern pattern = Pattern.compile(prefix + "(\\d+)" + extension);

    // Целевая ширина как на сайте
    private static final int TARGET_WIDTH = 750;

    public static void downloadPng(String outputFilePath) throws IOException {
        File dir = new File("src/main/resources/com/ukondy/imgs/");
        if (!dir.exists()) {
            System.out.println("Папка с картинками не найдена: " + "src/main/resources/com/ukondy/imgs/");
            return;
        }
        outputFilePath += "/img.jpg";

        // 1. Находим и сортируем исходные файлы
        List<FileNumberPair> filePairs = findFiles(dir);
        if (filePairs.isEmpty()) {
            System.out.println("Нет подходящих картинок для объединения.");
            return;
        }
        filePairs.sort(Comparator.comparingInt(pair -> pair.number));

        // 2. Загружаем картинки и рассчитываем итоговую высоту с учетом масштабирования под 750px
        List<BufferedImage> images = new ArrayList<>();
        long totalHeight = 0; // Использование long защищает от переполнения

        for (FileNumberPair pair : filePairs) {
            BufferedImage img = ImageIO.read(pair.file);
            if (img != null) {
                images.add(img);

                // Вычисляем коэффициент масштабирования под ширину 750 пикселей
                double scaleRatio = (double) TARGET_WIDTH / img.getWidth();
                // Рассчитываем новую пропорциональную высоту для этого кадра
                int scaledHeight = (int) Math.round(img.getHeight() * scaleRatio);

                totalHeight += scaledHeight;
            }
        }

        if (images.isEmpty()) {
            System.out.println("Не удалось прочитать ни одно изображение.");
            return;
        }

        // Проверяем теоретический предел для BufferedImage (максимум Integer.MAX_VALUE)
        if (totalHeight > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Общая высота изображения слишком велика даже для формата PNG!");
        }

        System.out.println("Склеиваем все " + images.size() + " кадров в одну бесконечную PNG-ленту...");
        System.out.println("Ширина холста: " + TARGET_WIDTH + "px. Итоговая высота составит: " + totalHeight + " пикселей.");

        // 3. Создаем холст под зафиксированную ширину 750 пикселей
        BufferedImage combinedImage = new BufferedImage(TARGET_WIDTH, (int) totalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = combinedImage.createGraphics();

        // Включаем качественную интерполяцию для сглаживания текста при масштабировании кадров
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Заполняем фон белым цветом
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, TARGET_WIDTH, (int) totalHeight);

        // 4. Отрисовываем картинки сверху вниз с принудительным приведением к ширине 750px
        int currentY = 0;
        for (BufferedImage img : images) {
            // Повторно вычисляем новую пропорциональную высоту кадра
            double scaleRatio = (double) TARGET_WIDTH / img.getWidth();
            int scaledHeight = (int) Math.round(img.getHeight() * scaleRatio);

            // Рисуем картинку, растягивая/сжимая её точно до 750 пикселей в ширину
            g2d.drawImage(img, 0, currentY, TARGET_WIDTH, scaledHeight, null);

            currentY += scaledHeight;
            img.flush(); // Сразу чистим память из-под старого кадра
        }
        g2d.dispose();

        // 5. Обрабатываем путь сохранения: принудительно меняем расширение на .png
        File outputFile = new File(outputFilePath);
        if (outputFile.isDirectory()) {
            outputFile = new File(outputFile, "merged_comic.png");
        } else {
            String path = outputFile.getAbsolutePath();
            if (path.contains(".")) {
                path = path.substring(0, path.lastIndexOf(".")) + ".png";
            } else {
                path += ".png";
            }
            outputFile = new File(path);
        }

        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }

        // Удаляем старый файл, если он остался, чтобы избежать блокировок доступа
        if (outputFile.exists()) {
            outputFile.delete();
        }

        // 6. Записываем в формате "png"
        System.out.println("Запись файла на диск (это может занять несколько секунд)...");
        boolean success = ImageIO.write(combinedImage, "png", outputFile);
        combinedImage.flush();

        if (success) {
            System.out.println("Супер! Все изображения склеены в один монолитный файл без отступов: " + outputFile.getAbsolutePath());
        } else {
            System.err.println("Не удалось записать итоговый PNG файл.");
        }
    }

    private static List<FileNumberPair> findFiles(File dir) {
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

    private static class FileNumberPair {
        File file;
        int number;

        FileNumberPair(File file, int number) {
            this.file = file;
            this.number = number;
        }
    }
}

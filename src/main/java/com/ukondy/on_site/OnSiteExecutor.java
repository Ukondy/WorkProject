package com.ukondy.on_site;

import com.ukondy.sites.Site;
import com.ukondy.sites.enums.Sites;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.jsoup.select.Elements;

import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.ukondy.utils.ImageMerger.downloadPng;
import static com.ukondy.utils.PdfConverter.downloadPdf;
import static io.restassured.RestAssured.get;

public class OnSiteExecutor {
    private static Site site;
    @SneakyThrows
    public static void execute(String url, String source, String resultType, String destination) {
        Response response = get(url);

        site = Sites.valueOf(source.toUpperCase()).getInstance();

        Elements images = site.getImages(response.asString());

        IntStream.range(0, images.size())
                .forEach(i -> site.downloadFiles(getLink(images, i), i));

        if(resultType.contains("PNG")) {
            downloadPng(destination);
        } else if(resultType.contains("PDF")) {
            downloadPdf(destination);
        }

        clearDir();
    }

    private static String getLink(Elements images, int i) {
        String src = images.get(i).attr("src");
        String dataSrc = images.get(i).attr("data-src");
        return src.isEmpty() ? dataSrc : src;
    }

    @SneakyThrows
    private static void clearDir() {
        FileUtils.cleanDirectory(Path.of("src/main/resources/com/ukondy/imgs/").toFile());
    }
}

package com.ukondy.sites;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import lombok.SneakyThrows;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class MangaBuff implements Site {

    @SneakyThrows
    @Override
    public void downloadFiles(String src, Integer num) {
        try {
            if (src.startsWith("//")) {
                src = "https:" + src;
            } else if (src.startsWith("/")) {
                src = "https://mangabuff.ru" + src;
            }

            System.out.println("Финальный URI для отправки: " + src);

            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(src))
                    .timeout(Duration.ofSeconds(15))
                    .header("Referer", "https://mangabuff.ru")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                    .header("Sec-Fetch-Site", "cross-site")
                    .header("Sec-Fetch-Mode", "no-cors")
                    .header("Sec-Fetch-Dest", "image")
                    .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                    .GET()
                    .build();

            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                byte[] imageBytes = response.body();

                String fileName = "src/main/resources/com/ukondy/imgs/" + "image_" + num + ".jpg";
                File file = new File(fileName);
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }

                try (OutputStream outStream = new FileOutputStream(file)) {
                    outStream.write(imageBytes);
                }
            } else {
                System.err.println("Сервер вернул ошибку " + response.statusCode() + " для картинки: " + src);
            }

        } catch (Exception e) {
            System.err.println("Ошибка при скачивании картинки " + src + ": " + e.getMessage());
        }

        Thread.sleep(1500);
    }


    @Override
    public Elements getImages(String response) {
        Document doc = Jsoup.parse(response);

        Elements section = doc.getElementsByClass("reader__pages");

        return section.select("img");
    }
}

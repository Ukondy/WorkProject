package com.ukondy.sites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static io.restassured.RestAssured.given;

public class Naver implements Site {

    @Override
    public void downloadFiles(String src, Integer num) {
        try {
            byte[] imageBytes =
                    given()
                            .header("Referer", "https://comic.naver.com/webtoon/detail?titleId=766563&no=276")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                            .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                            .header("Sec-Fetch-Site", "same-site")
                            .header("Sec-Fetch-Mode", "no-cors")
                            .header("Sec-Fetch-Dest", "image")
                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                            .when()
                            .get(src)
                            .then()
                            .statusCode(200)
                            .extract()
                            .asByteArray();

            String fileName = "src/main/resources/com/ukondy/imgs/" + "image_" + num + ".jpg";
            File file = new File(fileName);

            try (OutputStream outStream = new FileOutputStream(file)) {
                outStream.write(imageBytes);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при скачивании картинки " + src + ": " + e.getMessage());
        }
    }

    @Override
    public Elements getImages(String response) {
        Document doc = Jsoup.parse(response);

        Element section = doc.getElementById("sectionContWide");

        return section.select("img");
    }

}

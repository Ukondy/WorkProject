package com.ukondy.sites;

import org.jsoup.select.Elements;

public interface Site {
    void downloadFiles(String src, Integer num);

    Elements getImages(String asString);
}

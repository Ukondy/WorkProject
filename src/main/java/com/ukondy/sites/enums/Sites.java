package com.ukondy.sites.enums;

import com.ukondy.sites.MangaBuff;
import com.ukondy.sites.Naver;
import com.ukondy.sites.Site;

public enum Sites {
    NAVER(new Naver(), "Naver"),
//    KAKAO(new Naver(), "Kakao"),
    MANGABUFF(new MangaBuff(), "MangaBuff");
//    MANGALIB(new Naver(), "Mangalib");


    private Site instance;
    private final String name;
    Sites(Site instance, String name) {
        this.instance = instance;
        this.name = name;
    }

    public Site getInstance() {
        return instance;
    }

    public void setInstance(Site instance) {
        this.instance = instance;
    }

    public String getName() {
        return name;
    }
}

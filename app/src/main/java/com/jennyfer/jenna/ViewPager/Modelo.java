package com.jennyfer.jenna.ViewPager;

public class Modelo {
    private int Img;
    private String title;

    public Modelo(int img, String title ) {
        Img = img;
        this.title = title;
    }

    public int getImg() { return Img; }
    public void setImg(int img) { Img = img; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}

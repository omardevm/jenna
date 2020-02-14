package com.jennyfer.jenna.Tools;

import com.google.gson.annotations.SerializedName;

public class FlipperImage { //Hero

    @SerializedName("name")
    private String name;

    @SerializedName("imageurl")
    private String url;

    public FlipperImage(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

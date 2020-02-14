package com.jennyfer.jenna.Tools;

//https://www.simplifiedcoding.net/android-adapterviewflipper-example/
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlipperImages { //Heroes

    @SerializedName("flipperImages") //heroes
    private ArrayList<FlipperImage> flipperI;  //Hero  heros

    public FlipperImages(){ //Heroes

    }

    public ArrayList<FlipperImage> getFlipperI() { //Hero getHeros
        return flipperI; //heros
    }
}

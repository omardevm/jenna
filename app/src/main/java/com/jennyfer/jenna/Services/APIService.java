package com.jennyfer.jenna.Services;

import com.jennyfer.jenna.Tools.FlipperImages;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("flipperImages.php")
    Call<FlipperImages> getFlipperImages();
}

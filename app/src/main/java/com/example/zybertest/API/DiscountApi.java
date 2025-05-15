package com.example.zybertest.API;

import com.example.zybertest.Models.Discount;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DiscountApi {
    @GET("api/discounts/active")
    Call<List<Discount>> getActiveDiscounts();
}

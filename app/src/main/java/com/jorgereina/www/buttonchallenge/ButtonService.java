package com.jorgereina.www.buttonchallenge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jorgereina on 3/8/18.
 */

public interface ButtonService {

    @GET("user?candidate=jsr11237")
    Call<List<UserResponse>> getUserist();

    @POST("user")
    Call<UserResponse> createUser(@Body User user);

}

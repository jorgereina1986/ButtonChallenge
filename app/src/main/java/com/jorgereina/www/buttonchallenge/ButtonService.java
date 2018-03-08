package com.jorgereina.www.buttonchallenge;

import com.jorgereina.www.buttonchallenge.models.PreUser;
import com.jorgereina.www.buttonchallenge.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by jorgereina on 3/8/18.
 */

public interface ButtonService {

    @GET("user?candidate=jsr11237")
    Call<List<User>> getUserist();

    @POST("user")
    Call<User> createUser(@Body PreUser user);

}

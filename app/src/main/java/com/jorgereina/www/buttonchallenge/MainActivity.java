package com.jorgereina.www.buttonchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://fake-button.herokuapp.com/";

    private EditText name;
    private EditText email;
    private EditText candidate;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ButtonAdapter adapter;
    private List<UserResponse> responseList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ButtonAdapter(getApplicationContext(), responseList);
        layoutManager = new LinearLayoutManager(this);

        name = findViewById(R.id.name_et);
        email = findViewById(R.id.email_et);
        candidate = findViewById(R.id.candidate_et);
        recyclerView = findViewById(R.id.users_rv);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getNetworkRequest();
    }

    private void getNetworkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ButtonService service = retrofit.create(ButtonService.class);
        Call<List<UserResponse>> call = service.getUserist();
        call.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
//                responseList.addAll(response.body());
                Log.d("jrod", "onResponse: " + response.body().get(0).getName());
                responseList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t+"", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postNetoworkRequest() {

    }

}

package com.jorgereina.www.buttonchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

    private EditText nameEt;
    private EditText emailEt;
    private EditText candidateEt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button createBtn;
    private ButtonAdapter adapter;
    private List<UserResponse> responseList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ButtonAdapter(getApplicationContext(), responseList);
        layoutManager = new LinearLayoutManager(this);
        nameEt = findViewById(R.id.name_et);
        emailEt = findViewById(R.id.email_et);
        candidateEt = findViewById(R.id.candidate_et);
        recyclerView = findViewById(R.id.users_rv);
        createBtn = findViewById(R.id.create_btn);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        getNetworkRequest();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNetworkRequest();
                getNetworkRequest();
            }
        });
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
                responseList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t+"", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postNetworkRequest() {

        String name = nameEt.getText().toString();
        String email = emailEt.getText().toString();
        String candidate = candidateEt.getText().toString();

        User user = new User(name, email, candidate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ButtonService service = retrofit.create(ButtonService.class);
        Call<UserResponse> call = service.createUser(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

    }

}

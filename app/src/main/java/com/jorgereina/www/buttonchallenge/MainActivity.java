package com.jorgereina.www.buttonchallenge;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgereina.www.buttonchallenge.models.PreUser;
import com.jorgereina.www.buttonchallenge.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://fake-button.herokuapp.com/";
    private static final String CANDIDATE_PARAMETER = "jsr11237";

    private EditText nameEt;
    private EditText emailEt;
    private TextView candidateEt;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button createBtn;
    private ButtonAdapter adapter;
    private List<User> responseList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewsAndSetup();
        getUserLisRequest();
        setListeners();
    }

    private void getUserLisRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ButtonService service = retrofit.create(ButtonService.class);
        Call<List<User>> call = service.getUserist();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                responseList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createNewUserRequest() {

        final String name = nameEt.getText().toString();
        String email = emailEt.getText().toString();
        String candidate = CANDIDATE_PARAMETER;

        if (name.isEmpty() || email.isEmpty()) {
            showAlertDialog(R.string.alert_dialog_emty_field_title, R.string.alert_dialog_empty_field_message);
            if (!isValidEmail(email)) {
                showAlertDialog(R.string.alert_dialog_invalid_email_title, R.string.alert_dialog_invalid_email_message);
            }
        } else {

            if (!isValidEmail(email)) {
                showAlertDialog(R.string.alert_dialog_invalid_email_title, R.string.alert_dialog_invalid_email_message);
            } else {
                PreUser user = new PreUser(name, email, candidate);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ButtonService service = retrofit.create(ButtonService.class);
                Call<User> call = service.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        clearTextFields();
                        Toast toast = Toast.makeText(
                                getApplicationContext(),
                                R.string.toast_create_user_success,
                                Toast.LENGTH_LONG
                        );
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserLisRequest();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUserRequest();
            }
        });
    }

    private void initializeViewsAndSetup() {
        adapter = new ButtonAdapter(getApplicationContext(), responseList);
        layoutManager = new LinearLayoutManager(this);
        nameEt = findViewById(R.id.name_et);
        emailEt = findViewById(R.id.email_et);
        candidateEt = findViewById(R.id.candidate_et);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        recyclerView = findViewById(R.id.users_rv);
        createBtn = findViewById(R.id.create_btn);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public final boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void showAlertDialog(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearTextFields() {
        nameEt.setText("");
        emailEt.setText("");
    }
}

package com.jorgereina.www.buttonchallenge;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;
import android.view.Gravity;
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

public class ButtonPresenter implements ButtonPresenterContract.Presenter {

    private static final String BASE_URL = "http://fake-button.herokuapp.com/";
    private static final String CANDIDATE_PARAMETER = "jsr11237";

    private List<User> responseList = new ArrayList<>();
    private ButtonPresenterContract.View view;
    private ButtonAdapter adapter;

    public ButtonPresenterContract.Presenter setAdapter(ButtonAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public ButtonPresenterContract.Presenter setView(ButtonPresenterContract.View view) {
        this.view = view;
        return this;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    @Override
    public void onViewInitialized() {
        fetchUserLisRequest();
    }

    @Override
    public void onCreateButtonClick(String name, String email) {
        createNewUserRequest(name, email);
    }

    @Override
    public void onRefresh() {
        fetchUserLisRequest();
        view.setRefreshing(false);
    }

    private void fetchUserLisRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ButtonService service = retrofit.create(ButtonService.class);
        Call<List<User>> call = service.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                onFetchUserListResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                onFetchUserListFailure(t);
            }
        });
    }

    private void onFetchUserListResponse(List<User> response) {
        responseList.addAll(response);
        adapter.notifyDataSetChanged();
    }

    private void onFetchUserListFailure(Throwable throwable) {
        view.showErrorMessage(throwable.getMessage());
    }

    private void createNewUserRequest(String name, String email) {
        if (name.isEmpty() || email.isEmpty()) {
            view.showAlertDialog(R.string.alert_dialog_emty_field_title, R.string.alert_dialog_empty_field_message);
            if (!isValidEmail(email)) {
                view.showAlertDialog(R.string.alert_dialog_invalid_email_title, R.string.alert_dialog_invalid_email_message);
            }
        } else {

            if (!isValidEmail(email)) {
                view.showAlertDialog(R.string.alert_dialog_invalid_email_title, R.string.alert_dialog_invalid_email_message);
            } else {
                PreUser user = new PreUser(name, email, CANDIDATE_PARAMETER);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ButtonService service = retrofit.create(ButtonService.class);
                Call<User> call = service.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        onCreateNewUserResponse();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        onCreateNewUserFailure(t);
                    }
                });
            }
        }
    }

    private void onCreateNewUserResponse() {
        view.clearTextFields();
        view.showSuccessMessage(R.string.toast_create_user_success);
    }

    private void onCreateNewUserFailure(Throwable throwable) {
        view.showErrorMessage(throwable.getMessage());
    }

    public final boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

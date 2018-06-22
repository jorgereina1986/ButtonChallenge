package com.jorgereina.www.buttonchallenge;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jorgereina.www.buttonchallenge.databinding.ActivityMainBinding;
import com.jorgereina.www.buttonchallenge.models.PreUser;
import com.jorgereina.www.buttonchallenge.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ButtonPresenterContract.View {

    private ActivityMainBinding binding;
    private ButtonPresenterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        ButtonAdapter adapter = new ButtonAdapter();
        presenter = new ButtonPresenter()
                .setView(this)
                .setAdapter(adapter);
        getLifecycle().addObserver(presenter);
        binding.usersRv.setLayoutManager(new LinearLayoutManager(this));
        binding.usersRv.setAdapter(adapter);
        setListeners();
        presenter.onViewInitialized();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(presenter);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        binding.swipeLayout.setRefreshing(refreshing);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(int message) {
        Toast toast = Toast.makeText(
                getApplicationContext(),
                R.string.toast_create_user_success,
                Toast.LENGTH_LONG
        );
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    @Override
    public void showProgress() {
        binding.buttonPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.buttonPb.setVisibility(View.GONE);
    }

    @Override
    public void showAlertDialog(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void clearTextFields() {
        binding.nameEt.setText("");
        binding.emailEt.setText("");
    }

    private void setListeners() {
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();

            }
        });
        binding.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCreateButtonClick(binding.nameEt.getText().toString(), binding.emailEt.getText().toString());
            }
        });
    }
}

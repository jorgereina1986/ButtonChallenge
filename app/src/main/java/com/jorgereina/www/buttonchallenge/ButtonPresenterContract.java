package com.jorgereina.www.buttonchallenge;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

public interface ButtonPresenterContract {

    interface View {
        //e.g : view.doSomething();
        void loadData();
        void showProgress();
        void hideProgress();
        void showAlertDialog(int title, int message);
        void clearTextFields();
        void setRefreshing(boolean refreshing);
        void showErrorMessage(String message);

        void showSuccessMessage(int message);
    }

    interface Presenter extends LifecycleObserver {
        //e.g: presenter.onSomethingHappend();
        Presenter setAdapter(ButtonAdapter adapter);
        Presenter setView(View view);
        void onCreateButtonClick(String name, String email);
        void onRefresh();
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        void onCreate();
        void onViewInitialized();
    }
}

package com.example.consultants.week4daily3.ui.main;

import com.example.consultants.week4daily3.ui.base.BasePresenter;
import com.example.consultants.week4daily3.ui.base.BaseView;

public interface MainContract {

    interface View extends BaseView {

        void onSignIn(String email, String password);
        void onSignUp(String email, String password);

    }

    interface Presenter extends BasePresenter<View> {

    }
}

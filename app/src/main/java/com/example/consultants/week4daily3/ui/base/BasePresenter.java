package com.example.consultants.week4daily3.ui.base;

public interface BasePresenter<V extends BaseView> {

    void onAttach(V View);

    void onDetach();
}

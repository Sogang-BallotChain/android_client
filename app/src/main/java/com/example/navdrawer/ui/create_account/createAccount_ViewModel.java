package com.example.navdrawer.ui.create_account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class createAccount_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public createAccount_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.navdrawer.ui.seeThrough_vote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class seeThrough_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public seeThrough_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
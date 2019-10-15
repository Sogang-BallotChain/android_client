package com.example.navdrawer.ui.create_vote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class CreateVoteModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateVoteModel() {
        mText = new MutableLiveData<>();
        mText.setValue("생성하고자 하는 투표 이름과 설명을 입력하세요");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
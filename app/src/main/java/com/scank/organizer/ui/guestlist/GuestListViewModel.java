package com.scank.organizer.ui.guestlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuestListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GuestListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is GuestList fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
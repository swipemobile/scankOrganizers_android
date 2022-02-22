package com.scank.organizer.ui.manage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.scank.organizer.model.EventData;

public class ManageViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private EventData eventData;

    public ManageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Manage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public EventData getEventData() {
        return eventData;
    }

}
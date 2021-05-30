package com.example.icareer.jobSeeker.ui.notificationsJobSeeker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsJobSeekerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsJobSeekerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
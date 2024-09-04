package com.automotive.infotainment.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SpeedRepository {

    public static String TAG = SpeedRepository.class.getName();

    private static SpeedRepository repository;
    private final MutableLiveData<Float> speedInMiles = new MutableLiveData<>();

    public static SpeedRepository getInstance(){
        synchronized (SpeedRepository.class){
            if(repository == null){
                repository = new SpeedRepository();
            }
            return repository;
        }

    }

    public LiveData<Float> getSpeedInMiles(){
        Log.i(TAG, " getSpeedInMiles ");
        return this.speedInMiles;
    }

    public void setSpeedInMiles(Float val) {
        Log.i(TAG, "val "+val);
        this.speedInMiles.setValue(val);
    }

}

package com.automotive.infotainment.handler;

import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.util.Log;
import android.car.hardware.property.CarPropertyManager;

public class SpeedProperyHandler extends BasePropertyHandler{

    private static final String TAG = SpeedProperyHandler.class.getName();
    float vehicleSpeedMiles;
    int gearSelection;
    int previousGear = -1;

    public SpeedProperyHandler(CarPropertyManager carPropertyManager) {
        super(carPropertyManager);
        Log.i(TAG,"Constructor initialized");
    }

    @Override
    void generatePropertyList() {
        propertyList.add(VehiclePropertyIds.PERF_VEHICLE_SPEED);
        Log.i(TAG,"Property generated.");

    }

    @Override
    public void onChangeEvent(CarPropertyValue carPropertyValue) {
        Log.i(TAG, "onChangeEvent() id " + carPropertyValue.getPropertyId() + " , value "
                + carPropertyValue.getValue());
        int propertyId = carPropertyValue.getPropertyId();
        switch (propertyId) {
            case VehiclePropertyIds.PERF_VEHICLE_SPEED:
                vehicleSpeedMiles =  getFloatProperty(VehiclePropertyIds.PERF_VEHICLE_SPEED);
                mSpeedRepository.setSpeedInMiles(vehicleSpeedMiles);
                break;

            // Execute your Logic
        }
    }

    @Override
    public void onErrorEvent(int i, int i1) {
        Log.i(TAG,"onErrrorEvent" + i + "i1: " +i1);
    }

    @Override
    public void getDefaultProperties() {
        float prefSpeed =  getFloatProperty(VehiclePropertyIds.PERF_VEHICLE_SPEED);
        Log.i(TAG,"defaultGear : " +prefSpeed);
    }

}

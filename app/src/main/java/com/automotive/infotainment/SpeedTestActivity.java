package com.automotive.infotainment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.automotive.infotainment.vm.SpeedViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpeedTestActivity extends AppCompatActivity {

    public SpeedViewModel mSpeedmodel;
    public FirebaseDatabase mFirebaseDB;
    public DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speed_test);
        mFirebaseDB = FirebaseDatabase.getInstance();
        myRef = mFirebaseDB.getReference("users");

        mSpeedmodel = new ViewModelProvider(this).get(SpeedViewModel.class);
        ObserveSpeedData();
    }

    public void ObserveSpeedData() {
        if(mSpeedmodel.ObserveVehicleSpeed() != null) {
            mSpeedmodel.ObserveVehicleSpeed().observe(this, speed -> {
                if (speed > getOEMVehicleSpeed()) {
                    // Send notification or alert
                    sendAlert();
                }
            });
        }
    }

    public float getOEMVehicleSpeed() {
        final float[] speed = {0.0f};
        // Retrieve data for all users
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    speed[0] = userSnapshot.child("speed").getValue(Float.class);

                    if (speed[0] != 0.0f) {
                        System.out.println("User: " + userId + ", Speed: " + speed[0]);
                    } else {
                        System.out.println("Speed data not available for user: " + userId);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println(error.toException());
            }
        });
        return speed[0];
    }

    private static void sendAlert() {
        // Implement your notification or alert logic here
        System.out.println("Alert: " + "Speed limit exceeded!");
    }
}
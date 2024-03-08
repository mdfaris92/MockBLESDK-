package com.royalenfield.stubsdksample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.royalenfield.mocksdk.EnfieldSDK;
import com.royalenfield.mocksdk.MockSDK;

import java.util.Map;

import kotlinx.coroutines.InternalCoroutinesApi;

@InternalCoroutinesApi
public class SampleActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        TextView speed = (TextView) findViewById(R.id.speed);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        EnfieldSDK.INSTANCE.getBleObject(new EnfieldSDK.enfieldCallback() {
            @Override
            public void onResult(@NonNull Map<EnfieldSDK.KEY, String> result) {

                Log.e("Test --> ", ""+result);

                if(result.get(EnfieldSDK.KEY.SPEED) != null && result.get(EnfieldSDK.KEY.SOC) != null){
                    runOnUiThread(() -> {
                        speed.setText(result.get(EnfieldSDK.KEY.SPEED));
                    });

                    runOnUiThread(() -> {
                        progressBar.setProgress(Integer.parseInt(result.get(EnfieldSDK.KEY.SOC)));
                    });
                }


            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        EnfieldSDK.INSTANCE.stopEmittingValue();
    }
}
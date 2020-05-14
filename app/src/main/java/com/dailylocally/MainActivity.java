package com.dailylocally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dailylocally.ui.onboarding.OnBoardingActivity;

public class MainActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

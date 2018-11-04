package co.za.forecast;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import co.za.forecast.features.GetCurrentLocation;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int INTERNET_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inent = new Intent(SplashScreen.this, GetCurrentLocation.class);
                startActivity(inent);
                finish();
            }
        }, INTERNET_TIME_OUT);

    }
}

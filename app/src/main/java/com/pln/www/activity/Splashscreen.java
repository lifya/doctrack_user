package com.pln.www.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.pln.www.R;

public class Splashscreen extends AppCompatActivity {
    private int SLEEP_TIMER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread {
        public void run() {
            try{
                sleep(1000 * SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
            startActivity(intent);
            Splashscreen.this.finish();
        }
    }
}

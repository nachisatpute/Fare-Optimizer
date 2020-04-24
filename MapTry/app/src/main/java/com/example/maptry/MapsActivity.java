package com.example.maptry;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MapsActivity extends AppCompatActivity {
    ProgressBar pb;
    private Handler handler = new Handler();
    int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=findViewById(R.id.pb1);
        progressStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    progressStatus +=1;
                    try{
                        Thread.sleep(50);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(progressStatus);
                            if(progressStatus==99)
                            {
                                Intent i=new Intent(getApplicationContext(),Activity2.class);
                                startActivity(i);
                            }
                        }
                    });
                }
            }
        }).start();
    }
}


package com.example.maptry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    Button b;
    EditText name,range;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        b=findViewById(R.id.bt1);
        name=findViewById(R.id.edt1);
        range=findViewById(R.id.edt2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Activity2.this,Hotspot.class);
                i.putExtra("range",range.getText().toString());
                i.putExtra("id",1);
                startActivity(i);
            }
        });


    }
}

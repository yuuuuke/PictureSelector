package com.zwp.homework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zwp.homework.R;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PIC_CODE = 0x1001;

    private Button mBtnSelect;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnSelect = findViewById(R.id.btn_select);
        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectPicActivity.class);
                startActivityForResult(intent, SELECT_PIC_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

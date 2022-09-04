package com.zwp.homework.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zwp.homework.R;
import com.zwp.homework.bean.PicItem;

import java.util.ArrayList;

import static com.zwp.homework.activity.SelectPicActivity.SELECTED_PIC;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PIC_CODE = 0x1001;
    private static final int REQUEST_PERMISSION = 10086;

    private Button mBtnSelect;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnSelect = findViewById(R.id.btn_select);
        mTvResult = findViewById(R.id.tv_result);
        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int needPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (needPermission == PackageManager.PERMISSION_GRANTED) {
                    toSelectPic();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PIC_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<PicItem> list = (ArrayList<PicItem>) data.getSerializableExtra(SELECTED_PIC);
                StringBuilder result = new StringBuilder();
                for (PicItem item : list) {
                    result.append(item.getUrl() + "\n");
                }
                mTvResult.setText(result);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "请去设置打开读取设备存储权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "读取设备相册需要此权限，请打开此权限", Toast.LENGTH_SHORT).show();
                }
            } else {
                toSelectPic();
            }
        }
    }

    private void toSelectPic() {
        Intent intent = new Intent(MainActivity.this, SelectPicActivity.class);
        startActivityForResult(intent, SELECT_PIC_CODE);
    }
}

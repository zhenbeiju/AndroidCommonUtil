package com.zhenbeiju.commanutil.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import commanutil.base.BaseActivity;
import commanutil.view.DialogInfo;

public class MainActivity extends BaseActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);

    }

    @Override
    protected void onResume() {
        super.onResume();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInfo.showPickNumberDialog(v.getContext(), -100, 100, new DialogInfo.IOnPositionGot() {
                    @Override
                    public void onPositonGet(int position) {

                    }
                }, "", "");
            }
        });
    }
}

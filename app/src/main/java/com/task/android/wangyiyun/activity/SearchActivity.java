package com.task.android.wangyiyun.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.task.android.wangyiyun.R;

public class SearchActivity extends AppCompatActivity {
    private Button search_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        search_bt=findViewById(R.id.search_bt);
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.jnu.itime2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jnu.itime2.ui.ColorPickerView;

public class Color3Activity extends AppCompatActivity {
    private LinearLayout ll;
    private TextView tv;
    private ColorPickerView colorPickerView;
    public static final int CHANGED = 111;
    private ActionBar actionBar;
    ColorDrawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color3);

        actionBar=getSupportActionBar();
        ll = (LinearLayout) findViewById(R.id.ll_color);
        tv = (TextView) findViewById(R.id.tv_info);

        colorPickerView = new ColorPickerView(this);
        ll.addView(colorPickerView);

        int color=getIntent().getIntExtra("color",0);
        drawable= new ColorDrawable(color);
        actionBar.setBackgroundDrawable(drawable);
        colorPickerView.setOnColorBackListener(new ColorPickerView.OnColorBackListener() {
            @Override
            public void onColorBack(int a, int r, int g, int b) {
                tv.setText("R：" + r + "\nG：" + g + "\nB：" + b + "\n" + colorPickerView.getStrColor());
                tv.setTextColor(Color.argb(a, r, g, b));
                Intent intent = new Intent();
                intent.putExtra("color",Color.rgb(r,g,b));
                setResult(CHANGED, intent);
                 drawable= new ColorDrawable(Color.rgb(r,g,b));
                actionBar.setBackgroundDrawable(drawable);
            }
        });
    }

}

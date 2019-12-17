package com.jnu.itime2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.itime2.ui.ITime;

import java.text.ParseException;
import java.util.Date;


public class EditTime extends AppCompatActivity {


    public static final int RESULT_DELETE = 999;
    private EditText editTextTitle, editTextPS,editTime;
    private Button buttonOk, buttonCancel,buttonDelete;
    private ImageButton buttoncreate;
    private TextView t3,t2;
    private int editPosition,color;
    private int mday,mhour,mmin,msec,myear,mmonth;
    private ConstraintLayout editlayout;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time);

        t2= (TextView) findViewById(R.id.textView2);
        t3= (TextView) findViewById(R.id.textView3);
        editTime =  (EditText) findViewById(R.id.edit_text_time);
        editPosition = getIntent().getIntExtra("edit_position", 0);
        editTextTitle = (EditText) findViewById(R.id.edit_text_good_name);
        editTextPS = (EditText) findViewById(R.id.edit_text_good_price);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonOk = (Button) findViewById(R.id.button_ok);
        buttoncreate = (ImageButton) findViewById(R.id.button_create);
        editlayout=(ConstraintLayout)findViewById(R.id.Editlayout);
        buttonDelete=(Button) findViewById(R.id.button_delete);

        final String goodPrice = getIntent().getStringExtra("good_price");
        final String goodName = getIntent().getStringExtra("good_name");
         mday= getIntent().getIntExtra("good_year",0);
        mmonth = getIntent().getIntExtra("good_month",0);
        mday = getIntent().getIntExtra("good_day",0);
        color=getIntent().getIntExtra("color",0);

        final int New=getIntent().getIntExtra("NEW",0);


        ActionBar actionBar = getSupportActionBar();
        editlayout.setBackgroundColor(color);
        ColorDrawable drawable = new ColorDrawable(color);
        actionBar.setBackgroundDrawable(drawable);

        EditTime.this.editTime.setText(mday + "-" + mmonth + "-" + mday);



        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(New==1) {
                    Intent intent = new Intent();
                    intent.putExtra("edit_position", editPosition);
                    setResult(RESULT_DELETE, intent);
                    EditTime.this.finish();
                }
                if(New==6) {
                    Toast.makeText(EditTime.this, "删除成功！", Toast.LENGTH_SHORT).show();
                    EditTime.this.finish();
                }
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                     intent.putExtra("good_year",myear);
                    intent.putExtra("good_month",mmonth);
                    intent.putExtra("good_day",mday);

                    intent.putExtra("edit_position", editPosition);
                    intent.putExtra("good_name", editTextTitle.getText().toString().trim());
                    intent.putExtra("good_price", editTextPS.getText().toString());
                    intent.putExtra("good_time", editTime.getText().toString().trim());
                Calendar calendar = Calendar.getInstance();
                int day = mday-calendar.get(Calendar.DAY_OF_MONTH);
                intent.putExtra("cha", day);

                setResult(RESULT_OK, intent);
                EditTime.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTime.this.finish();
            }
        });


        editTextTitle.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    t2.setVisibility(View.GONE);
                    // 此处为得到焦点时的处理内容
                } else {
                    String serch_textip=editTextTitle.getText().toString().trim();
                    if(serch_textip.length()==0)
                        t2.setVisibility(View.VISIBLE);
                    // 此处为失去焦点时的处理内容
                }
            }
        });


        editTextPS.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    t3.setVisibility(View.GONE);
                    // 此处为得到焦点时的处理内容
                } else {
                    String serch_textip=editTextPS.getText().toString().trim();
                    if(serch_textip.length()==0)
                        t3.setVisibility(View.VISIBLE);
                    // 此处为失去焦点时的处理内容
                }
            }
        });





		editTime.setOnTouchListener(new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                showDatePickDlg();
                return true;
            }
            return false;
        }
    });
        editTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                showDatePickDlg();
            }
        }
    });
}

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditTime.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear=monthOfYear+1;
                EditTime.this.editTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                myear=year;
                mmonth=monthOfYear;
                mday=dayOfMonth;
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


}



package com.jnu.itime2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;



import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jnu.itime2.ui.ITime;
import com.jnu.itime2.ui.TimeFragmentAdapter;
import com.jnu.itime2.ui.TimeListFragment;
import com.jnu.itime2.ui.TimeSaver;
import com.jnu.itime2.ui.home.HomeFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Color.valueOf;


public class MainActivity extends AppCompatActivity {
    public static final int CONTEXT_MENU_ITEM_NEW = 1;
    public static final int CONTEXT_MENU_ITEM_UPDATE= CONTEXT_MENU_ITEM_NEW+1;
    public static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;
    public static final int CONTEXT_MENU_ITEM_ABOUT = CONTEXT_MENU_ITEM_DELETE+1;
    public static final int REQUEST_CODE_NEW_GOOD = 901;
    public static final int REQUEST_CODE_UPDATE_GOOD = 902;
    public static final int CHANGED = 111;
    private static final int REQUEST_CODE_COUNT_TIME = 903;
    public static MainActivity instance = null;
    private AppBarConfiguration mAppBarConfiguration;
    private List<ITime> listITime =new ArrayList<>();
   private TimeArrayAdapter ArrayAdapter;
   private TimeSaver timeSaver;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;

    private Toolbar toolbar;
    int colorr;
    CollapsingToolbarLayout collapsing_toolbar_layout ;
    AppBarLayout app_bar_layout;
    public static final String TAG = "ModeActivity";


    // 倒计时




    private  ImageView tou;
//


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeSaver.save();

    }

    @Override
    protected void onStop() {
        super.onStop();
        timeSaver.save();
    }


//
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//-i

        tou=findViewById(R.id.tou);



        app_bar_layout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        collapsing_toolbar_layout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        initView();
            InitData();
        colorr=Color.parseColor("#009688");

        fab=findViewById(R.id.fab);
        fab2=findViewById(R.id.floatingActionButton2);
        toolbar=findViewById(R.id.toolbar);

       ArrayAdapter=new TimeArrayAdapter
               (MainActivity.this,R.layout.list_view_time, listITime);
        TimeFragmentAdapter myPageAdapter = new TimeFragmentAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new TimeListFragment(ArrayAdapter));


        myPageAdapter.setData(datas);
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("闹钟");
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
// 将适配器设置进ViewPager
        viewPager.setAdapter(myPageAdapter);
// 将ViewPager与TabLayout相关联
        tabLayout.setupWithViewPager(viewPager);


////
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditTime.class);
                intent.putExtra("edit_position",0);
                intent.putExtra("color",colorr);
                startActivityForResult(intent, REQUEST_CODE_NEW_GOOD);

            }
        });
        FloatingActionButton fab2 = findViewById(R.id.floatingActionButton2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Color3Activity.class);
                intent.putExtra("color",colorr);
                startActivityForResult(intent, REQUEST_CODE_NEW_GOOD);

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//______
@RequiresApi(api = Build.VERSION_CODES.N)
private void InitData() {
    timeSaver=new TimeSaver(this);
    listITime =timeSaver.load();
    if(listITime.size()==0) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.SECOND)%3;
        switch (day)
        {
            case 0:listITime.add(new ITime("目前暂时没有闹钟", "暂无", R.mipmap.a,"无预设时间",0,0,0,0));break;
            case 1:listITime.add(new ITime("目前暂时没有闹钟", "暂无", R.mipmap.b,"无预设时间",0,0,0,0));break;
            case 2:listITime.add(new ITime("目前暂时没有闹钟", "暂无", R.mipmap.c,"无预设时间",0,0,0,0));break;
        }

    }

}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==this.findViewById(R.id.list_view_goods)) {
            int itemPosition=((AdapterView.AdapterContextMenuInfo)menuInfo).position;
            menu.setHeaderTitle(listITime.get(itemPosition).getName());
            menu.add(0, CONTEXT_MENU_ITEM_NEW, 0, "新建");
            menu.add(0, CONTEXT_MENU_ITEM_UPDATE, 0, "修改");
            menu.add(0, CONTEXT_MENU_ITEM_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_ITEM_ABOUT, 0, "查看倒计时");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case CONTEXT_MENU_ITEM_NEW: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                Intent intent = new Intent(MainActivity.this,EditTime.class);
                intent.putExtra("edit_position",menuInfo.position);
                intent.putExtra("color",colorr);
                startActivityForResult(intent, REQUEST_CODE_NEW_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_UPDATE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                ITime good= listITime.get(menuInfo.position);

                Intent intent = new Intent(MainActivity.this,EditTime.class);
                intent.putExtra("edit_position",menuInfo.position);
                intent.putExtra("good_year",good.getYear());
                intent.putExtra("good_day",good.getDay());
                intent.putExtra("good_month",good.getMonth());
                intent.putExtra("good_price",good.getPrice());
                intent.putExtra("good_name",good.getName());
                intent.putExtra("color",colorr);

                startActivityForResult(intent, REQUEST_CODE_UPDATE_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_DELETE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final int itemPosition=menuInfo.position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("询问")
                        .setMessage("你确定要删除这条吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listITime.remove(itemPosition);
                                ArrayAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
                break;
            }
            case CONTEXT_MENU_ITEM_ABOUT: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                ITime good= listITime.get(menuInfo.position);

                Intent intent = new Intent(MainActivity.this,CountActivity.class);
                intent.putExtra("edit_position",menuInfo.position);
                intent.putExtra("good_year",good.getYear());
                intent.putExtra("good_day",good.getDay());
                intent.putExtra("good_month",good.getMonth());
                intent.putExtra("good_price",good.getPrice());
                intent.putExtra("good_name",good.getName());
                intent.putExtra("good_time",good.getTime());
                intent.putExtra("color",colorr);
                startActivityForResult(intent, REQUEST_CODE_COUNT_TIME);
                break;
            }
        }
        return super.onContextItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_NEW_GOOD:
                if(resultCode==RESULT_OK)
                {

                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");
                    String price =data.getStringExtra("good_price");
                    String time=data.getStringExtra("good_time");
                    int year=data.getIntExtra("good_year",0);
                    int day=data.getIntExtra("good_day",0);
                    int month=data.getIntExtra("good_month",0);
                    int cha=data.getIntExtra("cha",0);



                    Calendar calendar = Calendar.getInstance();
                    int day2 = calendar.get(Calendar.SECOND)%3;
                    switch (day2)
                    {
                        case 0:listITime.add(position, new ITime(name, price, R.mipmap.a,time,year,month,day,cha));tou.setImageResource(R.mipmap.a);break;
                        case 1:listITime.add(position, new ITime(name, price, R.mipmap.b,time,year,month,day,cha));tou.setImageResource(R.mipmap.b);break;
                        case 2:listITime.add(position, new ITime(name, price, R.mipmap.c,time,year,month,day,cha));tou.setImageResource(R.mipmap.c);break;
                    }

                    ArrayAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();

                }
                else if(resultCode==CHANGED)
                {
                    colorr = data.getIntExtra("color",-1);

                    fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorr));
                    fab2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorr));
                    toolbar.setBackgroundColor(colorr);
                    collapsing_toolbar_layout.setContentScrimColor(colorr);


                }
                break;
            case REQUEST_CODE_UPDATE_GOOD:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");
                    String price =data.getStringExtra("good_price");
                    String time=data.getStringExtra("good_time");

                    int year=data.getIntExtra("good_year",0);
                    int day=data.getIntExtra("good_day",0);
                    int month=data.getIntExtra("good_month",0);
                    int cha=data.getIntExtra("cha",0);

                    ITime good= listITime.get(position);
                    good.setName(name);
                    good.setPrice(price);
                    good.setTime(time);
                    good.setyear(year);
                    good.setDay(day);
                    good.setMonth(month);
                    good.setpictureTime(cha);

                    ArrayAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
            case REQUEST_CODE_COUNT_TIME:
            {

            }

            break;
        }
    }

    public class TimeArrayAdapter extends ArrayAdapter<ITime>
    {
        private  int resourceId;
        public TimeArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ITime> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_good);
            TextView name = (TextView)item.findViewById(R.id.text_view_name);
            TextView price = (TextView)item.findViewById(R.id.text_view_price);
            TextView time = (TextView)item.findViewById(R.id.text_view_time);
            TextView picturetime = (TextView)item.findViewById(R.id.textView6);


            ITime ITime_item = this.getItem(position);
            img.setImageResource(ITime_item.getPictureId());
            name.setText("标题："+ ITime_item.getName());
            price.setText("备注："+ ITime_item.getPrice());
            time.setText("计时："+ ITime_item.getTime());
            picturetime.setText("剩余："+ ITime_item.getpictureTime()+"天");


            return item;
        }
    }


    private void initView()
    {


        collapsing_toolbar_layout.setTitle("ITIME!!!!");
        collapsing_toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsing_toolbar_layout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE);


        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e(TAG,"appBarLayoutHeight:"+appBarLayout.getHeight()+" getTotalScrollRange:"+appBarLayout.getTotalScrollRange()+" offSet:"+verticalOffset);
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    collapsing_toolbar_layout.setTitle("点击此处下拉ITIME");


                }else{
                    collapsing_toolbar_layout.setTitle("ITIME!!!!");
                }
            }
        });


    }


}

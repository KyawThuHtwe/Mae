package com.cu.mae;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    List<PagerData> pagerData;
    Integer[] colors=null;
    ArgbEvaluator argbEvaluator=new ArgbEvaluator();
    LinearLayout main;
    TextView date,mae,rule,mae_date1,mae_date2,website,mae_time;
    ImageView fullScreen;
    LinearLayout view,bottom;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    boolean res=true;
    private static final int FOREGROUND_PERMISSION_CODE = 101;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        MDetect.INSTANCE.init(this);
        date=findViewById(R.id.date);
        mae=findViewById(R.id.mae);
        rule=findViewById(R.id.rule);
        view=findViewById(R.id.view);
        bottom=findViewById(R.id.bottom);
        mae_date1=findViewById(R.id.mae_date1);
        mae_date2=findViewById(R.id.mae_date2);
        mae_time=findViewById(R.id.mae_time);
        fullScreen=findViewById(R.id.fullScreen);
        website=findViewById(R.id.website);
        pagerData = new ArrayList<>();
        pagerData.add(new PagerData(R.drawable.ma1, "၁"));
        pagerData.add(new PagerData(R.drawable.ma2, "၂"));
        pagerData.add(new PagerData(R.drawable.ma3, "၃"));
        pagerData.add(new PagerData(R.drawable.ma4, "၄"));
        pagerData.add(new PagerData(R.drawable.ma5, "၅"));
        pagerData.add(new PagerData(R.drawable.ma6, "၆"));

        viewPagerAdapter = new ViewPagerAdapter(pagerData, getApplicationContext());
        viewPager = findViewById(R.id.viewPager);
        main = findViewById(R.id.main);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPadding(30, 0, 30, 10);
        colors = new Integer[]{getResources().getColor(R.color.color1), getResources().getColor(R.color.color2), getResources().getColor(R.color.color3),getResources().getColor(R.color.color4),getResources().getColor(R.color.color5),getResources().getColor(R.color.color6)};
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (viewPagerAdapter.getCount() - 1) && position < (colors.length - 1)) {
                    main.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                    getWindow().setNavigationBarColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                    getWindow().setStatusBarColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                } else {
                    main.setBackgroundColor(colors[colors.length - 1]);
                    getWindow().setNavigationBarColor(colors[colors.length - 1]);
                    getWindow().setStatusBarColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Calendar cal = Calendar.getInstance();
        long year=cal.get(Calendar.YEAR);
        long month=cal.get(Calendar.MONTH)+1;
        long day=cal.get(Calendar.DAY_OF_MONTH);
        long ans=0;

        if(year==2020){
            if(month==11){
                ans=8-day;
            }else if(month<11){
                day=31-day;
                ans=8+day;
            }
        }
        String content="ဆႏၵမဲေပးရန္ "+change(ans+"")+" ရက္သာ လိုပါေတာ့သည္";
        if(ans==0){
            content="ယေန႔သည္ ဆႏၵမဲ ေပးရမည့္ေန႔ ျဖစ္သည္";
        }
        if(ans<0){
            content="";
        }
        if (MDetect.INSTANCE.isUnicode()) {
            content= Rabbit.zg2uni(content);
            mae.setText(Rabbit.zg2uni(mae.getText().toString()));
            rule.setText(Rabbit.zg2uni(rule.getText().toString()));
            mae_time.setText(Rabbit.zg2uni(mae_time.getText().toString()));
            mae_date1.setText(Rabbit.zg2uni(mae_date1.getText().toString()));
            mae_date2.setText(Rabbit.zg2uni(mae_date2.getText().toString()));
        }
        date.setText(content);

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(res){
                    viewPager.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
                    fullScreen.setImageResource(R.drawable.fullscreen_exit);
                    main.setBackgroundColor(colors[colors.length - 1]);
                    getWindow().setNavigationBarColor(colors[colors.length - 1]);
                    getWindow().setStatusBarColor(colors[colors.length - 1]);
                    res=false;
                }else {
                    viewPager.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.VISIBLE);
                    fullScreen.setImageResource(R.drawable.fullscreen);
                    res=true;
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                    main.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse("https://www.uec.gov.mm"));
                startActivity(i);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            checkPermission(Manifest.permission.FOREGROUND_SERVICE,FOREGROUND_PERMISSION_CODE);
        }
    }
    public String change(String str){
        StringBuilder change_str = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '0') {
                change_str.append("၀");
            } else if (str.charAt(i) == '1') {
                change_str.append("၁");
            } else if (str.charAt(i) == '2') {
                change_str.append("၂");
            } else if (str.charAt(i) == '3') {
                change_str.append("၃");
            } else if (str.charAt(i) == '4') {
                change_str.append("၄");
            } else if (str.charAt(i) == '5') {
                change_str.append("၅");
            } else if (str.charAt(i) == '6') {
                change_str.append("၆");
            } else if (str.charAt(i) == '7') {
                change_str.append("၇");
            } else if (str.charAt(i) == '8') {
                change_str.append("၈");
            } else if (str.charAt(i) == '9') {
                change_str.append("၉");
            }else if (str.charAt(i) == '.') {
                break;
            }
        }
        return change_str.toString();
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        startService();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        if(viewPager.getVisibility()==View.VISIBLE){
            viewPager.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            fullScreen.setImageResource(R.drawable.fullscreen);
            res=true;
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            main.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }else {
            super.onBackPressed();
        }
    }
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permission)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
            }
        }
        else {
            //Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FOREGROUND_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "FOREGROUND Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "FOREGROUND Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
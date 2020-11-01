package com.cu.mae;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

    TextView today,mae,reminder,gotIt;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_alarm);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();
        ringtone.setLooping(true);
        reminder=findViewById(R.id.remind);
        gotIt=findViewById(R.id.gotIt);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtone.stop();
                setAlarmReceive();
                finish();
            }
        });
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtone.stop();
                setAlarmReceive();
                stopService();
                finish();
            }
        });
        today=findViewById(R.id.today);
        mae=findViewById(R.id.mae);
        if (MDetect.INSTANCE.isUnicode()) {
            today.setText(Rabbit.zg2uni(today.getText().toString()));
            mae.setText(Rabbit.zg2uni(mae.getText().toString()));
        }
    }
    public void setAlarmReceive(){
        SharedPreferences preferences=getSharedPreferences("Receive", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("Receive",true).apply();
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        stopService(serviceIntent);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Exit Click",Toast.LENGTH_SHORT).show();
    }
}
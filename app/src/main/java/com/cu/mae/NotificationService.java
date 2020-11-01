package com.cu.mae;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;

import static com.cu.mae.MainActivity.NOTIFICATION_CHANNEL_ID;

public class NotificationService extends Service {

    HandlerThread handlerThread;
    Handler handler;
    MyTimerRunnable runnable;
    int count=0;

    @Override
    public void onCreate() {
        handlerThread=new HandlerThread("MyBackgroundThread");
        handlerThread.start();
        handler=new Handler(handlerThread.getLooper());
        runnable=new MyTimerRunnable();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //do heavy work on a background thread
        //stopSelf();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,2000);
        return START_NOT_STICKY;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        handlerThread.quitSafely();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyTimerRunnable implements Runnable{
        @Override
        public void run() {
            try {
                if(isReceive()){
                    count++;
                }

                MDetect.INSTANCE.init(getApplicationContext());
                Calendar cal = Calendar.getInstance();
                long year = cal.get(Calendar.YEAR);
                long month = cal.get(Calendar.MONTH) + 1;
                long day = cal.get(Calendar.DAY_OF_MONTH);
                long ans=0;
                if (year == 2020 && month == 11 && day == 8) {
                    if(cal.getTime().getHours()>=6 && cal.getTime().getMinutes()>=0){
                        if(isReceive()){
                            if(count==20){
                                count=0;
                                removeAlarmReceive();
                                startActivity(new Intent(getApplicationContext(), AlarmActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        }
                    }
                }
                if(year==2020){
                    if(month==11){
                        ans=8-day;
                    }else if(month<11){
                        day=31-day;
                        ans=8+day;
                    }
                }
                String content = "ဆႏၵမဲေပးရန္ " + change(ans + "") + " ရက္သာ လိုပါေတာ့သည္";
                String title="ႏိုဝင္ဘာလ ၈ရက္ေန႔သည္ ဆႏၵမဲေပးရမည့္ ေန႔ျဖစ္သည္";

                if(ans==0){
                    content="ယေန႔သည္ ဆႏၵမဲ ေပးရမည့္ေန႔ ျဖစ္သည္";
                }
                if (MDetect.INSTANCE.isUnicode()) {
                    title = Rabbit.zg2uni(title);
                    content = Rabbit.zg2uni(content);
                }
                if(year<=2020 && month<=11 & day<=8 && ans>=0){
                    Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), "10001")
                            .setContentTitle(title)
                            .setContentText(content)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setOngoing(true)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setContentIntent(pendingIntent).build();
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notification.flags |= Notification.FLAG_SHOW_LIGHTS;

                    if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                        int importance = NotificationManager. IMPORTANCE_HIGH ;
                        NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                        NotificationManager notificationManager=getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(notificationChannel) ;
                    }

                    startForeground(1, notification);
                }


            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            handler.postDelayed(this,2000);
        }
    }
    public void removeAlarmReceive(){
        SharedPreferences preferences=getSharedPreferences("Receive",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("Receive",false).apply();
    }
    public boolean isReceive(){
        SharedPreferences preferences=getSharedPreferences("Receive",Context.MODE_PRIVATE);
        return preferences.getBoolean("Receive",true);
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
}
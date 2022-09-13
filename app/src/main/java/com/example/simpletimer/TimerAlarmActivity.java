package com.example.simpletimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class TimerAlarmActivity extends AppCompatActivity {

    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_alarm);

        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, alarm);
        if (ringtone == null) {
            alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(this, alarm);
        }
        if (ringtone != null){
            ringtone.play();
        }
    }

    @Override
    protected void onDestroy() {
        if(ringtone!=null&& ringtone.isPlaying()){
            ringtone.stop();
        }
        super.onDestroy();
    }
}
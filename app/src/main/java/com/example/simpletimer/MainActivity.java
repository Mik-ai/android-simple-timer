package com.example.simpletimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView leverView = (ImageView) findViewById(R.id.timer_lever);

        leverView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                timerTouchHandle(leverView, motionEvent);
                return false;
            }
        });
    }

    private void timerTouchHandle(View view, MotionEvent motionEvent) {
        Intent alarmIntent = new Intent(this, TimerAlarmActivity.class);

        float pivotX = view.getPivotX();
        float pivotY = view.getPivotY();

        float[] ca = {pivotX - pivotX, pivotY - pivotY};
        float[] cb = {motionEvent.getX() - pivotX, motionEvent.getY() - pivotY};

        Log.i("viewCoords ", String.valueOf(pivotX) + " - " + String.valueOf(pivotY));
        Log.i("touchCoord", String.valueOf(motionEvent.getX()) + " - " + String.valueOf(motionEvent.getY()));

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int angle = (int) (motionEvent.getX() < pivotX ? 180 + Math.toDegrees(getAngle(ca, cb)) : 180 - Math.toDegrees(getAngle(ca, cb)));

            Log.i("angleFunc", String.valueOf(angle));
            int minutes = Math.round(angle / 6);
            Log.i("minutes", String.valueOf(minutes));

            view.startAnimation(timerRotation(minutes, angle));

            CountDownTimer countDownTimer = new CountDownTimer(minutes * 1000, 100) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    startActivity(alarmIntent);
                }
            }.start();

        }
    }

    private RotateAnimation timerRotation(int minutes, int angle) {
        RotateAnimation rotate = new RotateAnimation(angle, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new LinearInterpolator());

        rotate.setDuration(minutes * 1000);

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return rotate;
    }

    private double getAngle(float a[], float b[]) {
        double tx = b[0] - a[0], ty = b[1] - a[1];
        double t_length = Math.sqrt(tx * tx + ty * ty);
        return Math.acos(ty / t_length);
    }
}
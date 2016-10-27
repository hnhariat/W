package com.iey.w;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 1100160 on 2016. 10. 25..
 */
public class ScreenWathcer extends Service implements TextToSpeech.OnInitListener {

    public TextToSpeech speech;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.d("hatti.br", Intent.ACTION_SCREEN_ON);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                Log.d("hatti.br", "br");
                speak();
            }
        }

    };

    private void speak() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        String date = getString(R.string.str_date, dateFormat.format(new Date()));

        int amPm = Calendar.getInstance().get(Calendar.AM_PM);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int day = Calendar.getInstance().get(Calendar.DATE);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        long prevTime = PreferenceManager.getInstance(this).getLong("time");
        Calendar prevDate = Calendar.getInstance();
        prevDate.setTimeInMillis(prevTime);
        if (prevDate.get(Calendar.YEAR) == year &&
                prevDate.get(Calendar.MONTH) == month &&
                prevDate.get(Calendar.DATE) == day) {
            return;
        }
        if (amPm == Calendar.AM && hour >= 7 && hour < 9) {
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int amStreamMusicMaxVol = am.getStreamMaxVolume(am.STREAM_MUSIC);
            am.setStreamVolume(am.STREAM_MUSIC, (int) (amStreamMusicMaxVol * 0.4f), 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                speech.speak(date, TextToSpeech.QUEUE_FLUSH, null, "");
            } else {
                speech.speak(date, TextToSpeech.QUEUE_FLUSH, null);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Notification notification = builder.setContentTitle("아침입니다")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .build();

            NotificationManagerCompat nm = NotificationManagerCompat.from(getApplicationContext());
            nm.cancel(0);
            nm.notify(0, notification);
            PreferenceManager.getInstance(this).putLong("time", System.currentTimeMillis());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("hatti.service", "start");
        speech = new TextToSpeech(this, this);
        speech.setPitch(0.9f);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mReceiver, filter);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            speak();
        }
    }
}

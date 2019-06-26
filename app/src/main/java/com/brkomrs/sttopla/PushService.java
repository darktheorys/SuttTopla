package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.brkomrs.sttopla.necessary.helperFunctions;

import java.util.Timer;
import java.util.TimerTask;

public class PushService extends Service {

    Context context ;
    Notification notification;
    Timer timer;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        Toast.makeText(this, "Servis Çalıştı.Bu Mesaj Servis Class'dan", Toast.LENGTH_LONG).show();

        timer = new Timer();
        timer.schedule(new TimerTask() {  //every 10 minutes service do task.
            @Override
            public void run() {
                helperFunctions.sendPost(((dbHelper) getApplication()).getDaoSession()) ;
            }

        }, 0, 600000);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {//Servis stopService(); metoduyla durdurulduğunda çalışır
        timer.cancel();
        Toast.makeText(this, "Servis Durduruldu.Bu Mesaj Servis Class'dan", Toast.LENGTH_LONG).show();
    }
}

package com.qendel.fast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MyBroudCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if(intent.getAction().equalsIgnoreCase("com.example.Broadcast.fast")){
            NewMessageNotification notification = new NewMessageNotification();
            notification.notify(context,bundle.getString("title"),"","تم اضافة اشغار جديد",124);
        }


    }
}

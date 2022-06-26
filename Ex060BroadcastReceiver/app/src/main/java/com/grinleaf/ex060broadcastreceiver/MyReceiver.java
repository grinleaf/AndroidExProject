package com.grinleaf.ex060broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) { //(운영체제 능력 사용을 위한 context, 방송을 받았을 때 메시지를 담은 intent)
        Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();
    }
}

package com.android.tvremoteime;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zxt.dlna.dmp.RenderPlayerEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends Activity implements View.OnClickListener {

    private EditText dlnaNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlnaNameText = this.findViewById(R.id.etDLNAName);

        dlnaNameText.setText(DLNAUtils.getDLNANameSuffix(this.getApplicationContext()));

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DLNAUtils.setDLNANameSuffix(this, dlnaNameText.getText().toString());
    }


    @Override
    public void onClick(View v) {

    }

    @Subscribe(sticky = true)
    public void onEvent(RenderPlayerEvent event){
        Toast.makeText(this, event.getName(), Toast.LENGTH_SHORT).show();
    }

}

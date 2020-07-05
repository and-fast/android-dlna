package com.android.tvremoteime;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.tvremoteime.utils.BackgroundPopupUtil;
import com.zxt.dlna.dmp.RenderPlayerEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;


public class MainActivity extends Activity implements View.OnClickListener {

    private EditText dlnaNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlnaNameText = this.findViewById(R.id.etDLNAName);

        dlnaNameText.setText(DLNAUtils.getDLNANameSuffix(this.getApplicationContext()));

        EventBus.getDefault().register(this);

//        if (!canBackgroundStart(this)){
//            BackgroundPopupUtil.gotoAppDetailIntent(this);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        DLNAUtils.setDLNANameSuffix(this, dlnaNameText.getText().toString());

        //Toast.makeText(this, String.valueOf(canBackgroundStart(this)), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {

    }

    @Subscribe(sticky = true)
    public void onEvent(RenderPlayerEvent event){
        Toast.makeText(this, event.getName(), Toast.LENGTH_SHORT).show();
    }


    private boolean canBackgroundStart(Context context) {
        AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            //int op = 10021; // >= 23
            Method method = ops.getClass().getMethod("checkOpNoThrow", int.class, int.class, String.class);
            Integer result = (Integer) method.invoke(ops, 10021, android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

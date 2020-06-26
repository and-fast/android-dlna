package com.android.tvremoteime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity implements View.OnClickListener {

    private EditText dlnaNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlnaNameText = this.findViewById(R.id.etDLNAName);

        dlnaNameText.setText(DLNAUtils.getDLNANameSuffix(this.getApplicationContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        DLNAUtils.setDLNANameSuffix(this.getApplicationContext(), dlnaNameText.getText().toString());
    }


    @Override
    public void onClick(View v) {

    }
}

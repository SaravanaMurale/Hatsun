package com.sosaley.hatsun.menu;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.utils.AppConstant;
import com.sosaley.hatsun.utils.PermissionUtils;


public class QRDisplayActivity extends AppCompatActivity {

    public static Button btnScanQR,edit,update,sync;
    public static TextView displayQR;

   public static TextView clientName,plantName,batteryRoomNo,upsNo,rackNo,slaveNo,slaveType;

   public static RelativeLayout qrDisplayBlock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_display);

        btnScanQR=(Button) findViewById(R.id.btnScanQR);
        displayQR=(TextView)findViewById(R.id.displayQR);

        qrDisplayBlock=(RelativeLayout)findViewById(R.id.qrDisplayBlock);

        edit=(Button)findViewById(R.id.edit);
        update=(Button)findViewById(R.id.update);
        sync=(Button)findViewById(R.id.sync);

        clientName=(TextView)findViewById(R.id.clientName);
        plantName=(TextView)findViewById(R.id.plantName);
        batteryRoomNo=(TextView)findViewById(R.id.battery);
        upsNo=(TextView)findViewById(R.id.ups);
        rackNo=(TextView)findViewById(R.id.rack);
        slaveNo=(TextView)findViewById(R.id.slave);
        slaveType=(TextView)findViewById(R.id.slaveType);


        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PermissionUtils.hasPermission(QRDisplayActivity.this, Manifest.permission.CAMERA)) {


                    PermissionUtils.requestPermissions(QRDisplayActivity.this, new String[]{Manifest.permission.CAMERA}, AppConstant.CAMERA_ACCESS);

                } else {
                    callActivity();
                }


            }
            });




    }

    private void callActivity() {

        Intent intent=new Intent(QRDisplayActivity.this,QRScannerActivity.class);
        startActivity(intent);

    }
}
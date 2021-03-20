package com.sosaley.hatsun.menu;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.authentication.SigninActivity;
import com.sosaley.hatsun.utils.AppConstant;
import com.sosaley.hatsun.utils.PermissionUtils;
import com.sosaley.hatsun.utils.PreferencesUtil;


public class QRDisplayActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static Button btnScanQR, edit, update, sync;
    public static TextView displayQR;

    public static TextView clientName, plantName, batteryRoomNo, upsNo, rackNo, slaveNo, slaveType;

    public static RelativeLayout qrDisplayBlock;

    ImageView menuIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_display);

        btnScanQR = (Button) findViewById(R.id.btnScanQR);
        displayQR = (TextView) findViewById(R.id.displayQR);

        menuIcon = (ImageView) findViewById(R.id.menuIcon);

        qrDisplayBlock = (RelativeLayout) findViewById(R.id.qrDisplayBlock);

        edit = (Button) findViewById(R.id.edit);
        update = (Button) findViewById(R.id.update);
        sync = (Button) findViewById(R.id.sync);

        clientName = (TextView) findViewById(R.id.clientName);
        plantName = (TextView) findViewById(R.id.plantName);
        batteryRoomNo = (TextView) findViewById(R.id.battery);
        upsNo = (TextView) findViewById(R.id.ups);
        rackNo = (TextView) findViewById(R.id.rack);
        slaveNo = (TextView) findViewById(R.id.slave);
        slaveType = (TextView) findViewById(R.id.slaveType);

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncScannedDataWithServer();
            }
        });


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


        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(QRDisplayActivity.this, view);
                popupMenu.setOnMenuItemClickListener(QRDisplayActivity.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();
            }
        });
    }

    private void syncScannedDataWithServer() {

        String client_Name=clientName.getText().toString();

        System.out.println("ClientNameInQRDisplay"+client_Name);



    }

    private void callActivity() {

        Intent intent = new Intent(QRDisplayActivity.this, QRScannerActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:
                Toast.makeText(QRDisplayActivity.this, "Profile", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(QRDisplayActivity.this,ProfileActivity.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                PreferencesUtil.clearAll(QRDisplayActivity.this);
                Intent signin=new Intent(QRDisplayActivity.this, SigninActivity.class);
                startActivity(signin);
                finish();
                //Toast.makeText(QRDisplayActivity.this, "Logout", Toast.LENGTH_LONG).show();
                return true;

            default:
                return false;
       }
    }
}
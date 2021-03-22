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
import com.sosaley.hatsun.model.BaseDTO;
import com.sosaley.hatsun.model.ValidateBatteryDTO;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;
import com.sosaley.hatsun.utils.AppConstant;
import com.sosaley.hatsun.utils.PermissionUtils;
import com.sosaley.hatsun.utils.PreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QRDisplayActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static Button btnScanQR, edit, update, sync;
    public static TextView displayQR;

    public static TextView clientName, plantName,batteryId, batteryRoomNo, upsNo, rackNo, slaveNo, slaveType;

    public static RelativeLayout qrDisplayBlock;

    ImageView menuIcon;

    private String client_Name,plant_Name,battery_Id,battety_Room_No,ups_No,rack_No,slave_No,slave_Type;

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
        batteryId=(TextView)findViewById(R.id.batteryId);
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editScannedDataWithServer();
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

        edit.setVisibility(View.VISIBLE);

        client_Name=clientName.getText().toString().trim();
        plant_Name=plantName.getText().toString().trim();
        battery_Id=batteryId.getText().toString().trim();
        battety_Room_No= batteryRoomNo.getText().toString().trim();
        ups_No=upsNo.getText().toString().trim();
        rack_No=rackNo.getText().toString().trim();
        slave_No=slaveNo.getText().toString().trim();
        slave_Type=slaveType.getText().toString().trim();


        System.out.println("ClientNameInQRDisplay"+client_Name+" "+plant_Name+" "+battery_Id+" "+battety_Room_No+" "+ups_No+" "+rack_No+" "+slave_No+" "+slave_Type);

        sendDataToServerToSync(client_Name,plant_Name,battery_Id,battety_Room_No,ups_No,rack_No,slave_No,slave_Type);
    }

    private void editScannedDataWithServer() {

        Intent intent=new Intent(QRDisplayActivity.this,EditActivity.class);

        intent.putExtra("CLIENTNAME",client_Name);
        intent.putExtra("PLANTNAME",plant_Name);
        intent.putExtra("BATTERYID",battery_Id);
        intent.putExtra("BATTERYROOM",battety_Room_No);
        intent.putExtra("UPS",ups_No);
        intent.putExtra("RACKNO",rack_No);
        intent.putExtra("SLAVENO",slave_No);
        intent.putExtra("SLAVETYPE",slave_Type);

        startActivity(intent);

    }

    private void sendDataToServerToSync(String client_name, String plant_name, String battery_id, String battety_room_no, String ups_no, String rack_no, String slave_no, String slave_type) {

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        ValidateBatteryDTO validateBatteryDTO=new ValidateBatteryDTO(battery_id,client_name,plant_name,battety_room_no,ups_no,rack_no,slave_no,slave_type);


       Call<BaseDTO> call =apiInterface.syncScanDataWithServer(validateBatteryDTO);
       call.enqueue(new Callback<BaseDTO>() {
           @Override
           public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {

               BaseDTO baseDTO=response.body();

               System.out.println("ScanBaseResponse "+baseDTO.getResponseCode());


           }

           @Override
           public void onFailure(Call<BaseDTO> call, Throwable t) {

               System.out.println("ExceptionResult "+t.getMessage().toString());

           }
       });



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
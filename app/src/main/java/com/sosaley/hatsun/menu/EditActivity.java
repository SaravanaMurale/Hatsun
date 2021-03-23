package com.sosaley.hatsun.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.model.BaseDTO;
import com.sosaley.hatsun.model.ValidateBatteryDTO;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;
import com.sosaley.hatsun.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    private TextView clientNameTextView,plantNameTextView,batteryIdTextView;

    public static EditText batteryRoomNoEditText, upsNoEditText, rackNoEditText, slaveNoEditText, slaveTypeEditText;

    private String client_Name_Value,plant_Name_Value,battery_Id_Value,battety_Room_No_Value,ups_No_Value,rack_No_Value,slave_No_Value,slave_Type_Value;

    Button btnEditSubmit;

    private String currentUpsNo,currentRackNo,currentSlaveNo,currentSlaveType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent=getIntent();

        client_Name_Value=intent.getStringExtra("CLIENTNAME");
        plant_Name_Value=intent.getStringExtra("PLANTNAME");
        battery_Id_Value=intent.getStringExtra("BATTERYID");
        battety_Room_No_Value=intent.getStringExtra("BATTERYROOM");

        ups_No_Value=intent.getStringExtra("UPS");
        rack_No_Value=intent.getStringExtra("RACKNO");
        slave_No_Value=intent.getStringExtra("SLAVENO");
        slave_Type_Value=intent.getStringExtra("SLAVETYPE");

        clientNameTextView = (TextView) findViewById(R.id.clientNameEdit);
        plantNameTextView = (TextView) findViewById(R.id.plantNameEdit);
        batteryIdTextView=(TextView)findViewById(R.id.batteryIdEdit);
        batteryRoomNoEditText = (EditText) findViewById(R.id.batteryEdit);

        upsNoEditText = (EditText) findViewById(R.id.upsEdit);
        rackNoEditText = (EditText) findViewById(R.id.rackEdit);
        slaveNoEditText = (EditText) findViewById(R.id.slaveEdit);
        slaveTypeEditText = (EditText) findViewById(R.id.slaveTypeEdit);

        btnEditSubmit=(Button)findViewById(R.id.btnEditSubmit);


        setAllValue();

        btnEditSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendEditedValueToServer();

            }
        });

    }



    private void setAllValue() {

        clientNameTextView.setText(client_Name_Value);
        plantNameTextView.setText(plant_Name_Value);
        batteryIdTextView.setText(battery_Id_Value);

        batteryRoomNoEditText.setText(battety_Room_No_Value);
        upsNoEditText.setText(ups_No_Value);
        rackNoEditText.setText(rack_No_Value);
        slaveNoEditText.setText(slave_No_Value);
        slaveTypeEditText.setText(slave_Type_Value);


    }

    private void sendEditedValueToServer() {

        String userEnteredUPS=upsNoEditText.getText().toString();
        String userEnteredRackNo=rackNoEditText.getText().toString();
        String userEnteredSlaveNo=slaveNoEditText.getText().toString();
        String userEnteredSlaveType=slaveTypeEditText.getText().toString();


        if(!ups_No_Value.equals(userEnteredUPS)){

            System.out.println("UPSValueChanged");
            currentUpsNo=userEnteredUPS;
        }else {
            currentUpsNo=null;
        }

        if(!rack_No_Value.equals(userEnteredRackNo)){

            System.out.println("RackValueChanged");
            currentRackNo=userEnteredRackNo;
        }else {
            currentRackNo=null;
        }

        if(!slave_No_Value.equals(userEnteredSlaveNo)){

            System.out.println("SlaveNoChanged");
            currentSlaveNo=userEnteredSlaveNo;
        }else {
            currentSlaveNo=null;
        }

        if(!slave_Type_Value.equals(userEnteredSlaveType)){

            System.out.println("SlaveTypeChanged");
            currentSlaveType=userEnteredSlaveType;

        }else {
            currentSlaveType=null;
        }



        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        ValidateBatteryDTO validateBatteryDTO=new ValidateBatteryDTO(battery_Id_Value,currentUpsNo,currentRackNo,currentSlaveNo,currentSlaveType);

        Call<BaseDTO> call =apiInterface.sendEditedValueToServer(validateBatteryDTO);

        call.enqueue(new Callback<BaseDTO>() {
            @Override
            public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {

                BaseDTO baseDTO=response.body();

                System.out.println("EditResponse "+baseDTO.getResponseCode());

                ToastUtil.showLongToast(EditActivity.this,"Mail Sent Successfully");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchQRScanActivity();
                    }
                },2000);



            }

            @Override
            public void onFailure(Call<BaseDTO> call, Throwable t) {

                System.out.println("Exception "+t.getMessage().toString());

            }
        });

    }

    private void launchQRScanActivity() {

        Intent intent=new Intent(EditActivity.this,QRDisplayActivity.class);
        startActivity(intent);
        finish();

    }
}
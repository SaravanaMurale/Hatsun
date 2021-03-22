package com.sosaley.hatsun.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.sosaley.hatsun.R;

public class EditActivity extends AppCompatActivity {

    private TextView clientNameTextView,plantNameTextView,batteryIdTextView;

    public static EditText batteryRoomNoEditText, upsNoEditText, rackNoEditText, slaveNoEditText, slaveTypeEditText;

    private String client_Name_Value,plant_Name_Value,battery_Id_Value,battety_Room_No_Value,ups_No_Value,rack_No_Value,slave_No_Value,slave_Type_Value;



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


        setAllValue();

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
}
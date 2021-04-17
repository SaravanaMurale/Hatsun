package com.sosaley.hatsun.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zXingScannerView = new ZXingScannerView(QRScannerActivity.this);

        setContentView(zXingScannerView);


    }

    @Override
    public void handleResult(Result result) {

        String scannedResult = result.getText();

        //QRDisplayActivity.displayQR.setText(result.getText());

        QRDisplayActivity.batteryNameTitle.setVisibility(View.VISIBLE);
        QRDisplayActivity.descEdit.setVisibility(View.VISIBLE);
        QRDisplayActivity.btnSubmit.setVisibility(View.VISIBLE);


        String[] words = scannedResult.split(":");

        for (int i = 0; i < 1; i++) {

            System.out.println("ArraySize" + words.length);

            System.out.println("ArrayPosition0"+words[0]);
            System.out.println("ArrayPosition1"+words[1]);
            System.out.println("ArrayPosition2"+words[2]);
            System.out.println("ArrayPosition2"+words[3]);

            QRDisplayActivity.batteryNameTitle.setText(words[0]+":"+words[1]+":"+words[2]+":"+words[3]);

            Toast.makeText(QRScannerActivity.this,"ScanData"+ words[0]+" "+words[1]+" "+words[2]+" "+words[3],Toast.LENGTH_LONG).show();

            /*System.out.println("ArrayPosition0"+words[0]);
            System.out.println("ArrayPosition1"+words[1]);
            System.out.println("ArrayPosition2"+words[2]);
            System.out.println("ArrayPosition3"+words[3]);
            System.out.println("ArrayPosition4"+words[4]);
            System.out.println("ArrayPosition5"+words[5]);
            System.out.println("ArrayPosition6"+words[6]);
            System.out.println("ArrayPosition7"+words[7]);*/

            /*QRDisplayActivity.clientName.setText(words[1]);
            QRDisplayActivity.plantName.setText(words[2]);
            QRDisplayActivity.batteryId.setText(words[3]);
            QRDisplayActivity.batteryRoomNo.setText(words[4]);
            QRDisplayActivity.upsNo.setText(words[5]);
            QRDisplayActivity.rackNo.setText(words[6]);
            QRDisplayActivity.slaveNo.setText(words[7]);
            QRDisplayActivity.slaveType.setText(words[8]);*/


            /*System.out.println("7th position"+words[7]);
            System.out.println("8th position"+words[8]);*/
        }

        onBackPressed();


    }

    @Override
    protected void onResume() {
        super.onResume();

        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        zXingScannerView.stopCamera();
    }


}
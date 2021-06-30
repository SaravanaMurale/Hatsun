package com.sosaley.hatsun.menu;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.authentication.SigninActivity;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.utils.AppConstant;
import com.sosaley.hatsun.utils.MathUtil;
import com.sosaley.hatsun.utils.PermissionUtils;
import com.sosaley.hatsun.utils.PreferencesUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class QRDisplayActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static Button btnScanQR ;
    public static TextView displayQR;

    public static TextView batteryNameTitle;


    ImageView menuIcon;

    TextView dataSync;

    private String client_Name,plant_Name,battery_Id,battety_Room_No,ups_No,rack_No,slave_No,slave_Type;

    public static Button btnSubmit;
    public static EditText descEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_display);

        initView();


        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description=descEdit.getText().toString();

                sendDescriptionDataToRedmine(description);

                //sendDescriptionToServer(description);

               // sendSimplifiedRequest(description);


            }
        });

    }

    private void sendDescriptionDataToRedmine(String desc) {

        int project_Id=1;
        String description=desc;
        String subject="Project 1 post";
        int priority_id=4;
        int estimate_hours=8;
        boolean isPrivate=false;

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body;
                        /*body = RequestBody.create(mediaType,
                                "{\n" +
                                        "\"issue\": " +
                                        "{\n   " +
                                        " \"project_id\":"  +project_Id+",\n  " +
                                        "  \"subject\":"  +subject+",\n " +
                                        "  \"priority_id\":"  +priority_id+",\n " +
                                        "  \"description\":"  +description+",\n " +

                                        "}" +
                                        "\n}");*/

                         body = RequestBody.create(mediaType,
                                "{\n  \"issue\": {\n    \"project_id\":"+project_Id+",\n    \"subject\": "+subject+",\n    \"priority_id\": "+priority_id+",\n    \"description\":"+description+",\n    \"is_private\":"+isPrivate+",\n    \"estimated_hours\":"+estimate_hours+" \n  }\n}");



                        Request request = new Request.Builder()
                                .url("http://redmine.sosaley.co.in:83/issues.json")
                               // .url("http://192.168.0.23:80/redmine/issues.json")
                                .method("POST", body)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization",ApiClient.SERVER_AUTH )
                                .build();
                        Response response = client.newCall(request).execute();

                        descEdit.setText("");

                        System.out.println("Response"+response.body().toString());

                        Log.i(ContentValues.TAG, "response::"+response.body().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.start();

        Toast.makeText(QRDisplayActivity.this,"Issue Posted Successfully",Toast.LENGTH_LONG).show();

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

    private void stopBlinking() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MathUtil.syncStatusBlinkStop(QRDisplayActivity.this,dataSync);
            }
        },5000);


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

    private void initView() {
        btnScanQR = (Button) findViewById(R.id.btnScanQR);

        menuIcon = (ImageView) findViewById(R.id.menuIcon);
        batteryNameTitle=(TextView)findViewById(R.id.batteryNameTitle);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        descEdit=(EditText)findViewById(R.id.descriptionOfIssue);

    }



    /*private void sendDescriptionDataToRedmine(String description) {


        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body;
                        body = RequestBody.create(mediaType,
                                "{\n  " +
                                        "\"issue\": " +
                                        "{\n   " +
                                        " \"project_id\": 1,\n  " +
                                        "  \"subject\": \"BMS Support Project Iss\",\n  " +
                                        "  \"priority_id\": 4,\n   " +
                                        " \"description\":\"Battery is not connected properly\",\n" +
                                        "    \"is_private\":false,\n  " +
                                        "  \"estimated_hours\":\"8\"\n  " +
                                        "}" +
                                        "\n}");


                        Request request = new Request.Builder()
                                .url("http://redmine.sosaley.co.in:83/issues.json")
                                //.url("http://192.168.0.23:80/redmine/issues.json")
                                .method("POST", body)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization",ApiClient.SERVER_AUTH )
                                .build();
                        Response response = client.newCall(request).execute();

                        descEdit.setText("");


                        System.out.println("Response"+response.body().toString());

                        Log.i(ContentValues.TAG, "response::"+response.body().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.start();

        Toast.makeText(QRDisplayActivity.this,"Issue Posted Successfully",Toast.LENGTH_LONG).show();

    }*/


    /*private void sendDataToServerToSync(String client_name, String plant_name, String battery_id, String battety_room_no, String ups_no, String rack_no, String slave_no, String slave_type) {

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        ValidateBatteryDTO validateBatteryDTO=new ValidateBatteryDTO(battery_id,client_name,plant_name,battety_room_no,ups_no,rack_no,slave_no,slave_type);


        Call<ValidateBatteryDTO> call =apiInterface.syncScanDataWithServer(validateBatteryDTO);
        call.enqueue(new Callback<ValidateBatteryDTO>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<ValidateBatteryDTO> call, Response<ValidateBatteryDTO> response) {

                ValidateBatteryDTO validateBatteryDTO=response.body();

                if(validateBatteryDTO.getResponseCode().equals("200")){

                    dataSync.setTextColor(getColor(R.color.hatsun_blue));
                    MathUtil.syncStatusBlinkStart(QRDisplayActivity.this,dataSync,"Data synced successfully");

                    stopBlinking();


                }else if(validateBatteryDTO.getResponseCode().equals("500")){
                    //blinkText("Data Mismatch");

                    dataSync.setTextColor(getColor(R.color.red));
                    //dataSync.setText("Data Mismatch");
                    MathUtil.syncStatusBlinkStart(QRDisplayActivity.this,dataSync,"DataMismatch");
                    stopBlinking();


                    if(validateBatteryDTO.getUps()==null){

                    }else if(!validateBatteryDTO.getUps().equals(null)){


                        upsNo.setTextColor(getColor(R.color.red));
                        MathUtil.parameterStatusBlinkStart(QRDisplayActivity.this,upsNo,getString(R.string.mismatch_ups));

                        System.out.println("UPSDataMisMatch");


                    }

                    if(validateBatteryDTO.getRackId()==null){

                    }else if(!validateBatteryDTO.getRackId().equals(null)){

                        rackNo.setTextColor(getColor(R.color.red));
                        MathUtil.parameterStatusBlinkStart(QRDisplayActivity.this,rackNo,getString(R.string.mismatch_rack));
                        //blink
                        System.out.println("RackIdMisMatch");
                        //ToastUtil.showShortToast(QRDisplayActivity.this,"RackIdMisMatch");
                    }


                    if(validateBatteryDTO.getSlaveId()==null){

                    }else if(!validateBatteryDTO.getSlaveId().equals(null)){

                        slaveNo.setTextColor(getColor(R.color.red));
                        MathUtil.parameterStatusBlinkStart(QRDisplayActivity.this,slaveNo,getString(R.string.mismatch_slaveid));

                        //blink
                        System.out.println("SlaveIdMisMatch");
                        //ToastUtil.showShortToast(QRDisplayActivity.this,"SlaveIdMisMatch");
                    }


                    if(validateBatteryDTO.getSlaveType()==null){

                    }else if(!validateBatteryDTO.getSlaveType().equals(null)){

                        slaveType.setTextColor(getColor(R.color.red));
                        MathUtil.parameterStatusBlinkStart(QRDisplayActivity.this,slaveType,getString(R.string.mismatch_slavetype));

                        System.out.println("SlaveTypeMisMatch");
                        //ToastUtil.showShortToast(QRDisplayActivity.this,"SlaveTypeMisMatch");
                    }

                }

                System.out.println("ScanBaseResponse "+validateBatteryDTO.getResponseCode());


            }

            @Override
            public void onFailure(Call<ValidateBatteryDTO> call, Throwable t) {

                System.out.println("ExceptionResult "+t.getMessage().toString());

            }
        });



    }*/

     /*private void sendSimplifiedRequest(String description) {

        IssuePostDTO issuePostDTO=new IssuePostDTO(1,"Subject From App",4,"Desc From App",false,8);

        List<IssuePostDTO> issuePostDTOList=new ArrayList<>();
        issuePostDTOList.add(issuePostDTO);

        IssuePostList issuePostList=new IssuePostList(issuePostDTOList);

        Call<BaseDTO> call = SimplifiedRetrofit
                .getInstance()
                .getApi().postIssue(issuePostList);

        call.enqueue(new Callback<BaseDTO>() {
            @Override
            public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {

                System.out.println("ResponseSuccess"+response.code());
                System.out.println("ResponseMessage"+response.message().toString());
                System.out.println("ResponseErrorBody"+response.errorBody().toString());


            }

            @Override
            public void onFailure(Call<BaseDTO> call, Throwable t) {

            }
        });


    }*/


   /* private void sendDescriptionToServer(String description) {
        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        IssuePostDTO issuePostDTO=new IssuePostDTO(1,"Subject From App",4,"Desc From App",false,8);

        List<IssuePostDTO> issuePostDTOList=new ArrayList<>();
        issuePostDTOList.add(issuePostDTO);

        IssuePostList issuePostList=new IssuePostList(issuePostDTOList);



        //String token="Basic c3Jpbmk6U3JpbmlAMTIz";
        String token="Basic 008111fded86fc249e6e2cbfc5aecb9960d85ef3";
        String userName="srini";
        String password="Srini@123";

       // String authToken="Basic "+ Base64.encodeToString(userName:password);

        *//*Call<BaseDTO> call=apiInterface.postIssue(issuePostList);
        call.enqueue(new Callback<BaseDTO>() {
            @Override
            public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {


                System.out.println("ResponseSuccess"+response.code());
                System.out.println("ResponseMessage"+response.message().toString());
                System.out.println("ResponseErrorBody"+response.errorBody().toString());

            }

            @Override
            public void onFailure(Call<BaseDTO> call, Throwable t) {

                System.out.println("Exception"+t.getMessage().toString());

            }
        });*//*






    }*/


    /*private void syncScannedDataWithServer() {

        edit.setVisibility(View.VISIBLE);

        client_Name=clientName.getText().toString().trim();
        plant_Name=plantName.getText().toString().trim();
        battery_Id=batteryId.getText().toString().trim();
        battety_Room_No= batteryRoomNo.getText().toString().trim();
        ups_No=upsNo.getText().toString().trim();
        rack_No=rackNo.getText().toString().trim();
        slave_No=slaveNo.getText().toString().trim();
        slave_Type=slaveType.getText().toString().trim();

        //System.out.println("ClientNameInQRDisplay"+client_Name+" "+plant_Name+" "+battery_Id+" "+battety_Room_No+" "+ups_No+" "+rack_No+" "+slave_No+" "+slave_Type);

        sendDataToServerToSync(client_Name,plant_Name,battery_Id,battety_Room_No,ups_No,rack_No,slave_No,slave_Type);
    }*/

}
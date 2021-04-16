package com.sosaley.hatsun.redmine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.model.BaseDTO;
import com.sosaley.hatsun.model.IssuePostDTO;
import com.sosaley.hatsun.model.IssuePostList;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RedmineActivity extends AppCompatActivity {

    EditText getEditData;
    Button btnRedmineSubmit,getUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redmine);

        getEditData=(EditText)findViewById(R.id.getEditData);
        btnRedmineSubmit=(Button)findViewById(R.id.btnRedmineSubmit);
        getUser=(Button)findViewById(R.id.getUser);

        getUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getUserDetails();

                //getUserDetailsWithoutUserNameAndPass();

            }
        });


        btnRedmineSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendRedmineDetailsToServer();

                //sendRedmineIssueDetailsOKHTTP();



            }
        });

    }

   /* private void sendRedmineIssueDetailsOKHTTP() {


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
                        RequestBody body = RequestBody.create(mediaType,
                                "{\n  \"issue\": {\n    \"project_id\": 1,\n    \"subject\": \"Project 1 post\",\n    \"priority_id\": 4,\n    \"description\":\"Description\",\n    \"is_private\":false,\n    \"estimated_hours\":\"8\"\n  }\n}");
                        Request request = new Request.Builder()
                               // .url("http://redmine.sosaley.co.in:83/issues.json")
                                .url("http://192.168.0.23:80/redmine/issues.json")
                                .method("POST", body)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization",ApiClient.AUTH )
                                .build();
                        Response response = client.newCall(request).execute();

                        System.out.println("Response"+response.body().toString());

                        Log.i(ContentValues.TAG, "response::"+response.body().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.start();


    }*/

//    private void getUserDetailsWithoutUserNameAndPass() {
//
//        String token="Basic b6e957bce7820f60453eb2403a67bd35b7a994de";
//
//        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
//
//        Call<BaseDTO> call=apiInterface.getUserDetailsWithUserAndPass(token);
//        call.enqueue(new Callback<BaseDTO>() {
//            @Override
//            public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {
//
//                System.out.println("ResponseDetails "+response.code());
//                System.out.println("ResponseDetails "+response.message());
//
//            }
//
//            @Override
//            public void onFailure(Call<BaseDTO> call, Throwable t) {
//
//            }
//        });
//
//
//
//
//    }

//    private void getUserDetails() {
//
//        Call<BaseDTO> call = SimplifiedRetrofit
//                .getInstance()
//                .getApi().getUserDetails();
//
//        call.enqueue(new Callback<BaseDTO>() {
//            @Override
//            public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {
//
//                System.out.println("GetUserDetails "+response.code());
//                System.out.println("GetUserDetails "+response.message());
//
//            }
//
//            @Override
//            public void onFailure(Call<BaseDTO> call, Throwable t) {
//
//            }
//        });
//
//    }

    private void sendRedmineDetailsToServer() {

        //String token="Basic c3Jpbmk6U3JpbmlAMTIz";
        String token="b6e957bce7820f60453eb2403a67bd35b7a994de";

        IssuePostDTO issuePostDTO=new IssuePostDTO(1,"Subject From App",4,"Desc From App",8);

        List<IssuePostDTO> issuePostDTOList=new ArrayList<>();
        issuePostDTOList.add(issuePostDTO);

        IssuePostList issuePostList=new IssuePostList(issuePostDTOList);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        Call<BaseDTO> call=apiInterface.postIssue(ApiClient.AUTH,issuePostList);

        /*Call<BaseDTO> call = SimplifiedRetrofit
                .getInstance()
                .getApi().postIssue(token,issuePostList);*/

        call.enqueue(new Callback<BaseDTO>() {
            @Override
            public void onResponse(Call<BaseDTO> call, Response<BaseDTO> response) {

                Toast.makeText(RedmineActivity.this,response.message()+" "+response.code(),Toast.LENGTH_LONG).show();

                System.out.println("ResponseDetails "+response.message());
                System.out.println("ResponseDetails "+response.code());
                System.out.println("ResponseDetails "+response.errorBody());
            }

            @Override
            public void onFailure(Call<BaseDTO> call, Throwable t) {

                System.out.println("Exception"+t.getMessage().toString());

            }
        });


    }
}
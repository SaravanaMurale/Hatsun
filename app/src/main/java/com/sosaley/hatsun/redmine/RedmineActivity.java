package com.sosaley.hatsun.redmine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.model.BaseDTO;
import com.sosaley.hatsun.model.IssuePostDTO;
import com.sosaley.hatsun.model.IssuePostList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedmineActivity extends AppCompatActivity {

    EditText getEditData;
    Button btnRedmineSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redmine);

        getEditData=(EditText)findViewById(R.id.getEditData);
        btnRedmineSubmit=(Button)findViewById(R.id.btnRedmineSubmit);


        btnRedmineSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendRedmineDetailsToServer();

            }
        });

    }

    private void sendRedmineDetailsToServer() {

        String token="Basic c3Jpbmk6U3JpbmlAMTIz";

        IssuePostDTO issuePostDTO=new IssuePostDTO(1,"Subject From App",4,"Desc From App",8);

        List<IssuePostDTO> issuePostDTOList=new ArrayList<>();
        issuePostDTOList.add(issuePostDTO);

        IssuePostList issuePostList=new IssuePostList(issuePostDTOList);

        Call<BaseDTO> call = SimplifiedRetrofit
                .getInstance()
                .getApi().postIssue(token,issuePostList);

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
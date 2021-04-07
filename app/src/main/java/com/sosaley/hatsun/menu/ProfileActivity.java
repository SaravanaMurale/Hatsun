package com.sosaley.hatsun.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sosaley.hatsun.R;
import com.sosaley.hatsun.model.GetUserDTO;
import com.sosaley.hatsun.model.UserResponseDTO;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;
import com.sosaley.hatsun.utils.PreferencesUtil;
import com.sosaley.hatsun.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    RelativeLayout userNameBlock, emailBlock, mobileBlock, changePasswordBlock;

    TextView userName,email,mobile,changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameBlock = (RelativeLayout) findViewById(R.id.profileBlock);
        emailBlock = (RelativeLayout) findViewById(R.id.emailBlock);
        mobileBlock = (RelativeLayout) findViewById(R.id.mobileBlock);
        changePasswordBlock = (RelativeLayout) findViewById(R.id.changePasswordBlock);

        userName=(TextView)findViewById(R.id.userName);
        email=(TextView)findViewById(R.id.email);
        mobile=(TextView)findViewById(R.id.mobile);
        changePassword=(TextView)findViewById(R.id.changePassword);



        getUserDetailsFromServer();


        userNameBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog("Update Name", 1);

            }
        });

        emailBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog("Update Email", 2);

            }
        });

        mobileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Update Mobile Number", 3);
            }
        });

        changePasswordBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog("Update Password", 4);

            }
        });

    }

    private void getUserDetailsFromServer() {

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        GetUserDTO getUserDTO=new GetUserDTO(PreferencesUtil.getValueInt(ProfileActivity.this,PreferencesUtil.USER_ID));

        Call<UserResponseDTO> call =apiInterface.getUserDetails(getUserDTO);

        call.enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {

                UserResponseDTO userResponseDTO=response.body();

                if(userResponseDTO!=null){

                    userName.setText(userResponseDTO.getUserName());
                    email.setText(userResponseDTO.getUserEmail());
                    mobile.setText(userResponseDTO.getUserMobile());

                }else {
                    ToastUtil.showLongToast(ProfileActivity.this,getString(R.string.no_user_data));
                }


            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {

            }
        });



    }

    private void openDialog(String hintData, final int i) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.layout_dialog_userprofile, null);

        final EditText update = (EditText) view.findViewById(R.id.updateName);
        update.setHint(hintData);

        builder.setView(view);

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (i == 1) {
                    //update name

                    //updateUserDetails()

                } else if (i == 2) {
                    //update Email

                } else if (i == 3) {

                    //update mobile

                } else if (i == 4) {
                    //update password
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
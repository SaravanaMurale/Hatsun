package com.sosaley.hatsun.authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sosaley.hatsun.R;
import com.sosaley.hatsun.menu.QRDisplayActivity;
import com.sosaley.hatsun.model.UserDTO;
import com.sosaley.hatsun.model.UserResponseDTO;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;
import com.sosaley.hatsun.utils.PreferencesUtil;
import com.sosaley.hatsun.utils.ToastUtil;
import com.sosaley.hatsun.utils.Validation;

import java.io.Serializable;
import java.util.logging.Level;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sosaley.hatsun.utils.AppConstant.REQ_CODE;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;

    Button signUpBtn;

    EditText userName, userMobileNumber, userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        initView();


        signInButton = (SignInButton) findViewById(R.id.gmailSignup);

        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sendPostRequest();

                validateUserDetails();


                //launchOTPActivity();


            }
        });


        signInButton.setOnClickListener(this);

    }


    private void launchOTPActivity() {

        Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
        intent.putExtra("MOBILE", "+919123521374");
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.gmailSignup:
                signIn();
                break;

            /*case R.id.gLogout:
                signOut();
                break;*/
        }

    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQ_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == REQ_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                if (!Validation.validateName(personName)) {
                    ToastUtil.showShortToast(SignupActivity.this, getString(R.string.valid_name));
                    return;
                }
                if (!Validation.validateEmail(personEmail)) {
                    ToastUtil.showShortToast(SignupActivity.this, getString(R.string.valid_email));
                    return;
                }

                if (personName != null && personEmail != null) {
                    sendNamdAndGmailToServer(personName, personEmail);
                }

                // Toast.makeText(SignupActivity.this,"NameAndEmail "+personName+" "+personEmail,Toast.LENGTH_LONG).show();

                System.out.println("NameAndEmail " + personName + " " + personEmail);
                
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        signOut();
                    }
                },3000);*/

            }


            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            // Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);

            System.out.println("SignInResultFailedCode " + e.getStatusCode());

        }
    }


    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            System.out.println("SuccessfullyLogout");
                        }


                    }
                });

    }

    private void validateUserDetails() {

        String signupName = userName.getText().toString();
        String signupMobile = userMobileNumber.getText().toString();
        String signupEmail = userEmail.getText().toString();
        String signupPassword = userPassword.getText().toString();

        /*if(!Validation.validateName(signupName)){
            ToastUtil.showShortToast(SignupActivity.this,getString(R.string.valid_name));
            return;
        }
        if(!Validation.validateMobileNumber(signupMobile)){
            ToastUtil.showShortToast(SignupActivity.this,getString(R.string.valid_mobile));
            return;
        }

        if(!Validation.validateEmail(signupEmail)){
            ToastUtil.showShortToast(SignupActivity.this,getString(R.string.valid_email));
            return;
        }

        if(!Validation.validatePassword(signupPassword)){
            ToastUtil.showShortToast(SignupActivity.this,getString(R.string.valid_password));
            return;
        }*/

        /*if(Validation.validateName(signupName) && Validation.validateMobileNumber(signupMobile) && Validation.validateEmail(signupEmail) && Validation.validatePassword(signupPassword) ) {
            doSaveInServer(signupName, signupMobile, signupEmail, signupPassword);

        }*/


        doSaveInServer("Murali", "8123521374", "sarav@gmail.com", "aaaaaaaa");

        /* UserDTO userDTO=new UserDTO("Murali","9123521374","sara@gmail.com","aaaaaaaa");

            Intent intent=new Intent(SignupActivity.this,OTPActivity.class);
            intent.putExtra("MOBILE","9123521374");
            startActivity(intent);*/

    }

    private void sendNamdAndGmailToServer(String personName, String personEmail) {

        UserDTO gmailUserDTO = new UserDTO(personName, personEmail);
        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        Call<UserResponseDTO> call = apiInterface.saveGmailUser(gmailUserDTO);
        call.enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {

                UserResponseDTO userResponseDTO = response.body();

                if (userResponseDTO.getResponseCode().equals("200")) {

                    System.out.println("ValueInserted");
                    PreferencesUtil.setValueSInt(SignupActivity.this, PreferencesUtil.USER_ID, userResponseDTO.getUserId());
                    launchHomeScreen();

                } else if (userResponseDTO.getResponseCode().equals("700")) {
                    System.out.println("DataIsMissing");
                }


            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {

                System.out.println("ExceptionMessage " + t.getMessage().toString());

            }
        });


    }


    private void doSaveInServer(String signupName, String signupMobile, String signupEmail, String signupPassword) {

        final UserDTO userDTO = new UserDTO(signupName, signupMobile, signupEmail, signupPassword);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        Call<UserResponseDTO> call = apiInterface.saveUserRegistration(userDTO);

        call.enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {

                UserResponseDTO userResponseDTO = response.body();

                if (userResponseDTO.getResponseCode().equals("700")) {
                    ToastUtil.showLongToast(SignupActivity.this, "Your Already Register User,Please Signin");
                } else if (userResponseDTO.getResponseCode().equals("200")) {
                    System.out.println("ValueInserted");

                    PreferencesUtil.setValueSInt(SignupActivity.this, PreferencesUtil.USER_ID, userResponseDTO.getUserId());

                    launchHomeScreen();


                }

                /*System.out.println("ReceivedData "+userResponseDTO.getResponseCode()+" "+userResponseDTO.getUserId()+" "+userResponseDTO.getUserEmail()+" "+userName);


                System.out.println("ResponseSuccess "+response.body());

                Toast.makeText(SignupActivity.this,"ResponseSuccess "+response.body(),Toast.LENGTH_LONG).show();*/

            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                System.out.println("ExceptionMessage " + t.getMessage().toString());
                Toast.makeText(SignupActivity.this, "ExceptionMessage " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void launchHomeScreen() {

        Intent intent = new Intent(SignupActivity.this, QRDisplayActivity.class);
        //intent.putExtra("Google", (Serializable) mGoogleSignInClient);
        startActivity(intent);
        finish();

    }


    private void initView() {
        userName = (EditText) findViewById(R.id.signupName);
        userMobileNumber = (EditText) findViewById(R.id.signupMobile);
        userEmail = (EditText) findViewById(R.id.signupEmail);
        userPassword = (EditText) findViewById(R.id.signupPassword);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
    }

}


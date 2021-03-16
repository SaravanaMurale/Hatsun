package com.sosaley.hatsun.authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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
import com.sosaley.hatsun.model.UserDTO;
import com.sosaley.hatsun.retrofit.ApiClient;
import com.sosaley.hatsun.retrofit.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sosaley.hatsun.utils.AppConstant.REQ_CODE;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;

    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signInButton=(SignInButton)findViewById(R.id.gmailSignup);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signUpBtn=(Button)findViewById(R.id.signUpBtn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sendPostRequest();

                UserDTO userDTO=new UserDTO("Murali","9999999999","sara@gmail.com","aaaaaaaaaa");


                ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
                //Call<ResponseBody> call =apiInterface.saveUserRegistration(userDTO);

                Call<ResponseBody> call=apiInterface.saveUserRegistration(userDTO);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        System.out.println("ResponseSuccess "+response.body());

                        Toast.makeText(SignupActivity.this,"ResponseSuccess "+response.body(),Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("ExceptionMessage "+t.getMessage().toString());
                        Toast.makeText(SignupActivity.this,"ExceptionMessage "+t.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

                //launchOTPActivity();


            }
        });


        signInButton.setOnClickListener(this);

    }

   /* private void sendPostRequest() {

        try {
            class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... params) {

                    String urlParameters = "192.168.0.61";

                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(urlParameters);
                    List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                    nameValuePairList.add(new BasicNameValuePair("carnumber", pref.getString("BatteryLID", "").toString()));
//                    nameValuePairList.add(new BasicNameValuePair("drivername", pref.getString("Driver Name", "").toString()));
                    nameValuePairList.add(new BasicNameValuePair("timestamp", currentDateandTime));
                    nameValuePairList.add(new BasicNameValuePair("livedata", pref.getString("Server values", "").toString()));
                    nameValuePairList.add(new BasicNameValuePair("location", pref.getString("Latitude", "").toString() + ","
                            + pref.getString("Longitude", "").toString()));

                    Log.d("Cloud Datas", pref.getString("BatteryLID", "") + pref.getString("Driver Name", "").toString() + currentDateandTime + pref.getString("Server values", "") + pref.getString("Latitude", "") + pref.getString("Longitude", ""));

                    try {
                        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                        httpPost.setEntity(urlEncodedFormEntity);

                        try {

                            HttpResponse httpResponse = httpClient.execute(httpPost);
                            InputStream inputStream = httpResponse.getEntity().getContent();

                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                            StringBuilder stringBuilder = new StringBuilder();

                            String bufferedStrChunk = null;

                            while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                                stringBuilder.append(bufferedStrChunk);
                            }

                            return stringBuilder.toString();

                        } catch (ClientProtocolException cpe) {
                            cpe.printStackTrace();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }

                    } catch (UnsupportedEncodingException uee) {
                        uee.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                }
            }

            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
            sendPostReqAsyncTask.execute();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    private void launchOTPActivity() {

        Intent intent=new Intent(SignupActivity.this,OTPActivity.class);
        intent.putExtra("MOBILE","+919123521374");
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

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

                Toast.makeText(SignupActivity.this,"NameAndEmail "+personName+" "+personEmail,Toast.LENGTH_LONG).show();

                System.out.println("NameAndEmail "+personName+" "+personEmail);
                
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        signOut();
                    }
                },3000);

            }


            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           // Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);

            System.out.println("SignInResultFailedCode "+e.getStatusCode());

        }
    }

    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            System.out.println("SuccessfullyLogout");
                        }


                    }
                });

    }

}
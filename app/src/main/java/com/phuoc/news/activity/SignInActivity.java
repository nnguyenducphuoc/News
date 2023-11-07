package com.phuoc.news.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.phuoc.news.R;
import com.phuoc.news.myutils.XMLDOMParser;
import com.phuoc.news.utils.Credentials;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SignInActivity extends AppCompatActivity {

    SignInClient oneTapClient;
    BeginSignInRequest signUpRequest;
    private static final int ONE_TAP = 21;
    Button btnSignUp, btnLogin;
    EditText edtEmail, edtPassword;

    ProgressDialog progressDialog;

    TextView txtIsValidSignUp;

    ImageView googleLogin, facebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUi();
        initListener();


    }




    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickSignIn();
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoogleLogin();
            }
        });

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFacebookLogin();
            }
        });
    }

    // xml
    private void onClickSignIn() throws ParserConfigurationException {
        try {
            progressDialog.show();
            // Tạo một đối tượng XmlPullParser
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("user.xml");

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            String email = null;
            String password = null;
            boolean isValid = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();

                    if (tagName.equals("email")) {
                        email = parser.nextText();
                    } else if (tagName.equals("password")) {
                        password = parser.nextText();
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String tagName = parser.getName();

                    if (tagName.equals("account")) {
                        // Đã đọc xong một tài khoản, bạn có thể xử lý dữ liệu ở đây
                        if (email != null && password != null) {
                            String emailUser = edtEmail.getText().toString().trim();
                            String passUser = edtPassword.getText().toString().trim();
                            // In ra email và password của tài khoản
                            if (emailUser.equals(email) && passUser.equals(password)) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                                isValid = true;
                            }

                        }
                        email = null;
                        password = null;
                    }
                }
                eventType = parser.next();

            }

            progressDialog.dismiss();
            if (!isValid) {
                txtIsValidSignUp.setText("The email or password is invalid!!");
                txtIsValidSignUp.setVisibility(View.VISIBLE);
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }





//        Document document = null;
//        progressDialog.show();
//        String emailUser = edtEmail.getText().toString().trim();
//        String passUser = edtPassword.getText().toString().trim();
//        try {
//            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = parserFactory.newPullParser();
//            InputStream is = getAssets().open("user.xml");
//            document = null;
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//
//            DocumentBuilder db = factory.newDocumentBuilder();
//            //   InputSource is = new InputSource();
//            //    is.setCharacterStream(new StringReader(content.toString()));
//            // is.setEncoding("UTF-8");
//            document = db.parse(is);
//        } catch (IOException | XmlPullParserException e) {
//                Log.e("Error: ", e.getMessage(), e);
//
//            } catch (SAXException e) {
//                Log.e("Error: ", e.getMessage(), e);
//
//            }
//
//
//            NodeList accountList = document.getElementsByTagName("account");
//
//            for (int i = 0; i < accountList.getLength(); i++) {
//                Element accountElement = (Element) accountList.item(i);
//
//                String email = accountElement.getElementsByTagName("email").item(0).getTextContent();
//                String password = accountElement.getElementsByTagName("password").item(0).getTextContent();
//
//                if (emailUser.equals(email) && passUser.equals(password)) {
//                    progressDialog.dismiss();
//                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finishAffinity();
//                }
//
//            }
//            progressDialog.dismiss();
//            txtIsValidSignUp.setText("The email or password is invalid!!");
//        } catch (Exception e) {
//            e.toString();
//        }
    }

    private void onClickFacebookLogin() {
        Intent intent = new Intent(this, FacebookAuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }

    private void onClickGoogleLogin() {


        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.your_web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("TAG", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d("TAG", e.getLocalizedMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case ONE_TAP:
                    try {
                        SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                        String idToken = credential.getGoogleIdToken();
                        if (idToken !=  null) {
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    } catch (ApiException e) {
                        switch (e.getStatusCode()) {
                            case CommonStatusCodes.CANCELED:
                                Log.d("TAG", "One-tap dialog was closed.");
                                // Don't re-prompt the user.
                                // showOneTapUI = false;
                                break;
                            case CommonStatusCodes.NETWORK_ERROR:
                                Log.d("TAG", "One-tap encountered a network error.");
                                // Try again or just ignore.
                                break;
                            default:
                                Log.d("TAG", "Couldn't get credential from result."
                                        + e.getLocalizedMessage());
                                break;
                        }
                    }
                    break;
            }
    }

    // with firebase
    private void onClickSignIn1() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if (Credentials.isValidEmail(strEmail)) {
            if (Credentials.isValidPassword(strPassword)) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressDialog.dismiss();
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                txtIsValidSignUp.setText("The password is invalid!!");
                txtIsValidSignUp.setVisibility(View.VISIBLE);
            }
        } else {
            txtIsValidSignUp.setText("The email is invalid!!");
            txtIsValidSignUp.setVisibility(View.VISIBLE);
        }


    }

    private void initUi() {
        facebookLogin = findViewById(R.id.facebook_login);
        googleLogin = findViewById(R.id.google_login);
        txtIsValidSignUp = findViewById(R.id.txt_is_valid_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up_sign_in);
        btnLogin = findViewById(R.id.btn_login);
        edtEmail = findViewById(R.id.edt_email_sign_in);
        edtPassword = findViewById(R.id.edt_pass_sign_in);
        progressDialog = new ProgressDialog(this);
    }
}
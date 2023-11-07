package com.phuoc.news.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.phuoc.news.R;
import com.phuoc.news.model.New_Details;
import com.phuoc.news.myutils.XMLDOMParser;
import com.phuoc.news.utils.Credentials;

import org.jsoup.nodes.TextNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SignUpActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword, edtConfirm;
    Button btnSignUp;
    ProgressDialog progressDialog;
    TextView txtIsValidSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUi();
        initListener();
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onClickSignUp();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void onClickSignUp() throws IOException {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String strConfirmPassword = edtConfirm.getText().toString().trim();

        if (Credentials.isValidEmail(strEmail) && Credentials.isValidPassword(strPassword)) {
            if (Credentials.isConfirmPasswordValid(strPassword, strConfirmPassword)) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                txtIsValidSignUp.setText("The confirm password is invalid!!");
                txtIsValidSignUp.setVisibility(View.VISIBLE);
            }
        } else {
            txtIsValidSignUp.setText("The email is invalid!!");
            txtIsValidSignUp.setVisibility(View.VISIBLE);
        }




    }

    private void onUpdateXml() throws IOException {
        InputStream is = getAssets().open("user.xml");
        try {

            StringBuilder content = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }

            bufferedReader.close();

            XMLDOMParser parser = new XMLDOMParser();

            Document document = parser.getDocument(content.toString());

            Element newAccount = document.createElement("account");

            Element emailElement = document.createElement("email");
            Text emailText = document.createTextNode(edtEmail.getText().toString().trim());
            emailElement.appendChild(emailText);

            Element passwordElement = document.createElement("password");
            Text passwordText = document.createTextNode(edtPassword.getText().toString().trim());
            passwordElement.appendChild(passwordText);

            newAccount.appendChild(emailElement);
            newAccount.appendChild(passwordElement);

            Element root = document.getDocumentElement();
            root.appendChild(newAccount);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            // StreamResult result = getAssets().openXmlResourceParser("user.xml");
            // transformer.transform(source, result);
        } catch (Exception e) {
            e.toString();
        }

    }

//    private void onClickUpdateXML()  {
//        File xmlFile = new File(getFilesDir(), "user.xml");
//
//        try {
//            // Read the existing XML content from the file
//            InputStream is = new FileInputStream(xmlFile);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            StringBuilder xmlContent = new StringBuilder();
//
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                xmlContent.append(line).append("\n");
//            }
//
//            reader.close();
//            is.close();
//
//            // Parse the XML content
//            String newAccountXml = "    <account>\n" +
//                    "        <email>newuser@gmail.com</email>\n" +
//                    "        <password>newpassword</password>\n" +
//                    "    </account>\n";
//
//            int insertPosition = xmlContent.indexOf("</user>");
//            if (insertPosition >= 0) {
//                xmlContent.insert(insertPosition, newAccountXml);
//            }
//
//            // Write the updated XML content back to the file
//            FileWriter fileWriter = new FileWriter(xmlFile);
//            fileWriter.write(xmlContent.toString());
//            fileWriter.close();
//
//            // The XML file now contains the additional <account> element
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
//
//            fileOutputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void initUi() {
        txtIsValidSignUp = findViewById(R.id.txt_is_valid_sign_up);
        edtEmail = findViewById(R.id.edt_email_sign_up);
        edtPassword = findViewById(R.id.edt_pass_sign_up);
        edtConfirm = findViewById(R.id.edt_confirm_sign_up);
        btnSignUp = findViewById(R.id.btn_sign_up);

        progressDialog = new ProgressDialog(this);
    }


}
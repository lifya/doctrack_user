package com.pln.www.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pln.www.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private Button bLogin;
    private EditText etUsername, etPassword;
    private ProgressDialog progressDialog;
    private TextView fpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        etUsername = (EditText) findViewById(R.id.etOldPassword);
        etPassword = (EditText) findViewById(R.id.etNewPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);
      //  progressDialog = new ProgressDialog(this);


        fpass = (TextView) findViewById(R.id.fpass);
        fpass.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            sendtoStart();
        }
    }

    public void sendtoStart(){
        Intent startIntent = new Intent(LoginActivity.this, BerandaActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void onWait(){
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }



    private void userLogin(){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        onWait();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), BerandaActivity.class));
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == bLogin){
            userLogin();
        }
        else if (v == fpass) {
            RessetPasswordActivity home = new RessetPasswordActivity();
            sentoReset();
        }
    }

    private void sentoReset() {
            Intent startIntent = new Intent(LoginActivity.this, RessetPasswordActivity.class);
            startActivity(startIntent);
            finish();
    }
}

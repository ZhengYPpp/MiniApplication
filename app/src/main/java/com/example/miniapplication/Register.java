package com.example.miniapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Register extends AppCompatActivity {
    TextView registerGoLoginText;
    EditText registerAccount, registerPassword,registerComfrimPassword,regesterPhone;
    Button registerButton;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerAccount = findViewById(R.id.account);
        registerPassword = findViewById(R.id.password);
        registerComfrimPassword = findViewById(R.id.comfrimPassword);
        regesterPhone = findViewById(R.id.phone);
        registerButton = findViewById(R.id.registerBtn);
        registerGoLoginText = findViewById(R.id.goLoginText);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registerGoLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerAccount.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                String cfPassword = registerComfrimPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    registerAccount.setError("This Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    registerPassword.setError("This password is Required");
                    return;
                }

                if(!cfPassword.equals(password) ){
                    Toast.makeText(Register.this,"error" + password + cfPassword,Toast.LENGTH_SHORT).show();
                    registerComfrimPassword.setError("passwords not match");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Register.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}
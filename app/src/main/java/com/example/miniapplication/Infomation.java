package com.example.miniapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Infomation extends AppCompatActivity {

    TextView role;
    EditText name,phone,postcode,adress;
    Button submit;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);

        role = findViewById(R.id.role);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        postcode = findViewById(R.id.postcode);
        adress = findViewById(R.id.address);
        submit = findViewById(R.id.submit);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null){
            String s = b.getString("name");
            //Toast.makeText(Infomation.this,s,Toast.LENGTH_SHORT).show();
            role.setText(s);
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                String userPostcode = postcode.getText().toString().trim();
                String userAddress = adress.getText().toString().trim();

                if(TextUtils.isEmpty(userName)){
                    name.setError("This Name is Required");
                    return;
                }

                if(TextUtils.isEmpty(userPhone)){
                    phone.setError("This phone is Required");
                    return;
                }

                if(TextUtils.isEmpty(userPostcode)){
                    postcode.setError("This postcode is Required");
                    return;
                }

                if(TextUtils.isEmpty(userAddress)){
                    adress.setError("This address is Required");
                    return;
                }

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("address").document(userID);
                Map<String,Object> Address = new HashMap<>();
                Address.put("name",userName);
                Address.put("phone",userPhone);
                Address.put("postcode",userPostcode);
                Address.put("address",userAddress);

                documentReference.set(Address).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Infomation.this,"OnSuccess :user Profile is created for "+userID,Toast.LENGTH_SHORT).show();
                    }
                });


                GoBackMain();
            }
        });



    }

    private void GoBackMain(){
        Intent i = new Intent(Infomation.this,MainActivity.class);
        startActivity(i);
    }

}
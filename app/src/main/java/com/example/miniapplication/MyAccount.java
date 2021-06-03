package com.example.miniapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccount extends AppCompatActivity {
    Button logoutBtn,deleteAddress1,deleteAddress2,deleteAddress3;
    private FirebaseAuth mAuth;
    EditText Address1,Address2,Address3;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        // Initialize And Assign Variable 初始化并分配变量
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set "send" as the default
        bottomNavigationView.setSelectedItemId(R.id.account);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.check:
                        startActivity(new Intent(getApplicationContext(),
                                CheckItem.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.send:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        return true;
                }
                return false;
            }
        });

        logoutBtn = findViewById(R.id.logoutButton);
        mAuth = FirebaseAuth.getInstance();
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MyAccount.this,Login.class);
                startActivity(intent);
            }
        });

        Address1 = findViewById(R.id.address1);
        Address2 = findViewById(R.id.address2);
        Address3 = findViewById(R.id.address3);

        Address1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,Infomation.class);
                intent.putExtra("name","AddressOne");
                startActivity(intent);
            }
        });

        Address2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,Infomation.class);
                intent.putExtra("name","AddressTwo");
                startActivity(intent);
            }
        });

        Address3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,Infomation.class);
                intent.putExtra("name","AddressThree");
                startActivity(intent);
            }
        });

        // Set Value
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("AddressOne").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(MyAccount.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();
                        Address1.setText(document.getString("name") + "\n" + document.getString("phone")+ "\n"+ document.getString("postcode")+"\n"+document.getString("address"));
                    } else {
                        Toast.makeText(MyAccount.this, "No such document",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyAccount.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        DocumentReference docRef2 = fStore.collection("AddressTwo").document(userID);
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(MyAccount.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();
                        Address2.setText(document.getString("name") + "\n" + document.getString("phone")+ "\n"+ document.getString("postcode")+"\n"+document.getString("address"));
                    } else {
                        Toast.makeText(MyAccount.this, "No such document",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyAccount.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        DocumentReference docRef3 = fStore.collection("AddressThree").document(userID);
        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(MyAccount.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();
                        Address3.setText(document.getString("name") + "\n" + document.getString("phone")+ "\n"+ document.getString("postcode")+"\n"+document.getString("address"));
                    } else {
                        Toast.makeText(MyAccount.this, "No such document",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyAccount.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete Address
        deleteAddress1 = findViewById(R.id.delAddress1);
        deleteAddress2 = findViewById(R.id.delAddress2);
        deleteAddress3 = findViewById(R.id.delAddress3);

        deleteAddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef.delete();
                Address1.setText("");
            }
        });

        deleteAddress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef2.delete();
                Address2.setText("");
            }
        });

        deleteAddress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef3.delete();
                Address3.setText("");
            }
        });


    }
}
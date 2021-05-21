package com.example.miniapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText sender,receiver;
    Spinner spin;
    String [] Type = {"Daily Use","Food","Doc","Electronics","Clothes"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize And Assign Variable 初始化并分配变量
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set "send" as the default
        bottomNavigationView.setSelectedItemId(R.id.send);

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
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),
                                MyAccount.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        sender = findViewById(R.id.sendInfo);
        receiver = findViewById(R.id.receiveInfo);

        //Jump to Sender Info page
        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Infomation.class);
                intent.putExtra("name","Sender");
                startActivity(intent);
            }
        });

        //Jump to Receiver Info page
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Infomation.class);
                intent.putExtra("name","Receiver");
                startActivity(intent);
            }
        });

        //Set Spinner
        spin = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);



    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected Item Description: "+Type[position] ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}
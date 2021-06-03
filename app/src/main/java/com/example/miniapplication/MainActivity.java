package com.example.miniapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText sender,receiver;
    Button submit;
    Spinner spin;
    String [] Type = {"Daily Use","Food","Doc","Electronics","Clothes"};

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    DatabaseReference OrderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize And Assign Variable 初始化并分配变量
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set "send" as the default
        bottomNavigationView.setSelectedItemId(R.id.send);

        // Perform ItemSelectedListener
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

        // Jump to Sender Info page
        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Infomation.class);
                intent.putExtra("name","Sender");
                startActivity(intent);
            }
        });

        // Jump to Receiver Info page
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Infomation.class);
                intent.putExtra("name","Receiver");
                startActivity(intent);
            }
        });


        // Set Value
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("Sender").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(MainActivity.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();
                        sender.setText(document.getString("name") + "\n" + document.getString("phone")+ "\n"+ document.getString("postcode"));
                    } else {
                        Toast.makeText(MainActivity.this, "No such document",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        DocumentReference docRef2 = fStore.collection("Receiver").document(userID);
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(MainActivity.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();
                        receiver.setText(document.getString("name") + "\n" + document.getString("phone")+ "\n"+ document.getString("postcode"));
                    } else {
                        Toast.makeText(MainActivity.this, "No such document",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set Spinner
        spin = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);


        // Submit
        OrderRef = FirebaseDatabase.getInstance().getReference();
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(MainActivity.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();

                                String senderName = document.getString("name");
                                String senderPhone = document.getString("phone");
                                String senderPostcode = document.getString("postcode");
                                String senderAddress = document.getString("address");


                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast.makeText(MainActivity.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();

                                                String receiverName = document.getString("name");
                                                String receiverPhone = document.getString("phone");
                                                String receiverPostcode = document.getString("postcode");
                                                String receiverAddress = document.getString("address");

                                                String type = spin.getSelectedItem().toString();

                                                Timestamp time = Timestamp.now();

                                                System.out.println(time);

                                                String userRef = "order/" + userID ;

                                                DatabaseReference  UserOrderKeyRef = OrderRef.child("order").child(userID).push();

                                                String OrderPushID = UserOrderKeyRef.getKey();


//                                                DocumentReference documentReference = fStore.collection("order").document(userID);
                                                Map<String,Object> Address = new HashMap<>();
                                                Address.put("orderId",getTime()+ getRandomNum());
                                                Address.put("orderType",type);
                                                Address.put("createTime",time);
                                                Address.put("senderName",senderName);
                                                Address.put("senderPhone",senderPhone);
                                                Address.put("senderPostcode",senderPostcode);
                                                Address.put("senderAddress",senderAddress);
                                                Address.put("receiverName",receiverName);
                                                Address.put("receiverPhone",receiverPhone);
                                                Address.put("receiverPostcode",receiverPostcode);
                                                Address.put("receiverAddress",receiverAddress);

                                                Map AddressContent = new HashMap();
                                                AddressContent.put(userRef + "/" + OrderPushID, Address);

                                                OrderRef.updateChildren(AddressContent).addOnCompleteListener(new OnCompleteListener()
                                                {
                                                    @Override
                                                    public void onComplete(@NonNull Task task)
                                                    {
                                                        if (task.isSuccessful())
                                                        {
                                                            Toast.makeText(MainActivity.this, "Order created", Toast.LENGTH_SHORT).show();
                                                            sender.setText("");
                                                            receiver.setText("");
                                                            docRef2.delete();
                                                            docRef.delete();

                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


//                                                documentReference.set(Address).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//
//                                                        Toast.makeText(MainActivity.this,"The order has been created for " + userID,Toast.LENGTH_SHORT).show();
//                                                    }
//                                                });



                                            } else {
                                                Toast.makeText(MainActivity.this, "No such document",Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(MainActivity.this, "No such document",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });




//                    System.out.println("/以上为testRandom1()的测试///" + getTime()+ getRandomNum() + temp + str );




            }
        });

    }

    public static String getTime() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMdd");
        System.out.println("时间戳："+sdfTime.format(new Date()));
        return sdfTime.format(new Date());
    }

    public static int getRandomNum(){
        Random r = new Random();
        return r.nextInt(900000000)+100000000;
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected Item Description: "+Type[position] ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}
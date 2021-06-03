package com.example.miniapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CheckItem extends AppCompatActivity {
    private final List<Order> orderList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private OrderAdapter orderAdapter;
    private RecyclerView OrderListView;

    FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private DatabaseReference OrderRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);

        // Initialize And Assign Variable 初始化并分配变量
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set "send" as the default
        bottomNavigationView.setSelectedItemId(R.id.check);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.check:
                        return true;
                    case R.id.send:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
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

        // RecycleView
        orderAdapter = new OrderAdapter(orderList);
        OrderListView = (RecyclerView) findViewById(R.id.order_list);
        linearLayoutManager = new LinearLayoutManager(this);
        OrderListView.setLayoutManager(linearLayoutManager);
        OrderListView.setAdapter(orderAdapter);




    }

    @Override
    protected void onStart()
    {
        super.onStart();

        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
        String userID = fAuth.getCurrentUser().getUid();

        OrderRef = FirebaseDatabase.getInstance().getReference();
        OrderRef.child("order").child(userID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s)
                    {
                        Order order = dataSnapshot.getValue(Order.class);

                        orderList.add(order);

                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s)
                    {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot)
                    {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });
//
//        DocumentReference docRef = fStore.collection("order").document(userID);
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Order order = documentSnapshot.toObject(Order.class);
//
//                     orderList.add(order);
//                     orderAdapter.notifyDataSetChanged();
//            }
//        });


//        DocumentReference docRef = fStore.collection("order").document(userID);
//
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Toast.makeText(CheckItem.this, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();
//
////                        Order order = document.getData();
////
////                        orderList.add(order);
////
////                        orderAdapter.notifyDataSetChanged();
//
//                    } else {
//                        Toast.makeText(CheckItem.this, "No such document",Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(CheckItem.this, "get failed with " + task.getException(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }
}
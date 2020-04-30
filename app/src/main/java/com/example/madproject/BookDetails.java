package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetails extends AppCompatActivity {

    EditText et1,et2,et3,et4,et5;
    Button b2,b3,b4,b5;
    Bus bus;
    DatabaseReference dbRef;

    private void clearControls(){
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        et1 = findViewById(R.id.e_from);
        et2 = findViewById(R.id.e_to);
        et3 = findViewById(R.id.e_date);
        et4 = findViewById(R.id.e_time);
        et5 = findViewById(R.id.e_seat);


        b2 = findViewById(R.id.btn_book1);
        b3 = findViewById(R.id.btn_show);
        b4 = findViewById(R.id.btn_update);
        b5 = findViewById(R.id.btn_del);


        bus = new Bus();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Bus");

                try{
                    if(TextUtils.isEmpty(et1.getText().toString()))
                        Toast.makeText(getApplicationContext(),"From",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(et2.getText().toString()))
                        Toast.makeText(getApplicationContext(),"To",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(et3.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Date",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(et4.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Time",Toast.LENGTH_SHORT).show();

                    else{
                        bus.setFrom(et1.getText().toString().trim());
                        bus.setTo(et2.getText().toString().trim());
                        bus.setDate(et3.getText().toString().trim());
                        bus.setTime(et3.getText().toString().trim());
                        bus.setNoOfSeats(Integer.parseInt(et4.getText().toString().trim()));

                        dbRef.push().setValue(bus);
                        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }
                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Bus").child("Bus1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            et1.setText(dataSnapshot.child("from").getValue().toString());
                            et2.setText(dataSnapshot.child("to").getValue().toString());
                            et3.setText(dataSnapshot.child("date").getValue().toString());
                            et4.setText(dataSnapshot.child("time").getValue().toString());
                            et5.setText(dataSnapshot.child("noOfseats").getValue().toString());
                        } else {
                            Toast.makeText(getApplicationContext(),"No source to display", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Bus");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Bus1")){
                            try{
                                bus.setFrom(et1.getText().toString().trim());
                                bus.setTo(et2.getText().toString().trim());
                                bus.setDate(et3.getText().toString().trim());
                                bus.setTime(et4.getText().toString().trim());
                                bus.setNoOfSeats(Integer.parseInt(et5.getText().toString().trim()));

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Bus").child("Bus1");
                                dbRef.setValue(bus);
                                clearControls();

                                Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                            }
                            catch(NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid seat Number",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Source to Update",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Bus1")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Bus").child("Bus1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Source to Delete",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
            }
        });



    }
}

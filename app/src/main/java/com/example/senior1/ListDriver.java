package com.example.senior1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ListDriver extends AppCompatActivity {
    ArrayList<String> listItems;
    ArrayList<String > listOfID;
    //DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference2;
    FirebaseDatabase firebaseDatabase2;
    ArrayAdapter<String> adapter;
   // private FirebaseAuth mAuth;
    ListView listView;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_driver);




        listItems = new ArrayList<>();
        listOfID = new ArrayList<>();
        listOfID.add("0");
         listView = findViewById(R.id.list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        listItems.add("SOURCE==>DESTINATION");
        adapter.notifyDataSetChanged();
        loadingBar = new ProgressDialog(getApplicationContext());
        loadingBar.setTitle("loding all  Governorate");
        loadingBar.setMessage("please wait ...");
        //loadingBar.show();
      //  mAuth = FirebaseAuth.getInstance();


        listView.setOnItemClickListener((listView, itemView, itemPosition, itemId) -> {

            Toast.makeText(getApplicationContext(), "index="+itemPosition, Toast.LENGTH_SHORT).show();
            //adapter.getPosition(listView.getSelectedItem().toString());
            Intent i = new Intent(getApplicationContext(), DriverCall.class);
            i.putExtra("driverid",listOfID.get((int) itemId));
            System.out.println(listOfID.get((int) itemId));
            startActivity(i);
        });
    }



    @Override
    protected void onStart() {

        try {

            databaseReference2 = firebaseDatabase2.getReference("event");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    System.out.println("onDataChange  ");
                    int i = 1;
                    for (DataSnapshot child : snapshot.getChildren()) {

                        event e = child.getValue(event.class);
                        if (Objects.requireNonNull(e).src_District != null) {
                            listItems.add(i + "--" + e.src_District + "==>" + e.dest_District);
                            listOfID.add(e.id);
                            i++;
                        }

                        System.out.println("getLocation_Name_En  " + e);
                        adapter.notifyDataSetChanged();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    System.out.println("onCancelled: " + error.getMessage());

                }
            });
           // loadingBar.dismiss();
           // Intent gototab = new Intent(getApplicationContext(),tab.class);
            //startActivity(gototab);
        } catch (DatabaseException e) {
            Toast.makeText(getApplicationContext(), "failed save becose" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        adapter.notifyDataSetChanged();
        super.onStart();
    }
}

//                    databaseReference2.child(d.Governorate).child(d.District).child(d.Pcode).setValue(d.Location_Name_En);
//                    hashdist.add(d.Location_Name_En);

//                    String gover=d.Governorate;
//                    String distr=d.District;
//
//                    if(!hashdist.contains(distr))
//                    {
//                        hashcover.add(gover);
//                        hashdist.add(distr);
//                        databaseReference2.child(d.Governorate).child(d.District).child(d.Pcode).setValue(d.Location_Name_En);
//

//                    }
// listItems.add(destArea.getGovernorate());

// databaseReference2 = firebaseDatabase2.getReference("Governorate");

//  databaseReference = firebaseDatabase.getReference("Governorate");
//   HashSet<String> hashcover;
//    HashSet<String> hashdist;
//        databaseReference = firebaseDatabase.getReference("Profile").child(GetSrcDest.p.getId());
//        Log.i("tg", "firebaseDatabase: "+firebaseDatabase);
//  databaseReference = firebaseDatabase.getReference("DB_LB");
//System.out.println("ffff"+databaseReference.);
// databaseReference.orderByChild("Governorate").equalTo("Mount Lebanon");
//  for(String i : hashdist)
//                {
//                    listItems.add(i);
//                     System.out.println("/////////: "+i);
//                    adapter.notifyDataSetChanged();
//                }
//   hashcover=new HashSet<>();
//                hashdist=new HashSet<>();
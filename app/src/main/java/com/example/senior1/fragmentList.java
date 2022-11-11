package com.example.senior1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.jvm.internal.Intrinsics;


public class fragmentList extends Fragment {

    ArrayList<String > listOfID;
    //DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference2;
    FirebaseDatabase firebaseDatabase2;
    ArrayAdapter<String> adapter;
    ListView listView;
    String type;
    String s;
    String  dest,src;
    String datadistrictsrc,datadistrictdst;
    Query query;

    public fragmentList() {}

// private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    View view;
    ArrayList<String> listItems;
    SharedPreferences  sp2,sp;

//  public  ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if(result.getResultCode()== Activity.RESULT_OK){
//                        Intent i2=result.getData();
//                       src=i2.getStringExtra("districtsrc");
//                          dest=i2.getStringExtra("districtdest");
//                    }
//
//                }
//            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_list, container, false);
        Intrinsics.checkNotNullExpressionValue(view, "inflater.inflate(R.layou…agment, container, false)");
       Bundle bundle= this.getArguments();
       if(bundle.getString("districtsrc")!=null) {
           datadistrictsrc = bundle.getString("districtsrc");
           datadistrictdst = bundle.getString("districtdest");
       }

        Log.i("tg", "haaaasaaaaannnnnn: "+datadistrictsrc+":: "+datadistrictdst);

        SharedPreferences  sp = this.getActivity().getSharedPreferences("seprateuser", Context.MODE_PRIVATE);

         sp2 = this.getActivity().getSharedPreferences("bundle", Context.MODE_PRIVATE);
        SharedPreferences.Editor e =sp2.edit();
         if(datadistrictsrc!=null) {
             e.putString("districtsrc", datadistrictsrc);
//        e.putString("districtdest","hello");
             Log.i("tg", "yyyyyyyyyy: ");
             e.apply();
             e.commit();


         }


        type= sp.getString("ProfileType","");
        Log.i("tg", "ProfileType77777777777: "+type);

        listItems = new ArrayList<>();
        listOfID = new ArrayList<>();
        listOfID.add("0");
        listView=( ListView)view.findViewById(R.id.list2);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listItems);
       // ad = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);


        loadingBar = new ProgressDialog(getContext());
        loadingBar.setTitle("loading all  Governorate");
        loadingBar.setMessage("please wait ...");
        listView.setOnItemClickListener((listView, itemView, itemPosition, itemId) -> {
            Log.i("tg", "itemPosition: "+itemPosition+"itemId:"+itemId);
            if(itemPosition==0) {
                Intent i = new Intent(getContext(), spinnerActivity.class);
                i.putExtra("mode",s);
                startActivity(i);
               // mGetContent.launch(i);

            }
            else {
                 if( (int)itemId<listOfID.size()){
                    Intent i = new Intent(getContext(), DriverCall.class);
                    i.putExtra("driverid", listOfID.get((int) itemId));
                    System.out.println(listOfID.get((int) itemId));
                    startActivity(i);
                }

            }
        });
            if(type.equals("Driver")){
                s="____add Event____";
            }else{
                s="<Search...";
            }

        return view;
    }

    @Override
    public void onStart() {
        Log.i("tg", "onStart: ");
        SharedPreferences  sp = this.getActivity().getSharedPreferences("seprateuser", Context.MODE_PRIVATE);
        type= sp.getString("ProfileType","");
        Log.i("tg", "type: "+type);
        try {


            if(type.equals("Driver"))
            {
                listItems.clear();
                listItems.add(s);
                Log.i("tg", "adddddd: ");
                databaseReference2 = firebaseDatabase2.getReference("event");
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Log.i("tg", "onDataChange: ");

                        int i = 1;
                        for (DataSnapshot child : snapshot.getChildren()) {
                            event e = child.getValue(event.class);
                            if (Objects.requireNonNull(e).src_District != null) {

                                listItems.add(i + "--" + e.src_District + "==>" + e.dest_District);
                                listOfID.add(e.id);
                                i++;

                            }
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        System.out.println("onCancelled: " + error.getMessage());

                    }
                });

            }
            else if(type.equals("Rider"))
            {

                    listItems.clear();
                    listItems.add(s);
                    Log.i("tg", "adddddd: "+datadistrictsrc);

                        databaseReference2 = firebaseDatabase2.getReference("event");
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Log.i("tg", "onDataChange: ");
                                   // &&datadistrictsrc!=null
                                int i = 1;
                               listOfID.clear();
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    event e = child.getValue(event.class);
                                    if (Objects.requireNonNull(e).src_District != null &&sp2.getString("districtsrc","")!=null)
                                    {

                                        if (e.getSrc_District().trim().equals(sp2.getString("districtsrc","").trim()))
                                        {
                                            Log.i("tg", i + "--" + e.src_District + "==>" + e.dest_District);
                                            listItems.add(i + "--" + e.src_District + "==>" + e.dest_District);
                                            listOfID.add(e.id);
                                            i++;
                                        }

                                    }
                                    adapter.notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                System.out.println("onCancelled: " + error.getMessage());

                            }
                        });

                }






        } catch (DatabaseException e) {
            Toast.makeText(getContext(), "failed save because: " + e.getMessage(), Toast.LENGTH_SHORT).show();


        }
        adapter.notifyDataSetChanged();
        super.onStart();
    }


}
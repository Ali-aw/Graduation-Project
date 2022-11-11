package com.example.senior1;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoginRegister extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("tg", "888888888888888888onResume888888888888: ");

        SharedPreferences sp=getSharedPreferences("seprateuser", Context.MODE_PRIVATE);
        Log.i("tg", "onResume: "+sp.getBoolean("login",false));
        if(sp.getBoolean("login",false))
        {
            Log.i("tg", "onResume1111: "+sp.getBoolean("login",false));
            SharedPreferences.Editor e =sp.edit();
            e.putString("ProfileType",type);

            e.apply();
            e.commit();
            Toast.makeText(getApplicationContext(), "I am"+sp.getString("ProfileType",""), Toast.LENGTH_SHORT).show();

            Intent i= new Intent(getApplicationContext(),tab.class);
            startActivity(i);
        }

    }
    private FirebaseAuth mAuth;

    private TextView register_link;

    private Button login_button, register_button;
    private EditText Email, Password, first_name, last_name, mobile_nb;
    private ProgressDialog loadingBar;
    private DatabaseReference Database_Ref;
    private static String tid;
    Spinner spinner;
    private String checker = "";
    private CircleImageView circle_Image_View;
    ArrayList<String> prf;
    Uri resultUri;
    FirebaseDatabase firebaseDatabase1;
    public static Profile p;
    boolean SignUp = false;
    Handler handlermain;
    FirebaseUser firebaseUser;


    SharedPreferences sh;
    RadioButton Rd_male;
    RadioButton Rd_female;
    RadioGroup RG_genre;
    String genre = "g";
    AlertDialog alert,alertDialog;
    AlertDialog alert2;
    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    ImageButton bt_image;
    SignInButton btSignIn;
    String type;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("745652453193-9k50sb34smiv4rbnukf2cjsvbt9dbt8b.apps.googleusercontent.com").requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);
        btSignIn=findViewById(R.id.bt_sign_in);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize sign in intent
//                Email.setEnabled(false);
//                Password.setEnabled(false);
//                first_name.setEnabled(false);
//                last_name.setEnabled(false);
                Intent intent=googleSignInClient.getSignInIntent();
                // Start activity for result
                startActivityForResult(intent,100);
            }
        });


        handlermain = new Handler(this.getMainLooper());
        ////spinner
//        String[] proffessionitems = new String[]{"Proffession", "Good", "Normal", "Satisfactory"};
//        spinner = findViewById(R.id.Profession);
//        final List<String> arrayList = new ArrayList<>(Arrays.asList(proffessionitems));
//        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_customer_login_register);
//        spinner.setAdapter(spinnerArrayAdapter);
//        spinnerArrayAdapter.notifyDataSetChanged();
// spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_customer_login_register);






        
        Rd_male = findViewById(R.id.male);
        Rd_female = findViewById(R.id.female);
        RG_genre = findViewById(R.id.radioGroup2);
        first_name = findViewById(R.id.firstname);
        last_name = findViewById(R.id.lastname);
        mobile_nb = findViewById(R.id.mobile_nb);
        login_button = findViewById(R.id.customer_login);
        register_button = findViewById(R.id.register_button);
        register_link = findViewById(R.id.register_link);

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        ///////////////////////////////////////////////////////////

        register_button.setVisibility(View.INVISIBLE);
        register_button.setEnabled(false);
        first_name.setVisibility(View.INVISIBLE);
        last_name.setVisibility(View.INVISIBLE);
        mobile_nb.setVisibility(View.INVISIBLE);
        btSignIn.setVisibility(View.INVISIBLE);
        login_button.setEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        p = new Profile();
        Intent intent = getIntent();
         type=intent.getStringExtra("ProfileType");
        p.setProfileType(type);

        //link--
        register_link.setTextColor(Color.BLUE);
        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSignIn.setVisibility(View.VISIBLE);
                login_button.setVisibility(View.INVISIBLE);
                register_link.setVisibility(View.INVISIBLE);
                register_button.setVisibility(View.VISIBLE);
                first_name.setVisibility(View.VISIBLE);
                last_name.setVisibility(View.VISIBLE);
                mobile_nb.setVisibility(View.VISIBLE);

                register_button.setEnabled(true);
            }
        });


        //radio groupe
        RG_genre.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                genre = ((RadioButton) findViewById(RG_genre.getCheckedRadioButtonId()))
                        .getText().toString();
                System.out.println(genre);
            }
        });
        //AlertDialog
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("Save?");
        builder2.setCancelable(true);
        builder2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i("tg", "888888888888888888888888888888: ");
                  //  registerByGoogle();
                    storeInRealTimedb(p);

        }
        });
        builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert2 = builder2.create();

        //image button
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setMessage("Save?");
        builder3.setCancelable(true);
        builder3.setPositiveButton("save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i("tg", "888888888888888888888888888888: ");
              //  if(validateFields())
                {
                    registerByGoogle();

                }

            }
        });
        builder3.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialog = builder3.create();




    }
    private boolean validateFields() {
        int yourDesiredLength = 8;
        if (mobile_nb.getText().length() < yourDesiredLength) {
            mobile_nb.setError("Write a 8 digit Valid Phone Number Then Enter Your Gender ");
            return false;
        } else if (!Rd_male.isChecked() && !Rd_female.isChecked()) {
            Toast.makeText(getApplicationContext(), "Enter Your Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public  void registerByGoogle() {
        Log.i("tg", "333333333333333: ");
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Log.i("tg", "222222222222: "+mAuth.getCurrentUser().getPhoneNumber());
            String email = firebaseUser.getEmail();

            btSignIn.setVisibility(View.INVISIBLE);
            Password.setVisibility(View.INVISIBLE);




            String[] name =firebaseUser.getDisplayName().split(" ");
            p.setEmail(firebaseUser.getEmail());
            p.setPhoneNb(mobile_nb.getText().toString());
            p.setGenre(genre);
            p.setLName(name[1]);
            p.setFName(name[0]);
            p.setId(firebaseUser.getUid());
            Email.setText(firebaseUser.getEmail());
            Password.setText("******");
            first_name.setText(name[1]);
            last_name.setText(name[0]);

            SignUp = true;
            storeInRealTimedb(p);


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if(requestCode==100)
        {
            // When request code is equal to 100
            // Initialize task
            Task<GoogleSignInAccount> signInAccountTask=GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            // check condition
            if(signInAccountTask.isSuccessful())
            {
                // When google sign in successful
                // Initialize string
                String s="Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount=signInAccountTask
                            .getResult(ApiException.class);
                    // Check condition
                    if(googleSignInAccount!=null)
                    {
                        // When sign in account is not equal to null
                        // Initialize auth credential
                        AuthCredential authCredential= GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        ,null);
                        // Check credential
                        mAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Check condition
                                        if(task.isSuccessful())
                                        {

                                            // Check condition

                                            sh = getSharedPreferences("id", Context.MODE_APPEND);
                                            SharedPreferences.Editor e = sh.edit();

                                            e.putString("id", mAuth.getCurrentUser().getUid());
                                            e.commit();

                                                alertDialog.show();

                                                // When user already sign in
                                                // redirect to profile activity


//                                            Intent intent = getIntent();
//                                            String type = intent.getStringExtra("ProfileType");
//                                             if(type.equals("Driver"))
//                                             {
//                                                 startActivity(new Intent(getApplicationContext(),spinnerActivity.class)
//
//                                                         .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                                             }
//                                             else
//                                             {
//                                                 startActivity(new Intent(getApplicationContext(),ListDriver.class)
//
//                                                         .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                                             }










                                            // When task is successful
                                            // Redirect to profile activity
                                           // startActivity(new Intent(getApplicationContext()
                                             //       ,tab.class)
                                              //      .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            // Display Toast
                                            displayToast("Firebase authentication successful");

                                        }
                                        else
                                        {
                                            // When task is unsuccessful
                                            // Display Toast
                                            displayToast("Authentication Failed :"+task.getException()
                                                    .getMessage());
                                        }
                                    }
                                });

                    }
                }
                catch (ApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Register Customer  by imail and pass
    public void Register2(View view) {

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String FName = first_name.getText().toString();
        String LName = last_name.getText().toString();
        String PhoneNb = mobile_nb.getText().toString();
        p.setPhoneNb(PhoneNb);
        p.setFName(FName);
        p.setLName(LName);
        p.setEmail(email);
        p.setPassword(password);
        p.setGenre(genre);
        Register(p);

    }

    private void Register(Profile Customer) {


        if (TextUtils.isEmpty(p.email)) {
            Toast.makeText(getApplicationContext(), "Please write the Email...", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(p.password)) {
            Toast.makeText(getApplicationContext(), "Please write the Password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Customer Registration");
            loadingBar.setMessage("please wait ...");
            loadingBar.show();

            //add data on firebase
            mAuth.createUserWithEmailAndPassword(p.email, p.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        SignUp = true;

                        sh = getSharedPreferences("id", Context.MODE_APPEND);
                        SharedPreferences.Editor e = sh.edit();

                        e.putString("id", mAuth.getCurrentUser().getUid());
                        e.commit();

                        tid = mAuth.getCurrentUser().getUid();
                        p.setId(mAuth.getCurrentUser().getUid());
                        System.out.println("id" + tid);


                    } else {
                        Toast.makeText(getApplicationContext(), "Customer Register unsucessfully!!", Toast.LENGTH_SHORT).show();
                    }
                    loadingBar.dismiss();
                    alert2.show();
                }
            });


        }
    }

    private void storeInRealTimedb(Profile customer) {

        Log.i("tg", "OnlineCustid.. " + SignUp);
        if (SignUp == true) {
            Log.i("tg", "RegisterCustomer2RegisterCustomer2: ");
            if (TextUtils.isEmpty(customer.FName)) {

                Toast.makeText(getApplicationContext(), "Please write the FName...", Toast.LENGTH_SHORT).show();
            }

            if (TextUtils.isEmpty(customer.LName)) {

                Toast.makeText(getApplicationContext(), "Please write the LName...", Toast.LENGTH_SHORT).show();

            }
            if (TextUtils.isEmpty(customer.PhoneNb) && customer.PhoneNb.length() != 8) {

                Toast.makeText(getApplicationContext(), "Please write the PhoneNb...", Toast.LENGTH_SHORT).show();

            }
            if (genre.equals("g")) {

                Toast.makeText(getApplicationContext(), "Please choose the genre...", Toast.LENGTH_SHORT).show();

            } else {

                HandlerThread handlerThread = new HandlerThread("myhandler");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                handler.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    p.setId(sh.getString("id", ""));

                                    System.out.println("id2" + p.getId());
                                    firebaseDatabase1 = FirebaseDatabase.getInstance();
                                    Database_Ref = firebaseDatabase1.getReference("profile");


                                    Database_Ref.child(p.getId()).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Log.i("tg", "RegisterCustomer2RegisterCustomer2..if: ");

                                            if (task.isSuccessful()) {
                                                SharedPreferences sp=getSharedPreferences("seprateuser", Context.MODE_PRIVATE);

                                                    SharedPreferences.Editor e =sp.edit();
                                                    e.putBoolean("login",Boolean.TRUE);
                                                    e.apply();
                                                Log.i("tg", "realtime: "+sp.getBoolean("login",false));


                                                Toast.makeText(getApplicationContext(), "Customer Register sucessfully", Toast.LENGTH_SHORT).show();
                                                        SignUp = false;
                                                        Intent ChooseSrcDst = new Intent(getApplicationContext(), tab.class);
                                                        //ChooseSrcDst.putStringArrayListExtra("profile",prf);
                                                        startActivity(ChooseSrcDst);
                                            } else {
                                                Toast.makeText(getApplicationContext(), " unsucessfully insert data!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.i("tg", "Exception.. RegisterCustomer2" + e.getMessage());
                                    Toast.makeText(getApplicationContext(), "Exception.. RegisterCustomer2" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        }
    }

    //login cutomer
    public void login(View view) {

        Log.i("tg", "inside Login ");
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        p.setEmail(email);
        p.setPassword(password);
        Login(p);
    }


    private void Login(Profile customer) {
        if (TextUtils.isEmpty(customer.email)) {
            Toast.makeText(getApplicationContext(), "Please write the Email...", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(customer.password)) {
            Toast.makeText(getApplicationContext(), "Please write the password...", Toast.LENGTH_SHORT).show();

        } else {


            Log.i("tg", " else ........signInWithEmailAndPassword Login ");
            loadingBar.setTitle("Customer login");
            loadingBar.setMessage("please wait ...");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(customer.email, customer.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(Task<AuthResult> task) {
                    Log.i("tg", "signInWithEmailAndPassword Login ");

                    if (task.isSuccessful()) {
                        sh = getSharedPreferences("id", Context.MODE_APPEND);
                        SharedPreferences.Editor e = sh.edit();

                        e.putString("id", mAuth.getCurrentUser().getUid());
                        e.commit();

                        p.setId(mAuth.getCurrentUser().getUid());
                        Log.i("tg", "cust id..." + mAuth.getCurrentUser().getUid());


                        Intent goToMapActivity = new Intent(getApplicationContext(), tab.class);

                        startActivity(goToMapActivity);
                        Toast.makeText(getApplicationContext(), "Customer login sucessfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Customer login unsucessfully!!", Toast.LENGTH_SHORT).show();
                    }
                    loadingBar.dismiss();

                }
            });


        }

    }


}
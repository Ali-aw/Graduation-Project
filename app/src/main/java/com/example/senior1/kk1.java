package com.example.senior1;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

public final class kk1 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.setContentView(R.layout.activity_kk1);
        final EditText mEditText = (EditText)this.findViewById(R.id.editText);
        Button mButton = (Button)this.findViewById(R.id.button);
        FragmentManager var10000 = this.getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(var10000, "supportFragmentManager");
        FragmentManager mFragmentManager = var10000;
        FragmentTransaction var7 = mFragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(var7, "mFragmentManager.beginTransaction()");
        final FragmentTransaction mFragmentTransaction = var7;
        final MyCustomFragment mFragment = new MyCustomFragment();
        mButton.setOnClickListener((OnClickListener)(new OnClickListener() {

            public final void onClick(View it) {

                Bundle mBundle = new Bundle();
                EditText var10002 = mEditText;
                Intrinsics.checkNotNullExpressionValue(var10002, "mEditText");
                mBundle.putString("mText", var10002.getText().toString());
                mFragment.setArguments(mBundle);
                mFragmentTransaction.add(R.id.frameLayout, (Fragment)mFragment).commit();
            }
        }));
    }
}

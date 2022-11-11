package com.example.senior1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;


public final class MyCustomFragment extends Fragment {
    private TextView myTextView;

    @NotNull
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View view1 = inflater.inflate(R.layout.my_custom_fragment, container, false);
        Intrinsics.checkNotNullExpressionValue(view1, "inflater.inflate(R.layou…agment, container, false)");
        View view = view1;
        myTextView = (TextView) view1.findViewById(R.id.fragmentTextView);
        if (myTextView == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.widget.TextView");
        } else {
            this.myTextView = (TextView)myTextView;
            Bundle bundle = this.getArguments();
            Intrinsics.checkNotNull(bundle);
            String message = bundle.getString("mText");
            TextView var7 = this.myTextView;
            if (var7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("myTextView");
            }


            return view;
        }
    }


}

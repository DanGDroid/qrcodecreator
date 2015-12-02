package com.example.daniel.qrcodecreator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.qrcodecreator.R;
import com.example.daniel.qrcodecreator.activities.MainActivity;

/**
 * Created by Admin on 12/2/2015.
 */
public class Loginfragment extends Fragment implements View.OnClickListener {

    private EditText et;

    public static Loginfragment newInstance() {
        Loginfragment fragment = new Loginfragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {

        et = (EditText) view.findViewById(R.id.login_et);
        Button loginButton = (Button) view.findViewById(R.id.login_btn);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Log.d("Login", et.getText().toString());
        if (!et.getText().toString().isEmpty()) {
            hideKeyboard();
            ((MainActivity) getActivity()).addCreateQrFragment(et.getText().toString());
            et.setText("");
        } else {
            Toast.makeText(getActivity(), "NO email ENTERED", Toast.LENGTH_SHORT).show();
        }

    }




    public void hideKeyboard() {

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }
}

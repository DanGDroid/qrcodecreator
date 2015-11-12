package com.example.daniel.qrcodecreator.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.daniel.qrcodecreator.MyWifiProperties;
import com.example.daniel.qrcodecreator.R;
import com.example.daniel.qrcodecreator.fragments.CreateQrFragment;
import com.example.daniel.qrcodecreator.fragments.QrCodeFragment;
import com.example.daniel.qrcodecreator.fragments.WifiListFragment;

public class MainActivity extends AppCompatActivity {

    private WifiListFragment mWifiListFragment;
    private CreateQrFragment mCreateQrFragment;
    private QrCodeFragment mQrCodeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCreateQrFragment();

    }

    public void addWifiListFragment() {
        mWifiListFragment = WifiListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mWifiListFragment)
                .addToBackStack(null)
                .commit();
    }

    public void addCreateQrFragment() {
        mCreateQrFragment = CreateQrFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mCreateQrFragment)
                .addToBackStack(null)
                .commit();
    }

    public void addQrCodeFragment(MyWifiProperties my,String pass) {
        mQrCodeFragment = QrCodeFragment.newInstance(my,pass);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mQrCodeFragment)
                .addToBackStack(null)
                .commit();
    }

}

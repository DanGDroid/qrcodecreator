package com.example.daniel.qrcodecreator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daniel.qrcodecreator.fragments.Loginfragment;
import com.example.daniel.qrcodecreator.utils.MyWifiProperties;
import com.example.daniel.qrcodecreator.R;
import com.example.daniel.qrcodecreator.fragments.CreateQrFragment;
import com.example.daniel.qrcodecreator.fragments.QrCodeFragment;
import com.example.daniel.qrcodecreator.fragments.WifiListFragment;

public class MainActivity extends AppCompatActivity {

    private WifiListFragment mWifiListFragment;
    private CreateQrFragment mCreateQrFragment;
    private QrCodeFragment mQrCodeFragment;
    private Loginfragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addLoginFragment();

    }

    public void addWifiListFragment(String username) {
        mWifiListFragment = WifiListFragment.newInstance(username);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mWifiListFragment)
                .addToBackStack(null)
                .commit();
    }

    public void addLoginFragment() {
        mLoginFragment = Loginfragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mLoginFragment)
                .addToBackStack(null)
                .commit();
    }

    public void addCreateQrFragment(String username) {
        mCreateQrFragment = CreateQrFragment.newInstance(username);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mCreateQrFragment)
                .addToBackStack(null)
                .commit();
    }

    public void addQrCodeFragment(MyWifiProperties my,String pass,String username) {
        mQrCodeFragment = QrCodeFragment.newInstance(my,pass,username);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mQrCodeFragment)
                .addToBackStack(null)
                .commit();
    }

    public interface Listener <T>{

        void getResult(T item);
    }


}

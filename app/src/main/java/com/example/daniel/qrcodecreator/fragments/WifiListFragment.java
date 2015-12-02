package com.example.daniel.qrcodecreator.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.daniel.qrcodecreator.utils.MyWifiProperties;
import com.example.daniel.qrcodecreator.R;
import com.example.daniel.qrcodecreator.utils.WifiConfigManager;
import com.example.daniel.qrcodecreator.activities.MainActivity;
import com.example.daniel.qrcodecreator.adapters.WifiListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 12/11/2015.
 */
public class WifiListFragment extends Fragment {

    private String passwordInput;
    private String usernameInput;
    private ImageView qrIv;
    // private MyWifiProperties myWifi;
    private RecyclerView mRecyclerView;
    private WifiListAdapter adapter;


    public static WifiListFragment newInstance(String username) {
        Bundle args = new Bundle();
        WifiListFragment fragment = new WifiListFragment();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    /*
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            myWifi = new MyWifiProperties(getActivity());
        }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_list, container, false);
        usernameInput = getArguments().getString("username");
        findViews(view);
        if (adapter != null) {

            adapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter = new WifiListAdapter(getWifiList(), this);
            mRecyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void findViews(View view) {

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private List<MyWifiProperties> getWifiList() {

        List<MyWifiProperties> wifiList = new ArrayList<>();

        WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> networkList = wifi.getScanResults();
        if (networkList != null) {
            for (ScanResult network : networkList) {
                MyWifiProperties myWifi = new MyWifiProperties();
                String rawString = network.capabilities;
                myWifi.setSsid(network.SSID);
                Log.i("WIFI CONNECTION:  ", network.SSID + " capabilities : " + rawString);
                if (rawString.contains("nopass"))
                    myWifi.setType("nopass");
                if (rawString.contains("WEP"))
                    myWifi.setType("WEP");
                if (rawString.contains("WPA"))
                    myWifi.setType("WPA");
                if (myWifi.getType() != null)
                    wifiList.add(myWifi);
            }
            return wifiList;
        }
        //Take ceare if no Wifi at all
        return null;
    }

    public void showPasswordDialog(final MyWifiProperties myWifi) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("WIFI CONNECTION: " + myWifi.getSsid());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("ENTER PASSWORD");
        builder.setView(input);
        builder.setPositiveButton("Creat QR code", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passwordInput = input.getText().toString();
                myWifi.setPassword(passwordInput);
                //TODO fix check password vlidity//
                //connectToWifi(myWifi);
                ((MainActivity) getActivity()).addQrCodeFragment(myWifi, passwordInput,usernameInput);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void showNoPasswordDialog(final MyWifiProperties myWifi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("WIFI CONNECTION: " + myWifi.getSsid());
        builder.setPositiveButton("Creat QR code", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passwordInput = "";
                myWifi.setPassword(passwordInput);
              // connectToWifi(myWifi);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private boolean isConnected() {

        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    public  void connectToWifi(final MyWifiProperties myWifiProperties) {

        if (myWifiProperties != null) {
            new WifiConfigManager((WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE)
                    , new MainActivity.Listener() {
                @Override
                public void getResult(Object item) {

                    if(isConnected())
                        ((MainActivity) getActivity()).addQrCodeFragment(myWifiProperties, passwordInput,usernameInput);
                    else
                        Toast.makeText(getActivity(),"Try Another Password",Toast.LENGTH_SHORT).show();
                }
            }).execute(myWifiProperties);

        } else {
            Log.d("WifiSpark", "Wrong Wifi Params cannot parse!!");
        }
    }
}


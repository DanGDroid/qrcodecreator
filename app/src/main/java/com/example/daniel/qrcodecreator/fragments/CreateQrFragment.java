package com.example.daniel.qrcodecreator.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.qrcodecreator.R;
import com.example.daniel.qrcodecreator.activities.CreateQrActivity;
import com.example.daniel.qrcodecreator.activities.MainActivity;

/**
 * Created by Daniel on 12/11/2015.
 */
public class CreateQrFragment extends Fragment {

    public static CreateQrFragment newInstance(){
        CreateQrFragment fragment = new CreateQrFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_qr,container,false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {

        Button qrButton = (Button)view.findViewById(R.id.qr_btn);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).addWifiListFragment();
            }
        });
    }
}

package com.example.daniel.qrcodecreator.fragments;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.daniel.qrcodecreator.utils.MyWifiProperties;
import com.example.daniel.qrcodecreator.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by Daniel on 12/11/2015.
 */
public class QrCodeFragment extends Fragment {

    public static QrCodeFragment newInstance(MyWifiProperties myWifiProperties,String password){
        Bundle args = new Bundle();
        QrCodeFragment fragment = new QrCodeFragment();
        args.putString("pass",password);
        args.putSerializable("wifi",myWifiProperties);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code,container,false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {

        //TextView title = (TextView) view.findViewById(R.id.qr_title_tv);
        MyWifiProperties myWifi = (MyWifiProperties) getArguments().getSerializable("wifi");
        String passwordInput = getArguments().getString("pass");
        ImageView qrImage = (ImageView) view.findViewById(R.id.qr_iv);
        qrImage.setImageBitmap(toBitmap(createQr(
                qrCodeString(myWifi.getType(), myWifi.getSsid(), passwordInput))));
    }

    private BitMatrix createQr(String qrString) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix matrix = writer.encode(qrString, BarcodeFormat.QR_CODE, 400, 400);
            return matrix;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    private String qrCodeString(String type, String ssid, String password) {
        return "WIFI:T:" + type + ";S:" + ssid + ";P:" + password + ";;";
    }
}

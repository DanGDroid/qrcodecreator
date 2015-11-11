package com.example.daniel.qrcodecreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


/**
 * Created by Daniel on 09/11/2015.
 */
public class CreateQrActivity extends AppCompatActivity {

    private String passwordInput;
    private ImageView qrIv;
    private MyWifiProperties myWifi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_layout);
        myWifi = new MyWifiProperties(this);
        qrIv = (ImageView) findViewById(R.id.qr_iv);
        if (myWifi.getType().equals("nopass"))
            showNoPasswordDialog();
        else
            showPasswordDialog();
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

    private void showPasswordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WIFI CONNECTION: " + myWifi.getSsid());
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("ENTER PASSWORD");
        builder.setView(input);
        builder.setPositiveButton("Creat QR code", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passwordInput = input.getText().toString();
                qrIv.setImageBitmap(toBitmap(createQr(
                        qrCodeString(myWifi.getType(), myWifi.getSsid(), passwordInput))));
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

    private void showNoPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WIFI CONNECTION: " + myWifi.getSsid());
        builder.setPositiveButton("Creat QR code", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passwordInput = "";
                qrIv.setImageBitmap(toBitmap(createQr(
                        qrCodeString(myWifi.getType(), myWifi.getSsid(), passwordInput))));
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

    private String qrCodeString(String type, String ssid, String password) {
        return "WIFI:T:" + type + ";S:" + ssid + ";P:" + password + ";;";
    }
}


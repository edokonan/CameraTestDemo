package com.technoalliance.taocrexample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.polarxiong.camerademo.Demo1MainActivity;
import com.technoalliance.taocrlibrary.OcrImageCtrl;
import com.technoalliance.taocrlibrary.OcrResultInfo;
import com.technoalliance.taocrlibrary.OcrViewController;
import com.technoalliance.taocrlibrary.taOCRResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.technoalliance.taocrexample.R.id.activity_main;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            int grantResult = grantResults[0];
            boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //Demo1
        Button btnCameraDemo1 = (Button) findViewById(R.id.btnCameraDemo1);
        btnCameraDemo1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int SUBACTIVITY1 = 210;
                Intent intent = new Intent(getApplicationContext(), Demo1MainActivity.class);
                startActivityForResult(intent, SUBACTIVITY1);
            }
        });

        //Demo2
        final Button button = (Button) findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Intent it = new Intent(getApplicationContext(),OcrView.class);
                //startActivity(it);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
               if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    requestCameraPermission();
                   return;
                }
                //else
                //{
                //for(int i = 0; i< taOCRResult.recCount; i++)
               // {
               //    taOCRResult.ocrValues[i].rectScanImage.recycle();
               // }
                    int SUBACTIVITY1 = 208;

                for (int t1 = 0; t1 < 4; t1++) {
                    taOCRResult.ocrValues[t1] = new OcrResultInfo();
                    taOCRResult.ocrValues[t1].rectScanIndex=t1+1;
                }



                    final ImageView v1 = (ImageView) findViewById(R.id.imageView5);
                    v1.setImageBitmap(null);
                    final TextView t1 = (TextView) findViewById(R.id.textView5);
                    t1.setText("");
                    final TextView t11 = (TextView) findViewById(R.id.textView6);
                    t11.setText(OcrViewController.getOcrResult()[0].rectScanName);



                    final ImageView v18 = (ImageView) findViewById(R.id.imageView5);
                    v18.setImageBitmap(null);
                    final TextView t18 = (TextView) findViewById(R.id.textView5);
                    t18.setText("");
                    final TextView t118 = (TextView) findViewById(R.id.textView6);
                    t118.setText("");

                    final ImageView v2 = (ImageView) findViewById(R.id.imageView6);
                    v2.setImageBitmap(null);
                    final TextView t2 = (TextView) findViewById(R.id.textView7);
                    t2.setText("");
                    final TextView t22 = (TextView) findViewById(R.id.textView8);
                    t22.setText("");

                    final ImageView v28 = (ImageView) findViewById(R.id.imageView6);
                    v28.setImageBitmap(null);
                    final TextView t28 = (TextView) findViewById(R.id.textView7);
                    t28.setText("");
                    final TextView t228 = (TextView) findViewById(R.id.textView8);
                    t228.setText("");

                    final ImageView v3 = (ImageView) findViewById(R.id.imageView7);
                    v3.setImageBitmap(null);
                    final TextView t3 = (TextView) findViewById(R.id.textView9);
                    t3.setText("");
                    final TextView t33 = (TextView) findViewById(R.id.textView10);
                    t33.setText("");

                    final ImageView v38 = (ImageView) findViewById(R.id.imageView7);
                    v38.setImageBitmap(null);
                    final TextView t38 = (TextView) findViewById(R.id.textView9);
                    t38.setText("");
                    final TextView t338 = (TextView) findViewById(R.id.textView10);
                    t338.setText("");

                    final ImageView v4 = (ImageView) findViewById(R.id.imageView8);
                    v4.setImageBitmap(null);
                    final TextView t4 = (TextView) findViewById(R.id.textView11);
                    t4.setText("");
                    final TextView t44 = (TextView) findViewById(R.id.textView12);
                    t44.setText("");

                    final ImageView v48 = (ImageView) findViewById(R.id.imageView8);
                    v48.setImageBitmap(null);
                    final TextView t48 = (TextView) findViewById(R.id.textView11);
                    t48.setText("");
                    final TextView t448 = (TextView) findViewById(R.id.textView12);
                    t448.setText("");


                OcrViewController.CameraUICallBackInterface aInterface = new OcrViewController.CameraUICallBackInterface() {
                    @Override
                    public void btnStopClicked() {
                        Log.i("TA", "[btnStopClicked]");
                    }

                    @Override
                    public void btnManualInputClicked() {
                        Log.i("TA", "[btnManualInputClicked]");
                    }

                    @Override
                    public void onTimeOut() {
                        Log.i("TA", "[onTimeOut]");
                    }

                    @Override
                    public void setOcrResultCallBack() {

                    }
                };
                OcrViewController.outputImageLog(true);
                    OcrViewController.setCallBack(aInterface);
                    Intent intent = new Intent(getApplicationContext(), OcrViewController.class);
                    startActivityForResult(intent, SUBACTIVITY1);
               // }



            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(resultCode == 208) {
                String ss = data.getStringExtra("ta");
                if(ss.equals("rec_over")) {
                    if (OcrViewController.getOcrResult()[0] != null) {
                        final ImageView v1 = (ImageView) findViewById(R.id.imageView5);
                        v1.setImageBitmap(OcrViewController.getOcrResult()[0].rectScanImage);
                        final TextView t1 = (TextView) findViewById(R.id.textView5);
                        t1.setText(OcrViewController.getOcrResult()[0].rectScanValue);
                        final TextView t11 = (TextView) findViewById(R.id.textView6);
                        t11.setText(OcrViewController.getOcrResult()[0].rectScanName);
                    }
                    else
                    {
                        final ImageView v18 = (ImageView) findViewById(R.id.imageView5);
                        v18.setImageBitmap(null);
                        final TextView t18 = (TextView) findViewById(R.id.textView5);
                        t18.setText("");
                        final TextView t118 = (TextView) findViewById(R.id.textView6);
                        t118.setText("");
                    }
                    if (OcrViewController.getOcrResult()[1] != null) {
                        final ImageView v2 = (ImageView) findViewById(R.id.imageView6);
                        v2.setImageBitmap(OcrViewController.getOcrResult()[1].rectScanImage);
                        final TextView t2 = (TextView) findViewById(R.id.textView7);
                        t2.setText(OcrViewController.getOcrResult()[1].rectScanValue);
                        final TextView t22 = (TextView) findViewById(R.id.textView8);
                        t22.setText(OcrViewController.getOcrResult()[1].rectScanName);
                    }
                    else
                    {
                        final ImageView v28 = (ImageView) findViewById(R.id.imageView6);
                        v28.setImageBitmap(null);
                        final TextView t28 = (TextView) findViewById(R.id.textView7);
                        t28.setText("");
                        final TextView t228 = (TextView) findViewById(R.id.textView8);
                        t228.setText("");
                    }
                    if (OcrViewController.getOcrResult()[2] != null) {
                        final ImageView v3 = (ImageView) findViewById(R.id.imageView7);
                        v3.setImageBitmap(OcrViewController.getOcrResult()[2].rectScanImage);
                        final TextView t3 = (TextView) findViewById(R.id.textView9);
                        t3.setText(OcrViewController.getOcrResult()[2].rectScanValue);
                        final TextView t33 = (TextView) findViewById(R.id.textView10);
                        t33.setText(OcrViewController.getOcrResult()[2].rectScanName);
                    }
                    else {
                        final ImageView v38 = (ImageView) findViewById(R.id.imageView7);
                        v38.setImageBitmap(null);
                        final TextView t38 = (TextView) findViewById(R.id.textView9);
                        t38.setText("");
                        final TextView t338 = (TextView) findViewById(R.id.textView10);
                        t338.setText("");
                    }
                    if (OcrViewController.getOcrResult()[3] != null) {
                        final ImageView v4 = (ImageView) findViewById(R.id.imageView8);
                        v4.setImageBitmap(OcrViewController.getOcrResult()[3].rectScanImage);
                        final TextView t4 = (TextView) findViewById(R.id.textView11);
                        t4.setText(OcrViewController.getOcrResult()[3].rectScanValue);
                        final TextView t44 = (TextView) findViewById(R.id.textView12);
                        t44.setText(OcrViewController.getOcrResult()[3].rectScanName);
                    }
                    else
                    {
                        final ImageView v48 = (ImageView) findViewById(R.id.imageView8);
                        v48.setImageBitmap(null);
                        final TextView t48 = (TextView) findViewById(R.id.textView11);
                        t48.setText("");
                        final TextView t448 = (TextView) findViewById(R.id.textView12);
                        t448.setText("");
                    }

                }
            }
            else
            {

            }
            super.onActivityResult(requestCode, resultCode, data);
    }
}

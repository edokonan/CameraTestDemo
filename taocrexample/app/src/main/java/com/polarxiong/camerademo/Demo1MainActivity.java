package com.polarxiong.camerademo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.Manifest;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.technoalliance.taocrexample.R;


/**
 * Created by zhantong on 16/4/28.
 */
public class Demo1MainActivity extends Activity {
    private ImageIdenti imageIndety = new ImageIdenti();
    private CameraPreview mPreview;
    private static final String TAG = "M Permission";
    private int REQUEST_CODE_CAMERA_PERMISSION = 0x01;
    private int REQUEST_CODE_other_PERMISSION = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo1_activity_main);

        // パーミッションを持っているか確認する
        if (PermissionChecker.checkSelfPermission(
                Demo1MainActivity.this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // パーミッションをリクエストする
            requestCameraPermission();
            return;
        }else{
            initUI();
        }
        if (PermissionChecker.checkSelfPermission(
                Demo1MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // パーミッションをリクエストする
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_CODE_other_PERMISSION);
            return;
        }
//        requestOtherPermission();
        txt_info.setText("5秒後画像を自動撮る");
        autofocusaftertime(5);
    }
    static final int REQUEST_CODE = 1;


    boolean flg_auto_picture_capture = true;

    //起動したら、画像を自動取る
    void autofocusaftertime(int i){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Log.d(TAG, "自动focus~~~~~~~~~~~~~~");
//                mPreview.autofocus():
                mPreview.autoTackPicture=true;
                mPreview.handleFocusMetering(null,mPreview.mCamera,250,450);
            }
        }, i * 1000);
    }
    //画像認識失敗したら、もう一回、画像を取る
    void ImageIndentification(){
        if(!flg_auto_picture_capture)
            return;
        boolean result =  imageIndety.getNumberWithImage();
        if(result){
            txt_info.setText("数字認識できました");
        }else{
            txt_info.setText("数字認識できない、１秒後画像再取得する");
            autofocusaftertime(1);
        }
    }
    void stopAutoCapture(){
        flg_auto_picture_capture=false;
        mPreview.autoTackPicture=true;
        txt_info.setText("停止");
    }
    TextView txt_info ;

    void initUI(){
        final ImageView mediaPreview = (ImageView) findViewById(R.id.media_preview);
        initCamera();
        Button buttonSettings = (Button) findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAutoCapture();
                getFragmentManager().beginTransaction().replace(R.id.camera_preview, new SettingsFragment()).addToBackStack(null).commit();
            }
        });

        final Button buttonCapturePhoto = (Button) findViewById(R.id.button_capture_photo);
        buttonCapturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAutoCapture();
                mPreview.takePicture(mediaPreview);
            }
        });
        final Button buttonCaptureVideo = (Button) findViewById(R.id.button_capture_video);
        buttonCaptureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreview.isRecording()) {
                    mPreview.stopRecording(mediaPreview);
                    buttonCaptureVideo.setText("录像");
                } else {
                    if (mPreview.startRecording()) {
                        buttonCaptureVideo.setText("停止");
                    }
                }
            }
        });
        mediaPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAutoCapture();
                Intent intent = new Intent(Demo1MainActivity.this, ShowPhotoVideo.class);
                intent.setDataAndType(mPreview.getOutputMediaFileUri(), mPreview.getOutputMediaFileType());
                startActivityForResult(intent, 0);
            }
        });

        buttonCaptureVideo.setVisibility(View.GONE);
//        buttonCaptureVideo.setVisibility(View.GONE);


        final Button buttonStart = (Button) findViewById(R.id.button_start);
        final Button buttonStop= (Button) findViewById(R.id.button_stop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAutoCapture();
            }
        });
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flg_auto_picture_capture)return;
                flg_auto_picture_capture=true;

                txt_info.setText("画像を自動撮る");
                autofocusaftertime(1);
            }
        });

        txt_info = (TextView) findViewById(R.id.txt_info);
//        txt_info.setTextSize(20);
    }
    FrameLayout preview;
    private void initCamera() {
        mPreview = new CameraPreview(this);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        final ImageView mediaPreview = (ImageView) findViewById(R.id.media_preview);
        mPreview.pictureImageView = mediaPreview;
        mPreview.activity = this;


        SettingsFragment.passCamera(mPreview.getCameraInstance());
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SettingsFragment.setDefault(PreferenceManager.getDefaultSharedPreferences(this));
        SettingsFragment.init(PreferenceManager.getDefaultSharedPreferences(this));
    }

    public void onPause() {
        super.onPause();
        mPreview = null;
    }

    public void onResume() {
        super.onResume();
        if (mPreview == null) {
            initCamera();
        }
    }


    // Permission handling for Android 6.0
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale:追加説明");
            // 権限チェックした結果、持っていない場合はダイアログを出す
            new AlertDialog.Builder(this)
                    .setTitle("パーミッションの追加説明")
                    .setMessage("このアプリで写真を撮るにはパーミッションが必要です")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Demo1MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CODE_CAMERA_PERMISSION);
                        }
                    })
                    .create()
                    .show();
            return;
        }
        // 権限を取得する
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA
                },
                REQUEST_CODE_CAMERA_PERMISSION);
        return;
    }
//    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//    <uses-permission android:name="android.permission.RECORD_AUDIO" />
//    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.CAMERA
//        }, REQUEST_CODE);

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (grantResults.length != 1 ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult:DENYED");

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    Log.d(TAG, "[show error]");
                    new AlertDialog.Builder(this)
                            .setTitle("パーミッション取得エラー")
                            .setMessage("再試行する場合は、再度Requestボタンを押してください")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // サンプルのため、今回はもう一度操作をはさんでいますが
                                    // ここでrequestCameraPermissionメソッドの実行でもよい
                                }
                            })
                            .create()
                            .show();
                } else {
                    Log.d(TAG, "[show app settings guide]");
                    new AlertDialog.Builder(this)
                            .setTitle("パーミッション取得エラー")
                            .setMessage("今後は許可しないが選択されました。アプリ設定＞権限をチェックしてください（権限をON/OFFすることで状態はリセットされます）")
                            .create()
                            .show();
                }
            } else {
                Log.d(TAG, "onRequestPermissionsResult:GRANTED");
                // TODO 許可されたのでカメラにアクセスする
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

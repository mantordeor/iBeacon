package com.example.ibeacontest;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.annotation.TargetApi;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothManager;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.RemoteException;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.altbeacon.beacon.Beacon;
//import org.altbeacon.beacon.BeaconConsumer;
//import org.altbeacon.beacon.BeaconLocalBroadcastProcessor;
//import org.altbeacon.beacon.BeaconManager;
//import org.altbeacon.beacon.BeaconParser;
//import org.altbeacon.beacon.Identifier;
//import org.altbeacon.beacon.InternalBeaconConsumer;
//import org.altbeacon.beacon.MonitorNotifier;
//import org.altbeacon.beacon.RangeNotifier;
//import org.altbeacon.beacon.Region;
//import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;
//
//import java.util.Collection;
//import java.util.UUID;
//
//public class MainActivity extends AppCompatActivity implements BeaconConsumer{
//    private TextView tv_get;
//
//    public static final Region REGION_BEACON_01 = new Region("REGION_BEACON_01", Identifier.parse("699ebc80-e1f3-11e3-9a0f-0cf3ee3bc012"), null, null);
//    public static final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";
//    private BeaconManager beaconManager;
//    String TAG = "DDD";
//    public static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
//    public static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        verifyBluetooth();
//        requestPermissions();
//            // 初始化 BeaconManager
//        beaconManager = BeaconManager.getInstanceForApplication(this);
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_FORMAT));
//        beaconManager.bind(this);
//        // 設定 Beacon 監聽器
//        beaconManager.addRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//                tv_get = findViewById(R.id.tv_get);
//                /*if(beacons.size() > 0){
//                    for (Beacon beacon : beacons) {
//                        String uuid = beacon.getId1().toString();
//                       int major = beacon.getId2().toInt();
//                        int minor = beacon.getId3().toInt();
//
//                         根據需要進行相應的操作
//                         您可以將接收到的 Beacon 資料顯示在 TextView 上
//                        tv_get.setText("UUID: " + uuid + "\nMajor: " + major + "\nMinor: " + minor);
//                    }
//                }*/
//            }
//        });
//
//       //  開始偵測 Beacon
//         try {
//            /*UUID uuid = UUID.fromString("699ebc80-e1f3-11e3-9a0f-0cf3ee3bc012");
//            int major = 1;
//            int minor = 11365;*/
//            Region region = new Region("MyRegion", Identifier.parse("699ebc80-e1f3-11e3-9a0f-0cf3ee3bc012"), null, null);
//            beaconManager.startRangingBeaconsInRegion(region);
//        } catch (Exception e) {
//            Log.e(TAG, "Beacon detection error: " + e.getMessage());
//        }
//
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST_FINE_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d(TAG, "fine location permission granted");
//                } else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                }
//                return;
//            }
//            case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d(TAG, "background location permission granted");
//                } else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons when in the background.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                }
//                return;
//            }
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(beaconManager != null){
//            /*UUID uuid = UUID.fromString("699ebc80-e1f3-11e3-9a0f-0cf3ee3bc012");
//            int major = 1;
//            int minor = 11365;
//            Region region = new Region("MyRegion", Identifier.fromUuid(uuid), Identifier.fromInt(major), Identifier.fromInt(minor));
//            beaconManager.stopRangingBeacons(region);
//            beaconManager.removeAllMonitorNotifiers();*/
//            beaconManager.unbind(this);
//        }
//    }
//    @Override
//    public void onBeaconServiceConnect(){
//        beaconManager.setMonitorNotifier(new MonitorNotifier() {
//            @Override
//            public void didEnterRegion(Region region) {
//                Log.e(TAG,"AAA");
//            }
//
//            @Override
//            public void didExitRegion(Region region) {
//                Log.e(TAG,"BBB");
//            }
//
//            @Override
//            public void didDetermineStateForRegion(int state, Region region) {
//                Log.e(TAG,"CCC");
//            }
//        });
//        try{
//            beaconManager.startMonitoringBeaconsInRegion(new Region("MyRegion", Identifier.parse("699ebc80-e1f3-11e3-9a0f-0cf3ee3bc012"), null, null));
//        }catch (RemoteException e){
//            e.printStackTrace();
//        }
//    }
//    private void verifyBluetooth() {
//        try {
//            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Bluetooth not enabled");
//                builder.setMessage("Please enable bluetooth in settings and restart this application.");
//                builder.setPositiveButton(android.R.string.ok, null);
//                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        finishAffinity();
//                    }
//                });
//                builder.show();
//            }
//        }
//        catch (RuntimeException e) {
//            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Bluetooth LE not available");
//            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
//            builder.setPositiveButton(android.R.string.ok, null);
//            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    finishAffinity();
//                }
//
//            });
//            builder.show();
//
//        }
//
//    }
//    private void requestPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    if (this.checkSelfPermission(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        if (!this.shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
//                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            builder.setTitle("This app needs background location access");
//                            builder.setMessage("Please grant location access so this app can detect beacons in the background.");
//                            builder.setPositiveButton(android.R.string.ok, null);
//                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                                @TargetApi(23)
//                                @Override
//                                public void onDismiss(DialogInterface dialog) {
//                                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
//                                            2);
//                                }
//
//                            });
//                            builder.show();
//                        }
//                        else {
//                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            builder.setTitle("Functionality limited");
//                            builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
//                            builder.setPositiveButton(android.R.string.ok, null);
//                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                                @Override
//                                public void onDismiss(DialogInterface dialog) {
//                                }
//
//                            });
//                            builder.show();
//                        }
//                    }
//                }
//            } else {
//                if (!this.shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
//                                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
//                            PERMISSION_REQUEST_FINE_LOCATION);
//                }
//                else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                }
//
//            }
//        }
//    }
//}
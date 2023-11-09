package com.example.weeeapp;

import android.app.Activity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class BeaconController {

    private static final long DEFAULT_FOREGROUND_SCAN_PERIOD = 1000L; //掃描頻率 1 秒
    private final Activity activity;
    private BeaconManager beaconManager;
    private Boolean isScanning = false;
    private Region region;

    private final BeaconParser beaconParser = new BeaconParser()
            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");

    public BeaconController(Activity activity) {
        this.activity = activity;
    }

    public void beaconInit() {
        this.region = new Region("UniqueID", null, null, null);

        beaconManager = BeaconManager.getInstanceForApplication(activity);

        //beacon AddStone m:0-3=4c000215 or alt beacon = m:2-3=0215
        beaconManager.getBeaconParsers().add(beaconParser);
        beaconManager.setForegroundScanPeriod(DEFAULT_FOREGROUND_SCAN_PERIOD);
    }

    public void startScanning(BeaconModify beaconModify) {
        isScanning = true;
        beaconManager.addRangeNotifier(beaconModify::modifyData);
        beaconManager.startRangingBeacons(region);
    }

    public void stopScanning() {
        isScanning = false;
        beaconManager.removeAllMonitorNotifiers();
        beaconManager.stopRangingBeacons(region);
        beaconManager.removeAllRangeNotifiers();
    }

    public boolean checkScanning() {
        return isScanning;
    }
    //實作介面
    public interface BeaconModify {
        //塞beacons, 實體層(身分證)
        void modifyData(Collection<Beacon> beacons, Region region);
    }
}

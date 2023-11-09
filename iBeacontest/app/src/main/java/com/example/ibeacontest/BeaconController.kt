package com.example.ibeacontest

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseSettings

import org.altbeacon.beacon.*

/**
 * Beacon 串接功能
 * @param ctx Activity
 * @param region Region
 */
class BeaconController(
    val ctx: Activity,
    var region: Region
) {
    companion object {
        private const val TAG = "RangingActivity"
        private const val DEFAULT_FOREGROUND_SCAN_PERIOD = 1000L
    }

    private var beacon: Beacon? = null
    private var beaconManager: BeaconManager = BeaconManager.getInstanceForApplication(ctx)
    private var beaconTransmitter: BeaconTransmitter? = null
    private var beaconParser: BeaconParser? = null

    private var beaconIsScanning = false
    private var beaconIsCasting = false

    init {
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"))
        beaconManager.foregroundScanPeriod = DEFAULT_FOREGROUND_SCAN_PERIOD

        beaconParser = BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25")
    }


    /**
     * 開啟藍牙
     * @param beaconModify BeaconModify
     */
    fun startScanning(beaconModify: BeaconModify) {
        beaconManager.removeAllRangeNotifiers()
        beaconManager.addRangeNotifier(beaconModify::modifyData)
        beaconManager.startRangingBeacons(region)
        beaconIsScanning = true
    }

    /**
     * 停止藍牙
     */
    fun stopScanning() {
        beaconManager.removeAllMonitorNotifiers()
        beaconManager.stopRangingBeacons(region)
        beaconManager.removeAllRangeNotifiers()
        beaconIsScanning = false
    }

    /**
     * 檢查是否在搜尋Beacon
     */
    fun isScanning() :Boolean {
        return beaconIsScanning
    }

    /**
     * 廣播初始設定Beacon
     * @param uuid String
     * @param major String
     * @param minor String
     */
    fun broadcastBeacon(uuid: String, major: String, minor: String) {
        try {
            if (major.isNotBlank() && major != "null" && minor.isNotBlank() && minor != "null") {
                beacon = Beacon.Builder()
                    .setId1(uuid)
                    .setId2(major)
                    .setId3(minor)
                    .setManufacturer(0x0118)
                    .setTxPower(-69)
                    .setDataFields(listOf(0L))
                    .build()

                beaconTransmitter = BeaconTransmitter(ctx, beaconParser)
                beaconTransmitter!!.advertiseTxPowerLevel = AdvertiseSettings.ADVERTISE_TX_POWER_HIGH
                beaconTransmitter!!.advertiseMode = AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY
            } else {

            }
        } catch (e: Exception) {

        }
    }

    /**
     * 開始廣播Beacon
     */
    fun startBeaconCasting() {
        if (!isBeaconCasting()) beaconTransmitter?.startAdvertising(beacon)
        beaconIsCasting = true
    }

    /**
     * 停止廣播Beacon
     */
    fun stopBeaconCasting() {
        if (beaconIsCasting) beaconTransmitter?.stopAdvertising()
        beaconIsCasting = false
    }

    /**
     * 檢查是否在廣播Beacon
     */
    fun isBeaconCasting(): Boolean {
        return beaconIsCasting
    }

    interface BeaconModify {
        fun modifyData(beacons: Collection<Beacon?>?, region: Region?)
    }
}
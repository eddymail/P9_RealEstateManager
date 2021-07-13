package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Base64;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.activities.MainActivity;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Illustration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    public static MainActivity context = null;

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.84);
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     *
     * @param euros
     * @return
     */
    public static int convertEuroToDollars(int euros) {
        return (int) Math.round(euros * 1.19);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    public static String getTodayDate() {
        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    /**
     * Vérification de la connexion réseau et wifi
     *
     * @return boolean
     */
    public static boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;
        // get Connectivity Manager object to check connection
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_WIFI = true;

            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData = true;
        }
        return have_MobileData || have_WIFI;
    }

    /**
     * Detect device is Android phone or Android tablet
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Get the illustration stock in device
     * @param house
     * @return illustration
     */
    public static String getIllustrationFromDevice(House house) {
        File file = new File(house.getIllustration());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap != null) {
            String illustration = getStringImage(bitmap);
            return illustration;
        }
        return null;
    }

    /**
     * Get the picture stock in device
     * @param illustration
     * @return illustrationGallery
     */
    public static String getIllustrationGalleryFromDevice(Illustration illustration) {
        File file = new File(illustration.getPicture());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap != null) {
            String illustrationGallery = getStringImage(bitmap);
            return illustrationGallery;
        }
        return null;
    }

    /**
     * Convert a bitmap into a string
     * @param bitmap
     * @return a string
     */
    public static String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return "data:image/jpeg;base64," + Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    /**
     * Calculate monthly payment
     */
    public static double calculateMonthlyPayment(double interestRate, double loanPeriod, double loanAmount) {
        double r = interestRate / 1200;
        double r1 = Math.pow(r + 1, loanPeriod);
        return ((r + (r / (r1 - 1))) * loanAmount);
    }

    /**
     * Calculate total payment
     */
    public static double calculateTotalPayment(double monthlyPayment, double loanPeriod) {
        return (monthlyPayment * loanPeriod);

    }

    /**
     * Get latitude and longitude from an adress
     *
     * @param address
     * @return latitude and longitude
     */
    public static LatLng getLatLngFromAddress(String address) {
        Geocoder coder = new Geocoder(context);
        List<Address> addresses;
        try {
            addresses = coder.getFromLocationName(address, 10);
            if (addresses == null) {
            }
            Address location = addresses.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            LatLng latLng = new LatLng(lat, lng);
            return latLng;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

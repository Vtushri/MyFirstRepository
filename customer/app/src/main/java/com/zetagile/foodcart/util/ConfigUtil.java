package com.zetagile.foodcart.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.zetagile.foodcart.constants.LayoutType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    static final String configProperties = "config.properties";
    static final String appProperties = "app.properties";

    String vat;
    String service_tax;
    String service_charge;
    String other_charge;
    String payment_paypal;
    String payment_cop;
    String disable_drive_thru;
    String disable_pickup;
    String disable_home_delivery;
    String secure_protocol;

    public ConfigUtil(Context context) {
        try {
            AssetManager manager = context.getAssets();
            InputStream in = manager.open(configProperties);
            Properties properties = new Properties();
            properties.load(in);

            vat = properties.getProperty("VAT");
            service_tax = properties.getProperty("SERVICE_TAX");
            service_charge = properties.getProperty("SERVICE_CHARGE");
            other_charge = properties.getProperty("OTHER_CHARGE");
            payment_paypal = properties.getProperty("PAYMENT_PAYPAL");
            payment_cop = properties.getProperty("PAYMENT_COP");
            disable_drive_thru = properties.getProperty("DISABLE_DRIVE_THRU");
            disable_pickup = properties.getProperty("DISABLE_PICKUP");
            disable_home_delivery = properties.getProperty("DISABLE_HOME_DELIVERY");
            secure_protocol = properties.getProperty("SECURE_PROTOCOL");

            if (vat != null)
                vat = vat.trim();
            if (service_tax != null)
                service_tax = service_tax.trim();
            if (other_charge != null)
                other_charge = other_charge.trim();
            if (service_charge != null)
                service_charge = service_charge.trim();
            if (payment_paypal != null)
                payment_paypal = payment_paypal.trim();
            if (payment_cop != null)
                payment_cop = payment_cop.trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LayoutType getLayoutType(Context context) {
        try {
            AssetManager manager = context.getAssets();
            InputStream in = manager.open(configProperties);
            Properties properties = new Properties();
            properties.load(in);

            String images_layout = properties.getProperty("LAYOUT_TYPE");

            if (images_layout != null)
                images_layout = images_layout.trim();

            if (images_layout != null && images_layout.equals(LayoutType.IMAGES_LAYOUT.name()))
                return LayoutType.IMAGES_LAYOUT;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return LayoutType.TEXT_LAYOUT;
    }

    public static long getTrackInterval(Context context) {
        try {
            AssetManager manager = context.getAssets();
            InputStream in = manager.open(appProperties);
            Properties properties = new Properties();
            properties.load(in);
            String track_time_interval = properties.getProperty("TRACK_TIME_INTERVAL");
            if (track_time_interval != null && !track_time_interval.isEmpty())
                return Integer.parseInt(track_time_interval);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isVatApplicable() {

        if (vat != null && !vat.isEmpty() && vat.equals("YES"))
            return true;

        return false;
    }

    public boolean isServiceChargesApplicable() {
        if (service_charge != null && !service_charge.isEmpty() && service_charge.equals("YES"))
            return true;

        return false;
    }

    public boolean isServiceTaxApplicable() {
        if (service_tax != null && !service_tax.isEmpty() && service_tax.equals("YES"))
            return true;

        return false;
    }

    public boolean isOtherChargeApplicable() {
        if (other_charge != null && !other_charge.isEmpty() && other_charge.equals("YES"))
            return true;

        return false;
    }

    public boolean isPaypalApplicalble() {
        if (payment_paypal != null && !payment_paypal.isEmpty() && payment_paypal.equals("YES"))
            return true;

        return false;
    }

    public boolean isCOPApplicable() {
        if (payment_cop != null && !payment_cop.isEmpty() && payment_cop.equals("YES"))
            return true;

        return false;
    }

    public boolean isDriveThruDisabled() {

        if (disable_drive_thru != null && !disable_drive_thru.isEmpty() && disable_drive_thru.equalsIgnoreCase("yes"))
            return true;
        return false;
    }

    public boolean isPickupDisabled() {

        if (disable_pickup != null && !disable_pickup.isEmpty() && disable_pickup.equalsIgnoreCase("yes"))
            return true;
        return false;
    }

    public boolean isHomeDeliveryDisabled() {

        if (disable_home_delivery != null && !disable_home_delivery.isEmpty() && disable_home_delivery.equalsIgnoreCase("yes"))
            return true;
        return false;
    }

    public boolean isSecureProtocolEnabled(){
        if(secure_protocol != null && !secure_protocol.isEmpty() && secure_protocol.equalsIgnoreCase("YES"))
            return true;
        return false;
    }
}
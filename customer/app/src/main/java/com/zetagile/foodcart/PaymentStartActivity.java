package com.zetagile.foodcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ShippingAddress;
import com.zetagile.foodcart.asynctasks.PaymentTask;
import com.zetagile.foodcart.constants.PaymentModes;
import com.zetagile.foodcart.dto.AddressLog;
import com.zetagile.foodcart.fragments.OrderSummary;
import com.zetagile.foodcart.fragments.catelog.CartFragment;

import java.math.BigDecimal;


public class PaymentStartActivity extends Activity {


    public static final int RESULT_SERVER_ERROR = 21;
    public static final int RESULT_INVALID_CONFIG = 22;
    private static final String TAG = "Payment";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "ASWWkKMA1XPeN_iMBO44M_txEuWjqbhXxiy4EBpHhQ7zhlXKtTQPQ18hLCtoMZy1pZOpcZ8d1jY6xSvr";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);
    String str_order_id;
    double payAmount;
    String str_user_id;
    String addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Payment start screen is created");

        Intent cartIntent = getIntent();

        str_order_id = cartIntent.getStringExtra(OrderSummary.ORDER_ID);
        payAmount = cartIntent.getDoubleExtra(OrderSummary.ORDER_AMOUNT, 0.0);
        str_user_id = cartIntent.getStringExtra(OrderSummary.ORDER_USER_ID);
        addressId = cartIntent.getStringExtra(OrderSummary.SHIPPING_ADDRESS_ID);


//        System.out.println(str_order_id+payAmount);
        if (str_user_id != null && str_order_id != null) {
            Log.i(TAG, "Starting paypal service");

            Intent intent_paypal_service = new Intent(this, PayPalService.class);
            intent_paypal_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent_paypal_service);

            PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent_payment = new Intent(PaymentStartActivity.this, PaymentActivity.class);

            // send the same configuration for restart resiliency
            intent_payment.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

            intent_payment.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

            Log.i(TAG, "Starting paypal screens");
            startActivityForResult(intent_payment, REQUEST_CODE_PAYMENT);
        } else {
            Log.i(TAG, "Invalid intputs to payment screen from cart fragment");
            this.setResult(RESULT_INVALID_CONFIG);
            this.finish();
        }
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(payAmount), "USD", str_order_id,
                paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "Papal response is received");

                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirm != null) {
                    try {

                        Log.i(TAG, "Starting payment service");
                        PaymentTask payment_service = new PaymentTask(this, str_order_id, str_user_id, confirm.toJSONObject().toString(), PaymentModes.PAYPAL, addressId);
                        payment_service.execute(confirm, str_order_id, payAmount, str_user_id);

                    } catch (Exception e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                        this.setResult(RESULT_SERVER_ERROR);
                        this.finish();
                    }
                }
            } else if (resultCode == PaymentActivity.RESULT_CANCELED) {
                Log.i(TAG, "Some problem occurred");
                this.setResult(RESULT_CANCELED);
                this.finish();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                this.setResult(RESULT_INVALID_CONFIG);
                this.finish();
            }
        }
    }
}

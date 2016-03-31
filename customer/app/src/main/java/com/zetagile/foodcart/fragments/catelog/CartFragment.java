package com.zetagile.foodcart.fragments.catelog;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zetagile.foodcart.LoginActivity;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.StoreListActivity;
import com.zetagile.foodcart.adapters.CartProductListAdapter;
import com.zetagile.foodcart.asynctasks.AddUserShippingAddress;
import com.zetagile.foodcart.asynctasks.ApplyOfferTask;
import com.zetagile.foodcart.asynctasks.GetAddressesByUserID;
import com.zetagile.foodcart.asynctasks.OrderCreationTask;
import com.zetagile.foodcart.asynctasks.UpdateCartItemTask;
import com.zetagile.foodcart.constants.OrderType;
import com.zetagile.foodcart.dto.AddressLog;
import com.zetagile.foodcart.constants.StoreStatus;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.Location;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.OrderProduct;
import com.zetagile.foodcart.dto.OrderStatus;
import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.ConfigUtil;
import com.zetagile.foodcart.util.CustomTimePickerDialog;
import com.zetagile.foodcart.util.SmackClientUtil;
import com.zetagile.foodcart.util.jsonutils.JavaToJsonString;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;
import com.zetagile.foodcart.util.time.TimeDateDistanceUtil;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CartFragment extends Fragment {


    public static final String ORDER_TYPE = "ORDERTYPE";
    public static final String SELECT_STORE_REQUEST = "SELECT_STORE_REQUEST";
    protected static final int STORE_REQUEST_CODE = 2;

    public static final String TAG = "CART_FRAGMENT";

    private SmackMessageListener messageListener = new SmackMessageListener();

    public static SmackClientUtil smackClientUtil;

    View fragmentView;
    Activity activity;

    protected Store selected_store;
    Order ord_order;
    long pickupReserveTime;

//    List<Cart_ProductView> list_cart_products = new ArrayList<>();
//    Cart cart;

    ListView lv_product_list_view;
    Button btn_checkout_button;
    CartProductListAdapter adapter_products;

    TextView tv_total_price;
    TextView tv_cart_empty;
    EditText offer_text_field;
    TextView tv_offer_apply;
    TextView tv_continue_shopping;
    TextView tv_cart_items;

    ProgressDialog response_dialog_waiting;
    private Location user_location;
    private long time_reach_store;

    AddressLog userShippingAddress = new AddressLog();
    public static String userShippingAddressID = "ADDRESS_ID";

    public static void addToCart(final Activity activity, User user, Cart_ProductView cart_product) {

        UpdateCartItemTask async_task_http_add_to_cart = new UpdateCartItemTask(activity, cart_product, user) {
            @Override
            protected void onPostExecute(Cart cart_result) {
                super.onPostExecute(cart_result);

                if (cart_result != null) {
                    Toast.makeText(activity, "Added to cart", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(activity, " Product is NOT added to cart... please try Again \n Server Error", Toast.LENGTH_SHORT).show();
            }
        };

        async_task_http_add_to_cart.execute();
    }

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_cart, null);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ord_order = new Order();

        lv_product_list_view = (ListView) fragmentView.findViewById(R.id.cart_item_list);

        tv_cart_empty = (TextView) fragmentView.findViewById(R.id.cart_empty_msg);
        tv_total_price = (TextView) fragmentView.findViewById(R.id.cart_total_price);
        btn_checkout_button = (Button) fragmentView.findViewById(R.id.btn_check_out);
        tv_cart_items = (TextView) fragmentView.findViewById(R.id.no_of_cart_items);
        tv_continue_shopping = (TextView) fragmentView.findViewById(R.id.continueshopping);
        offer_text_field = (EditText) fragmentView.findViewById(R.id.offer_text);
        tv_offer_apply = (TextView) fragmentView.findViewById(R.id.btn_apply_offer);

        adapter_products = new CartProductListAdapter(
                activity,
                R.layout.cart_item_layout,
                btn_checkout_button,
                tv_cart_items,
                tv_total_price,
                tv_cart_empty,
                new ArrayList<Cart_ProductView>());

        adapter_products.setNotifyOnChange(true);

        lv_product_list_view.setAdapter(adapter_products);

        adapter_products.getDataAndInitCartList();

        offer_text_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer_text_field.clearFocus();
            }
        });

        tv_offer_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User usr_user = UserStorage.getUser(activity);
                if (usr_user != null) {

                    String offer_code = String.valueOf(offer_text_field.getText());

                    if (offer_code != null && !offer_code.isEmpty()) {
                        try {
                            ApplyOfferTask offerTask = new ApplyOfferTask(activity, usr_user.getUserAccountId(),
                                    offer_code) {
                                @Override
                                protected void onPostExecute(Cart cart_result) {
                                    super.onPostExecute(cart_result);
                                    adapter_products.setCart(cart_result);
                                    offer_text_field.setText("");
                                }
                            };

                            offerTask.execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    Toast.makeText(activity, "Please login...", Toast.LENGTH_LONG).show();
            }
        });

        btn_checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User usr_user = UserStorage.getUser(activity);

                if (usr_user != null) {

                    smackClientUtil = new SmackClientUtil(activity, usr_user, messageListener);
                    smackClientUtil.connectToServer();

                    createOrder(ord_order);
                    createChooseOrderType();

                } else {
                    createLoginDialog();
                }
            }
        });

        tv_continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment.createFragment(activity.getFragmentManager());
            }
        });

        response_dialog_waiting = new ProgressDialog(activity);
        response_dialog_waiting.setMessage("Checking for availability...");
        response_dialog_waiting.setCancelable(false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LoginActivity.LOGIN_REQUEST_CODE) {
            adapter_products.getDataAndInitCartList();

        } else if (requestCode == STORE_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                selected_store = data.getParcelableExtra(StoreListActivity.SELECTED_STORE);
                user_location = data.getParcelableExtra(StoreListActivity.LOCATION);
                time_reach_store = data.getLongExtra(StoreListActivity.DURATION, 0) + TimeDateDistanceUtil.getCurrentMillis();

                sendOrderNotification();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                createChooseOrderType();
            } else
                Toast.makeText(activity, "Store selection not done", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateDrivethruTime() {
        long today_time = TimeDateDistanceUtil.getDurationOfTodayTime(time_reach_store);

        return (today_time < selected_store.getWorkingTill() && selected_store.getWorkingFrom() <= today_time);

    }

    private boolean validatePickUpTime() {

        long todayTime = TimeDateDistanceUtil.getDurationOfTodayTime(pickupReserveTime);

        //Checking whether the selected time is in between store open timings or not
        if(!(todayTime > selected_store.getWorkingFrom() && todayTime <= selected_store.getWorkingTill())) {
            String message = "Please select time between "
                    + TimeDateDistanceUtil.getDurationAmPm(selected_store.getWorkingFrom())
                    + " and "
                    + TimeDateDistanceUtil.getDurationAmPm(selected_store.getWorkingTill());

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            return false;
        }

        //Checking whether pickup time is greater than the current time + waiting time
        if(todayTime < TimeDateDistanceUtil.getDurationOfTodayTime(TimeDateDistanceUtil.getCurrentMillis() + selected_store.getWaitingTime())) {
            String message = "Please select time after "
                    + TimeDateDistanceUtil.getDurationAmPm(TimeDateDistanceUtil.getDurationOfTodayTime(TimeDateDistanceUtil.getCurrentMillis() + selected_store.getWaitingTime()));

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            return false;
        }
        System.out.println("Hello --- >" + TimeDateDistanceUtil.getDurationOfTodayTime(TimeDateDistanceUtil.getCurrentMillis() + selected_store.getWaitingTime()));
        System.out.println("Result: --- >" + TimeDateDistanceUtil.getDurationAmPm(TimeDateDistanceUtil.getDurationOfTodayTime(TimeDateDistanceUtil.getCurrentMillis() + selected_store.getWaitingTime())));
        return true;
    }

    private Order createOrder(Order order) {

        order.getProducts().clear();
        OrderProduct orderProduct;

        List<Cart_ProductView> list_cart_products = adapter_products.getCart().getProducts();

        order.setTotalAmount(adapter_products.getCart().getTotalPrice());
        order.setSubTotal(adapter_products.getCart().getSubTotal());

        for (int i = 0; list_cart_products != null && i < list_cart_products.size(); i++) {
            orderProduct = new OrderProduct();

            orderProduct.setProduct(list_cart_products.get(i).getProduct());
            orderProduct.setQuantity(list_cart_products.get(i).getQuantity());

            order.getProducts().add(orderProduct);
        }
        return order;
    }

    public void vendorResponse(String str_reply) {
        try {

            if (response_dialog_waiting != null && response_dialog_waiting.isShowing())
                response_dialog_waiting.dismiss();

            Log.i(TAG, "Vendor Reply ---->" + str_reply);

            OrderStatus os_order_status = new JsonStringToJava().convert_order_status(str_reply);

            if (os_order_status.isAvailable()) {

                //If the order available
                ord_order.setOrderType(os_order_status.getOrderType());
                ord_order.setProcessingTime(os_order_status.getProcessingTime());
                ord_order.setDeliveryTime(new Date(os_order_status.getDeliveryTime()));

                Toast.makeText(activity, "Available", Toast.LENGTH_SHORT).show();
                proceedToOrderAndPayment();

            } else if (os_order_status.getDeliveryTime() != 0){

                // If the selected order not available
                ord_order.setProcessingTime(os_order_status.getProcessingTime());
                ord_order.setDeliveryTime(new Date(os_order_status.getDeliveryTime()));

                if (ord_order.getOrderType().equals(OrderType.DRIVETHRU.name())) {
                    //Drive thru will be changed to pickup order type upon vendor resuggestion
                    String time = TimeDateDistanceUtil.getTimeOfDate(os_order_status.getDeliveryTime());

                    ord_order.setOrderType(OrderType.PICKUP.name());
                    createPickupDialog("Your order is not available for Drive-Thru at the moment." +
                            " \n\n If you want you can pickup at " + time);
                } else if (ord_order.getOrderType().equals(OrderType.PICKUP.name())) {
                    //Pure pickup Orders Dialog
                    ord_order.setOrderType(OrderType.PICKUP.name());
                    String time = TimeDateDistanceUtil.getTimeOfDate(os_order_status.getDeliveryTime());
                    createPickupDialog("Your order is not available for pickup at "+ TimeDateDistanceUtil.getTimeOfDate(pickupReserveTime) +
                            " \n\n But you can pickup at " + time);
                } else if (ord_order.getOrderType().equals(OrderType.HOMEDELIVERY.name())) {
                    //HomeDelivery remains homedelivery order type
                    ord_order.setOrderType(OrderType.HOMEDELIVERY.name());
                    String time = TimeDateDistanceUtil.getTimeOfDate(os_order_status.getDeliveryTime());
                    createPickupDialog("Your order is not available for home-delivery at your specified time" +
                            " \n\n But you can get the order delivered at your home at " + time);
                }
            } else {
                createErrorDialog("Your order is not available");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void proceedToOrderAndPayment() {

        User user = UserStorage.getUser(activity);
        if (user != null) {
            OrderCreationTask ocs_order_creation = new OrderCreationTask(this, activity, ord_order, user.getUserAccountId(), selected_store.getStoreId());
            ocs_order_creation.execute();
        } else
            Toast.makeText(activity, "Please login...!!!", Toast.LENGTH_SHORT).show();
    }

    private void gotoStoreScreen() {

//        if (selected_store != null) {
//            createTimeDialog();
//            return;
//        }
        Intent intent_store_activity = new Intent(activity, StoreListActivity.class);

        intent_store_activity.putExtra(SELECT_STORE_REQUEST, SELECT_STORE_REQUEST);

        intent_store_activity.putExtra(ORDER_TYPE, ord_order.getOrderType());

        startActivityForResult(intent_store_activity, STORE_REQUEST_CODE);

    }

    private void sendOrderNotification() {

        if(!isStoreOpen()) {
            return;
        }

        if(ord_order.getOrderType() != null && ord_order.getOrderType().equals(OrderType.PICKUP.name())) {

            createTimeDialog();

        } else if(ord_order.getOrderType() != null && ord_order.getOrderType().equals(OrderType.DRIVETHRU.name())) {

            if (validateDrivethruTime()) {

                response_dialog_waiting.show();
                smackClientUtil.sendDrivethruNotification(selected_store.getStoreId(), ord_order, time_reach_store, user_location);

            } else {

                selected_store = null;
                ord_order.setOrderType(null);

                createErrorDialog("Store will be closed by the time you reach there... " +
                        "\n Please try again.");
            }

        } else if(ord_order.getOrderType() != null && ord_order.getOrderType().equals(OrderType.HOMEDELIVERY.name())) {

            createTimeDialog();

        } else
            Toast.makeText(activity, "Invalid order type", Toast.LENGTH_LONG).show();

    }

    public boolean isStoreOpen() {
        String status = smackClientUtil.getStoreStatus(selected_store.getStoreId());

        if (status == null || status.equals(StoreStatus.CLOSED.name())) {
            selected_store = null;
            ord_order.setOrderType(null);
            createErrorDialog("Store closed currently...");
            return false;
        } else
            return true;
    }

    private void createPickupDialog(String display_text) {
        final Dialog dialog_drive_pick = new Dialog(activity);
        dialog_drive_pick.setCancelable(false);
        dialog_drive_pick.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_drive_pick.setContentView(R.layout.dialog_with_two_buttons);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_drive_pick.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog_drive_pick.getWindow().setAttributes(lp);


        ((TextView) dialog_drive_pick.findViewById(R.id.dialog_title)).setText("Order Pickup");
        ((TextView) dialog_drive_pick.findViewById(R.id.dialog_info)).setText(display_text);

        ((Button) dialog_drive_pick.findViewById(R.id.dialog_ok)).setText("Cancel");
        ((Button) dialog_drive_pick.findViewById(R.id.dialog_continue)).setText("Continue");

        dialog_drive_pick.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_drive_pick.dismiss();
            }
        });

        dialog_drive_pick.findViewById(R.id.dialog_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_drive_pick.dismiss();
                proceedToOrderAndPayment();
            }
        });

        dialog_drive_pick.show();
    }

    private void createChooseOrderType() {

        final Dialog dialog_order_type = new Dialog(activity);
        dialog_order_type.setCancelable(true);
        dialog_order_type.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_order_type.setContentView(R.layout.dialog_choose_one);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_order_type.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog_order_type.getWindow().setAttributes(lp);

        TextView tv_title = (TextView) dialog_order_type.findViewById(R.id.tv_title);

        Button btn_drive_thru = (Button) dialog_order_type.findViewById(R.id.btn_choose_option1);
        Button btn_pick_up = (Button) dialog_order_type.findViewById(R.id.btn_choose_option2);
        Button btn_home_delivery = (Button) dialog_order_type.findViewById(R.id.btn_choose_option3);
        TextView text_or = (TextView) dialog_order_type.findViewById(R.id.text_or);
        TextView text_or_2 = (TextView) dialog_order_type.findViewById(R.id.text_or_2);

        tv_title.setText("Select Order Type");

        btn_drive_thru.setText("DRIVE-THRU");
        btn_pick_up.setText("PICK-UP");
        btn_home_delivery.setText("HOME-DELIVERY");

        // Enable or Disable Drive Thru
        ConfigUtil util = new ConfigUtil(activity);
        if (util.isDriveThruDisabled()) {
            text_or.setVisibility(View.GONE);
            btn_drive_thru.setVisibility(View.GONE);
        }

        // Enable or Disable Pickup
        if (util.isPickupDisabled()) {
            text_or_2.setVisibility(View.GONE);
            btn_pick_up.setVisibility(View.GONE);
        }

        // Enable or Disable Home Delivery
        if (util.isHomeDeliveryDisabled()) {
            text_or_2.setVisibility(View.GONE);
            btn_home_delivery.setVisibility(View.GONE);
        }

        btn_drive_thru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_order_type.dismiss();

                ord_order.setOrderType(OrderType.DRIVETHRU.name());

                gotoStoreScreen();

            }
        });

        btn_pick_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_order_type.dismiss();

                ord_order.setOrderType(OrderType.PICKUP.name());

                gotoStoreScreen();
            }
        });

        btn_home_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_order_type.dismiss();

                ord_order.setOrderType(OrderType.HOMEDELIVERY.name());

                gotoDeliveryAddress(activity);
            }
        });

        dialog_order_type.show();

    }

    protected void gotoDeliveryAddress (final Activity activity) {

        final User current_user = UserStorage.getUser(activity);
        GetAddressesByUserID getAddressesByUserIDTask = new GetAddressesByUserID(activity, current_user.getUserAccountId()){
            @Override
            protected void onPostExecute(final List<AddressLog> addressLogList) {
                super.onPostExecute(addressLogList);

                final Dialog dialog_get_delivery_address = new Dialog(activity);
                dialog_get_delivery_address.setCancelable(true);
                dialog_get_delivery_address.setTitle("Choose Delivery Address");
                dialog_get_delivery_address.setContentView(R.layout.dialog_with_addresses);

                final Dialog dialog_update_delivery_address = new Dialog(activity);
                dialog_update_delivery_address.setCancelable(true);
                dialog_update_delivery_address.setTitle("Add Delivery Address");
                dialog_update_delivery_address.setContentView(R.layout.dialog_with_editable_addresses);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog_get_delivery_address.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
                lp1.copyFrom(dialog_update_delivery_address.getWindow().getAttributes());
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp1.gravity = Gravity.CENTER;

                dialog_update_delivery_address.getWindow().setAttributes(lp1);

                dialog_update_delivery_address.findViewById(R.id.dialog_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText adl1 = (EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_line_1);
                        EditText adl2 = (EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_line_2);
                        EditText adLan = (EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_landmark);
                        EditText adLoc = (EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_locality);
                        if (adl1.getText().toString().equals("")
                                || adl2.getText().toString().equals("")
                                || adLoc.getText().toString().equals("")) {
                            Toast.makeText(activity, "Please fill all mandatory fields...", Toast.LENGTH_SHORT).show();
                        } else {
                            //Capture the address field data and populate addresslog object and proceed further
                            String addressLogAsString = null;
                            userShippingAddress.setAddressLine1(adl1.getText().toString());
                            userShippingAddress.setAddressLine2(((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_line_2)).getText().toString());
                            userShippingAddress.setLandmark(((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_landmark)).getText().toString());
                            userShippingAddress.setLocality(((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_locality)).getText().toString());

                            try {
                                addressLogAsString = new JavaToJsonString().convert_addresslog(userShippingAddress);
                            } catch (Exception e) {
                                Log.e(TAG, "Unable to parse user shipping address data into json!");
                            }
                            Log.i(TAG, "HD: Adding the address " + userShippingAddress.getAddressLine1());
                            AddUserShippingAddress addUserShippingAddressTask = new AddUserShippingAddress(activity, current_user.getUserAccountId(), addressLogAsString) {
                                @Override
                                protected void onPostExecute(final String result) {
                                    super.onPostExecute(result);
                                    Log.i(TAG, "HD: Stored the address at backend with " + result);
                                    userShippingAddressID = result;
                                    dialog_update_delivery_address.dismiss();
                                    gotoStoreScreen();
                                }
                            };
                            addUserShippingAddressTask.execute();
                        }
                    }
                });

                dialog_get_delivery_address.findViewById(R.id.dialog_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_get_delivery_address.dismiss();
                        Iterator<AddressLog> iter = addressLogList.iterator();
                        if (iter.hasNext()) {
                            //#TODO : We may have to allow users to choose between their previous addresses not just the last one.
                            userShippingAddress = iter.next();
                            //We are entering the edit address page
                            dialog_update_delivery_address.setTitle("Edit Delivery Address");
                            ((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_line_1)).setText(userShippingAddress.getAddressLine1());
                            ((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_line_2)).setText(userShippingAddress.getAddressLine2());
                            ((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_landmark)).setText(userShippingAddress.getLandmark());
                            ((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_locality)).setText(userShippingAddress.getLocality());
                            //((EditText) dialog_update_delivery_address.findViewById(R.id.dialog_address_city)).setText(userShippingAddress.getCity());
                        }
                        dialog_update_delivery_address.show();
                    }
                });

                dialog_get_delivery_address.findViewById(R.id.dialog_continue).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_get_delivery_address.dismiss();
                        gotoStoreScreen();
                    }
                });

                if (addressLogList == null || addressLogList.isEmpty()) {
                    //There are no addresses stored which are linked to the current user
                    //Fetch details from user
                    Log.i("HD", "Received empty addressLog list, capturing address info from user");
                    dialog_update_delivery_address.show();
                } else {
                    //Address Collection is not empty so take the first address and ask for confirmation.
                    //#TODO We can show all the stored addresses if required in future
                    Log.i("HD", "Using existing addressLog element #1");
                    Iterator<AddressLog> iter = addressLogList.iterator();
                    if (iter.hasNext()) {
                        userShippingAddress = iter.next();
                        ((TextView) dialog_get_delivery_address.findViewById(R.id.dialog_address_line_1)).setText(userShippingAddress.getAddressLine1());
                        ((TextView) dialog_get_delivery_address.findViewById(R.id.dialog_address_line_2)).setText(userShippingAddress.getAddressLine2());
                        ((TextView) dialog_get_delivery_address.findViewById(R.id.dialog_address_landmark)).setText(userShippingAddress.getLandmark());
                        ((TextView) dialog_get_delivery_address.findViewById(R.id.dialog_address_locality)).setText(userShippingAddress.getLocality());
                        //((TextView) dialog_get_delivery_address.findViewById(R.id.dialog_address_city)).setText(userShippingAddress.getCity());
                        userShippingAddressID = userShippingAddress.getAddressId();
                    }
                    dialog_get_delivery_address.show();
                }
                Log.i(TAG, "HD: Obtained address information from backend");
            }
        };
        getAddressesByUserIDTask.execute();
    }

    protected void createTimeDialog() {

        final CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(activity) {
            @Override
            public void onTimeSet(int hours, int minutes) {

                pickupReserveTime = TimeDateDistanceUtil.getTodayDateTime(hours, minutes);

                if (!validatePickUpTime())
                    return;

                dismiss();

                response_dialog_waiting.show();
                smackClientUtil.sendPickupNotification(selected_store.getStoreId(), ord_order, pickupReserveTime, userShippingAddress);
            }

            @Override
            public void onBackPressed() {
                super.onBackPressed();
                gotoStoreScreen();
            }

            @Override
            public void onCancelClick() {
                super.onCancelClick();
                gotoStoreScreen();
            }
        };

        timePickerDialog.setTime(TimeDateDistanceUtil.getCurrentMillis() + selected_store.getWaitingTime());

        timePickerDialog.show();

//        final TimePickerDialog tpd = new TimePickerDialog(activity,
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay,
//                                          int minute) {
//
//                        pickupReserveTime = TimeDateDistanceUtil.getTodayDateTime(hourOfDay, minute);
//
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
//
//                        if (pickupReserveTime > TimeDateDistanceUtil.getCurrentMillis()) {
//                            sendOrderNotification();
//                        } else {
//                            Toast.makeText(activity, "Please Choose Time after " + simpleDateFormat.format(TimeDateDistanceUtil.getCurrentMillis()) + " ", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }, TimeDateDistanceUtil.getHourOfDay(time_in_millis_secs), TimeDateDistanceUtil.getMinuteOfDay(time_in_millis_secs), false);
//
//        tpd.setTitle("Choose Pickup Time");
//
//        tpd.setCancelable(true);
//
//        tpd.show();
    }

    protected void createErrorDialog(String message) {
        final Dialog dialog_login = new Dialog(activity);
        dialog_login.setCancelable(true);
        dialog_login.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_login.setContentView(R.layout.dialog_info);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_login.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog_login.getWindow().setAttributes(lp);

        TextView tv_title = (TextView) dialog_login.findViewById(R.id.dialog_title);
        tv_title.setText("Info");

        TextView tv_info = (TextView) dialog_login.findViewById(R.id.dialog_info);
        tv_info.setText(message);

        dialog_login.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_login.dismiss();
            }
        });

        dialog_login.show();
    }

    protected void createLoginDialog() {

        final Dialog dialog_login = new Dialog(activity);
        dialog_login.setCancelable(true);
        dialog_login.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_login.setContentView(R.layout.dialog_choose_one);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_login.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog_login.getWindow().setAttributes(lp);

        TextView tv_title = (TextView) dialog_login.findViewById(R.id.tv_title);

        Button btn_login = (Button) dialog_login.findViewById(R.id.btn_choose_option1);
        Button btn_guest_checkout = (Button) dialog_login.findViewById(R.id.btn_choose_option2);
        Button btn_other_checkout = (Button) dialog_login.findViewById(R.id.btn_choose_option3);
        TextView text_or_2 = (TextView) dialog_login.findViewById(R.id.text_or_2);

        tv_title.setText("Please Login");
        btn_guest_checkout.setText("Guest");
        btn_login.setText("Login");

        //We will always make the following UI items invisible
        //#TODO : can we create another dialog for this, so as not to interfere with order choosing dialog page
        btn_other_checkout.setVisibility(View.GONE);
        text_or_2.setVisibility(View.GONE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_login.dismiss();
                LoginActivity.startLoginActivity(CartFragment.this, activity);

            }
        });

        btn_guest_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_login.dismiss();
                LoginActivity.startSignupActivity(CartFragment.this, activity, true);
            }
        });

        dialog_login.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smackClientUtil != null && smackClientUtil.isConnected())
            smackClientUtil.disconnect();
    }

    public static void createCartFragment(FragmentManager manager) {

        if (manager != null) {

            Fragment fragment = manager.findFragmentByTag(TAG);

            if (fragment == null)
                fragment = new CartFragment();


            if (!fragment.isVisible()) {
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.frame, fragment, TAG);
                transaction.addToBackStack(null);
                transaction.commit();

                manager.executePendingTransactions();
            }
        }

    }

    public class SmackMessageListener implements ChatMessageListener {

        @Override
        public void processMessage(Chat chat, Message message) {
            final Message finalMessage = message;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vendorResponse(finalMessage.getBody());
                }
            });
        }
    }
}

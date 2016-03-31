package com.zetagile.foodcart.util.jsonutils;

import com.zetagile.foodcart.dto.AddressLog;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.dto.FeedBack;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.OrderNotification;
import com.zetagile.foodcart.dto.Payment;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.dto.Shipping;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.dto.UserDetails;
import com.zetagile.foodcart.dto.UserProfile;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JavaToJsonString {

    //User u=new User();

    public String convert_userpojo(User u) throws JsonGenerationException, JsonMappingException, IOException {
        User user = u;
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(user);
    }

    public String convert_feedback_pojo(FeedBack feedBack) throws JsonGenerationException, JsonMappingException, IOException {

        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(feedBack);
    }


    public String convert_user_profilepojo(UserProfile up) throws JsonGenerationException, JsonMappingException, IOException {
        UserProfile user_profile = up;
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(user_profile);
    }

    public String convert_userdetails(UserDetails ud) throws JsonGenerationException, JsonMappingException, IOException {
        return new ObjectMapper().writeValueAsString(ud);
    }

    public String convert_addresslog(AddressLog ad) throws JsonGenerationException, JsonMappingException, IOException {
        return new ObjectMapper().writeValueAsString(ad);
    }

    public String convert_order(Order order) throws JsonGenerationException, JsonMappingException, IOException {
        String response = new ObjectMapper().writeValueAsString(order);

        return response;
    }

    public String convert_payment(Payment payment) throws JsonGenerationException, JsonMappingException, IOException {
        String response = new ObjectMapper().writeValueAsString(payment);

        return response;
    }

    public String convert_shipping(Shipping shipping) throws JsonGenerationException, JsonMappingException, IOException {
        String response = new ObjectMapper().writeValueAsString(shipping);

        return response;
    }

    public String convertProductViewArray(ProductView[] arr_products) {
        String str_response = null;
        try {
            str_response = new ObjectMapper().writeValueAsString(arr_products);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str_response;
    }

    public String convertCartProductViewArray(Cart_ProductView[] arr_cart_products) {
        String str_response = null;
        try {
            str_response = new ObjectMapper().writeValueAsString(arr_cart_products);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str_response;
    }

    public String convert_order_notification(OrderNotification notification_newOrder) {

        try {
            return new ObjectMapper().writeValueAsString(notification_newOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertCategories(List<Category> categoryList) {

        try {
            return new ObjectMapper().writeValueAsString(categoryList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertCartToString(Cart cart) {
        if(cart != null){
            try {
                return new ObjectMapper().writeValueAsString(cart);
            } catch (IOException e) {
                return null;
            }
        }

        return null;
    }
}

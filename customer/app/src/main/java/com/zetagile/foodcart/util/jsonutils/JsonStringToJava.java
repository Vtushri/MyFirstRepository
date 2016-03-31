package com.zetagile.foodcart.util.jsonutils;

import com.zetagile.foodcart.dto.AddressLog;
import com.zetagile.foodcart.dto.Appointment;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.dto.Event;
import com.zetagile.foodcart.dto.FeedBack;
import com.zetagile.foodcart.dto.Offer;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.OrderStatus;
import com.zetagile.foodcart.dto.Product;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.dto.Promo;
import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.dto.User;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonStringToJava {


    public ArrayList<Order> convert_json_order_list(String orders) throws JsonParseException, JsonMappingException, IOException {
        if (orders != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Order> order_List = (ArrayList<Order>) objectMapper.readValue(orders, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Order.class));
            return order_List;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Promo> convert_adv_json(String adds) throws JsonParseException, JsonMappingException, IOException {
        if (adds != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Promo> adv_List = (List<Promo>) objectMapper.readValue(adds, TypeFactory.defaultInstance().constructCollectionType(List.class, Promo.class));

            return adv_List;
        }
        return null;
    }

    public User convert_user_json(String user) throws JsonParseException, JsonMappingException, IOException {
        if (user != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            User u = objectMapper.readValue(user, User.class);

            return u;
        }
        return null;
    }

    public Order convert_order_json(String sorder) throws JsonParseException, JsonMappingException, IOException {
        if (sorder != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Order order = objectMapper.readValue(sorder, Order.class);

            return order;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Category> convert_cat_json(String cats) throws JsonParseException, JsonMappingException, IOException {
        if (cats != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            List<Category> cat_List = (List<Category>) objectMapper.readValue(cats, TypeFactory.defaultInstance().constructCollectionType(List.class, Category.class));

            return cat_List;
        }
        return null;
    }

    public Category[] convert_catArray_json(String cats) throws JsonParseException, JsonMappingException, IOException {
        if (cats != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            Category[] cat_arr = objectMapper.readValue(cats, Category[].class);

            return cat_arr;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<AddressLog> convert_add_json(String address) throws JsonParseException, JsonMappingException, IOException {
        if (address != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            List<AddressLog> addr_List = (List<AddressLog>) objectMapper.readValue(address, TypeFactory.defaultInstance().constructCollectionType(List.class, AddressLog.class));

            return addr_List;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<ProductView> convert_products_json(String products) throws JsonParseException, JsonMappingException, IOException {
        if (products != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            List<ProductView> product_List = (List<ProductView>) objectMapper.readValue(products, TypeFactory.defaultInstance().constructCollectionType(List.class, ProductView.class));

            return product_List;
        }
        return null;
    }

    public List<Cart_ProductView> convert_cart_products_json(String products) throws JsonParseException, JsonMappingException, IOException {
        if (products != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            List<Cart_ProductView> product_List = objectMapper.readValue(products, TypeFactory.defaultInstance().constructCollectionType(List.class, Cart_ProductView.class));

            return product_List;
        }
        return null;
    }

    public Product convert_product(String product) throws JsonParseException, JsonMappingException, IOException {

        if (product != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product_obj = objectMapper.readValue(product, Product.class);
            return product_obj;
        }
        return null;
    }

    public ArrayList<Offer> convert_offer_list_json(String s) throws IOException {

        if (s != null) {

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Offer> offer_List = objectMapper.readValue(s, TypeFactory.defaultInstance().constructCollectionType(List.class, Offer.class));

            return offer_List;
        }
        return null;
    }

    public ArrayList<Promo> convert_promo_list_json(String s) throws IOException {

        if (s != null) {

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Promo> promo_List = objectMapper.readValue(s, TypeFactory.defaultInstance().constructCollectionType(List.class, Promo.class));

            return promo_List;
        }
        return null;
    }

    public ArrayList<Event> convert_event_list_json(String s) throws IOException {

        if (s != null) {

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Event> event_List = objectMapper.readValue(s, TypeFactory.defaultInstance().constructCollectionType(List.class, Event.class));

            return event_List;
        }
        return null;
    }

    public ProductView[] convertToProductArray(String str_json_string) throws IOException {

        if (str_json_string != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(str_json_string, ProductView[].class);

        }

        return null;
    }

    public Cart_ProductView[] convertToCartProductArray(String str_json_string) throws IOException {
        if (str_json_string != null) {

            return new ObjectMapper().readValue(str_json_string, Cart_ProductView[].class);
        }

        return null;
    }

    public OrderStatus convert_order_status(String str_order_status) throws IOException {

        if (str_order_status != null)
            return new ObjectMapper().readValue(str_order_status, OrderStatus.class);

        return null;
    }

    public ArrayList<Store> convert_stores(String str_stores) throws IOException {

        if (str_stores != null)
            return new ObjectMapper().readValue(str_stores, TypeFactory.defaultInstance().constructCollectionType(List.class, Store.class));
        return null;

    }

    public List<Appointment> convertAppointments2Java(String str_appointments) throws IOException {

        if (str_appointments != null)
            return new ObjectMapper().readValue(str_appointments, TypeFactory.defaultInstance().constructCollectionType(List.class, Appointment.class));
        return null;
    }

    public List<FeedBack> convertFeedbackStringToJava(String str_feedbacks) throws IOException {

        if (str_feedbacks != null)
            return new ObjectMapper().readValue(str_feedbacks, TypeFactory.defaultInstance().constructCollectionType(List.class, FeedBack.class));
        return null;

    }

    public Cart convertCartfromJson(String str_cart) {
        try {
            if (str_cart != null)
                return new ObjectMapper().readValue(str_cart, Cart.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<AddressLog> convert_address_log_from_string(String addresses) throws JsonParseException, JsonMappingException, IOException {
        if (addresses != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            List<AddressLog> addressLogList = objectMapper.readValue(addresses, TypeFactory.defaultInstance().constructCollectionType(List.class, AddressLog.class));

            return addressLogList;
        }
        return null;
    }
}

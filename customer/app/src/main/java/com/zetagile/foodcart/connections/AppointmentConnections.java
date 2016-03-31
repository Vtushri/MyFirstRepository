package com.zetagile.foodcart.connections;

import com.zetagile.foodcart.dto.Appointment;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class AppointmentConnections {

    public static String createAppointment(Appointment appointment, String user_id, String store_id) {


        ConnectionClass cc_connection = new ConnectionClass();

        String str_appointment = null;
        try {
            str_appointment = new ObjectMapper().writeValueAsString(appointment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str_response = null;
        if (str_appointment != null) ;
//             str_response = cc_connection.postRequest(ConnectionURLs.NEW_APPOINTMENT_URL + "?storeid=" + store_id + "&userid=" + user_id, str_appointment);
        else
            return null;

        if (str_response != null) {
            return str_response;
        }
        return null;
    }

    public static List<Appointment> getAllAppointments(String user_id, int page, int size) {
        ConnectionClass cc_connection = new ConnectionClass();

//        String str_response = cc_connection.getRequest(ConnectionURLs.GET_APPOINTMENTS_URL + user_id);

//        if (str_response != null) {
//            return new JsonStringToJava().convertAppointments2Java(str_response);
//        }
        return null;
    }

}

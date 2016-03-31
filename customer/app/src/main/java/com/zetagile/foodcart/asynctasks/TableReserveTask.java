package com.zetagile.foodcart.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.zetagile.foodcart.AppointmentActivity;
import com.zetagile.foodcart.connections.AppointmentConnections;
import com.zetagile.foodcart.dto.Appointment;

public class TableReserveTask extends AsyncTask<Void, Void, String> {

    ProgressDialog dialog_waiting;
    Context context;
    Appointment appointment;
    String user_id;
    String store_id;

    public TableReserveTask(Context context, Appointment appointment, String user_id, String store_id) {
        this.context = context;
        this.appointment = appointment;
        this.user_id = user_id;
        this.store_id = store_id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog_waiting = ProgressDialog.show(context, "Please wait...", "Reserving...");
    }

    @Override
    protected String doInBackground(Void... params) {
        String appointment_id = AppointmentConnections.createAppointment(appointment, user_id, store_id);
        return appointment_id;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog_waiting.isShowing())
            dialog_waiting.dismiss();

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        System.out.println(result);
        context.startActivity(new Intent(context, AppointmentActivity.class));
    }
}

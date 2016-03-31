package com.zetagile.foodcart.util.qtyutil;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.NumberPicker;

import com.zetagile.foodcart.R;

public class QuantityPickerDialog {

    Context context;
    NumberPicker qtyPicker;
    Dialog qtyDialog;

    public QuantityPickerDialog(Context context) {
        super();
        this.context = context;
    }

    public void makeDialog() {
        qtyDialog = new Dialog(context);
        qtyDialog.setContentView(R.layout.dialog_choose_qty);
        qtyDialog.setTitle("Choose Quantity");

        qtyPicker = (NumberPicker) qtyDialog.findViewById(R.id.qty_picker);
        qtyPicker.setMinValue(1);
        qtyPicker.setMaxValue(500);
        qtyPicker.setWrapSelectorWheel(false);
        qtyPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

    }

    public void setOkListener(View.OnClickListener clickListener) {

        qtyDialog.findViewById(R.id.set_button).setOnClickListener(clickListener);

    }

    public void showDialog() {
        qtyDialog.show();
    }

    public int getPickerCurrentValue() {
        return qtyPicker.getValue();
    }

    public void setPickerCurrentValue(int value) {
        qtyPicker.setValue(value);
    }

    public void dismiss() {
        qtyDialog.dismiss();
    }

}

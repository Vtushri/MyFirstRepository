package com.zetagile.foodcart.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.util.time.TimeDateDistanceUtil;

public abstract class CustomTimePickerDialog extends Dialog {

    TimePicker picker;
    Context context;
    TextView tv_title;

    public CustomTimePickerDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_time_picker);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        this.getWindow().setAttributes(lp);

        picker = (TimePicker) findViewById(R.id.timePicker);
        tv_title = (TextView) findViewById(R.id.tv_title);

        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveClick();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClick();
            }
        });
    }

    public void setTime(long milliSeconds) {
        picker.setIs24HourView(false);

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            picker.setHour(TimeDateDistanceUtil.getHourOfDay(milliSeconds));
            picker.setMinute(TimeDateDistanceUtil.getMinuteOfDay(milliSeconds));

        } else {

            picker.setCurrentHour(TimeDateDistanceUtil.getHourOfDay(milliSeconds));
            picker.setCurrentMinute(TimeDateDistanceUtil.getMinuteOfDay(milliSeconds));

        }
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void onPositiveClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            onTimeSet(picker.getHour(), picker.getMinute());
        else
            onTimeSet(picker.getCurrentHour(), picker.getCurrentMinute());
    }

    public void onCancelClick() {
        dismiss();
    }

    public abstract void onTimeSet(int hours, int minutes);
}

package com.zetagile.foodcart;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;


public class PolicyActivity extends BackNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        TextView policy_link_client =(TextView)findViewById(R.id.policy_link1);
        policy_link_client.setClickable(true);
        policy_link_client.setMovementMethod(LinkMovementMethod.getInstance());
        String client_link = "<a href='http://www.viandd.com/docs/viandd-privacy.pdf'> Privacy Policy </a>";
        policy_link_client.setText(Html.fromHtml(client_link));

        TextView policy_link_viandd = (TextView) findViewById(R.id.policy_link2);
        policy_link_viandd.setClickable(true);
        policy_link_viandd.setMovementMethod(LinkMovementMethod.getInstance());
        String viand_link = "<a href = 'http://www.viandd.com/docs/viandd-eula.pdf'>EULA Policy</a>";
        policy_link_viandd.setText(Html.fromHtml(viand_link));

        TextView policy_link_others = (TextView) findViewById(R.id.policy_link3);
        policy_link_others.setClickable(true);
        policy_link_others.setMovementMethod(LinkMovementMethod.getInstance());
        String other_link = "<a href = "+getResources().getString(R.string.vendor_policy)+">" +getResources().getString(R.string.app_name) +" Policy</a>";
        policy_link_others.setText(Html.fromHtml(other_link));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

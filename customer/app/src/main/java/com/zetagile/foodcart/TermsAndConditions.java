package com.zetagile.foodcart;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TermsAndConditions extends BackNavigation {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        TextView terms_link_client =(TextView)findViewById(R.id.terms_link1);
        terms_link_client.setClickable(true);
        terms_link_client.setMovementMethod(LinkMovementMethod.getInstance());
        String client_link = "<a href='http://www.viandd.com/docs/viandd-privacy.pdf'> Privacy Policy </a>";

        terms_link_client.setText(Html.fromHtml(client_link));

        TextView terms_link_viandd = (TextView) findViewById(R.id.terms_link2);
        terms_link_viandd.setClickable(true);
        terms_link_viandd.setMovementMethod(LinkMovementMethod.getInstance());
        String viand_link = "<a href ='http://www.viandd.com/docs/viandd-eula.pdf'>EULA Policy</a>";
        terms_link_viandd.setText(Html.fromHtml(viand_link));

        TextView terms_link_others = (TextView) findViewById(R.id.terms_link3);
        terms_link_others.setClickable(true);
        terms_link_others.setMovementMethod(LinkMovementMethod.getInstance());
        String other_link = "<a href = "+getResources().getString(R.string.vendor_policy)+">" +getResources().getString(R.string.app_name) +" Policy</a>";
        terms_link_others.setText(Html.fromHtml(other_link));


    }
}

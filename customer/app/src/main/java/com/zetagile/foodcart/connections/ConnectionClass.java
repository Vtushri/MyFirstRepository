package com.zetagile.foodcart.connections;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.zetagile.foodcart.util.ConfigUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ConnectionClass {


    private static final String TAG = "CONNECTION_CLASS";

    private static X509Certificate ca = null;
    private void getKey(Context context) {
        try {
            AssetManager manager = context.getAssets();
            InputStream in = manager.open("appserverkeystore");

            KeyStore trusted = KeyStore.getInstance("PKCS12");
            trusted.load(in, "Zet@15o7".toCharArray());

            ca = (X509Certificate) trusted.getCertificate("appserver");

            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
            trustManagerFactory.init(trusted);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

           //create a new SSL socket Factory instead of default one to avoid conflict with Google Maps //HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(createSslSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * create a custom sslsocketfactory to trust server connections
     *
     * @return
     * @throws Exception
     */
    private static SSLSocketFactory createSslSocketFactory() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                for (X509Certificate cert : chain) {

                    Log.d(TAG, "Root certificate has " + ca.getIssuerDN().getName() + " and " + ca.getSubjectDN().getName());
                    Log.d(TAG, "Found a certificate with " + cert.getIssuerDN().getName() + " and " + cert.getSubjectDN().getName());
                    // check if current certificate belongs to google
                    // We can't verify google's certificate by key because we are not
                    // storing its certificate in our truststore right now. So we 
                    // accept any certificate issued by Google Internet Authority G2 irrespective of public key content.
                    if (cert.getIssuerDN().getName().equals("CN=Google Internet Authority G2,O=Google Inc,C=US")) {
                        return;
                    }

                    // check if the certificate is the selfsigned trusted one
                    if (verifyCertificate(ca, cert)) {
                        return;
                    }
                }

                // if none certificate trusted throw certificate exception to tell to not trust connection
                try {
                    throw new CertificateException();
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }
        }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        return sslContext.getSocketFactory();
    }

    /**
     * verify a certificate against the other
     */
    private static boolean verifyCertificate(X509Certificate cert1, X509Certificate cert2) {
        try {
            cert1.verify(cert2.getPublicKey());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getRequest(Context context, String url) {
        Log.d(TAG, "GET Request: " + url);
        try {
            ConfigUtil utils = new ConfigUtil(context);

            if (utils.isSecureProtocolEnabled()) {

                URL https_url = new URL(url);
                getKey(context);

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) https_url.openConnection();


                if (httpsURLConnection.getResponseCode() == HttpStatus.SC_OK
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_ACCEPTED
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_CREATED
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_MOVED_PERMANENTLY
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_MOVED_TEMPORARILY
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_MULTI_STATUS
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_NO_CONTENT) {


                    BufferedReader br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

                    StringBuilder str = new StringBuilder();

                    while (true) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        str.append(line);
                    }

                    br.close();

                    String result = str.toString();
                    if (!result.isEmpty())
                        return result;
                }
            } else {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url);
                HttpResponse http_response = httpclient.execute(httpget);
                Log.d(TAG, "GET Request: " + url);
                if (http_response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_MULTI_STATUS
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_PARTIAL_CONTENT
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                    HttpEntity httpEntity = http_response.getEntity();
                    String result = EntityUtils.toString(httpEntity);
                    Log.d(TAG, "GET Response: " + result);
                    return result;
                }

                String failed_reason = http_response.getStatusLine().getReasonPhrase();
                displayError(context, "Error :" + failed_reason);
                Log.d(TAG, "GET Response error: " + failed_reason);
            }

        } catch (Exception e) {
            e.printStackTrace();
            displayError(context, "Request Error : Please try again");
            Log.d(TAG, "GET Response exception: " + e.getLocalizedMessage());
            Log.d(TAG, "GET Response exception: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String postRequest(Context context, String url, String str_json_data) {
        try {
            Log.d(TAG, "POST Request: " + url);
            Log.d(TAG, "POST Data: " + str_json_data);

            ConfigUtil utils = new ConfigUtil(context);

            if (utils.isSecureProtocolEnabled()) {

                URL https_url = new URL(url);

                getKey(context);

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) https_url.openConnection();

                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setRequestProperty("Content-Type", "application/json");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);

                DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
                wr.writeBytes(str_json_data);
                wr.flush();
                wr.close();

                if (httpsURLConnection.getResponseCode() == HttpStatus.SC_OK
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_ACCEPTED
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_CREATED
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_MOVED_PERMANENTLY
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_MOVED_TEMPORARILY
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_MULTI_STATUS
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT
                        || httpsURLConnection.getResponseCode() == HttpStatus.SC_NO_CONTENT) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

                    StringBuilder str = new StringBuilder();

                    while (true) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        str.append(line);
                    }
                    br.close();
                    String result = str.toString();
                    if (!result.isEmpty())
                        return result;

                }

            } else {
                HttpClient httpclient = new DefaultHttpClient();

                StringEntity se_entity = new StringEntity(str_json_data);
                HttpPost http_post = new HttpPost(url);
                http_post.setEntity(se_entity);
                http_post.setHeader("Accept", "application/json");
                http_post.setHeader("Content-type", "application/json");

                HttpResponse http_response = httpclient.execute(http_post);

                if (http_response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_MULTI_STATUS
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_PARTIAL_CONTENT
                        || http_response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                    HttpEntity httpEntity = http_response.getEntity();
                    String result = EntityUtils.toString(httpEntity);
                    Log.d(TAG, "POST Response: " + result);
                    return result;
                }

                String failed_reason = http_response.getStatusLine().getReasonPhrase();
                displayError(context, "Error :" + failed_reason);
                Log.d(TAG, "POST Response error: " + failed_reason);
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayError(context, "Request Error : Please try again");
            Log.d(TAG, "POST Response exception: " + e.getLocalizedMessage());
        }

        return null;
    }

    public boolean isJsonString(String input) {
        try {
            new JSONObject(input);
        } catch (JSONException exception) {
            try {
                new JSONArray(input);
            } catch (JSONException e) {
                return false;
            }
        }
        return true;
    }

    public void displayError(Context context, String str_response) {
        System.out.println("ERROR: " + str_response);
    }

}

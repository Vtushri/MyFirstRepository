package com.zetagile.foodcart.fragments.aboutus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.adapters.FacebookAdapter;
import com.zetagile.foodcart.dto.facebook.FacebookFeeds;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FeedBackFragment extends Fragment {

    EditText comment;
    Button submit, cancel;
    FloatingActionButton postButton;
    AccessToken accessToken, myToken;
    CallbackManager callbackManager;
    JSONArray object;
    List<FacebookFeeds> listData = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Activity context;
    Dialog dialog = null;
    TextView tv_no_feedback;

    String app_id;
    String fbAccessToken;
    String page_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app_id = getString(R.string.fb_app_id);
        fbAccessToken = getString(R.string.fb_access_token);
        page_id = getString(R.string.fb_page_id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.activity_feed_back, container, false);
        tv_no_feedback = (TextView) view.findViewById(R.id.no_feedback);

        dialog = new Dialog(context);
        dialog.setTitle("Feedback");
        dialog.setContentView(R.layout.feedbackcommentsdialog);
        comment = (EditText) dialog.findViewById(R.id.ed_comment_dialog);
        submit = (Button) dialog.findViewById(R.id.dialog_submit);
        cancel = (Button) dialog.findViewById(R.id.dialog_cancel);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        refreshButtonsState();
        getComments(context);

        postButton = (FloatingActionButton) view.findViewById(R.id.post_data);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (refreshButtonsState() == false) {
                    LoginManager.getInstance().logInWithPublishPermissions(FeedBackFragment.this, Arrays.asList("manage_pages,publish_actions"));
                    callManager();
                } else {
                    dialog.show();
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle params = new Bundle();
                            params.putString("message", comment.getText().toString());

                            GraphRequest graphRequest = new GraphRequest(accessToken, "/" + page_id + "/feed", params, HttpMethod.POST, new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse response) {
                                    System.out.println("I am the response" + response);
                                }
                            });
                            graphRequest.executeAsync();
                            dialog.hide();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                        }
                    });
                }
            }
        });

        return view;
    }


    private void getComments(final Context context) {

        myToken = new AccessToken(fbAccessToken, app_id, page_id, Arrays.asList("manage_pages, publish_actions, public_profile"), null, null, null, null);
        GraphRequest graphRequest = new GraphRequest(
                myToken,
                "/" + page_id + "/",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response.getJSONObject() == null) {
                            tv_no_feedback.setVisibility(View.VISIBLE);
                        } else
                            try {
                                object = response.getJSONObject().getJSONObject("feed").getJSONArray("data");


                                for (int j = 0; j < object.length(); j++) {

                                    FacebookFeeds feedBack = new FacebookFeeds();
                                    feedBack.setCommenter(object.getJSONObject(j).getJSONObject("from").getString("name"));

                                    if (!object.getJSONObject(j).toString().contains("message"))
                                        feedBack.setComment("No Message");
                                    else
                                        feedBack.setComment(object.getJSONObject(j).getString("message"));

                                    listData.add(feedBack);
                                }

                                adapter = new FacebookAdapter(listData);

                                recyclerView.setAdapter(adapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }
        );

        Bundle params = new Bundle();
        params.putString("fields", "feed{from,message}");
        graphRequest.setParameters(params);
        graphRequest.executeAsync();
    }


    private void callManager() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("I AM ON SUCCESS");
                refreshButtonsState();
            }

            @Override
            public void onCancel() {
                System.out.println("I AM ON CANCEL");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("I AM ON ERROR");
                e.printStackTrace();
            }
        });
    }

    private boolean refreshButtonsState() {
        if (!isLoggedIn()) {
            System.out.println("I am btnLoginPOst");
//            btnLoginToPost.setVisibility(View.VISIBLE);
            return false;

        } else {
            System.out.println("I am commentSection");
//            btnLoginToPost.setVisibility(View.GONE);
            return true;

        }
    }

    public boolean isLoggedIn() {
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("HHH" + requestCode + "," + resultCode + "," + data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            dialog.hide();
        } else {
            dialog.show();
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle params = new Bundle();
                    params.putString("message", comment.getText().toString());

                    GraphRequest graphRequest = new GraphRequest(accessToken, "/" + page_id + "/feed", params, HttpMethod.POST, new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            System.out.println("I am the response" + response);
                        }
                    });
                    graphRequest.executeAsync();
                    dialog.hide();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();
                }
            });
        }
    }


}

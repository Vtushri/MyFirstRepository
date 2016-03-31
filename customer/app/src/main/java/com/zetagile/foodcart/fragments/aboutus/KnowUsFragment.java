package com.zetagile.foodcart.fragments.aboutus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.widget.LikeView;
import com.zetagile.foodcart.R;

@SuppressLint("ValidFragment")
public class KnowUsFragment extends Fragment {
    private final ViewPager viewPager;
    CardView crd_photo, crd_video;
    TextView tv_small;
    LikeView likeView;

    @SuppressLint("ValidFragment")
    public KnowUsFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_know_us, container, false);
        tv_small = (TextView) view.findViewById(R.id.tv_small);
        crd_photo = (CardView) view.findViewById(R.id.photo_Card);
        crd_video = (CardView) view.findViewById(R.id.video_Card);
        likeView = (LikeView) view.findViewById(R.id.likeView);

        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        likeView.setObjectIdAndType("1703531436545339", LikeView.ObjectType.OPEN_GRAPH);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

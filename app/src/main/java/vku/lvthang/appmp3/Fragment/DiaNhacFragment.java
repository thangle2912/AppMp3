package vku.lvthang.appmp3.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import vku.lvthang.appmp3.R;

public class DiaNhacFragment extends Fragment {

    View view;
    CircleImageView circleImageView;
    public ObjectAnimator objectAnimator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_dia_nhac, container, false);
        circleImageView= view.findViewById(R.id.imgDianhac);
        objectAnimator=ObjectAnimator.ofFloat(circleImageView,"rotation",0f,360f);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        return view;
    }
    public void Playnhac(String hinhanh) {
        Picasso.with(getActivity()).load(hinhanh).into(circleImageView);
    }
}
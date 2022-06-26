package vku.lvthang.appmp3.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vku.lvthang.appmp3.Adapter.DanhsachPhatnhacAdapter;
import vku.lvthang.appmp3.PhatNhacActivity;
import vku.lvthang.appmp3.R;


public class DanhsachbaihatPhatnhacFragment extends Fragment {

    View view;
    RecyclerView RcvDanhsachbaihatPhatnhac;
    DanhsachPhatnhacAdapter danhsachPhatnhacAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_danhsachbaihat_phatnhac,container,false);
        RcvDanhsachbaihatPhatnhac = view.findViewById(R.id.RcvDanhsachbaihatPhatnhac);
        if (PhatNhacActivity.arrayListBaihat.size()>0){
            danhsachPhatnhacAdapter= new DanhsachPhatnhacAdapter(getActivity(), PhatNhacActivity.arrayListBaihat);
            RcvDanhsachbaihatPhatnhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            RcvDanhsachbaihatPhatnhac.setAdapter(danhsachPhatnhacAdapter);
        }
        return view;
    }
}
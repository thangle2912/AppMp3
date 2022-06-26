package vku.lvthang.appmp3.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.DanhhsachChudeActivity;
import vku.lvthang.appmp3.DanhsachTheloaiActivity;
import vku.lvthang.appmp3.DanhsachbaihatActivity;
import vku.lvthang.appmp3.Model.Chude;
import vku.lvthang.appmp3.Model.Chudevatheloai;
import vku.lvthang.appmp3.Model.PlaylistModel;
import vku.lvthang.appmp3.Model.Theloai;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;


public class ChudevaTheloaiFragment extends Fragment {

    View view;
    HorizontalScrollView horizontalScrollView;
    TextView txtxemthem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chudeva_theloai, container, false);
        anhxa();
        GetData();
        txtxemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhhsachChudeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void anhxa() {
        horizontalScrollView = view.findViewById(R.id.scvChudevatheloai);
        txtxemthem = view.findViewById(R.id.txtXemthemChudevatheloai);
    }
    private void GetData() {
        DataService dataService = APIService.getService();
        Call<Chudevatheloai> callback = dataService.GetDataChuDeVaTheLoai();
        callback.enqueue(new Callback<Chudevatheloai>() {
            @Override
            public void onResponse(Call<Chudevatheloai> call, Response<Chudevatheloai> response) {
                Chudevatheloai chudevatheloai = response.body();

                final ArrayList<Chude> chudeArrayList = new ArrayList<>();
                chudeArrayList.addAll(chudevatheloai.getChude());

                final ArrayList<Theloai> theloaiArrayList = new ArrayList<>();
                theloaiArrayList.addAll(chudevatheloai.getTheloai());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(580,300);
                layoutParams.setMargins(8,16,8,16);
                for (int i = 0;i <(chudeArrayList.size());i++){//chude
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(12);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (chudeArrayList.get(i).getHinhchude()!= null){
                        Picasso.with(getActivity()).load(chudeArrayList.get(i).getHinhchude()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhsachTheloaiActivity.class);
                            intent.putExtra("idchude",chudeArrayList.get(finalI));
                            startActivity(intent);
                        }
                    });
                }
                for (int j = 0;j <(theloaiArrayList.size());j++){//theloai
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(12);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (theloaiArrayList.get(j).getHinhtheloai()!= null){
                        Picasso.with(getActivity()).load(theloaiArrayList.get(j).getHinhtheloai()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalJ = j;
                   imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhsachbaihatActivity.class);
                            intent.putExtra("idtheloai",theloaiArrayList.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<Chudevatheloai> call, Throwable t) {

            }
        });
    }
}
package vku.lvthang.appmp3.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vku.lvthang.appmp3.Fragment.MoodFragment;
import vku.lvthang.appmp3.Model.MoodModel;
import vku.lvthang.appmp3.R;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {

    private List<MoodModel> moodList;
    private MoodFragment context;
    private IClickItemListener iClickItemListener;

    public interface IClickItemListener{
        void onClickItemHits(int moodModel);
    }


    public MoodAdapter(List<MoodModel> moodList, MoodFragment context,IClickItemListener iClickItemListener) {
        this.moodList = moodList;
        this.context = context;
        this.iClickItemListener = iClickItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_mood,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(moodList.get(position).getImg())
                .into(holder.imgMood);

    }
    @Override
    public int getItemCount() {

        return moodList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMood = itemView.findViewById(R.id.imgMood);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iClickItemListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            iClickItemListener.onClickItemHits(position);
                        }
                    }
                }
            });

        }
    }
}

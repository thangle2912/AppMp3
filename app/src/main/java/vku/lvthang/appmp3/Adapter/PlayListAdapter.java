package vku.lvthang.appmp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vku.lvthang.appmp3.Model.PlaylistModel;
import vku.lvthang.appmp3.R;

public class PlayListAdapter extends ArrayAdapter<PlaylistModel> {
    public PlayListAdapter(@NonNull Context context, int resource, @NonNull List<PlaylistModel> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        TextView txtNamePlaylist;
        ImageView imgBackgroundplaylist, imgPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      ViewHolder viewHolder = null;
      if (convertView == null){
          LayoutInflater layoutInflater = LayoutInflater.from(getContext());
          convertView = layoutInflater.inflate(R.layout.item_playlist,null);

          viewHolder = new ViewHolder();
          viewHolder.txtNamePlaylist= convertView.findViewById(R.id.txtNamePlaylist);
          viewHolder.imgBackgroundplaylist=convertView.findViewById(R.id.imgBackgroundPlaylist);
          viewHolder.imgPlaylist=convertView.findViewById(R.id.imgPlaylist);
            convertView.setTag(viewHolder);
      }else {
          viewHolder = (ViewHolder) convertView.getTag();
      }
        PlaylistModel playlistModel= getItem(position);
        Picasso.with(getContext()).load(playlistModel.getHinh()).into(viewHolder.imgBackgroundplaylist);
        Picasso.with(getContext()).load(playlistModel.getIcon()).into(viewHolder.imgPlaylist);
        viewHolder.txtNamePlaylist.setText(playlistModel.getTen());
        return convertView;
    }
}

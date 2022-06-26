package vku.lvthang.appmp3.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaihatModel implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Ten")
    @Expose
    private String ten;
    @SerializedName("Hinh")
    @Expose
    private String hinh;
    @SerializedName("Casi")
    @Expose
    private String casi;
    @SerializedName("Link")
    @Expose
    private String link;
    @SerializedName("Luotthich")
    @Expose
    private String luotthich;

    protected BaihatModel(Parcel in) {
        id = in.readString();
        ten = in.readString();
        hinh = in.readString();
        casi = in.readString();
        link = in.readString();
        luotthich = in.readString();
    }

    public static final Creator<BaihatModel> CREATOR = new Creator<BaihatModel>() {
        @Override
        public BaihatModel createFromParcel(Parcel in) {
            return new BaihatModel(in);
        }

        @Override
        public BaihatModel[] newArray(int size) {
            return new BaihatModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getCasi() {
        return casi;
    }

    public void setCasi(String casi) {
        this.casi = casi;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(String luotthich) {
        this.luotthich = luotthich;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ten);
        dest.writeString(hinh);
        dest.writeString(casi);
        dest.writeString(link);
        dest.writeString(luotthich);
    }
}
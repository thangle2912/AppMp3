package vku.lvthang.appmp3.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlbumModel implements Serializable {

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

}

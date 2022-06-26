package vku.lvthang.appmp3.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theloai implements Serializable {

    @SerializedName("Idtheloai")
    @Expose
    private String idtheloai;
    @SerializedName("Idchude")
    @Expose
    private String idchude;
    @SerializedName("Tentheloai")
    @Expose
    private String tentheloai;
    @SerializedName("Hinhtheloai")
    @Expose
    private String hinhtheloai;

    public String getIdtheloai() {
        return idtheloai;
    }

    public void setIdtheloai(String idtheloai) {
        this.idtheloai = idtheloai;
    }

    public String getIdchude() {
        return idchude;
    }

    public void setIdchude(String idchude) {
        this.idchude = idchude;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public String getHinhtheloai() {
        return hinhtheloai;
    }

    public void setHinhtheloai(String hinhtheloai) {
        this.hinhtheloai = hinhtheloai;
    }

}
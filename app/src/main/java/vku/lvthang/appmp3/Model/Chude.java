package vku.lvthang.appmp3.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Chude implements Serializable {

    @SerializedName("Idchude")
    @Expose
    private String idchude;
    @SerializedName("Tenchude")
    @Expose
    private String tenchude;
    @SerializedName("Hinhchude")
    @Expose
    private String hinhchude;

    public String getIdchude() {
        return idchude;
    }

    public void setIdchude(String idchude) {
        this.idchude = idchude;
    }

    public String getTenchude() {
        return tenchude;
    }

    public void setTenchude(String tenchude) {
        this.tenchude = tenchude;
    }

    public String getHinhchude() {
        return hinhchude;
    }

    public void setHinhchude(String hinhchude) {
        this.hinhchude = hinhchude;
    }

}

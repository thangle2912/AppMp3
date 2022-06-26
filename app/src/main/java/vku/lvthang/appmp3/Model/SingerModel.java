package vku.lvthang.appmp3.Model;

public class SingerModel {

    private String img;
    private String nameSinger;

    public SingerModel(String img, String nameSinger) {
        this.img = img;
        this.nameSinger = nameSinger;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }
}


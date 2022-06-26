package vku.lvthang.appmp3.Model;


public class SongModel {
    private String name;
    private String img;
    private String url;
    private  String singer;
    private  String id;
    private  String playlist;


    public SongModel() {
    }

    public SongModel(String name, String img, String url, String singer, String id,String playlist) {
        this.name = name;
        this.img = img;
        this.url = url;
        this.singer = singer;
        this.id = id;
        this.playlist= playlist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }
}

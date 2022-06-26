package vku.lvthang.appmp3.Sevice;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vku.lvthang.appmp3.Model.AlbumModel;
import vku.lvthang.appmp3.Model.BaihatModel;
import vku.lvthang.appmp3.Model.Chude;
import vku.lvthang.appmp3.Model.Chudevatheloai;
import vku.lvthang.appmp3.Model.LoginResponseModel;
import vku.lvthang.appmp3.Model.PlaylistModel;
import vku.lvthang.appmp3.Model.QuangcaoModel;
import vku.lvthang.appmp3.Model.RegistrationResponseModel;
import vku.lvthang.appmp3.Model.Theloai;

public interface DataService {

    @GET("songbanner.php")
    Call<List<QuangcaoModel>> GetDataBanner();

    @GET("playlistday.php")
    Call<List<PlaylistModel>> GetDataPlaylistDay();

    @GET("chudevatheloai.php")
    Call<Chudevatheloai> GetDataChuDeVaTheLoai();

    @GET("album.php")
    Call<List<AlbumModel>> GetDataAlbum();

    @GET("baihatduocthich.php")
    Call<List<BaihatModel>> GetDataBaiHatYeuThich();

    @FormUrlEncoded
    @POST ("danhsachbaihat.php")
    Call<List<BaihatModel>> GetDataDanhsachbaihatQuangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST ("danhsachbaihat.php")
    Call<List<BaihatModel>> GetDataDanhsachbaihatPlaylist(@Field("idplaylist") String idplaylist);

    @FormUrlEncoded
    @POST ("danhsachbaihat.php")
    Call<List<BaihatModel>> GetDataDanhsachbaihatTheloai(@Field("idtheloai") String idtheloai);

    @FormUrlEncoded
    @POST ("danhsachbaihat.php")
    Call<List<BaihatModel>> GetDataDanhsachbaihatAlbum(@Field("idalbum") String idalbum);


    @GET("allplaylist.php")
    Call<List<PlaylistModel>> GetDataDanhsachPlaylist();

    @GET("allchude.php")
    Call<List<Chude>> GetDataDanhsachChude();

    @GET("allalbum.php")
    Call<List<AlbumModel>> GetDataDanhsachAlbum();

    @GET("allbaihat.php")
    Call<List<BaihatModel>> GetDataDanhsachBaihat();


    @FormUrlEncoded
    @POST ("theloaitheochude.php")
    Call<List<Theloai>> GetDataDanhsachtheloaiChude(@Field("idchude") String idchude);

    @FormUrlEncoded
    @POST ("updateluotthich.php")
    Call<String> UpdateLuotthich(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST ("timkiembaihat.php")
    Call<List<BaihatModel>> GetDataSearchbaihat(@Field("tukhoa") String tukhoa);

    @FormUrlEncoded
    @POST("register.php")
    Call<RegistrationResponseModel> register(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponseModel> login(@Field("email") String email, @Field("password") String password);


}

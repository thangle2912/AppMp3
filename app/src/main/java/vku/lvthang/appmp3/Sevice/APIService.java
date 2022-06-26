package vku.lvthang.appmp3.Sevice;

public class APIService {

    private  static String base_url ="https://appmp3-thangle2912.000webhostapp.com/Sever/";
    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
    public static final String PREFERENCE_NAME = "session";
    public static final String KEY_ISE_LOGGED_IN = "isLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
}

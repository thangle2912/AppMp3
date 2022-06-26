package vku.lvthang.appmp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Model.LoginResponseModel;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button buttonLogin;
    TextView textCreateAccount,txtBoqua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtBoqua=findViewById(R.id.txtboqua);
        txtBoqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SplashActivity.class));
            }
        });
        textCreateAccount = findViewById(R.id.lnkRegister);
        textCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        inputEmail = findViewById(R.id.txtloginEmail);
        inputEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(LoginActivity.this);
                }
            }
        });
        inputPassword = findViewById(R.id.txtloginPwd);
        inputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(LoginActivity.this);
                }
            }
        });

        buttonLogin = findViewById(R.id.btnlogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEmail.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Nhập Email", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(inputEmail.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                }   else if (inputPassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    private void login() {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setMessage("Đăng nhập...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        DataService dataService = APIService.getService();
        Call<LoginResponseModel> login = dataService.login(inputEmail.getText().toString(), inputPassword.getText().toString());
        login.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                LoginResponseModel responseBody = response.body();
                if (responseBody != null) {
                    if (responseBody.getSuccess().equals("1")) {
                        SharedPreferences preferences = getSharedPreferences(APIService.PREFERENCE_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(APIService.KEY_ISE_LOGGED_IN, true);
                        editor.putString(APIService.KEY_USERNAME, responseBody.getUserDetailObject().getUserDetails().get(0).getFirstName() + " " + responseBody.getUserDetailObject().getUserDetails().get(0).getLastName());
                        /*editor.putString(Constants.KEY_LASTNAME, responseBody.getUserDetailObject().getUserDetails().get(0).getLastName());*/
                        editor.putString(APIService.KEY_EMAIL, responseBody.getUserDetailObject().getUserDetails().get(0).getEmail());
                        editor.apply();
                        Toast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
package vku.lvthang.appmp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Model.RegistrationResponseModel;
import vku.lvthang.appmp3.Sevice.APIRetrofitClient;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class RegisterActivity extends AppCompatActivity {

    EditText inputName, inputPassword, inputemail;
    Button buttonRegister;
    TextView linklogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = findViewById(R.id.txtName);
        inputemail = findViewById(R.id.txtEmail);
        inputPassword = findViewById(R.id.txtPwd);
        linklogin = findViewById(R.id.lnkLogin);
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonRegister = findViewById(R.id.btnregister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (inputName.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Nhập tên", Toast.LENGTH_SHORT).show();
                } else if (inputemail.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Nhập email", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(inputemail.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                }  else {

                    HashMap<String, String> params = new HashMap<>();
                    params.put("name", inputName.getText().toString());
                    params.put("email", inputemail.getText().toString());
                    params.put("password", inputPassword.getText().toString());
                    register(params);
                }

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

    private void register(HashMap<String, String> params) {

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setMessage("Đăng kí...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DataService dataService = APIService.getService();
        Call<RegistrationResponseModel> registerCall = dataService.register(params);
        registerCall.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponseModel> call, @NonNull Response<RegistrationResponseModel> response) {
                RegistrationResponseModel responseBody = response.body();
//                Log.d("BBB",responseBody.getSuccess());
                if (responseBody != null) {
                    if (responseBody.getSuccess().equals("1")) {
                        Toast.makeText(RegisterActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponseModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
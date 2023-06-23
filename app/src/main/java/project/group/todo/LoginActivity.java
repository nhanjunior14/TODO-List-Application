package project.group.todo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText user, password;
    private Button login;
    private ImageView showPass, hidePass;
    private TextView register;
    private ProgressDialog process;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.edt_Username);
        password = findViewById(R.id.edt_Password);
        login = findViewById(R.id.btn_Login);
        showPass = findViewById(R.id.imgv_Showpass);
        hidePass = findViewById(R.id.imgv_Hidepass);
        register = findViewById(R.id.tv_Register);
        process = new ProgressDialog(this);

        showPass.setVisibility(View.GONE);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPass.setVisibility(View.GONE);
                hidePass.setVisibility(View.VISIBLE);
                password.setSelection(password.length());
            }
        });

        hidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hidePass.setVisibility(View.GONE);
                showPass.setVisibility(View.VISIBLE);
                password.setSelection(password.length());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonLogin();
            }
        });
    }

    private void clickButtonLogin() {
        String email = user.getText().toString().trim();
        String pass = password.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        process.show();

        if(email.equals("")){
            Toast.makeText(LoginActivity.this,"Vui lòng nhập tài khoản",Toast.LENGTH_SHORT).show();
            process.dismiss();
        } else if(pass.equals("")) {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            process.dismiss();
        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    process.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(new Intent(LoginActivity.this, NavActivity.class));
                        finishAffinity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

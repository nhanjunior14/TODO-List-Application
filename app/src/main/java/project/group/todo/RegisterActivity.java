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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password, passwordConfirm;
    ImageView showpass, hidepass, showpassConfirm, hidepassConfirm;
    Button btnRegister;
    ProgressDialog process;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.edt_Email);
        password = findViewById(R.id.edt_password);
        passwordConfirm = findViewById(R.id.edt_confirm_password);
        showpass = findViewById(R.id.imgv_Showpass);
        hidepass = findViewById(R.id.imgv_Hidepass);
        showpassConfirm = findViewById(R.id.imgv_confirm_Showpass);
        hidepassConfirm = findViewById(R.id.imgv_confirm_Hidepass);
        btnRegister = findViewById(R.id.btn_Register);
        process = new ProgressDialog(this);

        showpass.setVisibility(View.GONE);
        showpassConfirm.setVisibility(View.GONE);

        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showpass.setVisibility(View.GONE);
                hidepass.setVisibility(View.VISIBLE);
                password.setSelection(password.length());
            }
        });

        hidepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hidepass.setVisibility(View.GONE);
                showpass.setVisibility(View.VISIBLE);
                password.setSelection(password.length());
            }
        });

        showpassConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showpassConfirm.setVisibility(View.GONE);
                hidepassConfirm.setVisibility(View.VISIBLE);
                passwordConfirm.setSelection(passwordConfirm.length());
            }
        });

        hidepassConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hidepassConfirm.setVisibility(View.GONE);
                showpassConfirm.setVisibility(View.VISIBLE);
                passwordConfirm.setSelection(passwordConfirm.length());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonRegister();
            }
        });
    }

    private void clickButtonRegister() {
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirmPass = passwordConfirm.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        process.show();

        if(email.equals("")) {
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
            process.dismiss();
        } else if(pass.equals("")) {
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            process.dismiss();
        } else if(confirmPass.equals("")) {
            Toast.makeText(RegisterActivity.this, "Vui lòng xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
            process.dismiss();
        } else if(!pass.equals(confirmPass)){
            Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không trùng khớp với mật khẩu, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
            process.dismiss();
        } else {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    process.dismiss();

                    if(task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finishAffinity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Tài khoản tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

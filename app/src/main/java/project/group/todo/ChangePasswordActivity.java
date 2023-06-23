package project.group.todo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import project.group.todo.Fragment.Account;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText edtNewPass;
    private Button btnUpdate;
    private ImageButton btnBack;
    private ImageView showNewpass, hideNewpass;
    private ProgressDialog process;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edtNewPass = findViewById(R.id.edt_newPassword);
        btnUpdate = findViewById(R.id.btn_UpdatePass);
        btnBack = findViewById(R.id.btn_Back);
        showNewpass = findViewById(R.id.imgv_showNewpass);
        hideNewpass = findViewById(R.id.imgv_hideNewpass);
        process = new ProgressDialog(this);

        showNewpass.setVisibility(View.GONE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this, Account.class));
            }
        });

        showNewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showNewpass.setVisibility(View.GONE);
                hideNewpass.setVisibility(View.VISIBLE);
                edtNewPass.setSelection(edtNewPass.length());
            }
        });

        hideNewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hideNewpass.setVisibility(View.GONE);
                showNewpass.setVisibility(View.VISIBLE);
                edtNewPass.setSelection(edtNewPass.length());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangePassword();
            }
        });
    }

    private void onClickChangePassword() {
        String newPass = edtNewPass.getText().toString().trim();
        process.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(newPass.equals("")) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
            process.dismiss();
        }

        if (user != null) {
            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        process.dismiss();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        process.dismiss();
                    }
                }
            });
        }
    }
}
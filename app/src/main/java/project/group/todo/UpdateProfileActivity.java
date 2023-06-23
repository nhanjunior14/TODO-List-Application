package project.group.todo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class UpdateProfileActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private ImageView imgUser;
    private EditText name, email;
    private Button btnUpdate;
    private Uri uri;
    public static int REQUEST_CODE = 10;

    final public ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();

                if(intent == null) {
                    return;
                }

                uri = intent.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    setBitmapImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        btnBack = findViewById(R.id.btn_Back);
        imgUser = findViewById(R.id.imgv_ImageUpdate);
        name = findViewById(R.id.edt_NameUpdate);
        email = findViewById(R.id.edt_EmailUpdate);
        btnUpdate = findViewById(R.id.btn_Update);

        setInformationOfUser();

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonUpdate();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateProfileActivity.this, NavActivity.class));
            }
        });
    }

    public void setBitmapImage(Bitmap imageBitmap) {
        imgUser.setImageBitmap(imageBitmap);
    }

    private void setInformationOfUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            return;
        }

        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.nhu_nhi).into(imgUser);
    }

    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onClickGallery();
            return;
        }

        if(PackageManager.PERMISSION_GRANTED == this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            onClickGallery();
        } else {
            String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(per, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE) {
            if(PackageManager.PERMISSION_GRANTED == grantResults[0] && grantResults.length > 0) {
                onClickGallery();
            } else {
                Toast.makeText(this, "Cho phép để chọn được ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickGallery() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
        resultLauncher.launch(Intent.createChooser(intent, "Chọn hình ảnh"));
    }

    private void clickButtonUpdate() {
        String username = name.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            return;
        }

        if(uri == null) {
            uri = user.getPhotoUrl();
        }

        UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                .setDisplayName(username).setPhotoUri(uri).build();

        user.updateProfile(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(UpdateProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    NavActivity.username.setText(user.getDisplayName());
                    Glide.with(NavActivity.imageUser.getContext()).load(uri).error(R.drawable.nhu_nhi).into(NavActivity.imageUser);
                }
            }
        });
    }
}

package project.group.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import project.group.todo.Object.Todo;
import project.group.todo.RealtimeDatabase.RealtimeDb;

public class AddActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnAdd;
    EditText edtTitle, edtContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnBack = findViewById(R.id.btn_back);
        btnAdd = findViewById(R.id.btn_add);
        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, NavActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();

                if(title == null || title.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(content == null || content.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
                Todo todo = new Todo(title, content, setDate.format(new Date()));
                RealtimeDb.getInstance().addTodoNew(todo);

                Toast.makeText(AddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

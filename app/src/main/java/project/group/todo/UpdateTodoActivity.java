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

import project.group.todo.Object.Todo;
import project.group.todo.RealtimeDatabase.RealtimeDb;

public class UpdateTodoActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnAdd;
    EditText edtTitle, edtContent;

    private Todo todo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnBack = findViewById(R.id.btn_back);
        btnAdd = findViewById(R.id.btn_add);
        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);

        todo = (Todo) getIntent().getSerializableExtra("data");
        edtTitle.setText(todo.getTitle());
        edtContent.setText(todo.getContent());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateTodoActivity.this, NavActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();

                if(title.isEmpty() || title == null) {
                    Toast.makeText(UpdateTodoActivity.this, "Bạn chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(content.isEmpty() || content == null) {
                    Toast.makeText(UpdateTodoActivity.this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
                    return;
                }

                todo.setTitle(title);
                todo.setContent(content);
                RealtimeDb.getInstance().updateTodo(todo.getId(), todo);
                Toast.makeText(UpdateTodoActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}

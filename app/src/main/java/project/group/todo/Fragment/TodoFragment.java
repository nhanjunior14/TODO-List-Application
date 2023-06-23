package project.group.todo.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import project.group.todo.AddActivity;
import project.group.todo.Interface.OnClickOption;
import project.group.todo.Object.Todo;
import project.group.todo.Object.TodoAdaptor;
import project.group.todo.R;
import project.group.todo.RealtimeDatabase.RealtimeDb;
import project.group.todo.UpdateTodoActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoFragment extends Fragment implements OnClickOption {
    View view;
    List<Todo> todo;
    RecyclerView recycler;
    FloatingActionButton addNode;

    public static TodoAdaptor todoAdaptor;
    public static ImageView iconNode;
    public static TextView tvContent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Todo.
     */
    // TODO: Rename and change types and number of parameters
    public static TodoFragment newInstance(String param1, String param2) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_fragment_todo, container, false);

        //Init
        iconNode = view.findViewById(R.id.imgv_iconNode);
        tvContent = view.findViewById(R.id.tv_content);
        recycler = view.findViewById(R.id.view_node);
        addNode = view.findViewById(R.id.btn_addnote);

        todo = RealtimeDb.getInstance().getAll();
        todoAdaptor = new TodoAdaptor(todo, this);

        if(todoAdaptor.getItemCount() != 0) {
            iconNode.setVisibility(View.INVISIBLE);
            tvContent.setVisibility(View.INVISIBLE);
        } else {
            iconNode.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
        }

        recycler.setAdapter(todoAdaptor);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);

        //Listener
        addNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        todoAdaptor.updateContent(RealtimeDb.getInstance().getAll());

        if(todoAdaptor.getItemCount() != 0) {
            iconNode.setVisibility(View.INVISIBLE);
            tvContent.setVisibility(View.INVISIBLE);
        } else {
            iconNode.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickDelete(int id) {
        new AlertDialog.Builder(getContext()).setTitle("Xác nhận")
                .setMessage("Bạn chắc chắn xoá nội dung này?")
                .setIcon(R.drawable.ic_baseline_warning_amber)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RealtimeDb.getInstance().deleteTodo(id);

                        todoAdaptor.updateContent(RealtimeDb.getInstance().getAll());
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    @Override
    public void onClickUpdate(Todo todo) {
        Intent intent = new Intent(getContext(), UpdateTodoActivity.class);

        intent.putExtra("data", todo);
        startActivity(intent);
    }

}
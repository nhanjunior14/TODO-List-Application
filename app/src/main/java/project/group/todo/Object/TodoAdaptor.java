package project.group.todo.Object;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.group.todo.Interface.OnClickOption;
import project.group.todo.R;

public class TodoAdaptor extends RecyclerView.Adapter<TodoAdaptor.ViewHolder> implements Filterable {
    private List<Todo> todo;
    private List<Todo> todoOldList;
    private OnClickOption itf;

    public TodoAdaptor(List<Todo> todo) {
        this.todo = todo;
        this.todoOldList = todo;
    }

    public TodoAdaptor(List<Todo> todo, OnClickOption itf) {
        this.todo = todo;
        this.todoOldList = todo;
        this.itf = itf;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        ImageView imgIconDelete;
        private Todo todo;

        public ViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tv_title);
            tvDate = view.findViewById(R.id.tv_date);
            imgIconDelete = view.findViewById(R.id.imgv_iconDelete);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itf.onClickUpdate(todo);
                }
            });

            imgIconDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itf.onClickDelete(todo.getId());
                }
            });
        }

        private void bindViewHolder(Todo todo) {
            this.todo = todo;

            tvTitle.setText(todo.getTitle());
            tvDate.setText(todo.getDate());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todolayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindViewHolder(todo.get(position));
    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateContent(List<Todo> newTodo) {
        todo.clear();
        todo.addAll(newTodo);

        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();

                if(search.isEmpty()) {
                    todo = todoOldList;
                } else {
                    List<Todo> listTodo = new ArrayList<>();

                    for(Todo todo : todoOldList) {
                        if(todo.getTitle().toLowerCase().contains(search.toLowerCase())) {
                            listTodo.add(todo);
                        }
                    }
                    todo = listTodo;
                }

                FilterResults results = new FilterResults();

                results.values = todo;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                todo = (List<Todo>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }
}

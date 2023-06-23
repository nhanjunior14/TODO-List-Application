package project.group.todo.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import project.group.todo.ChangePasswordActivity;
import project.group.todo.LoginActivity;
import project.group.todo.R;
import project.group.todo.UpdateProfileActivity;

public class Account extends Fragment {

    public Account() {}

    public static Account newInstance() {
        Account fragment = new Account();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_account, container, false);

        LinearLayout updateProfile = view.findViewById(R.id.btn_updateProfile);
        LinearLayout changePassWord = view.findViewById(R.id.btn_ChangePassWord);
        LinearLayout btnLogout = view.findViewById(R.id.btn_Logout);


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdateProfileActivity.class));
            }
        });

        changePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder logout = new AlertDialog.Builder(getContext());

                logout.setTitle("Bạn muốn đăng xuất?");
                logout.setCancelable(true);

                logout.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                });

                logout.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                logout.show();
            }
        });

        return view;
    }
}

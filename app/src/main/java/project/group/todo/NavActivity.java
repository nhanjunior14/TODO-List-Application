package project.group.todo;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import project.group.todo.Fragment.TodoFragment;

public class NavActivity extends AppCompatActivity {
    private DrawerLayout mainlayout;
    private Toolbar toolbar;
    private NavigationView navView;

    public static TextView username, email;
    public static ImageView imageUser;
    public static SearchView search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navView = findViewById(R.id.navigationView);

        mainlayout = findViewById(R.id.main_layout);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        initNavigationView();

        ActionBarDrawerToggle action = new ActionBarDrawerToggle(this,
                mainlayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mainlayout.addDrawerListener(action);
        action.syncState();

        NavController controller = Navigation.findNavController(this, R.id.navHost);
        NavigationUI.setupWithNavController(navView, controller);

        controller.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                toolbar.setTitle(destination.getLabel());
            }
        });

        showInformationUser();
    }

    private void initNavigationView() {
        username = navView.getHeaderView(0).findViewById(R.id.tv_username);
        email = navView.getHeaderView(0).findViewById(R.id.tv_email);
        imageUser = navView.getHeaderView(0).findViewById(R.id.image_user);
    }

    @SuppressLint("SetTextI18n")
    public void showInformationUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            return;
        }

        String name = user.getDisplayName();
        String emailUser = user.getEmail();
        Uri url = user.getPhotoUrl();

        if (name == null) {
            username.setText("Tên người dùng");
        } else {
            username.setText(name);
        }

        email.setText(emailUser);
        Glide.with(NavActivity.this).load(url).error(R.drawable.nhu_nhi).into(imageUser);
    }

    @Override
    public void onBackPressed() {
        if(mainlayout.isDrawerOpen(GravityCompat.START)) {
            mainlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) menu.findItem(R.id.iconSearch).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setMaxWidth(Integer.MAX_VALUE);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TodoFragment.todoAdaptor.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TodoFragment.todoAdaptor.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
package project.group.todo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration
                .Builder().name("project.group.todo")
                .schemaVersion(0).build();

        Realm.setDefaultConfiguration(config);
    }
}
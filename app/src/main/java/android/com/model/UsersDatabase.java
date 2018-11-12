package android.com.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.com.remote.APIClient;
import android.com.remote.APIInterface;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by guendouz on 15/02/2018.
 */

@Database(entities = {UserData.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {

    private static UsersDatabase INSTANCE;

    public abstract UserDao userDao();

    private static final Object sLock = new Object();

    static  APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public static UsersDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UsersDatabase.class, "Users.db")
                        .allowMainThreadQueries()
                        .addCallback(roomCallback)
                        .build();
            }
            return INSTANCE;
        }
    }

    private static UsersDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}

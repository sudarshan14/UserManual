package android.com.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by guendouz on 15/02/2018.
 */

@Dao
public interface UserDao {

abstract
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserData userData);

    @Update
    void update(UserData userData);

    @Delete
    void delete(UserData userData);

    @Query("Select * from users")
    LiveData<List<UserData>> findAll();

}

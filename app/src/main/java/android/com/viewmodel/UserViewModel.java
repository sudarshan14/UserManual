package android.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.com.model.UserData;
import android.com.remote.UserRepository;
import android.support.annotation.NonNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel
{
    UserRepository objRepository ;
    LiveData<List<UserData>> objUserArrayList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        objRepository = new UserRepository(application);
        objUserArrayList = objRepository.getObjArrayListData();
    }

    public void insertUser(UserData objUserData){
        objRepository.insertUser(objUserData);
    }

    public void UpdateUser(UserData objUserData){
        objRepository.updateUser(objUserData);
    }

    public void DeleteUser(UserData objUserData){
        objRepository.deleteUser(objUserData);
    }

    public LiveData<List<UserData>> getAllUsers() {
        return objUserArrayList;
    }



}

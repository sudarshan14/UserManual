package android.com.remote;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.com.model.UserDao;
import android.com.model.UserData;
import android.com.model.UsersDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class UserRepository {


    public LiveData<List<UserData>> objArrayListData;
    public  ArrayList<UserData> objUsers;
    private UserDao objUserDao;

    public UserRepository(Application application)
    {
        getUSerListFromRetro();

        UsersDatabase objDatabase = UsersDatabase.getInstance(application);
        objUserDao = objDatabase.userDao();
        objArrayListData = objUserDao.findAll();
    }

    public void getUSerListFromRetro()
    {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Users> call2 = apiInterface.doGetUserList("2");
        call2.enqueue(new retrofit2.Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users userList = response.body();
                Integer text = userList.m_iPage;
                Integer total = userList.m_iPage;
                Integer totalPages = userList.m_iTotalPages;
                List<Users.Datum> datumList = userList.m_objLData;

                List<UserData> objUserData;
                for (Users.Datum datum : datumList) {

                    insertUser(new UserData(datum.m_iID,datum.m_strFirstName,datum.m_strLastName,datum.m_strAvatar));
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void insertUser(final UserData objUserData)
    {
        new InsertAsyncTask(objUserDao).execute(objUserData);
    }

    public void updateUser(UserData objUserData)
    {
        new UpdateAsyncTask(objUserDao).execute(objUserData);
    }

    public void deleteUser(UserData objUserData)
    {
        new DeleteAsyncTask(objUserDao).execute(objUserData);
    }

    public UserRepository()
    {
        //objDatabaseHelper =  new DatabaseHelper;
    }

    public LiveData<List<UserData>> getObjArrayListData() {
        return objArrayListData;
    }


    public static class InsertAsyncTask extends AsyncTask<UserData,Void,Void>
    {
        UserDao userDao;

        InsertAsyncTask(UserDao objUserDao)
        {
            this.userDao = objUserDao;
        }

        @Override
        protected Void doInBackground(UserData... userData) {
            userDao.insert(userData[0]);
            return null;
        }
    }


    public static class UpdateAsyncTask extends AsyncTask<UserData,Void,Void>
    {
        UserDao userDao;

        UpdateAsyncTask(UserDao objUserDao)
        {
            this.userDao = objUserDao;
        }

        @Override
        protected Void doInBackground(UserData... userData) {
            userDao.update(userData[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<UserData,Void,Void>
    {
        UserDao userDao;

        DeleteAsyncTask(UserDao objUserDao)
        {
            this.userDao = objUserDao;
        }

        @Override
        protected Void doInBackground(UserData... userData) {
            userDao.delete(userData[0]);
            return null;
        }
    }


}

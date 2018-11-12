package android.com.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIInterface
{

    @GET("/api/users?")
    Call<Users> doGetUserList(@Query("page") String page);
}

package test.pawoon.com.pawoontest.request;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import test.pawoon.com.pawoontest.model.User;

/**
 * Created by franky on 10/10/16.
 */

public interface RequestAPI {

    @GET("posts")
    Call<ArrayList<User>> groupUser();

}

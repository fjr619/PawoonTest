package test.pawoon.com.pawoontest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.pawoon.com.pawoontest.database.SqlHelper;
import test.pawoon.com.pawoontest.model.User;
import test.pawoon.com.pawoontest.request.RequestAPI;

public class MainActivity extends AppCompatActivity implements Callback<ArrayList<User>> {

    private Button viewButtonTitleList;
    private SqlHelper mSqlHelper;
    private SQLiteDatabase database;
    private ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSqlHelper = new SqlHelper(this);

        setUpRequest();

        viewButtonTitleList = (Button) findViewById(R.id.btn_list);
        viewButtonTitleList.setVisibility(View.GONE);
        viewButtonTitleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, ListTitleActivity.class);
                intent.putStringArrayListExtra("titleList", getAllTitleFromDB());
                startActivity(intent);
            }
        });
    }

    private void setUpRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())

        .build();

        RequestAPI  requestAPI = retrofit.create(RequestAPI.class);

        Call<ArrayList<User>> call = requestAPI.groupUser();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

        if (!response.body().isEmpty()) {
            ArrayList<User> arrayList = response.body();
            Log.i("tag"," total ="+arrayList.size());

            open(); //open db;


            for (int i=0 ; i<arrayList.size(); i++){
                User user = arrayList.get(i);

                ContentValues values = new ContentValues();
                values.put(SqlHelper.USER_ID, user.getUserId());
                values.put(SqlHelper.ID, user.getId());
                values.put(SqlHelper.TITLE, user.getTitle());
                values.put(SqlHelper.BODY, user.getBody());
                long insertId = database.insert(SqlHelper.TABLET_USER, null,
                        values);
            }

            close();

            //show button
            viewButtonTitleList.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
    }

    public void open() throws SQLException {
        database = mSqlHelper.getWritableDatabase();
    }

    public void close() {
        mSqlHelper.close();
    }

    private void deleteAllData(){
        open();
        database.execSQL("delete from "+ SqlHelper.TABLET_USER);
        close();
    }

    private ArrayList<String> getAllTitleFromDB() {
        String[] columns = { SqlHelper.TITLE};
        ArrayList<String> stringArrayList = new ArrayList<>();

        open();
        Cursor cursor = database.query(SqlHelper.TABLET_USER,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String title = cursor.getString(0);
            stringArrayList.add(title);
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return stringArrayList;
    }

    @Override
    public void onBackPressed() {

        deleteAllData();

        super.onBackPressed();
    }
}

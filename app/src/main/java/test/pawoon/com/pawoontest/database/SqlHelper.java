package test.pawoon.com.pawoontest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by franky on 10/10/16.
 */

public class SqlHelper extends SQLiteOpenHelper {

    public static final String USER_ID = "userID";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String TABLET_USER = "users";
    public static final String DATABASE_NAME = "users.db";

    private static final String DATABASE_CREATE = "create table "
            + TABLET_USER + "( " + USER_ID + ", " + ID + ", "+ TITLE +", "+BODY+");";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLET_USER);
        onCreate(db);
    }
}

package com.example.sikeda.mydbapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // open db
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        //transaction
        try{
            db.beginTransaction();
            db.execSQL("update users " +
                    "set score = score + 10 " +
                    "where name = 'hoge'");
            db.execSQL("update users " +
                    "set score = score - 10 " +
                    "where name = 'piyo'");
            db.setTransactionSuccessful();
        } catch (SQLException e){
            e.printStackTrace();
        }
        // 処理 select, insert, update, delete
        /*
        ContentValues newUser = new ContentValues();
        newUser.put(UserContract.Users.COL_NAME,"takeshi");
        newUser.put(UserContract.Users.COL_SCORE,44);
        long newId = db.insert(
                UserContract.Users.TABLE_NAME,
                null,
                newUser
        );
        ContentValues newScore = new ContentValues();
        newScore.put(UserContract.Users.COL_SCORE, 100);
        int updatedCount = db.update(
                UserContract.Users.TABLE_NAME,
                newScore,
                UserContract.Users.COL_NAME + " = ?",
                new String[] { "sikeda1" }
        );
        int deletedCount = db.delete(
                UserContract.Users.TABLE_NAME,
                UserContract.Users.COL_NAME + " = ?",
                new String[] { "dotinstall" }
        );*/
        Cursor c = null;
        c = db.query(
                UserContract.Users.TABLE_NAME,
                null, // fields
                null,//UserContract.Users.COL_SCORE + " > ?", // where
                null,//new String[] { "50" }, // where arg
                null, // groupBy
                null, // having
                null//UserContract.Users.COL_SCORE + " desc",  // order by "1"
        );
        Log.v("DB_TEST", "Count: " + c.getCount());
        while(c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(UserContract.Users._ID));
            String name = c.getString(c.getColumnIndex(UserContract.Users.COL_NAME));
            int score = c.getInt(c.getColumnIndex(UserContract.Users.COL_SCORE));
            Log.v("DB_TEST", "id: " + id + " name: " + name + " score: " + score);
        }
        c.close();

        // close db
        db.close();
    }

}
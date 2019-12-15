package es.upm.vvaquerizo.keyless;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static SQLiteDatabase db = null;

    private static SQLiteDatabase getDataBase(Context context) {
        if (db == null) {
            MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
            db = mySQLiteOpenHelper.getReadableDatabase();
        }
        return db;
    }

    public static List<DoorData> getDoorsList(Context context) {
        List<DoorData> doors = new ArrayList<DoorData>();
        Cursor cursor = getDataBase(context).query("doors", new String[] {"rowid","name","address","price","image"}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            int index = cursor.getColumnIndex("price");
            doors.add(new DoorData(cursor.getInt(cursor.getColumnIndex("rowid"))
                    ,cursor.getString(cursor.getColumnIndex("name"))
                    ,cursor.getString(cursor.getColumnIndex("address"))
                    ,cursor.getInt(cursor.getColumnIndex("price"))
                    ,cursor.getBlob(cursor.getColumnIndex("image"))));
            cursor.moveToNext();
        }

        return doors;
    }

    public static void addDoor(Context context, DoorData doorData) {
        ContentValues cv = new ContentValues();
        cv.put("name", doorData.name);
        cv.put("address", doorData.address);
        cv.put("price", doorData.price);
        cv.put("image", doorData.image);
        db.insert( "doors", null, cv );
    }

    public static boolean existUserPassword(Context context, String username, String password) {
        String md5password = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //md5password = md.digest("password".getBytes("UTF-8")).toString();
            md5password = new BigInteger(1, md.digest(password.getBytes("UTF-8"))).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] userPassword = {username,md5password};
        Cursor cursor = getDataBase(context).query("users",null, "name=? AND password=?", userPassword,null,null,null);
        return (cursor.getCount() >= 1);
    }
}

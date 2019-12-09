package es.upm.vvaquerizo.keyless;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "keyless.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public MySQLiteOpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDoorsTable(db);
        createUsersTable(db);
    }

    private void createDoorsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE doors (" +
                " name TEXT," +
                " address TEXT," +
                " price INT," +
                " image BLOB)");

        ContentValues cv = new ContentValues();
        cv.put("name", "Apartamentos Buenavista");
        cv.put("address", "C/ Buenavista, 21, 1º B");
        cv.put("price", 600);
        cv.put("image", getByteImageFromDrawable(R.drawable.imagen_piso_1));
        db.insert( "doors", null, cv );

        cv = new ContentValues();
        cv.put("name", "Pisos Soleados");
        cv.put("address", "Avenida Esperanza, 154, 4ºC");
        cv.put("price", 500);
        cv.put("image", getByteImageFromDrawable(R.drawable.imagen_piso_2));
        db.insert( "doors", null, cv);
    }

    private void createUsersTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                " name TEXT," +
                " password TEXT)");

        ContentValues cv = new ContentValues();
        cv.put("name","vvaquerizo");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            cv.put("password",md.digest("password".getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.insert("users", null, cv);

    }

    private byte[] getByteImageFromDrawable(int id) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

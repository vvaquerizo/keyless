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
        /*// get image from drawable
        Bitmap image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.imagen_piso_1);
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();*/
        

        db.execSQL("CREATE TABLE doors ( id INT," +
                " name TEXT," +
                " address TEXT," +
                " image BLOB)");

        ContentValues cv = new ContentValues();
        cv.put("id",1);
        cv.put("name", "Apartamentos Buenavista");
        cv.put("address", "C/ Buenavista, 21, 1º B");
        cv.put("image", getByteImageFromDrawable(R.drawable.imagen_piso_1));
        db.insert( "doors", null, cv );

        cv = new ContentValues();
        cv.put("id",2);
        cv.put("name", "Pisos Soleados");
        cv.put("address", "Avenida Esperanza, 154, 4ºC");
        cv.put("image", getByteImageFromDrawable(R.drawable.imagen_piso_2));
        db.insert( "doors", null, cv);

        /*db.execSQL("INSERT INTO doors (name, address) values " +
                "('Apartamentos Buenavista','C/ Buenavista, 21, 1º B')," +
                "('Pisos soleados','Avenida Esperanza, 154, 4ºC');");*/
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

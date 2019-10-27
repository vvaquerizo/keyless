package es.upm.vvaquerizo.keyless;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "keyless.db";
    private static final int DATABASE_VERSION = 1;

    public MySQLiteOpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE doors (" +
                " name TEXT," +
                " address TEXT)");

        db.execSQL("INSERT INTO doors (name, address) values " +
                "('Apartamentos Buenavista','C/ Buenavista, 21, 1º B')," +
                "('Pisos soleados','Avenida Esperanza, 154, 4ºC');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

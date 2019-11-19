package es.upm.vvaquerizo.keyless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class DoorListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_list);


        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("doors", null, null, null, null, null, null);

        List<DoorData> doors = new ArrayList<DoorData>();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            int index = cursor.getColumnIndex("price");
            doors.add(new DoorData(cursor.getString(cursor.getColumnIndex("name"))
                                    ,cursor.getString(cursor.getColumnIndex("address"))
                                    ,cursor.getInt(cursor.getColumnIndex("price"))
                                    ,cursor.getBlob(cursor.getColumnIndex("image"))));
            cursor.moveToNext();
        }



        // set up the RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerAdapter(this, doors);
        recyclerView.setAdapter(adapter);
    }
}

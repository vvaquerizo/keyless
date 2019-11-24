package es.upm.vvaquerizo.keyless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DoorListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerAdapter adapter;
    List<DoorData> doors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_list);

        doors = DataBase.getDoorsList(this);

        setUpRecyclerView();

        //Set listener to FAB
        FloatingActionButton addRoom = findViewById(R.id.add_room);
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoorListActivity.this,NewDoorActivity.class);
                DoorListActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // do what you need to do if your activity resumes
        doors.clear();
        doors.addAll(DataBase.getDoorsList(this));
        adapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        // set up the RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerAdapter(this, doors);
        recyclerView.setAdapter(adapter);
    }
}

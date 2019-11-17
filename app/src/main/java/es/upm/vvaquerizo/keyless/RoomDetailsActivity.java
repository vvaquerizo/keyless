package es.upm.vvaquerizo.keyless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;

public class RoomDetailsActivity extends AppCompatActivity {
    int door_id;
    DoorData doorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        // Get the Intent that started this activity and extract the position
        Intent intent = getIntent();
        Log.d("intent URI", intent.toUri(0));
        door_id = intent.getIntExtra("door_id",0);
        doorData = MyRecyclerAdapter.dataset.get(door_id);

        ImageView doorImage = findViewById(R.id.door_image);
        byte[] outImage = doorData.image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        doorImage.setImageBitmap(theImage);

        TextView doorNameText = findViewById(R.id.door_name_text);
        doorNameText.setText(doorData.name);

        TextView doorAddressText = findViewById(R.id.door_address_text);
        doorAddressText.setText(doorData.address);
    }
}

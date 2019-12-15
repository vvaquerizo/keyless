package es.upm.vvaquerizo.keyless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class NewDoorActivity extends AppCompatActivity {
    ImageView doorImage;
    byte imageByteArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_door);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(R.string.add_door);

        final EditText doorName = findViewById(R.id.add_name_text);
        final EditText doorAddress = findViewById(R.id.add_address_text);
        final EditText doorPrice = findViewById(R.id.add_price_number);
        doorImage = findViewById(R.id.add_door_image);
        Button addDoor = findViewById(R.id.add_button);

        doorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });

        addDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoorData doorData = new DoorData(0, doorName.getText().toString(),doorAddress.getText().toString(),Integer.parseInt(doorPrice.getText().toString()),imageByteArray);
                DataBase.addDoor(NewDoorActivity.this,doorData);

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                InputStream stream = null;
                try {
                    stream = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    doorImage.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream);
                    imageByteArray = byteStream.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
       switch (item.getItemId()) {
           case android.R.id.home:
               finish();
       }
        return true;
    }

}

package es.upm.vvaquerizo.keyless;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<DoorData> dataset;
    private LayoutInflater inflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_door_name;
        public TextView textView_door_address;
        public ImageView door_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_door_name = itemView.findViewById(R.id.door_name_text);
            textView_door_address = itemView.findViewById(R.id.door_address_text);
            door_image = itemView.findViewById(R.id.door_image);
        }
    }

    public MyRecyclerAdapter(Context context, List<DoorData> dataset) {
        this.dataset = dataset;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.door_card_layout, parent, false);
        return new MyRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView_door_name.setText(dataset.get(position).name);
        holder.textView_door_address.setText(dataset.get(position).address);

        //convert byte to bitmap take from contact class
        byte[] outImage=dataset.get(position).image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.door_image.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}

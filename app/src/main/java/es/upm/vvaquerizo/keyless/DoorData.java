package es.upm.vvaquerizo.keyless;

import android.graphics.Bitmap;

public class DoorData {
    public int id;
    public String name;
    public String address;
    public int price;
    public byte[] image;

    public DoorData(int id, String name, String address, int price, byte[] image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.image = image;
    }
}

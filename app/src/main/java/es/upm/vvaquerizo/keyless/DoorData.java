package es.upm.vvaquerizo.keyless;

import android.graphics.Bitmap;

public class DoorData {
    public String name;
    public String address;
    public int price;
    public byte[] image;

    public DoorData(String name, String address, int price, byte[] image) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.image = image;
    }
}

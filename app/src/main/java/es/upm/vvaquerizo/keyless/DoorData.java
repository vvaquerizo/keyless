package es.upm.vvaquerizo.keyless;

import android.graphics.Bitmap;

public class DoorData {
    public String name;
    public String address;
    public byte[] image;

    public DoorData(String name, String address, byte[] image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }
}

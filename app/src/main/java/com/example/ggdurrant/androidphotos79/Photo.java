package com.example.ggdurrant.androidphotos79;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

/**
 * Photo has image and filename information and write/read object methods
 * @author George Durrant
 * @author Omar Morsy
 */
public class Photo implements Serializable{
    private static final long serialversionUID = 1L;
    transient Bitmap image;
    private Map<String, ArrayList<String>> tagsHashTable = new HashMap<>();
    String photoName = "";

    public String getPhotoName(){
        return photoName;
    }

    public void setPhotoName(String s){
        this.photoName = s;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap b){
        this.image = b;
    }

    public String[] getTagsAsString() {
        String[] tags = new String[tagsHashTable.size()];
        tags = (String[]) tagsHashTable.values().toArray();
        return tags;
    }

    public String[][] getTagsWithPairs() {
        int count = 0;
        ArrayList<String> person = tagsHashTable.get("person");
        ArrayList<String> location = tagsHashTable.get("location");
        return null;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int i;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while((i = ois.read()) != -1){
            baos.write(i);
        }
        byte b[] = baos.toByteArray();
        image = BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        oos.defaultWriteObject();
        if(image!=null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, baos);
            byte b[] = baos.toByteArray();
            oos.write(b, 0, b.length);
        }
    }

}

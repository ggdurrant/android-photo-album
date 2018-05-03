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
    private ArrayList<String> personTags = new ArrayList<String>();
    private ArrayList<String> locationTags = new ArrayList<String>();
    String photoName = "";

    /**
     * returns photo filename
     * @return
     */
    public String getPhotoName(){
        return photoName;
    }

    /**
     * sets photo filename to s
     * @param s
     */
    public void setPhotoName(String s){
        this.photoName = s;
    }

    /**
     * returns photo image as Bitmap
     * @return
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * sets photo image
     * @param b
     */
    public void setImage(Bitmap b){
        this.image = b;
    }

    /**
     * returns person tags of the photo as ArrayList
     * @return
     */
    public ArrayList<String> getPersonTags(){
        return personTags;
    }

    /**
     * returns location tags of the photo as ArrayList
     * @return
     */
    public ArrayList<String> getLocationTags() {
        return locationTags;
    }

    /**
     * boolean method to determine if any person tags of photo contains the String s
     * @param s
     * @return
     */
    public boolean isPersonTag(String s){
        for(int i=0; i<personTags.size(); i++){
            if(personTags.get(i).contains(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * boolean method to determine if any location tags of photo contains the String s
     * @param s
     * @return
     */
    public boolean isLocationTag(String s){
        for(int i=0; i<locationTags.size(); i++){
            if(locationTags.get(i).contains(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * adds a person tag to the photo
     * @param s
     */
    public void addPersonTag(String s){
        personTags.add(s);
    }

    /**
     * adds a location tag to the photo
     * @param s
     */
    public void addLocationTag(String s){
        locationTags.add(s);
    }

    /**
     * removes person tag from the photo by index
     * @param i
     */
    public void removePersonT(int i){
        personTags.remove(i);
    }

    /**
     * removes location tag from the photo by index
     * @param i
     */
    public void removeLocationT(int i){
        locationTags.remove(i);
    }

    /**
     * removes person tag from the photo from the tags String
     * @param s
     */
    public void removePersonTag(String s){
        for(int i=0; i<personTags.size(); i++){
            if(personTags.get(i).equals(s)){
                personTags.remove(i);
            }
        }
    }

    /**
     * removes location tag from the photo from the tags String
     * @param s
     */
    public void removeLocationTag(String s) {
        for (int i = 0; i < locationTags.size(); i++) {
            if (locationTags.get(i).equals(s)) {
                locationTags.remove(i);
            }
        }
    }

    /**
     * readObject method
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * writeObject method
     * @param oos
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

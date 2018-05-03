package com.example.ggdurrant.androidphotos79;

import java.io.*;
import java.util.ArrayList;

/**
 * Album class contains name, number of photos, and ArrayList of photos
 * @author George Durrant
 * @author Omar Morsy
 */
public class Album implements Serializable{
    /**
     * for serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * album parameters
     */
    String name;
    ArrayList<Photo> photos = new ArrayList<Photo>();

    /**
     * constructor creates album with name s
     * @param s
     */
    public Album(String s){
        this.name = s;
    }

    /**
     * gets name of album
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * sets name of album
     * @param s
     */
    public void setName(String s){
        this.name = s;
    }

    /**
     * gets ArrayList of photos from album
     * @return
     */
    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    /**
     * sets ArrayList of photos
     * @param p
     */
    public void setPhotos(ArrayList<Photo> p){
        this.photos = p;
    }

    /**
     * adds a Photo to the list
     * @param p
     */
    public void addPhoto(Photo p){
        photos.add(p);
    }

    /**
     * gets number of photos in album
     * @return
     */
    public int getSize(){
        return photos.size();
    }

    /**
     * returns the filename of the photo
     * @return
     */
    @Override
    public String toString(){
        return this.name;
    }
}

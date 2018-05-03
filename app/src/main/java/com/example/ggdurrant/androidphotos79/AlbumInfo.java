package com.example.ggdurrant.androidphotos79;

import java.io.*;
import java.util.ArrayList;
import android.content.Context;

/**
 * AlbumInfo loads and saves the ArrayList of albums for the user
 * @author George Durrant
 * @author Omar Morsy
 */
public class AlbumInfo implements Serializable {

    /**
     * for serialization
     */
    private static final long serialVersionUID = 1L;
    public static final String fileName = "photosFile.ser";

    /**
     * album information
     */
    public ArrayList<Album> albums;
    public static Album result = new Album("results");

    /**
     * loads the user's album information
     * @param c
     * @return
     */
    public static AlbumInfo load(Context c){
        AlbumInfo a = null;
        try {
            FileInputStream fis = c.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            a = (AlbumInfo) ois.readObject();

            if(a.albums==null){
                a.albums = new ArrayList<Album>();
            }
            fis.close();
            ois.close();
        } catch (FileNotFoundException e){ return null;}
        catch (IOException e){ return null;}
        catch (ClassNotFoundException e){ return null;}
        catch (Exception e){ return null;}
        return a;
    }

    /**
     * saves the user's album information upon exit
     * @param c
     */
    public void save(Context c){

        try {
            FileOutputStream fos = c.openFileOutput(fileName,c.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e){ e.printStackTrace();}
        catch (IOException e){ e.printStackTrace();}
    }

    public boolean isAlbum(String s){
        boolean isA = false;
        for(int i=0; i<albums.size(); i++){
            if(albums.get(i).getName().equalsIgnoreCase(s));
            isA = true;
            return isA;
        }
        return isA;
    }
}

package com.example.ggdurrant.androidphotos79;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.DialogInterface;
import android.view.View;
import java.util.ArrayList;
import java.io.File;
import android.app.AlertDialog;
import android.widget.*;
import android.content.Context;


public class AlbumActivity extends AppCompatActivity{

    private GridView grid;
    private Album thisAlbum;
    private MyAdapter adapter;
    Context c = this;
    int i;
    private static final int PICK_PHOTO_REQUEST = 1;


    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albumactivity_main);

        grid = (GridView) findViewById(R.id.grid);

        adapter = new MyAdapter(this, getPhotos());

        grid.setAdapter(adapter);

        int i = getIntent().getIntExtra("index", 0);
        thisAlbum = MainActivity.info.albums.get(i);
        final int albumIndex = i;

        Button add = (Button) findViewById(R.id.addPhotoBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_REQUEST);

                grid.setAdapter(adapter);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if(resultCode == RESULT_OK){
            Uri thisImage = imageReturnedIntent.getData();

            ImageView i = new ImageView(c);

            i.setImageURI(thisImage);

            BitmapDrawable bd = (BitmapDrawable) i.getDrawable();
            Bitmap b = bd.getBitmap();

            Photo newPhoto = new Photo();
            newPhoto.setImage(b);

            File f = new File(thisImage.getPath());
            String path = f.getAbsolutePath();
            String filename = filePath(thisImage);
            newPhoto.setPhotoName(filename);
            thisAlbum.addPhoto(newPhoto);
            MainActivity.info.save(c);
            grid.setAdapter(adapter);

        }
    }


    private ArrayList getPhotos(){
        int i = getIntent().getIntExtra("index", 0);
        return MainActivity.info.albums.get(i).getPhotos();
    }

    private String filePath(Uri u){
        String filename = "";
        String[] column = {MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver cr = getApplicationContext().getContentResolver();
        Cursor c = cr.query(u, column, null, null, null);
        if(c!=null){
            try{
                if(c.moveToFirst()){
                    filename=c.getString(0);
                }
            } catch (Exception e){

            }
        }

        return filename;
    }
}

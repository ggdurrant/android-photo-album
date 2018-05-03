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
import android.text.InputType;
import android.view.View;
import java.util.ArrayList;
import java.io.File;
import android.app.AlertDialog;
import android.widget.*;
import android.content.Context;

/**
 * controls the individual album screen with a grid of photos and their filenames
 * @author George Durrant
 * @author Omar Morsy
 */
public class AlbumActivity extends AppCompatActivity{

    private GridView grid;
    private Album thisAlbum;
    private MyAdapter adapter;
    Context c = this;
    String input;
    int i;
    private static final int PICK_PHOTO_REQUEST = 1;

    /**
     * creates the Album view screen
     * @param savedInstanceState
     */
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albumactivity_main);

        /**
         * sets up the grid view of photos
         */
        grid = (GridView) findViewById(R.id.grid);
        adapter = new MyAdapter(this, getPhotos());
        grid.setAdapter(adapter);

        int i = getIntent().getIntExtra("index", 0);
        thisAlbum = MainActivity.info.albums.get(i);
        final int albumIndex = i;

        grid.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        grid.setSelection(0);

        /**
         * allows for move, delete, or display to be clicked when photo is selected
         */
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Button move = (Button) findViewById(R.id.movePhotoBtn);
            Button delete = (Button) findViewById(R.id.deletePhotoBtn);
            Button display = (Button) findViewById(R.id.displayPhotoBtn);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                view.setSelected(true);

                /**
                 * deletes a photo from the album
                 */
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.info.albums.get(getIntent().getIntExtra("index", 0)).getPhotos().remove(pos);
                        adapter = new MyAdapter(c, getPhotos());
                        grid.setAdapter(adapter);
                        MainActivity.info.save(c);
                    }
                });


                /**
                 * moves a photo from the current album to a specified one
                 */
                move.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder newAlbum = new AlertDialog.Builder(c);
                        newAlbum.setTitle("Album to move to: ");
                        final EditText txt = new EditText(c);
                        txt.setInputType(InputType.TYPE_CLASS_TEXT);
                        newAlbum.setView(txt);
                        newAlbum.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(txt.getText().toString().isEmpty()){
                                    AlertDialog.Builder error = new AlertDialog.Builder(c);
                                    error.setTitle("error: empty");
                                    error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            return;
                                        }
                                    });
                                    error.show();
                                }

//                                else if(MainActivity.info.isAlbum(input)){
//                                    AlertDialog.Builder error = new AlertDialog.Builder(c);
//                                    error.setTitle("error: duplicate");
//                                    error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            return;
//                                        }
//                                    });
//                                    error.show();
//                                }

                                else{
                                    input = txt.getText().toString();

                                    Photo thisPhoto = MainActivity.info.albums.get(getIntent().getIntExtra("index", 0)).getPhotos().get(pos);

                                    if(MainActivity.info.isAlbum(input)) {
                                        for (int i = 0; i < MainActivity.info.albums.size(); i++) {
                                            if (MainActivity.info.albums.get(i).getName().equalsIgnoreCase(input)) {
                                                Toast.makeText(getApplicationContext(), "Moving to album: "+input, Toast.LENGTH_SHORT).show();
                                                MainActivity.info.albums.get(i).addPhoto(thisPhoto);
                                                MainActivity.info.albums.get(getIntent().getIntExtra("index", 0)).getPhotos().remove(pos);
                                                adapter = new MyAdapter(c, getPhotos());
                                                grid.setAdapter(adapter);
                                                MainActivity.info.save(c);
                                                dialog.dismiss();
                                                break;
                                            }
                                        }
                                    }

                                    else {
                                        AlertDialog.Builder error = new AlertDialog.Builder(c);
                                        error.setTitle("error: not an album");
                                        error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                return;
                                            }
                                        });

                                        error.show();
                                    }

                                }
                            }
                        });

                        newAlbum.show();

                    }
                });


                /**
                 * displays a photo in the slideshow screen
                 */
                display.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(AlbumActivity.this, PhotoActivity.class);
                        intent.putExtra("albumIndex", albumIndex);
                        intent.putExtra("photoIndex", pos);
                        startActivity(intent);
                    }
                });

            }
        });

        /**
         * adds a photo chosen from the Android's storage to the album
         */
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

    /**
     * returns ArrayList of photos in the album
     * @return
     */
    private ArrayList getPhotos(){
        int i = getIntent().getIntExtra("index", 0);
        return MainActivity.info.albums.get(i).getPhotos();
    }

    /**
     * returns String of the filepath to a photo
     * @param u
     * @return
     */
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

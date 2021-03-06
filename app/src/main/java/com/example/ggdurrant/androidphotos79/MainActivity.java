package com.example.ggdurrant.androidphotos79;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;

/**
 * home screen with album titles which can be opened, added to, renamed, or deleted
 * implements a search feature
 * @author George Durrant
 * @author Omar Morsy
 */
public class MainActivity extends AppCompatActivity {

    ListView albums;
    ArrayAdapter<Album> adapter;
    public static AlbumInfo info;
    final Context c = this;
    String input;
    public static ArrayList<Photo> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        info = AlbumInfo.load(this);
        if(info==null){
            info = new AlbumInfo();
        }
        if(info.albums==null){
            info.albums = new ArrayList<Album>();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albums = (ListView) findViewById(R.id.albumList);
        albums.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albums.setSelection(0);
        albums.setItemsCanFocus(true);
        adapter = new ArrayAdapter<Album>(c, R.layout.album_main, info.albums);
        albums.setAdapter(adapter);

        Button add = (Button) findViewById(R.id.addBtn);


        final Button search = (Button) findViewById(R.id.searchBtn);

        /**
         * allows for delete, rename, or open of an album once selected
         */
        albums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Button delete = (Button) findViewById(R.id.deleteBtn);
            Button edit = (Button) findViewById(R.id.editBtn);
            Button open = (Button) findViewById(R.id.openBtn);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                view.setSelected(true);
                //albums.setSelected(true);

                /**
                 * opens the current album in the AlbumActivity screen
                 */
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
                        intent.putExtra("index", pos);
                        startActivity(intent);

                    }
                });

                /**
                 * deletes selected album
                 */
                delete.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        info.albums.remove(pos);
                        info.save(c);
                        albums.setAdapter(adapter);
                    }
                });

                /**
                 * renames selected album
                 */
                edit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        AlertDialog.Builder renamer = new AlertDialog.Builder(c);
                        renamer.setTitle("Rename album: ");
                        final EditText txt = new EditText(c);
                        txt.setInputType(InputType.TYPE_CLASS_TEXT);
                        renamer.setView(txt);
                        renamer.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
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

                                else if(info.isAlbum(txt.getText().toString())){
                                    AlertDialog.Builder error = new AlertDialog.Builder(c);
                                    error.setTitle("error: duplicate");
                                    error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            return;
                                        }
                                    });
                                    error.show();
                                }

                                else{
                                    input = txt.getText().toString();
                                    Toast.makeText(getApplicationContext(), "renaming this album: "+input, Toast.LENGTH_SHORT).show();
                                    info.albums.get(pos).setName(input);
                                    info.save(c);
                                    albums.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            }
                        });
                        renamer.show();
                    }

                });
            }
        });

        /**
         * adds an album
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                dialog.setTitle("Enter album name: ");

                final EditText txt = new EditText(c);
                txt.setInputType(InputType.TYPE_CLASS_TEXT);
                dialog.setView(txt);

                dialog.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        input = txt.getText().toString();
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

//                        else if(info.isAlbum(txt.getText().toString())){
//                            Toast.makeText(getApplicationContext(), txt.getText().toString(), Toast.LENGTH_SHORT).show();
//                            AlertDialog.Builder error = new AlertDialog.Builder(c);
//                            error.setTitle("error: duplicate3");
//                            error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                    return;
//                                }
//                            });
//                            error.show();
//                        }

                        else{
                            input = txt.getText().toString();
                            Toast.makeText(getApplicationContext(), "adding album: "+input, Toast.LENGTH_SHORT).show();
                            Album newAlbum = new Album(input);
                            info.albums.add(newAlbum);
                            info.save(c);
                            adapter = new ArrayAdapter<Album>(c, R.layout.album_main, info.albums);
                            albums.setAdapter(adapter);
                            dialog.dismiss();
                        }

                    }
                });


                dialog.show();




            }
        });


        /**
         * searches for photos with tags of person and/or location
         */
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder searcher = new AlertDialog.Builder(c);
                searcher.setTitle("Enter person and/or location: ");

                final EditText person = new EditText(c);
                person.setHint("person");
                final EditText location = new EditText(c);
                location.setHint("location");

                person.setInputType(InputType.TYPE_CLASS_TEXT);
                location.setInputType(InputType.TYPE_CLASS_TEXT);

                LinearLayout lay = new LinearLayout(c);
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(person);
                lay.addView(location);
                searcher.setView(lay);

                searcher.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        input = txt.getText().toString();
                        if(person.getText().toString().isEmpty() && location.getText().toString().isEmpty()){
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

                        else{
                            result = new ArrayList<Photo>();
                            for(int i=0; i<info.albums.size(); i++){
                                ArrayList<Photo> thePhotos = info.albums.get(i).getPhotos();
                                for(int j=0; j<thePhotos.size(); j++){
                                    if(thePhotos.get(j).isLocationTag(location.getText().toString()) || thePhotos.get(j).isPersonTag(person.getText().toString())){
                                        result.add(thePhotos.get(j));
                                    }
                                }
                            }

                            if(result.isEmpty()){
                                AlertDialog.Builder error = new AlertDialog.Builder(c);
                                error.setTitle("error: no matches found");
                                error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        return;
                                    }
                                });
                                error.show();
                            }

                            else{
                                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                });

                 searcher.show();
            }
        });


    }




}

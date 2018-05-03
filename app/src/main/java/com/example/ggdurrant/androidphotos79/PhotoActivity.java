package com.example.ggdurrant.androidphotos79;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import android.app.Dialog;
import android.view.*;
import android.content.Intent;
import android.content.Context;

/**
 * controls the slideshow screen of individual photos with options to add or remove tags, scroll
 * @author George Durrant
 * @author Omar Morsy
 */
public class PhotoActivity extends AppCompatActivity {

    final Context c = this;
    Photo thisPhoto;
    Album thisAlbum;
    ArrayAdapter<String> personAdapter;
    ArrayAdapter<String> locationAdapter;
    ArrayAdapter<String> adapter;
    String input;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoactivity_main);
        final int photoIndex = getIntent().getIntExtra("photoIndex", 0);
        final int albumIndex = getIntent().getIntExtra("albumIndex", 0);

        thisAlbum = MainActivity.info.albums.get(albumIndex);
        thisPhoto = MainActivity.info.albums.get(albumIndex).getPhotos().get(photoIndex);
        ImageView i = (ImageView) findViewById(R.id.slideshowImage);
        i.setImageBitmap(thisPhoto.getImage());

        final ListView personList = (ListView) findViewById(R.id.personTags);
        final ListView locationList = (ListView) findViewById(R.id.locationTags);
        personAdapter = new ArrayAdapter<String>(c, R.layout.tag_main, thisPhoto.getPersonTags());
        personList.setAdapter(personAdapter);
        locationAdapter = new ArrayAdapter<>(c, R.layout.tag_main, thisPhoto.getLocationTags());
        locationList.setAdapter(locationAdapter);



        Button left = (Button) findViewById(R.id.leftBtn);
        Button right = (Button) findViewById(R.id.rightBtn);
        Button home = (Button) findViewById(R.id.homeBtn);
        Button addPerson = (Button) findViewById(R.id.addPersonBtn);
        Button addLocation = (Button) findViewById(R.id.addLocationBtn);
        final Button delete = (Button) findViewById(R.id.deleteTagBtn);

        personList.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        personList.setSelection(0);

        /**
         * selects person tags for removal purposes
         */
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                view.setSelected(true);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thisPhoto.removePersonT(pos);
                        personAdapter = new ArrayAdapter<String>(c, R.layout.tag_main, thisPhoto.getPersonTags());
                        personList.setAdapter(personAdapter);
                        MainActivity.info.save(c);
                    }
                });
            }
        });

        locationList.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        locationList.setSelection(0);

        /**
         * selects location tags for removal purposes
         */
        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                view.setSelected(true);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thisPhoto.removeLocationT(pos);
                        locationAdapter = new ArrayAdapter<String>(c, R.layout.tag_main, thisPhoto.getLocationTags());
                        locationList.setAdapter(locationAdapter);
                        MainActivity.info.save(c);
                    }
                });
            }
        });


        /**
         * brings user back to album screen
         */
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoActivity.this, AlbumActivity.class);
                intent.putExtra("index", albumIndex);
                startActivity(intent);
            }
        });

        /**
         * adds person tag to photo
         */
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adder = new AlertDialog.Builder(c);
                adder.setTitle("Enter person: ");
                final EditText txt = new EditText(c);
                txt.setInputType(InputType.TYPE_CLASS_TEXT);
                adder.setView(txt);
                adder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (txt.getText().toString().isEmpty()) {
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
                        } else if (thisPhoto.isPersonTag(txt.getText().toString())) {
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
                        } else {
                            input = txt.getText().toString();
                            Toast.makeText(getApplicationContext(), "adding person tag: " + input, Toast.LENGTH_SHORT).show();
                            thisPhoto.addPersonTag(input);
                            personList.setAdapter(personAdapter);
                            MainActivity.info.save(c);
                            dialog.dismiss();
                        }
                    }
                });
                adder.show();
            }

        });

        /**
         * adds location tag to photo
         */
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adder = new AlertDialog.Builder(c);
                adder.setTitle("Enter location: ");
                final EditText txt = new EditText(c);
                txt.setInputType(InputType.TYPE_CLASS_TEXT);
                adder.setView(txt);
                adder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (txt.getText().toString().isEmpty()) {
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
                        } else if (thisPhoto.isLocationTag(txt.getText().toString())) {
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
                        } else {
                            input = txt.getText().toString();
                            Toast.makeText(getApplicationContext(), "adding location tag: " + input, Toast.LENGTH_SHORT).show();
                            thisPhoto.addLocationTag(input);
                            locationList.setAdapter(locationAdapter);
                            MainActivity.info.save(c);
                            dialog.dismiss();
                        }
                    }
                });
                adder.show();
            }

        });

        /**
         * scrolls left through album photos
         */
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PhotoActivity.this, PhotoActivity.class);
                intent.putExtra("albumIndex", albumIndex);
                int newPhotoIndex;
                if(photoIndex!=0){
                    newPhotoIndex = photoIndex-1;
                }
                else{
                    newPhotoIndex = thisAlbum.getSize()-1;
                }
                intent.putExtra("photoIndex", newPhotoIndex);
                startActivity(intent);
            }
        });

        /**
         * scrolls right through album photos
         */
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PhotoActivity.this, PhotoActivity.class);
                intent.putExtra("albumIndex", albumIndex);
                int newPhotoIndex;
                if(photoIndex!=thisAlbum.getSize()-1){
                    newPhotoIndex = photoIndex+1;
                }
                else{
                    newPhotoIndex = 0;
                }
                intent.putExtra("photoIndex", newPhotoIndex);
                startActivity(intent);
            }

        });
    }
}

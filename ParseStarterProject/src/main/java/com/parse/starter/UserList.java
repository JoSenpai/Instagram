package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.analytics.internal.zzy.s;

public class UserList extends AppCompatActivity {

    static ArrayList<String> users;
    ListView userList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User Feed");

        users = new ArrayList<>();
        userList = (ListView)findViewById(R.id.userList);
        ArrayList<String> names = new ArrayList<>();
        names.add("hello");
        names.add("ignored");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);

        //gets the list of usernames and puts it into array list
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null)
                {
                    if(objects.size() > 0)
                    {
                        for(ParseUser object: objects)
                        {
                            if(object.getString("username").equals(object.getCurrentUser().getUsername()))
                            {
                                Log.i("CURRENT USER", "FOUND");
                            }else
                            {
                                users.add(object.getString("username"));
                                Log.i("users", object.getString("username"));
                            }

                        }
                        userList.setAdapter(arrayAdapter);
                    }
                }else
                {
                    e.printStackTrace();
                }
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),ViewImages.class);
                intent.putExtra("userName",i);
                startActivity(intent);
            }
        });
    }

    //options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //if share is selected, if no permission ask for permission
        if(item.getItemId() == R.id.share){
            //if version is marshmellow ask for permission, else just get photo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    //ask for permission
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,},1);
                }else
                {
                    getPhoto();
                }
            }else
            {
                getPhoto();
            }
        }else if(item.getItemId() == R.id.logout)
        {
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //when user clicks allow, permission is then granted, thus go to media
        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }

    public void getPhoto()
    {
        //goes to media section of phone
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    //takes the photo selected, saves it into bitmap
    //updates the imageView with what is stored in the bitmap
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //when image is clicked in the media
        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            //similar to url, but link to image
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Log.i("Photo","Received");

                //data is written into a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //compresses the image to PNG format and told to be in byte array
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

                //stores the bitmap into small bytes
                byte[] byteArray = stream.toByteArray();
                System.out.println(byteArray);
                //each file has the bytes of an image
                ParseFile file = new ParseFile("image.png", byteArray);

                //where images are stored
                ParseObject object = new ParseObject("Image");
                object.put("image",file);
                object.put("username", ParseUser.getCurrentUser().getUsername());
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null)
                        {
                            Toast.makeText(UserList.this, "Image shared", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(UserList.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

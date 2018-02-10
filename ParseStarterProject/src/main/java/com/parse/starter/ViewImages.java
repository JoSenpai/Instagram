package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static android.R.attr.data;
import static android.R.id.input;
import static com.google.android.gms.analytics.internal.zzy.i;
import static com.parse.starter.R.id.linearLayout;
import static com.parse.starter.UserList.users;

public class ViewImages extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        String selectedUser = "default";
        intent = getIntent();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.logo));
        linearLayout.addView(imageView);


        for (int i = 0; i < users.size(); i++) {
            if (intent.getIntExtra("userName", 99999) == i) {
                setTitle(users.get(i) + "'s Feed");
                selectedUser = users.get(i);
            }
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("username", selectedUser);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null)
                {
                    Log.i("Images found", "no errors");
                    if(objects.size() > 0)
                    {
                        for(final ParseObject object: objects)
                        {
                            Log.i("Images Found", object.toString());
                            //System.out.println(object.getParseFile("image"));

                            //gets the parse file image data
                            ParseFile file = (ParseFile)object.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e == null && data != null)
                                    {
                                        //creates a bitmap and decodes the data in the file
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        //creates a new image view
                                        ImageView imageView = new ImageView(getApplicationContext());

                                        //gives XML attributes to the new imageView
                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        //gives a default logo for the first picture in the imagesView
                                        imageView.setImageBitmap(bitmap);
                                        //adds the instagram logo
                                        linearLayout.addView(imageView);
                                    }
                                }
                            });

                        }
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"User Feed is Empty",Toast.LENGTH_SHORT).show();
                        Log.i("No images", "User has not uploaded images");
                    }

                }
            }
        });
    }

}



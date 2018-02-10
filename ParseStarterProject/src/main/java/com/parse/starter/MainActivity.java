/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.start;
import static com.google.android.gms.analytics.internal.zzy.w;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText loginID;
    EditText password;
    Button login;
    TextView signUp;
    ImageView wallpaper;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      setTitle("Instagram");

      init();
      //this makes the keyboard dissapear when clicked on the background
      wallpaper.setOnClickListener(this);

      //this will call the login button when the keyboard tick is pressed
      password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
          @Override
          public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
              if(i == EditorInfo.IME_ACTION_DONE)
              {
                  loginPressed(findViewById(R.id.loginView));
              }

              return false;
          }
      });

  }

  public void init()
  {
      loginID = (EditText)findViewById(R.id.loginID);
      password = (EditText)findViewById(R.id.password);
      login = (Button)findViewById(R.id.login);
      signUp = (TextView)findViewById(R.id.signUp);
      wallpaper = (ImageView)findViewById(R.id.wallpaper);
  }

  public void loginPressed(View view)
  {
      //hides the keyboard after pressing button
      //InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      //mgr.hideSoftInputFromWindow(password.getWindowToken(),0);

      String loginIDText = loginID.getText().toString();
      String passwordText = password.getText().toString();

      if(loginIDText.equals(""))
      {
          Toast.makeText(getApplicationContext(),"Login ID is empty",Toast.LENGTH_SHORT).show();
          return;
      }
      if(passwordText.equals(""))
      {
          Toast.makeText(getApplicationContext(),"Password is empty",Toast.LENGTH_SHORT).show();
          return;
      }

      if(login.getTag() == "login" || login.getTag() == null)
      {
          ParseUser.logInInBackground(loginIDText, passwordText, new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                  if(user != null)
                  {
                      Log.i("Login ", "Successful!");
                      Intent i = new Intent();
                      i.setClass(getApplicationContext(),UserList.class);
                      startActivity(i);
                  }else
                  {
                      Log.i("Login ", "Failure" + e.toString());
                  }
              }
          });
      }

      if(login.getTag() == "signUp")
      {
          ParseUser parseUser = new ParseUser();

          parseUser.setUsername(loginID.getText().toString());
          parseUser.setPassword(password.getText().toString());
          parseUser.signUpInBackground(new SignUpCallback() {
              @Override
              public void done(ParseException e) {
                  if(e == null)
                  {
                      Log.i("User Creation","SUCCESS");
                  }else
                  {
                      Log.i("User Creation","FAILURE");
                  }
              }
          });
      }
      ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void loginSignUp(View view)
  {
      Toast.makeText(this, "Changed mode", Toast.LENGTH_SHORT).show();

      if(login.getTag() == null || login.getTag().toString().equals("login"))
      {
          login.setText("Sign Up");
          login.setTag("signUp");
          signUp.setText("Log In");
      }else if(login.getTag().toString().equals("signUp"))
      {
          login.setText("Log In");
          login.setTag("login");
          signUp.setText("Sign Up");

      }
  }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.wallpaper)
        {
            Log.i("WALLPAPER", " CLICKED");
            //hides the keyboard after pressing button
            InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }
}
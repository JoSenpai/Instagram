package com.parse.starter;

import android.util.Log;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

/**
 * Created by jonathanyong on 7/12/17.
 */

public class PraticeCode {

    //creates the table
    /*
    ParseObject score = new ParseObject("Score");
    score.put("username", "jonathan");
    score.put("score",98);

    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {

        if(e == null)
        {
          Log.i("SaveInBackground", "Success");
        }else
        {
          Log.i("SaveInBackground", "ERROR" + e.toString());
        }
      }
    });


    //edits the table
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("2YJrTVM437", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {

        if(e == null && object != null)
        {
          object.put("score",200);
          object.saveInBackground();
            Log.i("ObjectValue", object.getString("username"));
            Log.i("ObjectValueScore", Integer.toString(object.getInt("score")));
        }
      }
    });


    ParseObject tweet = new ParseObject("Tweet");
    tweet.put("username", "joj016");
    tweet.put("tweet", "hey there");

    tweet.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null)
        {
          Log.i("Tweet ", "Success");
        }else
        {
          Log.i("Unsuccessful, error", e.toString());
        }
      }
    });


    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Tweet");
    query2.getInBackground("DpLByYg4Oy", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e == null)
        {
          object.put("tweet", "this will be changed");
          object.saveInBackground();
        }
      }
    });
    */

    /* Limit and edit specific
    ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Score");

    //query3.whereEqualTo("username", "jonathan");
    //query3.setLimit(1);
    query3.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {

        if(e == null)
        {
          Log.i("Find in background", "Retrieved " + objects.size() + " objects");

          if(objects.size() > 0)
          {
            for(ParseObject object: objects)
            {
              Log.i("findInBackgroundResults", Integer.toString(object.getInt("score")));
              if(object.getInt("score") >= 200)
              {
                object.put("score", object.getInt("score")+ 50);
                object.saveInBackground();
                Log.i("Score updated", " added 50 to score over 200");
              }
            }
          }
        }
      }
    });
    */

    /* Sign Up Code
    ParseUser parseUser = new ParseUser();

    parseUser.setUsername("joj016");
    parseUser.setPassword("rasanyonya");

    parseUser.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if(e == null)
            {
                Log.i("Sign Up", "SUCCESSFUL");
            }else
            {
                Log.i("Sign Up", "FAILED");
            }
        }
    });
    */

    /* test login
    ParseUser.logInInBackground("joj016", "rasanyonya", new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null)
                    {
                        Log.i("Login", "Sucessful");
                    }else
                    {
                        Log.i("Login", "fail" + e.toString());
                    }
                }
            });
   */

    /*
      ParseUser.logOut();
    if(ParseUser.getCurrentUser() != null)
    {
        Log.i("Current User", ParseUser.getCurrentUser().getUsername());
    }else
    {
        Log.i("Current User", "User not logged in");
    }





            ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
    */
}

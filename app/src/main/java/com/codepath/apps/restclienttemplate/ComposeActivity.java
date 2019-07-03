package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    public void onTweet(View v) {
        EditText editTextTweet = (EditText) findViewById(R.id.et_simple);
        String strTweet = editTextTweet.getText().toString();

        client = TwitterApp.getRestClient(this);
        client.sendTweet(strTweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet composedTweet = Tweet.fromJSON(response);
                    Intent intent = new Intent();
                    intent.putExtra("tweet", Parcels.wrap(composedTweet));
                    setResult(RESULT_OK, intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }





}

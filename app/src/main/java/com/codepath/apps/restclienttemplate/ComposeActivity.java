package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        EditText editTextTweet = (EditText) findViewById(R.id.et_simple);
        String replyUsername = (String) getIntent().getStringExtra("reply_username");
        if (replyUsername != null) {
            editTextTweet.setText("@" + replyUsername + " ");
            Log.e("test", "Success");
        }
        final TextView tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        tvCharCount.setText("280");
        final TextWatcher txwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                tvCharCount.setText(String.valueOf(280-s.length()));
                if (s.length() > 280) {
                    tvCharCount.setTextColor(Color.RED);
                } else {
                    tvCharCount.setTextColor(Color.GRAY);
                }
            }
        };

        editTextTweet.addTextChangedListener(txwatcher);
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

package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    Tweet tweet;

    ImageView ivProfileImage;
    TextView tvUsername;
    TextView tvHandle;
    TextView tvBody;
    ImageView ivImage;

    private final int REQUEST_CODE = 20;
    TimelineActivity timelineActivity = new TimelineActivity();
    TweetAdapter adapter = new TweetAdapter(timelineActivity.tweets);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        tvBody = (TextView) findViewById(R.id.tvBody);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        // unwrap the tweet passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        tvUsername.setText(tweet.getUser().getName());
        tvHandle.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .into(ivProfileImage);

        if (tweet.imageUrl != null) {
            Glide.with(this)
                    .load(tweet.imageUrl + ":large")
                    .into(ivImage);
        }
    }

    public void onReply(View view) {
        Intent intent = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
        intent.putExtra("reply_username", tweet.user.screenName);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet newTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
        }
    }
}

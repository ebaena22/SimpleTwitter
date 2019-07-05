package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


@Parcel
public class Tweet {

    // list out attributes
    public String body;
    public long uid; // database ID for tweet
    public User user;
    public String createdAt;
    public int retweetCount;
    public int favoriteCount;
    public String imageUrl;

    public Tweet() {

    }

    // deserialize JSON Object
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.favoriteCount = jsonObject.getInt("favorite_count");
        tweet.imageUrl = null;

        if (jsonObject.has("extended_entities")) {
            JSONObject extendedEntities = jsonObject.getJSONObject("extended_entities");
            JSONArray media = extendedEntities.getJSONArray("media");
            if (media.length() != 0) {
                JSONObject firstImage = media.getJSONObject(0);
                String base_url = firstImage.getString("media_url_https");
                tweet.imageUrl = base_url + ":medium";
            }
        }

        return tweet;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

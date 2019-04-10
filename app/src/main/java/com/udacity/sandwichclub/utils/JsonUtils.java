package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    private static final String JSON_DETAILS_KEY_NAME = "name";
    private static final String JSON_DETAILS_KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String JSON_DETAILS_KEY_DESCRIPTION = "description";
    private static final String JSON_DETAILS_KEY_IMAGE = "image";
    private static final String JSON_DETAILS_KEY_INGREDIENTS = "ingredients";

    private static final String JSON_NAME_KEY_MAIN_NAME = "mainName";
    private static final String JSON_NAME_KEY_ALSO_KNOWN_AS = "alsoKnownAs";

    public static Sandwich parseSandwichJson(String json) {
        Log.i(LOG_TAG, "parseSandwichJson Received:\n" + json);
        return null;
    }
}

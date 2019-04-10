package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        try {
            JSONObject jsonDetails = new JSONObject(json);

            String name = jsonDetails.getString(JSON_DETAILS_KEY_NAME);
            JSONObject jsonName = new JSONObject(name);

            String mainName = jsonName.getString(JSON_NAME_KEY_MAIN_NAME);

            JSONArray jsonAlsoKnownAs = jsonName.getJSONArray(JSON_NAME_KEY_ALSO_KNOWN_AS);
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < jsonAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(jsonAlsoKnownAs.getString(i));
            }

            String placeOfOrigin = jsonDetails.getString(JSON_DETAILS_KEY_PLACE_OF_ORIGIN);

            String description = jsonDetails.getString(JSON_DETAILS_KEY_DESCRIPTION);

            String image = jsonDetails.getString(JSON_DETAILS_KEY_IMAGE);

            JSONArray jsonIngredients = jsonDetails.getJSONArray(JSON_DETAILS_KEY_INGREDIENTS);
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < jsonIngredients.length(); i++) {
                ingredients.add(jsonIngredients.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

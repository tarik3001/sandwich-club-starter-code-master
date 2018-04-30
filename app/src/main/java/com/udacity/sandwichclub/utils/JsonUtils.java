package com.udacity.sandwichclub.utils;

import android.content.Context;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String INGREDIENTS = "ingredients";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

    String mainName;

    public static Sandwich parseSandwichJson(String json) {
        JSONObject rootObject;
        String mainName;
        String placeOfOrigin;
        String description;
        String image;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
        try {
            //instantiate JSONObject
            rootObject = new JSONObject(json);

            //Find sandwich name
            JSONObject name = rootObject.getJSONObject(NAME);
            mainName = name.getString(MAIN_NAME);

            //Assign objects which aren't arrays
            placeOfOrigin = rootObject.getString(PLACE_OF_ORIGIN);
            description = rootObject.getString(DESCRIPTION);
            image = rootObject.getString(IMAGE);

            //Create array with alsoKnownAs values
            JSONArray alsoKnownAsArray = name.getJSONArray(ALSO_KNOWN_AS);
            for (int i = 0; i <alsoKnownAsArray.length(); i++){
                if (alsoKnownAsArray != null){
                    String alsoKnownAsString = alsoKnownAsArray.getString(i);
                    alsoKnownAs.add(alsoKnownAsString);
                }
            }

            JSONArray ingredientsArray = rootObject.getJSONArray(INGREDIENTS);
            for (int i = 0; i < ingredientsArray.length(); i ++){
                if (ingredientsArray != null){
                    String ingredientString = ingredientsArray.getString(i);
                    ingredientsList.add(ingredientString);
                }
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

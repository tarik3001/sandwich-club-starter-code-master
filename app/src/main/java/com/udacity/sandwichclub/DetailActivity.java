package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public TextView originTv;
    public TextView alsoKnownAsTv;
    public TextView ingredientsTv;
    public TextView descriptionTv;
    Sandwich sandwich;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // Set description using data from Json, hiding label and TextView if empty
        descriptionTv.setText(sandwich.getDescription());

        originTv.setText(sandwich.getPlaceOfOrigin());

        alsoKnownAsTv.setText(android.text.TextUtils.join(", ", sandwich.getAlsoKnownAs()));

        ingredientsTv.setText(android.text.TextUtils.join(", ", sandwich.getIngredients()));

    }
}

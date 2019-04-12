package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.image_iv) ImageView ingredientsIv;
    @BindView(R.id.also_known_tv) TextView alsoKnownAsTV;
    @BindView(R.id.origin_tv) TextView placeOfOriginTV;
    @BindView(R.id.description_tv) TextView descriptionTV;
    @BindView(R.id.ingredients_tv) TextView ingredientsTV;
    @BindView(R.id.image_error_tv) TextView imageErrorTv;
    @BindView(R.id.image_progressbar) ProgressBar imageProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich.getAlsoKnownAs(), sandwich.getPlaceOfOrigin(),
                sandwich.getDescription(), sandwich.getIngredients());

        imageProgressbar.setVisibility(View.VISIBLE);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new Callback() {
                    /*
                    Add error handling in instances where Picasso
                    is unable to load an image, such as network issues,
                    invalid URL, etc.
                    */
                    @Override
                    public void onSuccess() {
                        //Image successfully loaded, display the imageview and
                        //hide the error message
                        Log.d(LOG_TAG, "Picasso onSuccess()");
                        imageErrorTv.setVisibility(View.INVISIBLE);
                        ingredientsIv.setVisibility(View.VISIBLE);
                        imageProgressbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        //Image failed to load, hide the imageview and display
                        //the error message
                        Log.d(LOG_TAG, "Picasso onError()");
                        imageErrorTv.setVisibility(View.VISIBLE);
                        ingredientsIv.setVisibility(View.INVISIBLE);
                        imageProgressbar.setVisibility(View.INVISIBLE);
                    }
                });

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(@NonNull List<String> alsoKnownAs, @NonNull String placeOfOrigin,
                            @NonNull String description, @NonNull List<String> ingredients) {

        if (alsoKnownAs.size() > 0) {
            for (int i = 0; i < alsoKnownAs.size(); i++) {
                alsoKnownAsTV.append(alsoKnownAs.get(i));
                if (i < alsoKnownAs.size() - 1) {
                    alsoKnownAsTV.append("\n");
                }
            }
        } else {
            alsoKnownAsTV.setText(getString(R.string.detail_also_known_as_default));
        }

        if (!placeOfOrigin.equals("")) {
            placeOfOriginTV.setText(placeOfOrigin);
        } else {
            placeOfOriginTV.setText(getString(R.string.detail_place_of_origin_default));
        }

        if (!description.equals("")) {
            descriptionTV.setText(description);
        } else {
            descriptionTV.setText(getString(R.string.detail_description_default));
        }

        if (ingredients.size() > 0) {
            for (String s : ingredients) {
                ingredientsTV.append(s + "\n");
            }
        } else {
            ingredientsTV.setText(getString(R.string.detail_ingredients_default));
        }
    }
}

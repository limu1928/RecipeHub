package edu.neu.recipehub.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe implements Serializable {
    public String mRecipeName;
    public String mDescription;
    //TODO:: SHOULD THERE BE MAP<Ingredient,String>?
    public Map<String,String> mIngredients;
    public List<String> mInstruction;
    public List<Review> mReviews;

    public Recipe(String mRecipeName, String mDescription, Map<String, String> mIngredients,
                  List<String> mInstruction, List<Review> mReviews) {
        this.mRecipeName = mRecipeName;
        this.mDescription = mDescription;
        this.mIngredients = mIngredients;
        this.mInstruction = mInstruction;
        this.mReviews = mReviews;
    }

    public static Recipe getDummyRecipe(){
        Map<String, String> ingredientsMap = new HashMap<>();
        ingredientsMap.put("food","1 oz");
        ingredientsMap.put("boot","10 spoons");

        List<String> instructions = new ArrayList<>();
        instructions.add("First, add food into the pot");
        instructions.add("Second, add food into the hot tub");
        instructions.add("Third, add food into the fireplace");

        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.getDummyReview());
        reviews.add(Review.getDummyReview());
        reviews.add(Review.getDummyReview());
        return new Recipe("Food", "This is food, you know, THIS IS FOOD!",
                ingredientsMap,instructions,reviews);
    }
}

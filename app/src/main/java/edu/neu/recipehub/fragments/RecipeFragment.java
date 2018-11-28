package edu.neu.recipehub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.neu.recipehub.R;
import edu.neu.recipehub.objects.Recipe;

public class RecipeFragment extends Fragment {


    private static final String RECIPE = "recipe";

    private Recipe mRecipe;

    private OnFragmentInteractionListener mListener;

    private ImageView mRecipePhotoImageView;

    private TextView mRecipeNameTextView;

    private TextView mRecipeDescriptionTextView;

    private RecyclerView mIngredientsRecyclerView;

    private RecyclerView mInstructionsRecyclerView;

    private RecyclerView mReviewsRecyclerView;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance(Recipe recipe) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();

        args.putSerializable(RECIPE,recipe);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        //TODO:: THROW EXCEPTION WHEN GETTING WRONG OBJECT.
        mRecipe = (Recipe)args.getSerializable(RECIPE);
        initializeView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void initializeView(){
        mRecipePhotoImageView = getView().findViewById(R.id.recipePhotoImageView);

        mRecipeNameTextView = getView().findViewById(R.id.recipeNameTextView);

        mRecipeNameTextView.setText(mRecipe.mRecipeName);

        mRecipeDescriptionTextView = getView().findViewById(R.id.recipeDescriptionTextView);

        mRecipeDescriptionTextView.setText(mRecipe.mDescription);

        mIngredientsRecyclerView = getView().findViewById(R.id.ingredientsRecyclerView);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(mRecipe.mIngredients);

        mIngredientsRecyclerView.setAdapter(ingredientsAdapter);

        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mInstructionsRecyclerView = getView().findViewById(R.id.instructionsRecyclerView);

        InstructionsAdapter instructionsAdapter = new InstructionsAdapter(mRecipe.mInstruction);

        mInstructionsRecyclerView.setAdapter(instructionsAdapter);

        mInstructionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mReviewsRecyclerView = getView().findViewById(R.id.reviewsRecyclerView);

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(mRecipe.mReviews);

        mReviewsRecyclerView.setAdapter(reviewsAdapter);

        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

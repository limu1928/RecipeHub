package edu.neu.recipehub.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.recipehub.MainActivity;
import edu.neu.recipehub.R;
import edu.neu.recipehub.fragments.adapters.IngredientsAdapter;
import edu.neu.recipehub.fragments.adapters.InstructionsAdapter;
import edu.neu.recipehub.fragments.adapters.RecipePhotosAdapter;
import edu.neu.recipehub.fragments.adapters.ReviewsAdapter;
import edu.neu.recipehub.objects.Recipe;
import edu.neu.recipehub.objects.Review;
import edu.neu.recipehub.objects.User;

public class RecipeFragment extends Fragment {


    private static final String RECIPE = "recipe";

    private Recipe mRecipe;

    private OnFragmentInteractionListener mListener;

    private ImageView mRecipePhotoImageView;

    private RecyclerView mRecipePhotosRecyclerView;

    private TextView mRecipeNameTextView;

    private TextView mRecipeDescriptionTextView;

    private RecyclerView mIngredientsRecyclerView;

    private RecyclerView mInstructionsRecyclerView;

    private TextView mAddReviewTextView;

    private ReviewsAdapter mReviewsAdapter;

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
//        mRecipePhotoImageView = getView().findViewById(R.id.recipePhotoImageView);

        mRecipePhotosRecyclerView = getView().findViewById(R.id.recipePhotosRecyclerView);

        List<Integer> dummyList = new ArrayList<>();

        dummyList.add(R.drawable.hottest_this_weak);
        dummyList.add(R.drawable.highest_rated);

        RecipePhotosAdapter recipePhotosAdapter = new RecipePhotosAdapter(dummyList);

        mRecipePhotosRecyclerView.setAdapter(recipePhotosAdapter);

        mRecipePhotosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutCompat.HORIZONTAL,false));

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

        mAddReviewTextView = getView().findViewById(R.id.addReviewTextView);

        mAddReviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddReviewDialog();
            }
        });

        mReviewsRecyclerView = getView().findViewById(R.id.reviewsRecyclerView);

        mReviewsAdapter = new ReviewsAdapter(mRecipe.mReviews);

        mReviewsRecyclerView.setAdapter(mReviewsAdapter);

        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showAddReviewDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Input Review");
        alert.setMessage("Enter your review below: ");
        final EditText input = new EditText (getActivity());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String reviewString = input.getText().toString();
                mRecipe.mReviews.add(new Review(((MainActivity)getActivity()).getmCurrentUser(),reviewString));
//                mReviewsAdapter.notifyItemInserted(mRecipe.mReviews.size());
                mReviewsRecyclerView.setAdapter(new ReviewsAdapter(mRecipe.mReviews));

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

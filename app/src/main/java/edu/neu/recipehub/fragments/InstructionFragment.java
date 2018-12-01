package edu.neu.recipehub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


import edu.neu.recipehub.R;
import edu.neu.recipehub.objects.AlgoliaKey;
import edu.neu.recipehub.objects.Review;
import edu.neu.recipehub.objects.UploadHelper;


public class InstructionFragment extends Fragment {
    public static List<String> INSTRUCTION_LIST = new ArrayList<>();
    public static List<Review> REVIEW_LIST = new ArrayList<>();

    private HomeFragment.OnFragmentInteractionListener mListener;
    private List<Integer> idList;
    private View rootView;
    private LinearLayout parentLayout;
    private Button addBtn;
    private Button removeBtn;
    private Button publishBtn;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private Client client;
    private Index index;


    public InstructionFragment() {
        // Required empty public constructor
    }


    public static InstructionFragment newInstance() {
        InstructionFragment fragment = new InstructionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        INSTRUCTION_LIST.clear();
        REVIEW_LIST.clear();
        idList = new ArrayList<>();
        idList.add(R.id.insBox);
        rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
        parentLayout = rootView.findViewById(R.id.instructionParent);
        addBtn = rootView.findViewById(R.id.insAddBtn);
        removeBtn = rootView.findViewById(R.id.insRemoveBtn);
        publishBtn = rootView.findViewById(R.id.publishBtn);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("RecipeHub").child("recipe");
        mStorageRef = FirebaseStorage.getInstance().getReference("RecipeHub").child("photoUri");
        client = new Client(AlgoliaKey.ALGOLIA_ID, AlgoliaKey.ALGOLIA_ADMIN_KEY);
        index = client.getIndex("RecipeHub");

        setOnTextChangeListener(rootView.findViewById(R.id.instructionUpload0));
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddField();
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });


        publishBtn.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Integer id : idList) {
                    View parent = parentLayout.findViewById(id);
                    EditText insBox = parent.findViewById(R.id.insBox);
                    String ins = insBox.getText().toString();
                    if (!ins.isEmpty()) INSTRUCTION_LIST.add(ins);
                }
               String key = mDatabaseRef.push().getKey();
               UploadHelper uploadHelper = new UploadHelper(getActivity().getContentResolver(),
                       mDatabaseRef, mStorageRef,index, key);
               boolean[] success = {true};
               uploadHelper.upload(success);
               //TODO::add the confirmation page
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnFragmentInteractionListener) {
            mListener = (HomeFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void onAddField() {
        LayoutInflater inflator = (LayoutInflater) getActivity().
                getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        View newUploadWindow = inflator.inflate(R.layout.instruction_upload,null);
        setOnTextChangeListener(newUploadWindow);
        int newId = View.generateViewId();
        newUploadWindow.setId(newId);
        idList.add(newId);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        newUploadWindow.setLayoutParams(layoutParams);
        parentLayout.addView(newUploadWindow, parentLayout.getChildCount()-2);
    }

    private void onDelete() {
        if (idList.size() > 1) {
            Integer id = idList.get(idList.size()-1);
            View v = rootView.findViewById(id);
            parentLayout.removeView(rootView.findViewById(id));
            idList.remove(idList.size()-1);
            checkValid();
        }
    }

    private void setOnTextChangeListenerHelper(View v) {
        final EditText editText = (EditText) v;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkValid();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setOnTextChangeListener(View parent) {
        EditText insBox = parent.findViewById(R.id.insBox);
        setOnTextChangeListenerHelper(insBox);
    }

    private boolean checkValidHelper(Integer id) {
        View parent = parentLayout.findViewById(id);
        EditText insBox = parent.findViewById(R.id.insBox);
        String ins = insBox.getText().toString();
        if (!ins.isEmpty()) return true;
        return false;
    }

    private void checkValid() {
        boolean hasValid = false;
        for(Integer id : idList) {
            if (checkValidHelper(id)) {
                hasValid = true;
                break;
            }
        }
        if (hasValid) publishBtn.setEnabled(true);
        else publishBtn.setEnabled(false);
    }



}

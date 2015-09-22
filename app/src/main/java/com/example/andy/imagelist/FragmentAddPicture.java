package com.example.andy.imagelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;


/**
 * Created by andy on 19.09.15.
 */
public class FragmentAddPicture extends DialogFragment {
    private Button mButtonOk;
    private Button mButtonCancel;
    private ImageView mImage;
    private String mPicturePath;
    private EditText mEditTextTitle;
    private Context context;
    private final String IMAGE_PATH="image_path";
    private final String IMAGE_TITLE="image_title";

    public FragmentAddPicture() {

    }

    public void setPicturePath(String path) {
        mPicturePath = path;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_picture, null);
        getDialog().setTitle("My Dialog Title");
        context=v.getContext();
        mButtonOk=(Button)v.findViewById(R.id.button_ok);
        mEditTextTitle=(EditText)v.findViewById(R.id.editText_picture_title);
        mImage=(ImageView)v.findViewById(R.id.photo_preview);
        mButtonCancel=(Button)v.findViewById(R.id.button_cancel);
        if(savedInstanceState!=null){
            mEditTextTitle.setText(savedInstanceState.getString(IMAGE_TITLE));
            mPicturePath=savedInstanceState.getString(IMAGE_PATH);
        }
        mPicturePath="file://" + mPicturePath;;
        Picasso.with(context).load(mPicturePath).resize(300,300).into(mImage);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(IMAGE_PATH,mPicturePath)
                .putExtra(IMAGE_TITLE, mEditTextTitle.getText().toString());
                startActivity(intent);
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(IMAGE_PATH,mPicturePath);
        outState.putString(IMAGE_TITLE,mEditTextTitle.getText().toString());
        super.onSaveInstanceState(outState);
    }

}

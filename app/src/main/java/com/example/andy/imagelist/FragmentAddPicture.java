package com.example.andy.imagelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
        Picasso.with(context).load("file://" + mPicturePath).resize(300, 300).into(mImage);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedImage savedImage=new SavedImage(mEditTextTitle.getText().toString(),mPicturePath);
                try {
                    HelperFactory.getHelper().getSavedImageDAO().create(savedImage);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                dismiss();
            }
        });
        return v;
    }
}

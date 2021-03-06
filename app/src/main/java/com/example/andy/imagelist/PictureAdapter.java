package com.example.andy.imagelist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 17.09.15.
 */
public class PictureAdapter extends BaseAdapter {
    private ArrayList<PhotoItem> photos;
    private LayoutInflater inflater;


    public PictureAdapter(Context context,  ArrayList objects) {
        photos= objects;
        inflater = LayoutInflater.from(context) ;
    }


    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Planet to display

        PhotoViewHolder photoViewHolder;
        // The child views in each row.
        // Create a new row view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.photo_row, null);
            photoViewHolder = new PhotoViewHolder();
            photoViewHolder.setPhotoImage((ImageView) convertView.findViewById(R.id.photoImageView));
            photoViewHolder.setPhotoTitle((TextView) convertView.findViewById(R.id.photoTitleTextView));
            convertView.setTag(photoViewHolder);
        }
        // Reuse existing row view
        else {
            photoViewHolder = (PhotoViewHolder) convertView.getTag();
        }
        // Display planet data
        PhotoItem photo = (PhotoItem) this.getItem(position);
        photoViewHolder.getPhotoTitle().setText(photo.getTitle());
        Button deleteButton=(Button)convertView.findViewById(R.id.button_delete);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(v.getTag().toString());
                photos.remove(index);
                notifyDataSetChanged();
            }
        });
        DataForPicasso picassoData=new DataForPicasso(parent.getContext(),photo.getUrl(),photoViewHolder.getPhotoImage());
        DataForPicasso[] dataForPicassos={picassoData};
        final Subscription subscription = Observable.from(dataForPicassos)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DataForPicasso>() {
                    @Override
                    public void call(DataForPicasso picassoData) {
                        Picasso.with(picassoData.getContext()).load(picassoData.getPath()).resize(100, 100).into(picassoData.getImageView());
                    }
                });

        return convertView;
    }
}

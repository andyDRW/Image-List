package com.example.andy.imagelist;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andy on 21.09.15.
 */
public class PhotoViewHolder {

    private TextView mPhotoTitle;
    private ImageView mPhotoImage;

    public PhotoViewHolder() {}
    public PhotoViewHolder( TextView photoTitle,ImageView photoImage) {
        mPhotoTitle = photoTitle ;
        mPhotoImage=photoImage;
    }
    public TextView getPhotoTitle(){
        return mPhotoTitle;
    }
    public void setPhotoTitle(TextView photoTitle){
        mPhotoTitle=photoTitle;
    }
    public ImageView getPhotoImage(){
        return mPhotoImage;
    }
    public void setPhotoImage(ImageView photoImage){
        mPhotoImage=photoImage;
    }

}
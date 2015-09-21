package com.example.andy.imagelist;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by andy on 20.09.15.
 */
public class DataForPicasso {
    private Context context;
    private String path;
    private ImageView imageView;

    public DataForPicasso(){

    }

    public DataForPicasso(Context context, String path, ImageView imageView){
        this.context=context;
        this.path=path;
        this.imageView=imageView;
    }

    public Context getContext() {
        return context;
    }

    public String getPath() {
        return path;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}

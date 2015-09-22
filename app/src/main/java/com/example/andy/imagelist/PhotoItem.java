package com.example.andy.imagelist;

import com.googlecode.flickrjandroid.photos.Photo;

/**
 * Created by andy on 18.09.15.
 */
public class PhotoItem {
        private String title;
        private String url;

    public PhotoItem(){

    }
    public PhotoItem(String title, String url){
        this.title=title;
        this.url=url;
    }
    public PhotoItem(Photo photo){
        title=photo.getTitle();
        url="https://farm"+photo.getFarm()+".staticflickr.com/"
                +photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()
                +"_q"+"."+photo.getOriginalFormat();
    }
    public void setTitle(String newTitle){
        title=newTitle;
    }
    public String getTitle(){
        return title;
    }
    public void setUrl(String newUrl){
        url=newUrl;
    }
    public void setUrlByPhoto(Photo photo){
        url="https://farm"+photo.getFarm()+".staticflickr.com/"
                +photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()
                +"_q"+"."+photo.getOriginalFormat();
    }
    public String getUrl(){
        return url;
    }
}

package com.example.andy.imagelist;

import java.util.ArrayList;

/**
 * Created by andy on 22.09.15.
 */
public class SavedPhotosSingleton {
    private static ArrayList<PhotoItem> items;

    private SavedPhotosSingleton(){

    }

    public static boolean addItem(PhotoItem photoItem){
        if(items==null){
            items=new ArrayList<>();
        }
        for (PhotoItem item:items){
            if((photoItem.getTitle().compareTo(item.getTitle())==0)&&(photoItem.getUrl().compareTo(item.getUrl())==0)) {
                return false;
            }
        }
        items.add(photoItem);
        return true;
    }

    public static ArrayList<PhotoItem> getItems() {
        if(items==null){
            items=new ArrayList<>();
        }
        return items;
    }

    public static void setItems(ArrayList<PhotoItem> items) {
        SavedPhotosSingleton.items = items;
    }

    public static int getSize(){
        if(items!=null) {
            return items.size();
        }
        else {
            return 0;
        }
    }
}

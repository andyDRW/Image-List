package com.example.andy.imagelist;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by andy on 20.09.15.
 */
@DatabaseTable(tableName = "saved_image")
public class SavedImage {

    @DatabaseField(canBeNull = false, dataType = DataType.STRING,unique = false)
    private String title;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING,unique = false)
    private String path;
    @DatabaseField(generatedId = true)
    private int Id;

    public SavedImage(){
    }

    public SavedImage(String title, String path){
        this.title=title;
        this.path=path;
    }

    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}

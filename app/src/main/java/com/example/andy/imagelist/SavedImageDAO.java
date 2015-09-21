package com.example.andy.imagelist;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.util.List;

/**
 * Created by andy on 20.09.15.
 */
public class SavedImageDAO extends BaseDaoImpl<SavedImage, Integer> {

    protected SavedImageDAO(ConnectionSource connectionSource,
                          Class<SavedImage> dataClass) throws SQLException, java.sql.SQLException {
        super(connectionSource, dataClass);
    }

    public List<SavedImage> getAllSavedImages() throws SQLException, java.sql.SQLException {
        return this.queryForAll();
    }

}

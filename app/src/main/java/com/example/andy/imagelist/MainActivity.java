package com.example.andy.imagelist;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.PhotosInterface;
import com.googlecode.flickrjandroid.photos.SearchParameters;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends android.support.v7.app.AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private AsyncTask<String, Void, PhotoList>  mFlickrTask;
    private PictureAdapter mPictureAdapter;
    private PhotoList photoList;
    private ListView mPicListView;
    private EditText mSearchEditText;
    private Toolbar mToolbar;
    private  FragmentAddPicture fragmentAddPicture;
    private String addedPicturePath;
    private final String SAVED_SEARCH_STRING="Saved_search";


    @Override
    public void onRefresh() {
    }
    public class FlickrTask extends AsyncTask<String, Void, PhotoList> {
        @Override
        protected PhotoList doInBackground(String[] tags) {
            Flickr flickr = new Flickr(getString(R.string.api_key), getString(R.string.share_secret));
            PhotosInterface photosInterface = flickr.getPhotosInterface();
            SearchParameters searchParameters = new SearchParameters();
            searchParameters.setTags(tags);
            PhotoList photoList = null;
            try {
                photoList = photosInterface.search(searchParameters, 20, 0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FlickrException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(Photo photo :photoList){
                photo.getTitle();
            }
            return photoList;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_SEARCH_STRING,mSearchEditText.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        mPicListView = (ListView) findViewById(R.id.photo_list);
        mSearchEditText = (EditText) findViewById(R.id.search_edit_text);
        mSearchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    search(mSearchEditText.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });
        setSupportActionBar(mToolbar);

        String imagePath=getIntent().getStringExtra("image_path");
        if(imagePath!=null){
            String imageTitle=getIntent().getStringExtra("image_title");
            PhotoItem item=new PhotoItem(imageTitle,imagePath);
            SavedPhotosSingleton.addItem(item);
        }
        if (savedInstanceState != null) {
            String searchTag = savedInstanceState.getString(SAVED_SEARCH_STRING);
            if (searchTag.length() > 0) {
                mSearchEditText.setText(searchTag);
                search(searchTag);
            }
            else {
                setAdapter(SavedPhotosSingleton.getItems());
            }
        }
        else {
            setAdapter( SavedPhotosSingleton.getItems());
        }
    }

    private void setAdapter(ArrayList<PhotoItem> items){
        mPictureAdapter = new PictureAdapter(this, items);
        mPictureAdapter.notifyDataSetChanged();
        mPicListView.setAdapter(mPictureAdapter);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==R.id.search_button) {
            if(mSearchEditText.getText().length()>0) {
                search(mSearchEditText.getText().toString());
            }
            else {
                Toast toast=Toast.makeText(getApplicationContext(), getString(R.string.nothing_to_search), Toast.LENGTH_SHORT);
                toast .setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

        }
        if(id==R.id.add_button){
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 200);
        }


        return super.onOptionsItemSelected(item);
    }

    public void search(String tag){
        final String[] tags = tag
                .split(" ");
        mFlickrTask = new FlickrTask().execute(tags);
        try {
            photoList = mFlickrTask.get(5, TimeUnit.SECONDS);
            ArrayList<PhotoItem> itemsList = new ArrayList<>();
            for (Photo photo : photoList) {
                PhotoItem photoItem = new PhotoItem(photo);
                itemsList.add(photoItem);
            }
            if(SavedPhotosSingleton.getSize()>0){
                itemsList.addAll(SavedPhotosSingleton.getItems());
            }
            setAdapter(itemsList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(addedPicturePath!=null) {
            fragmentAddPicture = new FragmentAddPicture();
            fragmentAddPicture.setPicturePath(addedPicturePath);
            fragmentAddPicture.show(getFragmentManager(), null);
            addedPicturePath = null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            addedPicturePath = cursor.getString(columnIndex);
            cursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

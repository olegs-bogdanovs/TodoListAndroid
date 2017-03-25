package com.example.olegsbogdanovs.todolist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.olegsbogdanovs.todolist.model.Todo;
import com.example.olegsbogdanovs.todolist.model.TodoListDao;

import java.io.File;
import java.lang.ref.WeakReference;


public class PhotoAsyncTask extends AsyncTask<Todo, Void, Void> {
    private final WeakReference<Activity> mContextWeakReference;
    private final WeakReference<ImageView> mImageViewWeakReference;
    private File mPhotoFile;
    private Bitmap mPhotoBitmap;


    public PhotoAsyncTask(ImageView imageView, Activity activity) {
        mImageViewWeakReference = new WeakReference<>(imageView);
        mContextWeakReference = new WeakReference<>(activity);
    }

    @Override
    protected Void doInBackground(Todo... params) {
        mPhotoFile = TodoListDao.get(mContextWeakReference.get())
                .getPhotoFile(params[0]);

        if (mPhotoFile != null && mPhotoFile.exists()) {
            mPhotoBitmap = PhotoUtils.getScaledBitmap(mPhotoFile.getPath(),
                    mContextWeakReference.get());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ImageView imageView = mImageViewWeakReference.get();
        if (imageView != null && mPhotoBitmap != null){
            imageView.setImageBitmap(mPhotoBitmap);
        }
    }
}

package com.androidthumbnail;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = AndroidThumbnailModule.NAME)
public class AndroidThumbnailModule extends NativeAndroidThumbnailSpec {
  public static final String NAME = "AndroidThumbnail";

  public AndroidThumbnailModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  private static final String ERROR_UNABLE_TO_LOAD = "E_UNABLE_TO_LOAD";
  private static final String ERROR_UNABLE_TO_LOAD_PERMISSION = "E_UNABLE_TO_LOAD_PERMISSION";
  @Override
  public void getPhotos(final Promise promise) {

    WritableArray ImageList = new WritableNativeArray();
    Uri collection;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
    } else {
      collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
    String[] projection = new String[]{
      MediaStore.Images.Media._ID,
      MediaStore.Images.Media.DISPLAY_NAME,
      MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
      MediaStore.Images.Media.SIZE,
      MediaStore.Images.Media.DATA
    };

    String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC, " + MediaStore.Images.Media.DATE_MODIFIED + " DESC";

    ContentResolver resolver = getReactApplicationContext().getContentResolver();

    try (Cursor cursor = resolver.query(
      collection,
      projection,
      null,
      null,
      sortOrder
    )) {
      if (cursor == null) {
        promise.reject(ERROR_UNABLE_TO_LOAD, "Could not get media");
      } else {
        // Cache column indices.
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int nameColumn =
          cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

        int dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        while (cursor.moveToNext()) {
          // Get values of columns for a given Image.
          long id = cursor.getLong(idColumn);
          String name = cursor.getString(nameColumn);
          int size = cursor.getInt(sizeColumn);
          String album = cursor.getString(albumColumn);
          Uri contentUri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

          Uri photoUri = Uri.parse("file://" + cursor.getString(dataIndex));

          WritableMap image = new WritableNativeMap();
          image.putString("contentUri", contentUri.toString());
          image.putString("name", name);
          image.putString("album", album);
          image.putInt("size", size);
          image.putString("uri", photoUri.toString());

          // Stores column values and the contentUri in a local object
          // that represents the cursor file.
          ImageList.pushMap(image);
        }
        promise.resolve(ImageList);


      }
    } catch(SecurityException e){
      promise.reject(
        ERROR_UNABLE_TO_LOAD_PERMISSION,
        "Could not get cursor: need READ_EXTERNAL_STORAGE permission",
        e);
    }
  }
}

package com.androidthumbnail;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.Size;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.ThumbnailImageManagerDelegate;
import com.facebook.react.viewmanagers.ThumbnailImageManagerInterface;

@ReactModule(name = ThumbnailImageManager.NAME)
public class ThumbnailImageManager extends SimpleViewManager<ThumbnailImage> implements ThumbnailImageManagerInterface<ThumbnailImage> {

  public static final String NAME = "ThumbnailImage";

  private final ViewManagerDelegate<ThumbnailImage> mDelegate;

  public ThumbnailImageManager() {
    mDelegate = new ThumbnailImageManagerDelegate(this);
  }

  @Nullable
  @Override
  protected ViewManagerDelegate<ThumbnailImage> getDelegate() {
    return mDelegate;
  }

  @Override
  public String getName() {
    return NAME;
  }

//  @Override
//  public TestFabricView createViewInstance(ThemedReactContext context) {
//    return new TestFabricView(context);
//  }
  private ImgStartListener imgStartListener;

  private interface ImgStartListener {
    void setProps(ReadableMap props);
  }

  @Override
  public ThumbnailImage createViewInstance(ThemedReactContext context) {
    final ThumbnailImage thumbnail = new ThumbnailImage(context);
    final Handler handler = new Handler();

    imgStartListener = new ImgStartListener() {
      @Override
      public void setProps(ReadableMap imageProps) {
        String imagePath = imageProps.getString("contentUri");
        int thumbSize = imageProps.getInt("size");
        startDownloading(context, imagePath, thumbSize, handler, thumbnail);
      }

    };
    return thumbnail;
  }
  private void startDownloading(ThemedReactContext context, final String imagePath, final int thumbSize, final Handler handler, final ThumbnailImage thumbnail) {
    new Thread(new Runnable() {
      @RequiresApi(api = Build.VERSION_CODES.Q)
      @Override
      public void run() {
        try {
          Log.d("RTNThumbnailManager", "Hello from RTNThumbnailManager! " + "imagePath: " + imagePath);

          ContentResolver resolver = context.getContentResolver();
          Uri uri = Uri.parse(imagePath);
          int size = thumbSize * 2;

          Log.d("RTNThumbnailManager", "Hello from RTNThumbnailManager! " + "thumb: " + thumbSize);

          Size mSize = new Size(size, size);
          Bitmap bitmapThumbnail = resolver.loadThumbnail(uri, mSize, null);

          setImage(bitmapThumbnail, size, handler, thumbnail);
        } catch (Exception e) {
          Log.e("RTNThumbnailManager", "Error : " + e.getMessage());
        }
      }
    }).start();
  }

  private void setImage(final Bitmap bmp, final int size, Handler handler, final ThumbnailImage thumbnail) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        thumbnail.setImageBitmap(bmp);
        thumbnail.setMaxHeight(size);
        thumbnail.setMaxWidth(size);
      }
    });
  }

  @Override
  @ReactProp(name = "srcAndSize")
  public void setSrcAndSize(ThumbnailImage view, ReadableMap props) {
    imgStartListener.setProps(props);
  }
}

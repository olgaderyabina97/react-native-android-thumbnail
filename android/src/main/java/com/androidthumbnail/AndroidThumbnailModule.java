package com.androidthumbnail;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
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
  @Override
  public double multiply(double a, double b) {
    return a * b;
  }
}

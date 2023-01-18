package com.androidthumbnail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;


public class ThumbnailImage extends ImageView {

  public ThumbnailImage(Context context) {
    super(context);
    this.configureComponent();
  }

  public ThumbnailImage(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.configureComponent();
  }

  public ThumbnailImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.configureComponent();
  }
  private void configureComponent() {
    this.setScaleType(ScaleType.CENTER_CROP);
    this.setCropToPadding(true);
  }
}

const AndroidThumbnail = require('./NativeAndroidThumbnail').default;

const { CustomType } = require('./NativeAndroidThumbnail');

export function getPhotos(): Promise<
  Readonly<Array<Readonly<typeof CustomType>>>
> {
  return AndroidThumbnail.getPhotos();
}

// Fabric Image View
import React from 'react';
import type { ViewProps } from 'react-native/Libraries/Components/View/ViewPropTypes';

const ThumbnailImage = require('./ThumbnailImageNativeComponent').default;
const { CustomPropType } = require('./ThumbnailImageNativeComponent');

export const Thumbnail = (
  { contentUri, size }: Readonly<typeof CustomPropType>,
  style?: ViewProps
) => {
  const intSize = Math.round(size);
  const styles = { height: intSize, width: intSize };
  return (
    <ThumbnailImage
      srcAndSize={{ contentUri, size: intSize }}
      style={[styles, style]}
    />
  );
};

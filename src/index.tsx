const AndroidThumbnail = require('./NativeAndroidThumbnail').default;

export function multiply(a: number, b: number): number {
  return AndroidThumbnail.multiply(a, b);
}

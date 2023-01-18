import type { Int32 } from 'react-native/Libraries/Types/CodegenTypes';
import type { ViewProps } from 'react-native/Libraries/Components/View/ViewPropTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export type CustomPropType = {
  contentUri: string;
  size: Int32;
};

export interface NativeProps extends ViewProps {
  // add other props here
  srcAndSize: Readonly<CustomPropType>;
}

export default codegenNativeComponent<NativeProps>('ThumbnailImage');

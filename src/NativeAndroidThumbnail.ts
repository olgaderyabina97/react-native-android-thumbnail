import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { Int32 } from 'react-native/Libraries/Types/CodegenTypes';

export type CustomType = {
  contentUri: string;
  uri: string;
  size: Int32;
  name: string;
  album: string;
};

export interface Spec extends TurboModule {
  getPhotos(): Promise<Readonly<Array<Readonly<CustomType>>>>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('AndroidThumbnail');

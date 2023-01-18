# react-native-android-thumbnail

A native android component, which loads a visual thumbnail for the given contentUri, useful for showing many images from android devices. The package also provides a module for getting all images info, such as uri, albume name, size, image name and contentURI for loading thumbnail

## Installation

```sh
npm install react-native-android-thumbnail
```


## Updating manifests

Open your project's AndroidManifest.xml and add the following lines inside the <manifest> tag:

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

## Usage

```js
import { getPhotos, Thumbnail } from 'react-native-android-thumbnail';

// ...

const allPhotos = await getPhotos();

// ...

<Thumbnail contentUri={allPhotos[0].contentUri} size={100} />

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)

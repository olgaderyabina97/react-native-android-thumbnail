import * as React from 'react';

import {
  StyleSheet,
  View,
  Button,
  PermissionsAndroid,
  Alert,
  Text,
  Permission,
} from 'react-native';
import { getPhotos, Thumbnail } from 'react-native-android-thumbnail';

export default function App() {
  const [contentUri, setContentUri] = React.useState<string | null>(null);
  const [fetchingStatus, setFetchingStatus] = React.useState<string | null>(
    null
  );
  const permission: Permission = PermissionsAndroid.PERMISSIONS
    .READ_EXTERNAL_STORAGE as 'android.permission.READ_EXTERNAL_STORAGE';
  const noImagesAlert = () =>
    Alert.alert('No images', "You don't have any images yet", [
      {
        text: 'Cancel',
        onPress: () => console.log('Cancel Pressed'),
        style: 'cancel',
      },
      { text: 'OK', onPress: () => console.log('OK Pressed') },
    ]);

  const requestReadExternalStoragePermission = async () => {
    try {
      const granted = await PermissionsAndroid.request(permission, {
        title: 'Read External Storage Permission',
        message:
          'This App needs access to your media ' +
          'so you can take awesome pictures.',
        buttonNeutral: 'Ask Me Later',
        buttonNegative: 'Cancel',
        buttonPositive: 'OK',
      });
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        console.log('GRANTED!');
        setFetchingStatus('Fetching images, might take a few seconds');
        getPhotos().then((r) => {
          console.log(r);
          if (r && r !== undefined && r.length > 0) {
            const firstImage: string = r[0]?.contentUri;
            setContentUri(firstImage);
          } else {
            noImagesAlert();
          }
        });
      } else {
        console.log('External Storage permission denied');
        console.log('result', granted);
      }
    } catch (err) {
      console.warn(err);
    }
  };
  React.useEffect(() => {
    if (contentUri) {
      setFetchingStatus("Here's your thumbnail!");
    }
  }, [contentUri]);
  return (
    <View style={styles.container}>
      <Button
        title="Click me!"
        onPress={requestReadExternalStoragePermission}
      />
      {fetchingStatus && <Text style={styles.text}>{fetchingStatus}</Text>}
      {contentUri && <Thumbnail contentUri={contentUri} size={100} />}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  text: {
    marginVertical: '5%',
  },
});


#ifdef RCT_NEW_ARCH_ENABLED
#import "RNAndroidThumbnailSpec.h"

@interface AndroidThumbnail : NSObject <NativeAndroidThumbnailSpec>
#else
#import <React/RCTBridgeModule.h>

@interface AndroidThumbnail : NSObject <RCTBridgeModule>
#endif

@end

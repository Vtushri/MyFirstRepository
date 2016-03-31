//
//  screenview.m
//  Cream_Stone
//
//  Created by Hariteja P on 24/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "screenview.h"

@implementation screenview
- (CGSize)currentScreenSize {
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    CGSize screenSize = screenBounds.size;
    
    if ( NSFoundationVersionNumber <= NSFoundationVersionNumber_iOS_7_1 ) {
        UIInterfaceOrientation interfaceOrientation = [[UIApplication sharedApplication] statusBarOrientation];
        if ( UIInterfaceOrientationIsLandscape(interfaceOrientation) ) {
            screenSize = CGSizeMake(screenSize.height, screenSize.width);
        }
    }
    
    return screenSize;
}

@end

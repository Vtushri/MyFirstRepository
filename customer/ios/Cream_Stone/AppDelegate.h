//
//  AppDelegate.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import "XMPPStream.h"
#import "XMPPFramework.h"
#import "SlideNavigationContorllerAnimator.h"
#import "XMPPRoom.h"
#import "XMPPUserCoreDataStorageObject.h"
#import "XMPPRosterMemoryStorage.h"
#import "XMPPRosterCoreDataStorage.h"
#import "PayPalMobile.h"
#import "cartView.h"
#import "LocationTable.h"


@interface AppDelegate : UIResponder <UIApplicationDelegate,CLLocationManagerDelegate,XMPPRosterDelegate,XMPPStreamDelegate,XMPPRosterMemoryStorageDelegate,PayPalPaymentDelegate,LocationTableDelegate,UIAlertViewDelegate>
{
    XMPPStream *xmppStream;
    BOOL isauthenticate;
    BOOL isXmppConnected;
    XMPPRosterCoreDataStorage*xmppRosterStorage;
}
@property (strong, nonatomic) UIWindow *window;

@property(nonatomic, strong) CLLocationManager *locationManager;
@property (nonatomic, strong, readonly) XMPPStream *xmppStream;
@property (nonatomic,strong) NSString*password;
@property (nonatomic,strong,readonly) XMPPRoster*xmppRoster;
@property (nonatomic,strong,readonly) XMPPRoom*xmppRoom;
@property (nonatomic,strong,readonly) XMPPUserCoreDataStorageObject*xmppStorage;
@property (nonatomic,strong) NSString*environment;
@property (nonatomic,strong) Order*odr;
@property (nonatomic,strong) NSString*resultText;
@property (nonatomic,strong) NSDictionary*resultDict;
- (void)disconnect;
-(BOOL)connect;
- (void)requestAlwaysAuthorization;
- (NSManagedObjectContext *)managedObjectContext_roster;
@end
//
//  LocationTable.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
@protocol LocationTableDelegate;
#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "Order.h"
#import "User.h"
#import "XMPPRosterMemoryStorage.h"
#import "XMPPUserCoreDataStorageObject.h"
#import "XMPPRosterCoreDataStorage.h"
#import "XMPPStream.h"

@interface LocationTable : UIViewController<UITableViewDataSource,UITableViewDelegate,XMPPRosterMemoryStorageDelegate,XMPPStreamDelegate>
{
    XMPPRosterCoreDataStorage*xmppRosterStorage;
    XMPPStream*xmppStream;
    __unsafe_unretained NSObject  *_messageDelegate;
    NSIndexPath*selectedpath;
    NSNumber*latitu;
    NSNumber*longitu;
    NSIndexPath*indexpath;
}
@property (nonatomic,strong) NSMutableArray *locations;
@property (nonatomic,strong) NSMutableArray *json;
@property (strong, nonatomic) IBOutlet UITableView *mytable;
@property (nonatomic,strong) NSString*classstrng;
@property (nonatomic,strong) NSDictionary*locadic;
@property(nonatomic, strong) CLLocationManager *locationManager;
@property (nonatomic, assign) id  _messageDelegate;
@property (nonatomic,unsafe_unretained) id<LocationTableDelegate>delegate;
@property (nonatomic,strong) Order*order;
@property (nonatomic,strong) User*user;
@property (nonatomic,strong) NSString*dist;
@property (nonatomic,strong) NSString*duration;
-(void)retrieveData;
//- (NSManagedObjectContext *)managedObjectContext_roster;
@end
@protocol LocationTableDelegate

-(void)order:(Order *)ordr;

@end


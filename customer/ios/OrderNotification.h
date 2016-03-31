//
//  OrderNotification.h
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//


typedef enum {
    LOCATION,NOTIFY
}MessageType;

typedef enum {
    DRIVETHRU,PICKUP
}OrderType;

#import <Foundation/Foundation.h>
#import "Order.h"
#import "Location.h"
#import "User.h"
@interface OrderNotification : NSObject
@property (nonatomic,retain) Order*order;
@property (nonatomic,strong) User*from;
@property (nonatomic,strong) Location*location;
@property (nonatomic,strong) NSString*messageType;
@property (nonatomic,strong) NSString*orderType;
@property (nonatomic,assign) long arrivalTime;
-(id)initWithorder:(Order *)Order andlocation:(Location *) Location  andfrom:(User *) From andmessageType:(NSString *)msgtype andorderType:(NSString *)ordtype andarrivalTime:(long) ArrivalTime;


-(NSString *) converttoString:(MessageType)messageType;
-(NSString*) convertToString:(OrderType)orderType;
@end



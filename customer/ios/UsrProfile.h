//
//  UsrProfile.h
//  Cream_Stone
//
//  Created by Hariteja P on 13/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
@class User;
@class AddressLog;
#import <Foundation/Foundation.h>
#import "Order.h"
#import "AddressLog.h"
#import "User.h"
#import "Card.h"
@interface UsrProfile : NSObject
@property (nonatomic,strong) NSString*userAccountId;
@property (nonatomic,strong) AddressLog*addresslog;
@property (nonatomic,strong) NSMutableArray*adrresses;
@property (nonatomic,strong) NSString*alternateEmailId;
@property (nonatomic,strong) Card*card;
@property (nonatomic,strong) NSDate*timeStamp;
@property (nonatomic,strong) NSString*phoneNum1;
@property (nonatomic,strong) NSString*phoneNum2;
@property (nonatomic,strong) User*user;
@property (nonatomic,strong) Order*order;

-(id)initWithuserAccountId:(NSString *) UserAccountId andaddresslog:(AddressLog *) AddressLog andadrresses:(NSString *)Adrresses andalternateEmailId:(NSString *) AlternateEmailId andcard:(Card *) Card andtimeStamp:(NSDate *) TimeStamp andphoneNum1:(NSString *) PhoneNum1 andphoneNum2:(NSString *) PhoneNum2 anduser:(User *) User andorder:(Order *) Order;

-(void)initWithDictionary:(NSDictionary *)dictionary;
//- (NSDictionary *) dictionaryWithPropertiesOfObject;
-(NSMutableDictionary *) dictionaryWithPropertiesOfObject:(id)obj;
@end

//
//  AddressLog.h
//  Cream_Stone
//
//  Created by Hariteja P on 13/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
@class UsrProfile;
#import <Foundation/Foundation.h>
#import "UsrProfile.h"

@interface AddressLog : NSObject
@property (nonatomic,strong) NSString *addressId;
@property (nonatomic,strong) NSString *customerName;
@property (nonatomic,strong) NSString *address;
@property (nonatomic,strong) NSString *city;
@property (nonatomic,strong) NSString *zipcode;
@property (nonatomic,strong) NSString *state;
@property (nonatomic,strong) NSString *country;
@property (nonatomic,strong) NSString *landmark;
@property (nonatomic,strong) NSString *phoneNum1;
@property (nonatomic,strong) NSString *phoneNum2;
@property (nonatomic,strong) NSString *emailId;
@property (nonatomic,strong) UsrProfile *userprofile;

-(id)initWithaddressId:(NSString *)AddressId andcustomerName:(NSString *) CustomerName andaddress:(NSString *) Address andcity:(NSString *) City andzipcode:(NSString *) Zipcode andstate:(NSString *) State andcountry:(NSString *) Country andlandmark:(NSString *) Landmark andphoneNum1:(NSString *) Phonenum1 andphoneNum2:(NSString *) PhoneNum2 andemailId:(NSString *) EmailId anduserprofile:(UsrProfile *) Userprofile;
@end

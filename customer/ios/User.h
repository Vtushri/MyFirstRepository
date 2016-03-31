//
//  User.h
//  Cream_Stone
//
//  Created by Hariteja P on 12/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
@class Cart;
@class UsrProfile;

#import <Foundation/Foundation.h>
#import "UsrProfile.h"
#import "Cart.h"

@interface User : NSObject

@property (nonatomic,strong) NSString*userAccountId;
@property (nonatomic,strong) NSString*userRole;
@property (nonatomic,strong) NSString*userName;
@property (nonatomic,strong) NSString*password;
@property (nonatomic,strong) NSString*fullName;
@property (nonatomic,strong) NSString*firstName;
@property (nonatomic,strong) NSString*middleName;
@property (nonatomic,strong) NSString*lastName;
@property (nonatomic,strong) NSString*mobileNo;
@property (nonatomic,strong) NSString*emailId;
@property (nonatomic,assign) int token;
@property (nonatomic,assign) BOOL guest;
@property (nonatomic,strong) Cart*cart;
@property (nonatomic,strong) UsrProfile*userprofile;

-(id)initWithuserAccountId:(NSString *) UseraccountId anduserRole:(NSString *) UserRole anduserName:(NSString *) UserName andpassword:(NSString *)Pasword andfullName:(NSString *) Fullname andfirstName:(NSString *) FirstName andmiddleName:(NSString *) Middlename andlastName:(NSString *) Lastname andmobileNo:(NSString *) Mobileno andemailId:(NSString *) Emailid andtoken:(int) Token andguest:(BOOL) Guest andcart:(Cart *) kart anduserprofile:(UsrProfile *)Userprofile;

//-(void)encodeWithCoder:(NSCoder *)aCoder;
//-(id)initWithCoder:(NSCoder *)aDecoder;
-(NSObject *)initWithDictionary:(NSDictionary *)dictionary;
//- (NSDictionary *) dictionaryWithPropertiesOfObject:(id)obj;
-(NSMutableDictionary *) dictionaryWithPropertiesOfObject;
@end

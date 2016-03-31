//
//  User.m
//  Cream_Stone
//
//  Created by Hariteja P on 12/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "User.h"
#import <objc/runtime.h>
@implementation User
@synthesize userAccountId,userName,userRole,password,firstName,fullName,middleName,lastName,mobileNo,token,guest,emailId,cart,userprofile;

-(id)initWithuserAccountId:(NSString *)UseraccountId anduserRole:(NSString *)UserRole anduserName:(NSString *)UserName andpassword:(NSString *)Pasword andfullName:(NSString *)Fullname andfirstName:(NSString *)FirstName andmiddleName:(NSString *)Middlename andlastName:(NSString *)Lastname andmobileNo:(NSString *)Mobileno andemailId:(NSString *)Emailid andtoken:(int)Token andguest:(BOOL)Guest andcart:(Cart *)kart anduserprofile:(UsrProfile *)Userprofile {
    
    self =[super init];
    if(self)
    {
        userAccountId = UseraccountId;
        userRole = UserRole;
        userName = UserName;
        firstName = FirstName;
        fullName  = Fullname;
        middleName = Middlename;
        lastName = Lastname;
        password = Pasword;
        mobileNo = Mobileno;
        token = Token;
        guest = Guest;
        emailId = Emailid;
        cart = kart;
        userprofile = Userprofile;
    }
    return  self;
}
//-(id)initWithCoder:(NSCoder *)aDecoder
//{
//    self = [super init];
//    if (self) {
//        self.userAccountId = [aDecoder decodeObjectForKey:@"userAccountId"];
//        self.userRole = [aDecoder decodeObjectForKey:@"userRole"];
//        self.userName = [aDecoder decodeObjectForKey:@"userName"];
//        self.firstName = [aDecoder decodeObjectForKey:@"firstName"];
//        self.middleName = [aDecoder decodeObjectForKey:@"middleName"];
//        self.lastName = [aDecoder decodeObjectForKey:@"lastName"];
//        self.password = [aDecoder decodeObjectForKey:@"password"];
//        self.mobileNo = [aDecoder decodeObjectForKey:@"mobileNo"];
//        self.token = [aDecoder decodeIntForKey:@"token"];
//        self.guest = [aDecoder decodeBoolForKey:@"guest"];
//        self.emailId = [aDecoder decodeObjectForKey:@"emailId"];
//        self.fullName = [aDecoder decodeObjectForKey:@"fullName"];
//        self.cart = [aDecoder decodeObjectForKey:@"cart"];
//        self.userprofile = [aDecoder decodeObjectForKey:@"userprofile"];
//             }
//    return self;
//}
//
//-(void)encodeWithCoder:(NSCoder *)aCoder
//{
//    [aCoder encodeObject:self.userAccountId forKey:@"userAccountId"];
//    [aCoder encodeObject:self.userRole forKey:@"userRole"];
//    [aCoder encodeObject:self.userName forKey:@"userName"];
//    [aCoder encodeObject:self.firstName forKey:@"firstName"];
//    [aCoder encodeObject:self.middleName forKey:@"middleName"];
//    [aCoder encodeObject:self.lastName forKey:@"lastName"];
//    [aCoder encodeObject:self.password forKey:@"password"];
//    [aCoder encodeObject:self.mobileNo forKey:@"mobileNo"];
//    [aCoder encodeInt:self.token forKey:@"token"];
//    [aCoder encodeBool:self.guest forKey:@"guest"];
//    [aCoder encodeObject:self.emailId forKey:@"emailId"];
//    [aCoder encodeObject:self.fullName forKey:@"fullName"];
//    [aCoder encodeObject:self.userprofile forKey:@"userprofile"];
//    [aCoder encodeObject:self.cart forKey:@"cart"];
//    
//}
-(NSObject *)initWithDictionary:(NSDictionary *)dictionary
{
    self.userAccountId = [dictionary objectForKey:@"userAccountId"];
    self.userName = [dictionary objectForKey:@"userName"];
    self.userRole = [dictionary objectForKey:@"userRole"];
    self.firstName = [dictionary objectForKey:@"firstName"];
    self.middleName = [dictionary objectForKey:@"middleName"];
    self.lastName = [dictionary objectForKey:@"lastName"];
    self.fullName = [dictionary objectForKey:@"fullName"];
    self.password = [dictionary objectForKey:@"password"];
    self.mobileNo = [dictionary objectForKey:@"mobileNo"];
    self.token = [[dictionary objectForKey:@"token"] intValue];
    self.guest = [[dictionary objectForKey:@"guest"] boolValue];
    self.emailId = [dictionary objectForKey:@"emailId"];
    self.userprofile = [dictionary objectForKey:@"userprofile"];
    self.cart = [dictionary objectForKey:@"cart"];
    return self;
}
//- (NSDictionary *) convertToDictionary
//{
//    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
//    [dict setValue:self.userAccountId forKey:@"userAccountId"];
//    [dict setValue:self.userName forKey:@"userName"];
//    [dict setValue:self.fullName forKey:@"fullName"];
//    [dict setValue:self.firstName forKey:@"firstName"];
//    [dict setValue:self.middleName forKey:@"middleName"];
//    [dict setValue:self.lastName forKey:@"LastName"];
//    [dict setValue:self.mobileNo forKey:@"mobileNo"];
//    [dict setValue:[NSNumber numberWithInt:self.token] forKey:@"token"];
//    [dict setValue:[NSNumber numberWithBool:self.guest] forKey:@"guest"];
//    [dict setValue:self.emailId forKey:@"emailId"];
//     NSLog(@"cart=%@",self.cart);
//    if([self.cart isKindOfClass:[Cart class]]){
//        NSLog(@"cart is null");
//        
//    }
//    else{
//         [dict setObject:[self.cart convertToDictionary] forKey:@"cart"];
//    }
//   
//    [dict setObject:[self.userprofile dictionaryWithPropertiesOfObject:userprofile]  forKey:@"userprofile"];
//    return [NSDictionary dictionaryWithDictionary:dict];
//}
-(NSMutableDictionary *) dictionaryWithPropertiesOfObject
{
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    
    unsigned count;
    objc_property_t *properties = class_copyPropertyList([self class], &count);
    for (int i = 0; i < count; i++) {
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        Class classObject;
//        if ([key isEqual:@"cart"] || [key isEqual:@"userprofile"]) {
//            classObject = NSClassFromString(@"Cart");
//            classObject = NSClassFromString(@"userprofile");
//            
//        }
//        else
            classObject = NSClassFromString([key capitalizedString]);
        
        if (classObject) {
            NSLog(@"key:%@",key);
            id subObj = [self valueForKey:key];
            [dict setObject:subObj forKey:key];
        }
        else
        {
            id value = [self valueForKey:key];
            if(value && [value isKindOfClass:[NSMutableArray class]]) {
                NSMutableArray*array=[[NSMutableArray alloc] init];
                
                for (int j = 0; j < [value count]; j++) {
                    [array setObject:[self dictionaryWithPropertiesOfObject] atIndexedSubscript:j];
                }
                
                [dict setObject:array forKey:key];
                
            }else if(value)
                [dict setObject:value forKey:key];
            else {
                if ([value intValue] || [value longValue] || [value doubleValue] || [value floatValue] || [value charValue]) {
                    [dict setObject:[NSNull null] forKey:key];
                }
                else
                    [dict setObject:[NSNull null] forKey:key];
            }
        }
    }    free(properties);
    
    return [NSMutableDictionary dictionaryWithDictionary:dict];
}

@end

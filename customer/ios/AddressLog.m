//
//  AddressLog.m
//  Cream_Stone
//
//  Created by Hariteja P on 13/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "AddressLog.h"
#import <objc/runtime.h>
@implementation AddressLog
@synthesize addressId,address,emailId,state,city,country,customerName,landmark,phoneNum1,phoneNum2,userprofile,zipcode;

-(id)initWithaddressId:(NSString *)AddressId andcustomerName:(NSString *)CustomerName andaddress:(NSString *)Address andcity:(NSString *)City andzipcode:(NSString *)Zipcode andstate:(NSString *)State andcountry:(NSString *)Country andlandmark:(NSString *)Landmark andphoneNum1:(NSString *)Phonenum1 andphoneNum2:(NSString *)PhoneNum2 andemailId:(NSString *)EmailId anduserprofile:(UsrProfile *)Userprofile
{
    self = [super init];
    if(self)
    {
        addressId = AddressId;
        customerName = CustomerName;
        address = Address;
        city = City;
        zipcode = Zipcode;
        state = State;
        country = Country;
        landmark = Landmark;
        phoneNum1 = Phonenum1;
        phoneNum2 = PhoneNum2;
        emailId = EmailId;
        userprofile = Userprofile;
     }
    return  self;
}
-(NSMutableDictionary *) dictionaryWithPropertiesOfObject:(id)obj
{
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    
    unsigned count;
    objc_property_t *properties = class_copyPropertyList([obj class], &count);
    for (int i = 0; i < count; i++) {
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        Class classObject;
//        if ([key isEqual:@"product"]) {
//            classObject = NSClassFromString(@"productcategory");
//            
//        }
//        else
            classObject = NSClassFromString([key capitalizedString]);
        
        if (classObject) {
            NSLog(@"key:%@",key);
            id subObj = [self dictionaryWithPropertiesOfObject:[obj valueForKey:key]];
            [dict setObject:subObj forKey:key];
        }
        else
        {
            id value = [obj valueForKey:key];
            if(value && [value isKindOfClass:[NSMutableArray class]]) {
                NSMutableArray*array=[[NSMutableArray alloc] init];
                
                for (int j = 0; j < [value count]; j++) {
                    [array setObject:[self dictionaryWithPropertiesOfObject:value[j]] atIndexedSubscript:j];
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

//
//  UsrProfile.m
//  Cream_Stone
//
//  Created by Hariteja P on 13/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "UsrProfile.h"
#import <objc/runtime.h>
@implementation UsrProfile
@synthesize user,userAccountId,timeStamp,phoneNum1,phoneNum2,card,alternateEmailId,addresslog,adrresses,order;

-(id)initWithuserAccountId:(NSString *)UserAccountId andaddresslog:(AddressLog *)AddressLog andadrresses:(NSMutableArray *)Adrresses andalternateEmailId:(NSString *)AlternateEmailId andcard:(Card *)Card andtimeStamp:(NSDate *)TimeStamp andphoneNum1:(NSString *)PhoneNum1 andphoneNum2:(NSString *)PhoneNum2 anduser:(User *)User andorder:(Order *)Order
{
    self = [super init];
    if(self)
    {
        userAccountId = UserAccountId;
        user = User;
        adrresses = Adrresses;
        addresslog = AddressLog;
        alternateEmailId = AlternateEmailId;
        card = Card;
        timeStamp = TimeStamp;
        phoneNum1= PhoneNum1;
        phoneNum2 = PhoneNum2;
        order= Order;
        user = User;
    }
    return  self;
}
-(void)initWithDictionary:(NSDictionary *)dictionary{
    
    self.user = [dictionary objectForKey:@"user"];
    self.userAccountId = [dictionary objectForKey:@"cartId"];
    self.timeStamp = [dictionary objectForKey:@"timeStamp"];
    self.phoneNum1= [dictionary objectForKey:@"phoneNum1"] ;
    self.phoneNum2 = [dictionary objectForKey:@"phoneNum2"] ;
    self.card= [dictionary objectForKey:@"card"];
    self.alternateEmailId = [dictionary objectForKey:@"alternateEmailId"];
    self.adrresses = [dictionary objectForKey:@"adrresses"];
    self.addresslog = [dictionary objectForKey:@"addresslog"];
    
}
//- (NSDictionary *) dictionaryWithPropertiesOfObject
//{
//    NSMutableDictionary*dict = [[NSMutableDictionary alloc] init];
//    [dict setValue:self.user forKey:@"user"];
//    [dict setValue:self.userAccountId forKey:@"userAccountId"];
//    [dict setValue:self.timeStamp forKey:@"timeStamp"];
//    [dict setValue:self.phoneNum1 forKey:@"phoneNum1"];
//    [dict setValue:self.phoneNum2 forKey:@"phoneNum2"];
//    [dict setValue:self.card forKey:@"card"];
//    [dict setValue:self.alternateEmailId forKey:@"alternateEmailId"];
//    [dict setValue:self.adrresses forKey:@"adrresses"];
//    [dict setValue:self.addresslog forKey:@"addresslog"];
//    
//    return [NSDictionary dictionaryWithDictionary:dict];
//    
//}
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

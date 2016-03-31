//
//  OrderNotification.m
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "OrderNotification.h"
#import <objc/runtime.h>
@implementation OrderNotification
@synthesize from,order,location,messageType,orderType,arrivalTime;

-(id)initWithorder:(Order *)Order andlocation:(Location *)Location  andfrom:(User *)From andmessageType:(NSString*)msgtype andorderType:(NSString *)ordtype andarrivalTime:(long)ArrivalTime{
    
    self =[super init];
    if(self){
        order = Order;
        location = Location;
        from = From;
        messageType = msgtype;
        orderType = ordtype;
        arrivalTime= ArrivalTime;
    }
    return self;
}
  
-(NSString *) converttoString:(MessageType)msgType{
    NSString *result1 = nil;
    
    switch(msgType) {
        case LOCATION:
            result1 = @"LOCATION";
            break;
        case NOTIFY:
            result1 = @"NOTIFY";
            break;
        default:
            result1 = @"unknown";
    }
    
    return result1;
    
    
}
-(NSString*) convertToString:(OrderType)ordrType {
    NSString *result = nil;
    
    switch(ordrType) {
        case DRIVETHRU:
            result = @"DRIVETHRU";
            break;
        case PICKUP:
            result = @"PICKUP";
            break;
                default:
            result = @"unknown";
    }
    
    return result;
}
-(NSMutableDictionary *) dictionaryWithPropertiesOfObject:(id)obj
{
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    
    unsigned count;
    objc_property_t *properties = class_copyPropertyList([obj class], &count);
    for (int i = 0; i < count; i++) {
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        Class classObject;
        if ([key isEqual:@"product"]) {
            classObject = NSClassFromString(@"productcategory");
            
        }
        else
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

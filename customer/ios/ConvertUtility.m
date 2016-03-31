//
//  ConvertUtility.m
//  Cream_Stone
//
//  Created by Hariteja P on 10/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "ConvertUtility.h"
#import <objc/runtime.h>
#import "OrderNotification.h"
@implementation ConvertUtility
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
        else if ([key isEqual:@"from"] && [self isKindOfClass:[OrderNotification class]]) {
            classObject = NSClassFromString(@"User");
            
        }
        else if ([key isEqual:@"userprofile"]) {
            classObject = NSClassFromString(@"UsrProfile");
            
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

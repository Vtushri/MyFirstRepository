//
//  Stores.m
//  CreameStone
//
//  Created by Hariteja P on 11/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
#import "Stores.h"
#import <objc/runtime.h>
@implementation Stores
@synthesize storeAddress,storeId,latitude,longitude,name,storeCity,user,order;


-(id)initWithstoreId:(NSString *) cStoreId andname:(NSString *) cName andlatitude:(NSNumber *) cLatitude andlongitude:(NSNumber *)cLongitude andstoreAddress:(NSString *) cStoreAddress andstoreCity:(NSString *) cStoreCity andorder:(NSString *) cOrder anduser:(NSMutableArray *) cUser;

{
    self = [super init];
    if (self)     {
        
        storeId = cStoreId;
        name = cName;
        latitude =cLatitude;
        longitude =cLongitude;
        storeAddress =cStoreAddress;
        storeCity = cStoreCity;
        order = cOrder;
        user =cUser;
        
    }
    
    return  self;
}
-(NSMutableDictionary *) toDictionary
{
    unsigned int count = 0;
    
    NSMutableDictionary *dictionary = [NSMutableDictionary new];
    objc_property_t *properties = class_copyPropertyList([self class], &count);
    
    for (int i = 0; i < count; i++) {
        
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        id value = [self valueForKey:key];
        
        if (value == nil) {
            // nothing todo
        }
        else if ([value isKindOfClass:[NSNumber class]]
                 || [value isKindOfClass:[NSString class]]
                 || [value isKindOfClass:[NSDictionary class]]) {
            // TODO: extend to other types
            [dictionary setObject:value forKey:key];
        }
        else if ([value isKindOfClass:[NSObject class]]) {
            [dictionary setObject:[value toDictionary] forKey:key];
        }
        else {
            NSLog(@"Invalid type for %@ (%@)", NSStringFromClass([self class]), key);
        }
    }
    
    free(properties);
    
    return dictionary;}

@end

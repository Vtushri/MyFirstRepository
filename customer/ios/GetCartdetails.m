//
//  GetCartdetails.m
//  Cream_Stone
//
//  Created by Hariteja P on 31/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "GetCartdetails.h"
#import "productcategory.h"
#import <objc/runtime.h>
@implementation GetCartdetails
@synthesize  cart,c_p_id,product,quantity;

-(id)initWithc_p_id:(NSString *)cC_p_id andcart:(NSString *)cCart andproduct:(productcategory *)cProduct andquantity:(NSNumber *)cQuantity{
    self = [super init];
    if (self)     {
    
        c_p_id = cC_p_id;
        cart = cCart;
        product =cProduct;
        quantity =cQuantity;
        
          }
    return self;
}
- (NSDictionary *) dictionaryWithPropertiesOfObject{
    
    NSMutableDictionary*dict = [[NSMutableDictionary alloc] init];
    [dict setValue:self.c_p_id forKey:@"c_p_id"];
    [dict setValue:self.cart forKey:@"cart"];
    [dict setValue:self.quantity forKey:@"quantity"];
    [dict setValue:[product dictionaryWithPropertiesOfObject] forKey:@"product"];
    return [NSDictionary dictionaryWithDictionary:dict];
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

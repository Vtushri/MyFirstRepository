//
//  Cart.m
//  Cream_Stone
//
//  Created by Hariteja P on 13/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "Cart.h"
#import <objc/runtime.h>
@implementation Cart
@synthesize user,cartId,taxes,shippingCharges,transactionCharges,products,extraCharges,totalPrice,expiry;
-(id)initWithcartId:(NSString *)Cartid andtotalPrice:(double)TotalPrice andshippingCharges:(double)Shippingcharges andtransactionCharges:(double)Transactioncharges andtaxes:(double)Taxes andexpiry:(NSDate *)Expiry anduser:(User *)User andextraCharges:(double)Extracharges andproducts:(NSMutableArray*)Products
{
    self = [super init];
    if(self)
    {
        cartId= Cartid;
        totalPrice = TotalPrice;
        transactionCharges= Transactioncharges;
        shippingCharges = Shippingcharges;
        extraCharges = Extracharges;
        expiry = Expiry;
        products = Products;
        user = User;
        taxes = Taxes;
    } 

    return self;
}
-(void)initWithDictionary:(NSDictionary *)dictionary{
    
    self.user = [dictionary valueForKey:@"user"];
    self.cartId = [dictionary valueForKey:@"cartId"];
    self.taxes = [[dictionary valueForKey:@"taxes"] doubleValue];
    self.shippingCharges= [[dictionary valueForKey:@"shippingCharges"] doubleValue];
    self.transactionCharges = [[dictionary valueForKey:@"transactionCharges"] doubleValue];
    self.products = [dictionary valueForKey:@"products"];
    self.extraCharges = [[dictionary valueForKey:@"extraCharges"] doubleValue];
    self.totalPrice = [[dictionary valueForKey:@"totalPrice"] doubleValue];
    self.expiry = [dictionary valueForKey:@"expiry"];
    
}
- (NSDictionary *) convertToDictionary
{
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    [dict setValue:self.user forKey:@"user"];
    [dict setValue:self.cartId forKey:@"cartId"];
    [dict setValue:[NSNumber numberWithDouble:self.taxes] forKey:@"taxes"];
    [dict setValue:[NSNumber numberWithDouble:self.shippingCharges] forKey:@"shippingCharges"];
    [dict setValue:[NSNumber numberWithDouble:self.transactionCharges] forKey:@"transactionCharges"];
    [dict setValue:[NSNumber numberWithDouble:self.extraCharges] forKey:@"extraCharges"];
    [dict setValue:[NSNumber numberWithDouble:self.totalPrice] forKey:@"totalPrice"];
    NSMutableArray*dictionary = [[NSMutableArray alloc] init];
    if(![self.products isEqual:NULL]){
    for (int i=0; i<[self.products count]; i++) {
        [dictionary addObject:self.products[i]];
        }
    [dict setValue:dictionary forKey:@"products"];
    }
    else{
         [dict setValue:[NSNull null] forKey:@"products"];
    }
 
    [dict setValue:self.expiry forKey:@"expiry"];
  
    
    return dict;
}





-(NSMutableDictionary *) dictionaryWithPropertiesOfObject
{
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    
    unsigned count;
    objc_property_t *properties = class_copyPropertyList([self class], &count);
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

//
//  Order.m
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "Order.h"
#import <objc/runtime.h>
@implementation Order
@synthesize  orderDate,orderId,orderStatus,payment,processingTime,shipping,store,token,totalAmount,orderType,taxes,deliveryTime,products,shippingCharges,transactionCharges,userprofile;

-(id)initWithorderId:(NSString *)OrderId andorderDate:(NSDate *)Orderdate andtoken:(int)Token andtaxes:(double)Taxes andorderStatus:(NSString *)Orderstatus andtotalAmount:(double)Totalamount andorderType:(NSString *)OrderType anddeliveryTime:(long)Deliverytime andprocessingTime:(long)Processingtime andshipping:(Shipping *)Shipping andpayment:(Payment *)Payment andstore:(Stores *)Store andproducts:(NSMutableArray *)Products andshippingCharges:(double)Shippingcharges andtransactionCharges:(double)Transactioncharges anduserprofile:(UsrProfile *)Userprofile {
    self =[super init];
    if(self){
        
        orderId = OrderId;
        orderDate = Orderdate;
        token = Token;
        taxes = Taxes;
        totalAmount = Totalamount;
        orderType = OrderType;
        deliveryTime =Deliverytime;
        processingTime =Processingtime;
        shipping =Shipping;
        orderStatus =Orderstatus;
        payment = Payment;
        store = Store;
        products = Products;
        shippingCharges = Shippingcharges;
        transactionCharges = Transactioncharges;
    }
    return self;
}
-(NSDictionary *)convertToDictonary{
    
    
    
    return [NSDictionary dictionary];
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
                    [array setObject: value[j] atIndexedSubscript:j];
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

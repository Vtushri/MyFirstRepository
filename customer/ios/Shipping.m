//
//  Shipping.m
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "Shipping.h"

@implementation Shipping
@synthesize shippingId,shippingCharges,shippingMethod,shippingType,status,maxDays;

-(id)initWithshippingId:(NSString *)Shippingid andshippingMethod:(NSString *)Shippingmethod andshippingtype:(NSString *)Shippingtype andstatus:(NSString *)Status andshippingCharges:(double *)Shippingcharges andmaxDays:(int *)Maxdays{
    
    self =[super init];
    if(self){
    
        shippingId =Shippingid;
        shippingMethod = Shippingmethod;
        shippingType =Shippingtype;
        status =Status;
        shippingCharges = Shippingcharges;
        maxDays = Maxdays;
        
    
    }
    return  self;
}
@end

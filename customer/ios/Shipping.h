//
//  Shipping.h
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Shipping : NSObject
@property (nonatomic,strong) NSString*shippingId;
@property (nonatomic,strong) NSString*shippingMethod;
@property (nonatomic,strong) NSString*shippingType;
@property (nonatomic,strong) NSString*status;
@property (nonatomic) double*shippingCharges;
@property (nonatomic) int*maxDays;

-(id)initWithshippingId:(NSString *) Shippingid andshippingMethod:(NSString *) Shippingmethod andshippingtype:(NSString *) Shippingtype andstatus:(NSString *) Status andshippingCharges:(double *) Shippingcharges andmaxDays:(int *) Maxdays;
@end

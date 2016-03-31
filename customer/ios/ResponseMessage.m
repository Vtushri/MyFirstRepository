//
//  ResponseMessage.m
//  Cream_Stone
//
//  Created by Hariteja P on 16/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "ResponseMessage.h"

@implementation ResponseMessage
@synthesize orderType,processingTime,available,deliveryTime;

-(id)initWithavailable:(int)Available andorderType:(NSString *)Ordertype andprocessingTime:(long)Processingtime anddeliveryTime:(NSDate *)DeliveryTime{
    
    self =[super init];
    if(self)
    {
        available= Available;
        orderType = Ordertype;
        processingTime =Processingtime;
        deliveryTime= DeliveryTime;
    }
    return self;
    
}

@end

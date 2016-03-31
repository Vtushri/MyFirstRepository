//
//  ResponseMessage.h
//  Cream_Stone
//
//  Created by Hariteja P on 16/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ResponseMessage : NSObject

@property (nonatomic,assign) int available;
@property (nonatomic,strong) NSString*orderType;
@property (nonatomic,assign) long processingTime;
@property (nonatomic,assign) NSDate* deliveryTime;

-(id)initWithavailable:(int)Available andorderType:(NSString *) Ordertype andprocessingTime:(long) Processingtime anddeliveryTime:(NSDate *)DeliveryTime;
@end

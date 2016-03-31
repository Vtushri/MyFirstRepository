//
//  Payment.h
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
@class Order;
@class Card;
#import <Foundation/Foundation.h>

@interface Payment : NSObject
@property (nonatomic,strong) NSString *paymentId;
@property (nonatomic,strong) NSString*paymentStatus;
@property (nonatomic,strong) NSDate*timestamp;
@property (nonatomic,strong) NSString*paymentMode;
@property (nonatomic,assign) double paymentCharges;
@property (nonatomic,assign) double payAmount;
@property (nonatomic,strong) NSString*referenceId;
@property (nonatomic,strong) Order*order;
@property (nonatomic,strong) Card*card;

-(id)initWithpaymentId:(NSString *) Paymentid andpaymentStatus:(NSString *) Paymentstatus andtimestamp:(NSDate *) Timestamp andpaymentMode:(NSString *) Paymentmode andpaymentCharges:(double) Paymentcharges andpayAmount:(double) Payamount andreferenceId:(NSString *) Referenceid andorder:(Order *) Order andcard:(Card *) Card;
                                                                                                                                                                            
@end

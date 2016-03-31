//
//  Payment.m
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "Payment.h"

@implementation Payment
@synthesize paymentId,payAmount,paymentCharges,paymentMode,paymentStatus,card,order,timestamp,referenceId;


-(id)initWithpaymentId:(NSString *)Paymentid andpaymentStatus:(NSString *)Paymentstatus andtimestamp:(NSDate *)Timestamp andpaymentMode:(NSString *)Paymentmode andpaymentCharges:(double)Paymentcharges andpayAmount:(double)Payamount andreferenceId:(NSString *)Referenceid andorder:(Order *)Order andcard:(Card *)Card
{
    self =[super init];
    if(self){
        paymentId =Paymentid;
        paymentMode =Paymentmode;
        paymentCharges = Paymentcharges;
        paymentStatus =Paymentstatus;
        order = Order;
        card = Card;
        timestamp =Timestamp;
        payAmount = Payamount;
        referenceId =Referenceid;
       
    }
    return  self;
}
@end

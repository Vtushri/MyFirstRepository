//
//  Order.h
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

@protocol Order
@end

@class Shipping;
@class Payment;
@class Stores;
@class UsrProfile;
#import <Foundation/Foundation.h>

@interface Order : NSObject
@property (nonatomic,strong) NSString*orderId;
@property (nonatomic,strong) NSDate*orderDate;
@property (nonatomic,assign) int token;
@property (nonatomic,assign) double taxes;
@property (nonatomic,assign) double shippingCharges;
@property (nonatomic,assign) double transactionCharges;

@property (nonatomic,strong) NSString*orderStatus;
@property (nonatomic,assign) double totalAmount;
@property (nonatomic,strong) NSString*orderType;
@property (nonatomic,assign) long deliveryTime;
@property (nonatomic,assign) long processingTime;
@property (nonatomic,strong) Shipping*shipping;
@property (nonatomic,strong) Payment*payment;
@property (nonatomic,strong) Stores*store;
@property (nonatomic,strong) UsrProfile*userprofile;
@property (nonatomic,strong) NSMutableArray*products;

-(id)initWithorderId:(NSString *) OrderId andorderDate:(NSDate *) Orderdate andtoken:(int) Token andtaxes:(double) Taxes andorderStatus:(NSString *) Orderstatus andtotalAmount:(double) Totalamount andorderType:(NSString *) OrderType anddeliveryTime:(long) Deliverytime andprocessingTime:(long) Processingtime andshipping:(Shipping *)Shipping andpayment:(Payment *)Payment andstore:(Stores *) Store andproducts:(NSMutableArray*) Products andshippingCharges:(double) Shippingcharges andtransactionCharges:(double) Transactioncharges anduserprofile:(UsrProfile *)Userprofile;

-(NSMutableDictionary *) dictionaryWithPropertiesOfObject;
@end

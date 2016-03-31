//
//  Cart.h
//  Cream_Stone
//
//  Created by Hariteja P on 13/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
//@protocol GetcartDetails;

#import <Foundation/Foundation.h>
#import "User.h"

@interface Cart : NSObject
@property (nonatomic,strong) NSString*cartId;
@property (nonatomic,assign) double totalPrice;
@property (nonatomic,assign) double shippingCharges;
@property (nonatomic,assign) double transactionCharges;
@property (nonatomic,assign) double taxes;
@property (nonatomic,assign) NSDate*expiry;
@property (nonatomic,strong) User*user;
@property (nonatomic,assign) double extraCharges;
@property (nonatomic,strong) NSMutableArray*products;

-(id)initWithcartId:(NSString *) Cartid andtotalPrice:(double) TotalPrice andshippingCharges:(double) Shippingcharges andtransactionCharges:(double) Transactioncharges andtaxes:(double) Taxes andexpiry:(NSDate *) Expiry anduser:(User *) User andextraCharges:(double) Extracharges andproducts:(NSMutableArray*) Products;

-(void)initWithDictionary:(NSDictionary *)dictionary;
- (NSDictionary *) convertToDictionary;
-(NSMutableDictionary *) dictionaryWithPropertiesOfObject;
@end

//
//  orderProduct.h
//  Cream_Stone
//
//  Created by Hariteja P on 08/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//


#import <Foundation/Foundation.h>
#import "Order.h"
#import "productcategory.h"
@interface orderProduct : NSObject
@property (nonatomic,strong) NSString* o_p_id;
@property (nonatomic,retain) Order*order;
@property (nonatomic,strong) productcategory*product;
@property (nonatomic,assign) int quantity;

-(id)initWitho_p_id:(NSString *) O_p_id andorder:(Order *) Order andproduct:(productcategory *) Product andquantity:(int) Quantity;

@end

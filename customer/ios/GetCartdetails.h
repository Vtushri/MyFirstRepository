//
//  GetCartdetails.h
//  Cream_Stone
//
//  Created by Hariteja P on 31/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//


#import <Foundation/Foundation.h>
#import "productcategory.h"
@interface GetCartdetails : NSObject
@property (nonatomic,strong) NSString*c_p_id;
@property (nonatomic,strong) NSString*cart;
@property (nonatomic,strong) productcategory*product;
@property (nonatomic,strong) NSNumber*quantity;

-(id)initWithc_p_id : (NSString *)cC_p_id andcart : (NSString *)cCart andproduct : (productcategory *)cProduct andquantity : (NSNumber *)cQuantity;


- (NSDictionary *) dictionaryWithPropertiesOfObject;

@end

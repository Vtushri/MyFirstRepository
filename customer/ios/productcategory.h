//
//  productcategory.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface productcategory : NSObject <NSCoding>
@property(nonatomic,strong) NSString *productId;
@property(nonatomic,strong) NSString *imagesUrl;
@property(nonatomic,strong) NSString *productName;
@property(nonatomic,strong) NSString *productDesc;
@property(nonatomic,strong) NSString *productPrice1;
@property(nonatomic,strong) NSString *productPrice2;
@property(nonatomic,strong) NSString *addedDate;
@property(nonatomic,strong) NSString *rating;
@property (nonatomic,strong) NSString *offer_product;
@property(nonatomic,strong) NSString *cart_product;
@property (nonatomic,strong)NSString *categoryId;
@property (nonatomic,strong) NSString*manufacturer;
@property (nonatomic,strong) NSString*qty;


-(id)initWithproductId: (NSString *) cProductId andimagesUrl: (NSString *) cImageUrl andproductName : (NSString *) cProductName andproductPrice1: (NSString *) cProductPrice1 andproductPrice2: (NSString *) cProductprice2 andrating: (NSString *) cRating andaddedDate: (NSString *) cAddedDate andproductDesc: (NSString *) cProductDesc andoffer_product: (NSString *) cOffer_product andcart_product: (NSString *) cCart_product andcategoryId: (NSString *) cCategoryId andmanufacturer: (NSString *) cManufacturer andqty: (NSString *)cQty;

-(void)initWithDictionary:(NSDictionary *)dictionary;
- (NSDictionary *) dictionaryWithPropertiesOfObject;
- (NSDictionary *) toDictionary;
@end

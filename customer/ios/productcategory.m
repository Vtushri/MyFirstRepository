//
//  productcategory.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "productcategory.h"
#import <objc/runtime.h>
@implementation productcategory
@synthesize productId,productName,productDesc,productPrice1,productPrice2,offer_product,cart_product,categoryId,addedDate,rating,imagesUrl,qty,manufacturer;
-(id)initWithproductId: (NSString *) cProductId andimagesUrl: (NSString *) cImageUrl andproductName : (NSString *) cProductName andproductPrice1: (NSString *) cProductPrice1 andproductPrice2: (NSString *) cProductPrice2 andrating: (NSString *) cRating andaddedDate: (NSString *) cAddedDate andproductDesc: (NSString *) cProductDesc andoffer_product: (NSString *) cOffer_product andcart_product: (NSString *) cCart_product andcategoryId: (NSString *) cCategoryId andmanufacturer: (NSString *) cManufacturer andqty: (NSString *)cQty{
    
    self =[super init];
    if(self){
        productId = cProductId;
        productName =cProductName;
        productDesc =cProductDesc;
        productPrice1 = cProductPrice1;
        productPrice2 =cProductPrice2;
        categoryId = cCategoryId;
        offer_product =cOffer_product;
        cart_product =cCart_product;
        imagesUrl = cImageUrl;
        addedDate =cAddedDate;
        rating =cRating;
        manufacturer =cManufacturer;
        qty = cQty;
    }
    return self;
}
-(id)initWithCoder:(NSCoder *)aDecoder
{
    self = [super init];
    if (self) {
        self.productId = [aDecoder decodeObjectForKey:@"productId"];
        self.productName = [aDecoder decodeObjectForKey:@"location"];
        self.productPrice1 = [aDecoder decodeObjectForKey:@"productPrice1"];
        self.productPrice2 = [aDecoder decodeObjectForKey:@"prodcutprice2"];
        self.productDesc = [aDecoder decodeObjectForKey:@"productDesc"];
        self.categoryId = [aDecoder decodeObjectForKey:@"categoryId"];
        self.cart_product = [aDecoder decodeObjectForKey:@"cart_product"];
        self.offer_product = [aDecoder decodeObjectForKey:@"offer_product"];
        self.imagesUrl = [aDecoder decodeObjectForKey:@"imagesUrl"];
        self.addedDate = [aDecoder decodeObjectForKey:@"addedDate"];
        self.rating = [aDecoder decodeObjectForKey:@"rating"];
        self.manufacturer = [aDecoder decodeObjectForKey:@"manufacturer"];
        self.qty = [aDecoder decodeObjectForKey:@"qty"];
    }
  return self;
  }

-(void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:self.productId forKey:@"productId"];
    [aCoder encodeObject:self.productName forKey:@"productName"];
    [aCoder encodeObject:self.productPrice1 forKey:@"productPrice1"];
    [aCoder encodeObject:self.productPrice2 forKey:@"productPrice2"];
    [aCoder encodeObject:self.productDesc forKey:@"productDesc"];
    [aCoder encodeObject:self.categoryId forKey:@"categoryId"];
    [aCoder encodeObject:self.cart_product forKey:@"cart_product"];
    [aCoder encodeObject:self.offer_product forKey:@"offer_product"];
    [aCoder encodeObject:self.imagesUrl forKey:@"imagesUrl"];
    [aCoder encodeObject:self.addedDate forKey:@"addedDate"];
    [aCoder encodeObject:self.rating forKey:@"rating"];
    [aCoder encodeObject:self.manufacturer forKey:@"manufacturer"];
    [aCoder encodeObject:self.qty forKey:@"qty"];
}
-(void)initWithDictionary:(NSDictionary *)dictionary{
    	
        self.productId = [dictionary objectForKey:@"productId"];
        self.productName = [dictionary objectForKey:@"productName"];
        self.productPrice1 = [dictionary objectForKey:@"productPrice1"];
        self.productPrice2 = [dictionary objectForKey:@"prodcutprice2"];
        self.productDesc = [dictionary objectForKey:@"productDesc"];
        self.categoryId = [dictionary objectForKey:@"categoryId"];
        self.cart_product = [dictionary objectForKey:@"cart_product"];
        self.offer_product = [dictionary objectForKey:@"offer_product"];
        self.imagesUrl = [dictionary objectForKey:@"imagesUrl"];
        self.addedDate = [dictionary objectForKey:@"addedDate"];
        self.rating = [dictionary objectForKey:@"rating"];
        self.manufacturer = [dictionary objectForKey:@"manufacturer"];
        self.qty = [dictionary objectForKey:@"qty"];
 }
- (NSDictionary *) dictionaryWithPropertiesOfObject{
    NSMutableDictionary*dict = [[NSMutableDictionary alloc] init];
    [dict setValue:self.productId forKey:@"productId"];
    [dict setValue:self.productName forKey:@"productName"];
    [dict setValue:self.productPrice1 forKey:@"productPrice1"];
    [dict setValue:self.productPrice2 forKey:@"productPrice2"];
    [dict setValue:self.productDesc forKey:@"productDesc"];
    [dict setValue:self.categoryId forKey:@"categoryId"];
    [dict setValue:self.cart_product forKey:@"cart_product"];
    [dict setValue:self.offer_product forKey:@"offer_product"];
    [dict setValue:self.imagesUrl forKey:@"imagesUrl"];
    [dict setValue:self.addedDate forKey:@"addedDate"];
    [dict setValue:self.rating forKey:@"rating"];
    [dict setValue:self.manufacturer forKey:@"manufacturer"];
    [dict setValue:self.qty forKey:@"qty"];
    
    return [NSDictionary dictionaryWithDictionary:dict];
}
-(NSDictionary *) toDictionary
{
    unsigned int count = 0;
    
    NSMutableDictionary *dictionary = [NSMutableDictionary new];
    objc_property_t *properties = class_copyPropertyList([self class], &count);
    
    for (int i = 0; i < count; i++) {
        
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        id value = [self valueForKey:key];
        
        if (value == nil) {
            // nothing todo
        }
        else if ([value isKindOfClass:[NSNumber class]]
                 || [value isKindOfClass:[NSString class]]
                 || [value isKindOfClass:[NSDictionary class]]) {
            // TODO: extend to other types
            [dictionary setObject:value forKey:key];
        }
        else if ([value isKindOfClass:[NSObject class]]) {
            [dictionary setObject:[value toDictionary] forKey:key];
        }
        else {
            NSLog(@"Invalid type for %@ (%@)", NSStringFromClass([self class]), key);
        }
    }
    
    free(properties);
    
    return dictionary;
}

@end

//
//  BannerItems.h
//  example2
//
//  Created by Hariteja P on 21/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BannerItems : NSObject
@property (nonatomic,strong) NSString*bannerId;
@property (nonatomic,strong) NSString*bannerFlag;
@property (nonatomic,strong) NSString*imageURL;
@property (nonatomic,strong) NSString*offerId;
@property (nonatomic,strong) NSString*categoryId;
@property (nonatomic,strong) NSString*products;

-(id)initWithbannerID:(NSString *) cBannerId andbannerFlag:(NSString *) cBannerFlag andimageURL:(NSString *) cImageURL andofferId:(NSString *) cOfferId andcategoryId:(NSString *) cCategoryId andproducts:(NSString *) cProducts;
@end

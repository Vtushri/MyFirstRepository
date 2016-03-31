//
//  BannerItems.m
//  example2
//
//  Created by Hariteja P on 21/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "BannerItems.h"

@implementation BannerItems
@synthesize bannerId,bannerFlag,categoryId,imageURL,offerId,products;

-(id)initWithbannerID:(NSString *) cBannerId andbannerFlag:(NSString *) cBannerFlag andimageURL:(NSString *) cImageURL andofferId:(NSString *) cOfferId andcategoryId:(NSString *) cCategoryId andproducts:(NSString *) cProducts{
    
    self =[super init];
    if(self)
    {
        bannerId= cBannerId;
        bannerFlag =cBannerFlag;
        imageURL=cImageURL;
        offerId=cOfferId;
        categoryId=cCategoryId;
        products=cProducts;
    }

    return self;



}


@end

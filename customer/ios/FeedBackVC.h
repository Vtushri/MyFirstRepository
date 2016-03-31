//
//  FeedBackVC.h
//  example2
//
//  Created by Hariteja P on 03/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EDStarRating.h"
@interface FeedBackVC : UIViewController <EDStarRatingProtocol>
@property (nonatomic,strong) EDStarRating*starRatingImage;
@property (nonatomic,strong) UILabel*starRatingLabel;
@end

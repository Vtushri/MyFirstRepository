//
//  FeedBackVC.m
//  example2
//
//  Created by Hariteja P on 03/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "FeedBackVC.h"
#import "EDStarRating.h"
#import <FBSDKCoreKit/FBSDKCoreKit.h>
#import <FBSDKLoginKit/FBSDKLoginKit.h>
#import <FBSDKShareKit/FBSDKShareKit.h>

@interface FeedBackVC ()

@end

@implementation FeedBackVC
@synthesize starRatingImage;
- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    float width = [UIScreen mainScreen].bounds.size.width;
    starRatingImage = [[EDStarRating alloc] initWithFrame:CGRectMake(0,120,width-10,60)];
   // starRatingImage.backgroundImage=[UIImage imageNamed:@"starsbackground iOS.png"];
    starRatingImage.starImage = [UIImage imageNamed:@"Christmas Star-50.png"];
    starRatingImage.starHighlightedImage = [UIImage imageNamed:@"Star-50.png"];
    starRatingImage.maxRating = 5.0;
    starRatingImage.delegate = self;
    starRatingImage.horizontalMargin = 12;
    starRatingImage.editable=YES;
    starRatingImage.rating= 2.5;
    starRatingImage.displayMode=EDStarRatingDisplayAccurate;
    [self starsSelectionChanged:starRatingImage rating:2.5];
    // This one will use the returnBlock instead of the delegate
    
     starRatingImage.returnBlock = ^(float rating )
    {
        NSLog(@"ReturnBlock: Star rating changed to %.1f", rating);
        // For the sample, Just reuse the other control's delegate method and call it
        [self starsSelectionChanged:starRatingImage rating:rating];
    };
    FBSDKLoginButton *loginButton = [[FBSDKLoginButton alloc] init];
    loginButton.center = self.view.center;
    loginButton.readPermissions =
    @[@"public_profile", @"email", @"user_friends"];
        
    [self.view addSubview:loginButton];
    [self.view addSubview:starRatingImage];
    
    
 
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(void)starsSelectionChanged:(EDStarRating *)control rating:(float)rating
{
    NSString *ratingString = [NSString stringWithFormat:@"Rating: %.1f", rating];
    if( [control isEqual:starRatingImage] )
        _starRatingLabel.text = ratingString;
   
}


@end

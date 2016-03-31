//
//  AboutUsViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 06/11/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "AboutUsViewController.h"
#import <FBSDKShareKit/FBSDKShareKit.h>
#import <FBSDKLoginKit/FBSDKLoginKit.h>
#import <FBSDKCoreKit/FBSDKCoreKit.h>

@interface AboutUsViewController ()

@end

@implementation AboutUsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    FBSDKLikeControl *likeButton = [[FBSDKLikeControl alloc] init];
    likeButton.objectID = @"https://www.facebook.com/ZetFood";
    likeButton.center = self.view.center;
    [self.view addSubview:likeButton];

    likeButton.likeControlStyle = FBSDKLikeControlStyleBoxCount;
    // Change the style to box count
    likeButton.likeControlHorizontalAlignment =
    FBSDKLikeControlHorizontalAlignmentRight;
   NSString *fbAccessToken = [FBSDKAccessToken currentAccessToken].tokenString;
    NSLog(@"accessToken:%@",fbAccessToken);
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end

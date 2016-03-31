//
//  OfferViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 06/11/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "OfferViewController.h"
#import "PromotionsViewController.h"
#import "FeedBackVC.h"
#import "AboutUsViewController.h"


@interface OfferViewController ()

@end

@implementation OfferViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    PromotionsViewController*pvc = [[PromotionsViewController alloc] init];
    AboutUsViewController*avc = [[AboutUsViewController alloc] init];
    FeedBackVC*fvc = [[FeedBackVC alloc] init];
    
    NSArray*array = [NSArray arrayWithObjects:pvc,avc,fvc, nil];
    self.viewControllers = array;
  fvc.tabBarItem = [[UITabBarItem alloc] initWithTitle:@"Feedback" image:[UIImage imageNamed:@"Star-25.png"] tag:3];
  pvc.tabBarItem = [[UITabBarItem alloc] initWithTitle:@"Promotions" image:[UIImage imageNamed:@"Advertising-25.png"] tag:2];
  avc.tabBarItem = [[UITabBarItem alloc] initWithTitle:@"Know Us" image:[UIImage imageNamed:@"About-25.png"] tag:1];
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

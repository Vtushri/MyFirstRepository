//
//  MenuTabViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 01/12/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "MenuTabViewController.h"
#import "MenuSlideViewController.h"
#import "OthersViewController.h"
#import "ViewController.h"
@interface MenuTabViewController ()

@end

@implementation MenuTabViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    MenuSlideViewController*mcv = [[MenuSlideViewController alloc] init];
    OthersViewController*ovc = [[OthersViewController alloc] init];
    ViewController*vc = [[ViewController alloc] init];
    
    NSArray*array = [NSArray arrayWithObjects:mcv,ovc,vc, nil];
    self.viewControllers = array;
    mcv.tabBarItem = [[UITabBarItem alloc] initWithTitle:@"Menu" image:[UIImage imageNamed:@"List Filled-32.png"] tag:1];
    ovc.tabBarItem = [[UITabBarItem alloc] initWithTitle:@"More" image:[UIImage imageNamed:@"About Filled-32.png"] tag:2];
    
     vc.tabBarItem = [[UITabBarItem alloc] initWithTitle:@" " image:[UIImage imageNamed:@"Home-32.png"] tag:3];
    
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

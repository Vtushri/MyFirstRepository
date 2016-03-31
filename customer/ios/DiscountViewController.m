//
//  DiscountViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 01/12/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "DiscountViewController.h"

@interface DiscountViewController ()

@end

@implementation DiscountViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    self.view.backgroundColor = [UIColor whiteColor];
    UILabel*label = [[UILabel alloc] initWithFrame:CGRectMake(80, height/2, width/2, 30)];
    label.text = @"Page yet to build";
    label.textColor = [UIColor grayColor];
    label.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:label];
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

//
//  CurrentOrderViewController.m
//  example2
//
//  Created by Hariteja P on 03/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "CurrentOrderViewController.h"

@interface CurrentOrderViewController ()

@end

@implementation CurrentOrderViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    self.view.backgroundColor = [UIColor whiteColor];
    UILabel*label = [[UILabel alloc] initWithFrame:CGRectMake(10, height/2, width/2, 30)];
    label.text = @"Page yet to build";
    label.textColor = [UIColor grayColor];
    label.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:label];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}



@end

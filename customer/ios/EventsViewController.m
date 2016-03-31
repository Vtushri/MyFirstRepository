//
//  EventsViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 06/11/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "EventsViewController.h"

@interface EventsViewController ()

@end

@implementation EventsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    self.view.backgroundColor = [UIColor blueColor];
    UILabel*label = [[UILabel alloc] initWithFrame:CGRectMake(50, height/2, width/2, 30)];
    label.text = @"Page yet to build";
    label.textColor = [UIColor redColor];
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

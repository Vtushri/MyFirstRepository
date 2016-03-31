//
//  PromotionsViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 06/11/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "PromotionsViewController.h"
#import "MHTabBarController.h"
#import "EventsViewController.h"
#import "PromosViewController.h"
@interface PromotionsViewController ()

@end

@implementation PromotionsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    EventsViewController*pvc1 = [[EventsViewController alloc] init];
    PromosViewController*pvc2 = [[PromosViewController alloc] init];
    pvc1.title = @"Events";
    pvc2.title = @"Promos";
    NSArray *viewControllers = @[pvc1, pvc2];
    MHTabBarController *tabBarController = [[MHTabBarController alloc] init];
    
    tabBarController.delegate = self;
    tabBarController.viewControllers = viewControllers;
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    NSLog(@"%@ viewWillAppear", self.title);
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    NSLog(@"%@ viewDidAppear", self.title);
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    NSLog(@"%@ viewWillDisappear", self.title);
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    NSLog(@"%@ viewDidDisappear", self.title);
}

- (void)willMoveToParentViewController:(UIViewController *)parent
{
    [super willMoveToParentViewController:parent];
    NSLog(@"%@ willMoveToParentViewController %@", self.title, parent);
}

- (void)didMoveToParentViewController:(UIViewController *)parent
{
    [super didMoveToParentViewController:parent];
    NSLog(@"%@ didMoveToParentViewController %@", self.title, parent);
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

- (void)willRotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation duration:(NSTimeInterval)duration
{
    [super willRotateToInterfaceOrientation:toInterfaceOrientation duration:duration];
    NSLog(@"%@ willRotateToInterfaceOrientation", self.title);
}
- (BOOL)mh_tabBarController:(MHTabBarController *)tabBarController shouldSelectViewController:(UIViewController *)viewController atIndex:(NSUInteger)index
{
    NSLog(@"mh_tabBarController %@ shouldSelectViewController %@ at index %lu", tabBarController, viewController, (unsigned long)index);
    
    // Uncomment this to prevent "Tab 3" from being selected.
    //return (index != 2);
    
    return YES;
}

- (void)mh_tabBarController:(MHTabBarController *)tabBarController didSelectViewController:(UIViewController *)viewController atIndex:(NSUInteger)index
{
    NSLog(@"mh_tabBarController %@ didSelectViewController %@ at index %lu", tabBarController, viewController, (unsigned long)index);
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

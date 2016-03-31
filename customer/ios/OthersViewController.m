//
//  OthersViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 01/12/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "OthersViewController.h"
#import "OfferViewController.h"
#import "AboutUsViewController.h"
#import "PolicyViewController.h"
#import "PromotionsViewController.h"
#import "FeedBackVC.h"
#import "EventsViewController.h"
#import "LocationTable.h"
#import "DiscountViewController.h"
#import "SlideNavigationController.h"

#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]
@interface OthersViewController ()

@end

@implementation OthersViewController
@synthesize MoreTable,imageView,image,Moredata;
- (void)viewDidLoad {
    [super viewDidLoad];
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    self.MoreTable = [[UITableView alloc] initWithFrame:CGRectMake(0,0,width, height)];
    self.MoreTable.separatorColor = [UIColor lightGrayColor];
    MoreTable.delegate =self;
    MoreTable.dataSource=self;
    self.MoreTable.autoresizingMask = UIViewAutoresizingFlexibleWidth |
    UIViewAutoresizingFlexibleHeight;
    [self.MoreTable registerClass:[UITableViewCell class] forCellReuseIdentifier:@"leftMenuCell"];
    self.imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,width, height)];
    image = [UIImage imageNamed:@"bg.png"];
    self.imageView.image = image;
    self.MoreTable.backgroundView = self.imageView;
    Moredata = [NSArray arrayWithObjects:@"Know us more",@"Promotions",@"Feedback",@"Events",@"Offers",@"Stores",@"Policy", nil];
   
    [self.MoreTable reloadData];
    [self.view addSubview:self.MoreTable];
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
    
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
    return Moredata.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 50;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"leftMenuCell"];
   
    cell.textLabel.text = [self.Moredata objectAtIndex:indexPath.row];
    cell.textLabel.textColor =[UIColor brownColor];
    return cell;
    
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    EventsViewController*evc = [[EventsViewController alloc] init];
    PromotionsViewController*ovc = [[PromotionsViewController alloc] init];
    FeedBackVC*fvc = [[FeedBackVC alloc] init];
    DiscountViewController*dvc = [[DiscountViewController alloc] init];
    LocationTable*ltc = [[LocationTable alloc] init];
    PolicyViewController*pvc = [[PolicyViewController alloc] init];
    OfferViewController*Ofv = [[OfferViewController alloc] init];
     AboutUsViewController*avc = [Ofv.viewControllers objectAtIndex:1];
    
    switch (indexPath.row) {
        case 0:
           
            [[SlideNavigationController sharedInstance] pushViewController:Ofv animated:YES];
            break;
        case 1:
            [[SlideNavigationController sharedInstance] pushViewController:evc animated:YES];
            break;
        case 2:
            [[SlideNavigationController sharedInstance] pushViewController:ovc animated:YES];
            break;
        case 3:
            [[SlideNavigationController sharedInstance] pushViewController:fvc animated:YES];
            break;
        case 4:
            [[SlideNavigationController sharedInstance] pushViewController:dvc animated:YES];
            break;
        case 5:
            [[SlideNavigationController sharedInstance] pushViewController:ltc animated:YES];
            break;
        case 6:
            [[SlideNavigationController sharedInstance] pushViewController:pvc animated:YES];
            break;
        default:
            break;
    }



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

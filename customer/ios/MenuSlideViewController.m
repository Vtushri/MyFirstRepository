//
//  MenuSlideViewController.m
//  example2
//
//  Created by Hariteja P on 16/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "MenuSlideViewController.h"
#import "ViewController.h"
#import "CollectionViewController.h"
#import "cartView.h"
#import "productcategory.h"
#import "DescriptionViewController.h"
#import "SlideNavigationController.h"
#import "LocationsViewController.h"
#import "OfferViewController.h"
#import "CurrentOrderViewController.h"
#import "OrderHIstoryViewController.h"
#import "PolicyViewController.h"
#import "AboutUsViewController.h"
#import "FeedBackVC.h"
#import "LocationsViewController.h"
#import "LocationTable.h"
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]

#define  getMenu @"http://52.74.237.28:8080/ecart/rest/catrsrc/getsubcategory/CAT"

@interface MenuSlideViewController ()

@property (nonatomic,strong) NSMutableArray*images;

@end

@implementation MenuSlideViewController

@synthesize imageView,SlideTable,image,prodname,jsonretrieve,ID,images,secondtable;
- (id)initWithCoder:(NSCoder *)aDecoder
{
    self.slideOutAnimationEnabled = YES;
    
    return [super initWithCoder:aDecoder];
}

- (void)viewDidLoad {
    [super viewDidLoad];
  
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    self.SlideTable = [[UITableView alloc] initWithFrame:CGRectMake(0,0,width, height)];
    self.SlideTable.separatorColor = [UIColor lightGrayColor];
    SlideTable.delegate =self;
    SlideTable.dataSource=self;
    self.SlideTable.autoresizingMask = UIViewAutoresizingFlexibleWidth |
    UIViewAutoresizingFlexibleHeight;
    [self.SlideTable registerClass:[UITableViewCell class] forCellReuseIdentifier:@"leftMenuCell"];
    self.imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,width, height)];
    image = [UIImage imageNamed:@"bg.png"];
    self.imageView.image = image;
    self.SlideTable.backgroundView = self.imageView;
    jsonretrieve = [[NSMutableArray alloc]init];
    
   
    [self retrievedata3];
    [self.SlideTable reloadData];
    [self.view addSubview:self.SlideTable];
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
    
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
   return images.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 50;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"leftMenuCell"];
    List*pc = [images objectAtIndex:indexPath.row];
    cell.textLabel.text = pc.name;
    cell.textLabel.textColor =Rgb2UIColor(2,149,137);
     return cell;

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}
//- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
//{
//    NSString*title = [NSString stringWithFormat:@"Categories"];
//    return title;
//}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    List*object = [images objectAtIndex:indexPath.item];
        
        
    UICollectionViewFlowLayout *flowLayout = [[UICollectionViewFlowLayout alloc] init];
    
        [flowLayout setItemSize:CGSizeMake(182,227)];
        [flowLayout setMinimumInteritemSpacing:5];
        [flowLayout setSectionInset : UIEdgeInsetsMake(10,10,10,8)];
        [flowLayout setMinimumLineSpacing:20];
     
    CollectionViewController*vc = [[CollectionViewController alloc]initWithCollectionViewLayout:flowLayout];
    vc.ID = object.Id;
    
    
    [[SlideNavigationController sharedInstance] popToRootAndSwitchToViewController:vc
                                                             withSlideOutAnimation:self.slideOutAnimationEnabled
                                                                    andCompletion:nil];
   
}

-(void)retrievedata3{
    NSError*error;
    
    NSURL *url   = [NSURL URLWithString:getMenu];
    
    NSData *data = [NSData dataWithContentsOfURL:url];
    
    jsonretrieve = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    
   
    images = [[NSMutableArray alloc] init];
    for (int i=0; i<jsonretrieve.count;i++)
    {
        NSString * cID       = [[jsonretrieve objectAtIndex:i] objectForKey:@"id"];
        NSString * cName     = [[jsonretrieve objectAtIndex:i] objectForKey:@"name"];
        NSString * cLevel    = [[jsonretrieve objectAtIndex:i] objectForKey:@"level"];
        NSString * cParentID = [[jsonretrieve objectAtIndex:i] objectForKey:@"parentId"];
        NSString * cImageUrl = [[jsonretrieve objectAtIndex:i] objectForKey:@"imageUrl"];
        
        
        List *myList = [[List alloc]initWithId:(NSString *) cID andname: (NSString *) cName andlevel: (NSString *) cLevel andparentId:(NSString *) cParentID andimageUrl: (NSString *) cImageUrl];
        [images addObject:myList];
        
    }
    }



@end

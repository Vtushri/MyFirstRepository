//
//  ViewController.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
#import "CollectionViewController.h"
#import "loginViewController.h"
#import "List.h"
#import <MapKit/MapKit.h>
#import "MKNumberBadgeView.h"

@class TableViewCell;
@interface ViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,CLLocationManagerDelegate,UIAlertViewDelegate,UIActionSheetDelegate>
{
    UITableView*mytable;
    TableViewCell*tableViewCell;
    NSMutableArray*array1;
    NSMutableDictionary *totalDictionary;
}

@property(nonatomic, strong) CLLocationManager *locationManager;
//@property (nonatomic,strong) UIAlertView*alert;
@property (nonatomic,strong) NSMutableArray *itemsArray;
@property (nonatomic,strong) NSMutableArray *IDArray;
@property (nonatomic,strong) NSMutableArray *BannerArray;
@property (strong, nonatomic) IBOutlet UIButton *bannerbutton;
@property (nonatomic,strong) MKNumberBadgeView*cartnumber;
@property (nonatomic,strong) IBOutlet UITableView *mytable;
@property (nonatomic,strong) IBOutlet TableViewCell*tableViewCell;
@property (strong, nonatomic) IBOutlet UIPageControl *imageScroll;
@property (nonatomic,strong) NSMutableArray * imagesI;
@property (strong, nonatomic) IBOutlet UIScrollView *imagesscroll;
@property (strong,nonatomic) NSTimer*timer;
@property(nonatomic,strong) NSMutableArray*json;
@property (strong, nonatomic) IBOutlet UIImageView *DisplayImage;
@property (nonatomic,strong) NSMutableArray*json1;
@property (nonatomic,strong) NSMutableArray*json2;
@property (nonatomic,strong) NSMutableArray*catID;
@property (nonatomic,strong) NSString*userId;
@property (nonatomic,strong) IBOutlet UIImageView*imageView;
@property (nonatomic,strong) UIImage*image;

-(void)retrieveData;
-(void)retrieveData1;
-(void)retrieveData2;
- (IBAction)bannerbutton:(id)sender;


@end


//
//  MenuSlideViewController.h
//  example2
//
//  Created by Hariteja P on 16/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//



#import "MenuSlideViewController.h"
#import "SlideNavigationController.h"
#import "ViewController.h"

@interface MenuSlideViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,UICollectionViewDelegateFlowLayout>
@property (nonatomic,strong) IBOutlet UIImageView *imageView;
@property (nonatomic,strong) IBOutlet UITableView *SlideTable;
@property (nonatomic, assign) BOOL slideOutAnimationEnabled;
@property (nonatomic,strong) UIImage*image;
@property(nonatomic,assign)id delegate;
@property (nonatomic,strong) NSString*prodname;
@property(nonatomic,strong) NSString *ID;
@property (nonatomic,strong) NSMutableArray*jsonretrieve;
@property (nonatomic,strong) NSArray*secondtable;


-(void)retrievedata3;

@end

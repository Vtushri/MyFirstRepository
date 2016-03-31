//
//  OthersViewController.h
//  Cream_Stone
//
//  Created by Hariteja P on 01/12/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OthersViewController : UIViewController <UITableViewDataSource,UITableViewDelegate>
@property (strong, nonatomic) IBOutlet UITableView *MoreTable;
@property (nonatomic,strong) IBOutlet UIImageView*imageView;
@property (nonatomic,strong) UIImage*image;
@property (nonatomic,strong) NSArray*Moredata;

@end

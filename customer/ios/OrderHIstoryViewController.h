//
//  OrderHIstoryViewController.h
//  example2
//
//  Created by Hariteja P on 03/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OrderHIstoryViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *OrderTable;
@property (nonatomic,strong) NSMutableArray*json;
@property (nonatomic,strong) NSMutableArray*OrderArray;
-(void)jsondata;
@end

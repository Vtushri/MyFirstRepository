//
//  TableViewCell.h
//  example2
//
//  Created by Hariteja P on 28/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "DescriptionViewController.h"
@interface TableViewCell : UITableViewCell<UITableViewDataSource,UITableViewDelegate,UIPickerViewDataSource,UIPickerViewDelegate>
{
    UITableView *horizontalTableView;
   NSMutableArray*productArray;
    int selectedRow;
}


@property (strong, nonatomic) IBOutlet UITableView *horizontalTableView;
@property (nonatomic,strong) NSMutableArray*productArray;
@property (nonatomic,strong) UIAlertController*baseAlert;
@property (nonatomic,strong) UIImage*image;
@property (nonatomic,strong) NSString*Quantity;
@property (nonatomic,strong) NSString*prodId;
@property (nonatomic,strong) NSString*classstring;
- (NSString *) reuseIdentifier;

@end

//
//  OrderTableCell.h
//  Cream_Stone
//
//  Created by Hariteja P on 05/11/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "OrderHIstoryViewController.h"
@interface OrderTableCell : UITableViewCell
@property (strong, nonatomic) IBOutlet UILabel *Ordernumber;
@property (strong, nonatomic) IBOutlet UILabel *PlacedTime;
@property (strong, nonatomic) IBOutlet UILabel *DeliveryTime;
@property (strong, nonatomic) IBOutlet UILabel *Totalamt;
@property (strong, nonatomic) IBOutlet UILabel *PlacetimeDply;
@property (strong, nonatomic) IBOutlet UILabel *DeliverytmeDply;
@property (strong, nonatomic) IBOutlet UILabel *OrderType;
@property (nonatomic,assign)id delegate;
+(NSString *)reuseIdentifier;
@end

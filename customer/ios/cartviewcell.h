//
//  cartviewcell.h
//  Cream_Stone
//
//  Created by Hariteja P on 01/09/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "cartView.h"


@interface cartviewcell : UITableViewCell<UITextFieldDelegate>

@property (nonatomic)int quantity;
@property (nonatomic)int numb;
@property (nonatomic,assign)id delegate;
+(NSString *)reuseIdentifier;

@property (strong, nonatomic) IBOutlet UIImageView *cellimage;
@property (strong, nonatomic) IBOutlet UILabel *cellname;
@property (strong,nonatomic) NSString*num;
@property (strong, nonatomic) IBOutlet UILabel *cellprice1;

@property (strong, nonatomic) IBOutlet UILabel *qtylable;



@property (strong,nonatomic) NSArray*pickerData;

@property (strong, nonatomic) IBOutlet UILabel *pricechange;
@property (strong, nonatomic) IBOutlet UIButton *deletebtn;
@property (strong,nonatomic) IBOutlet UIButton*qtybtn;
@property (nonatomic,retain) UIImage*image;
@property (nonatomic,strong) NSString*url;

@property (nonatomic,strong) NSString*pricestrng;
@property (nonatomic,strong) NSDictionary*parcedData;
- (IBAction)deletecart:(id)sender;
-(void)updateQuantity;

@end


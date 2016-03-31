//
//  DescriptionViewController.h
//  example2
//
//  Created by Hariteja P on 07/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "loginViewController.h"
#import "productcategory.h"
#import "MKNumberBadgeView.h"
@interface DescriptionViewController : UIViewController<loginViewControllerDelegate>
@property (nonatomic,strong) IBOutlet UIImageView *cellImageView;
@property (nonatomic,strong) IBOutlet UILabel *cellimagename;
@property (nonatomic,strong) IBOutlet UILabel *price;
@property (nonatomic,strong) IBOutlet UITextView *producttext;
@property (nonatomic,strong) IBOutlet UILabel *LabelSpecify;
@property (nonatomic,strong) IBOutlet UIButton*addtoCart;
@property (nonatomic,strong) IBOutlet UIButton*Buynow;
@property (nonatomic,strong) IBOutlet UIButton*backbtn;
@property (strong, nonatomic) IBOutlet UIButton *ingredientsbtn;

- (IBAction)Ingredientbtn:(id)sender;


- (IBAction)buynow:(id)sender;
- (IBAction)Backbtn:(id)sender;

- (IBAction)backgroundtap:(id)sender;
- (IBAction)addtocart:(id)sender;
@property (nonatomic,strong) IBOutlet UILabel*specifyview;
@property (nonatomic,strong) UIImageView*cellphoto;
@property (nonatomic,strong) UIImage*img;
@property (nonatomic,strong) NSString*imageurl;
@property (nonatomic,strong) NSString*cellname;
@property (nonatomic,strong) NSString*productprice1;
@property (nonatomic,strong) NSString*productText;
@property (nonatomic,strong) NSString*prodId;
@property (nonatomic,strong) NSString*Quantity;
@property (nonatomic,strong) UIAlertController*baseAlert;
@property (nonatomic,strong) productcategory*product;
@property (nonatomic,strong) MKNumberBadgeView*cartnumber;
@property (nonatomic,strong)  UIView*Websoc;
@property (nonatomic,strong) UIDatePicker*datePicker;
@property (nonatomic,strong) IBOutlet UIImageView*imageView;
@property (nonatomic,strong) UIImage*Image;
@property (nonatomic,strong) NSString*ID;
-(void)addToCartproduct;
 @end

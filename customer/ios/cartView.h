//
//  cartView.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
@protocol cartViewDelegate;

#import <UIKit/UIKit.h>
#import "DBManager.h"
#import "cartviewcell.h"


//@required
//- (Order *)createorder;
//@end

@interface cartView :UIViewController<UIPopoverControllerDelegate,UITableViewDataSource,UITableViewDelegate,UIPickerViewDataSource,UIPickerViewDelegate,UITextFieldDelegate>{
    NSIndexPath*selectedIndexPath;
    double totalAmountOfOrder;
   
}
@property (strong, nonatomic) IBOutlet UITableView *carttable;
//@property (strong, nonatomic) IBOutlet UIButton *clearcart;
@property (strong, nonatomic) IBOutlet UIButton *checkOut;
@property (strong,nonatomic) IBOutlet UIToolbar *toolbar;
@property (strong,nonatomic) IBOutlet UIToolbar *toolbar2;
@property (nonatomic,strong) UIImage*img;
@property (nonatomic,strong) IBOutlet UILabel*totalAmtLabel;
@property (nonatomic,strong) IBOutlet UILabel*totalPrice;
@property (nonatomic,unsafe_unretained) id<cartViewDelegate> delegate;
@property (nonatomic,strong) IBOutlet UIImageView *imageView;
@property (nonatomic,strong) UIImage*Image;

@property (nonatomic,strong) NSString*namestrng;
@property (nonatomic,strong) NSString*pricestrng;
@property (nonatomic,strong) NSString*idStrng;
@property (nonatomic,strong) NSString*productid;
@property (nonatomic,strong) NSString*quantity;
@property (nonatomic,strong) NSMutableArray*json;
@property (nonatomic,strong) NSMutableArray*carttray;
@property (nonatomic,strong) NSDictionary*json2;

-(void)getcartdetails;
- (Order *)createorder;
//- (IBAction)clearcart:(id)sender;
- (IBAction)checkout:(id)sender;
-(void)createOrder;

-(NSMutableArray *)product;
@end
@protocol cartViewDelegate

-(void)order:(cartView *)sender amtValue:(double)value andProducts:(NSMutableArray *)products;

@end
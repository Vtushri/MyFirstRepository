//
//  cartView.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "cartView.h"
#import "DescriptionViewController.h"
#import "ViewController.h"
#import "LoginObject.h"
#import "GetCartdetails.h"
#import "cartviewcell.h"
#import "cartView.h"
#import "SlideNavigationController.h"
#import "KLCPopup.h"
#import "LoginObject.h"
#import "UIImageView+WebCache.h"
#import "LocationTable.h"
#import "RegisterViewController.h"
#import "Order.h"
#import "orderProduct.h"
#import "ConvertUtility.h"
#import "LocationsViewController.h"
#define getcart @"http://52.74.237.28:8080/ecart/rest/cartrsrc/cartbyuserid"
#define clearthecart @"http://52.74.237.28:8080/ecart/rest/cartrsrc/removeallcart"
#define websocket1 @"ws://52.74.237.28:8080/ecart/notify"
#define updatecart @"http://52.74.237.28:8080/ecart/rest/cartrsrc/updatecart"
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]

@interface cartView (){
    NSString*number	;
    NSInteger numrow;
    KLCPopup*popup;
    Order*order;
}
@property(nonatomic,strong)NSMutableArray*items;
@property (nonatomic,strong)NSMutableArray*pickerData;
@property (nonatomic,strong) UIPickerView*PickerView;
@property (nonatomic,strong) UIButton *backgroundTapButton;
@end

@implementation cartView
@synthesize img,namestrng,pricestrng,idStrng,json,carttray,carttable,items,checkOut,json2,pickerData,quantity,PickerView,toolbar,totalAmtLabel,totalPrice,delegate,imageView,Image,toolbar2;

static NSString*identifier = @"Cell";
//-(void)viewWillAppear:(BOOL)animated{
//    
//    [self getcartdetails];
//    totalAmountOfOrder = 0;
//    [self.carttable reloadData];
//    
//}
-(void)getOfflineData
{
    
    carttray = [[NSMutableArray alloc] init];
    self.json = [[DBManager sharedAppManager] getSavedDataFromOfflineDB];
    NSLog(@"self.json:%@",self.json);
    for(int i=0;i<self.json.count;i++){
        NSString*cC_p_id = nil;
        NSString*cCart = nil;
        productcategory*cProduct = [[productcategory alloc] init];
        [cProduct initWithDictionary:[self.json objectAtIndex:i]];
        NSNumber*cQuantity = [NSNumber numberWithInt:1];
        GetCartdetails*cart = [[GetCartdetails alloc] initWithc_p_id:(NSString *)cC_p_id andcart:(NSString *)cCart andproduct:(productcategory *)cProduct andquantity:(NSNumber *)cQuantity];
        
        [carttray addObject:cart];
        
    }
    
}

- (void)viewDidLoad {
    [super viewDidLoad];
  
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    
    Image = [UIImage imageNamed:@"bg.png"];
    self.imageView = [[UIImageView alloc] initWithImage:Image];
    self.imageView.image = Image;
    self.imageView.userInteractionEnabled = YES;
    
    self.carttable = [[UITableView alloc] init];
    self.carttable.rowHeight = 135;
    [self.carttable registerClass:[cartviewcell class] forCellReuseIdentifier:@"Cell"];
    self.carttable.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"bg.png"]];
    
//    self.clearcart = [[UIButton alloc] initWithFrame:CGRectMake(42,height-35,114,30)];
//    [self.clearcart setBackgroundColor:Rgb2UIColor(46, 139, 87)];
//    [self.clearcart setTitle:@"ClearCart" forState:UIControlStateNormal];
//    [self.clearcart addTarget:self action:@selector(clearcart:) forControlEvents:UIControlEventTouchUpInside];
  
      self.checkOut = [[UIButton alloc] initWithFrame:CGRectMake(0,	)];
      [self.checkOut setBackgroundColor:[UIColor whiteColor]];
      [self.checkOut setTitle:@"Check Out" forState:UIControlStateNormal];
    [self.checkOut addTarget:self action:@selector(checkout:) forControlEvents:UIControlEventTouchUpInside];
   
    self.totalAmtLabel = [[UILabel alloc] initWithFrame:CGRectMake(self.checkOut.frame.size.width,height-30,110,30)];
    [self.totalAmtLabel setText:@"Total Amount:"];
    [self.totalAmtLabel setTextAlignment:NSTextAlignmentRight];
    [self.totalAmtLabel setFont:[UIFont fontWithName:@"Arial" size:12]];
    [self.totalAmtLabel setTextAlignment:NSTextAlignmentLeft];
   
    self.totalPrice = [[UILabel alloc] initWithFrame:CGRectMake(self.checkOut.frame.size.width,height-35, 80, 30)];
    [self.totalPrice setText:@" "];
    [self.totalPrice setFont:[UIFont fontWithName:@"Times New Roman" size:12]];
    [self.totalPrice setTextColor:Rgb2UIColor(46, 139, 87)];
   
    self.toolbar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, height-70, width,60)];
    [self.toolbar setBackgroundColor:[UIColor clearColor]];
    
    
    [self.view addSubview:self.carttable];
    [self.view addSubview:self.toolbar];
   
    [self.view addSubview:self.checkOut];
    [self.view addSubview:self.totalAmtLabel];
    [self.view addSubview:self.totalPrice];
   // [self.view addSubview:self.imageView];
    self.carttable.delegate =self;
    self.carttable.dataSource =self;
    
//    [self.carttable reloadData];
   
}

- (void)showAnimate
{
    self.view.transform = CGAffineTransformMakeScale(1.3, 1.3);
    self.view.alpha = 0;
    [UIView animateWithDuration:.25 animations:^{
        self.view.alpha = 1;
        self.view.transform = CGAffineTransformMakeScale(1, 1);
    }];
}

- (void)removeAnimate
{
    [UIView animateWithDuration:.25 animations:^{
        self.view.transform = CGAffineTransformMakeScale(1.3, 1.3);
        self.view.alpha = 0.0;
    } completion:^(BOOL finished) {
        if (finished) {
            [self.view removeFromSuperview];
        }
    }];
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
    }
    

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
   
    return [carttray count];
}


-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
   cartviewcell*cell =[tableView dequeueReusableCellWithIdentifier:identifier];
   
   if(cell==nil)
   {
      cell = [[cartviewcell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
   }
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"cartviewcell" owner:self options:nil];

    cell.delegate =self;
    cell = [nib objectAtIndex:0];
   
       for (id oneObject in nib)
        if ([oneObject isKindOfClass:[cartviewcell class]])
            cell = (cartviewcell *)oneObject;
 
    GetCartdetails*gcd = [carttray objectAtIndex:indexPath.row];
    productcategory*productdts = gcd.product;
        
    NSString*userid =[[LoginObject getInstance] login];
   
    if(userid==NULL){
      
        NSString*image = productdts.imagesUrl;
        
        NSCharacterSet*set = [NSCharacterSet URLQueryAllowedCharacterSet];
       [cell.cellimage sd_setImageWithURL:[NSURL URLWithString:[image stringByAddingPercentEncodingWithAllowedCharacters:set]]];
       
        NSString*prdname = productdts.productName;
        cell.cellname.text = prdname;
        NSString*prdprice = productdts.productPrice1;
         NSString*prdprice1 = [prdprice stringByReplacingOccurrencesOfString:@"Price:₹" withString:@""];
        //int quantityItem = 1;
         int quantityItem = [gcd.quantity integerValue] ? [gcd.quantity intValue]: 1;
        float price1 = (float)[prdprice1 floatValue]*quantityItem;

        totalAmountOfOrder = price1+ totalAmountOfOrder;
        cell.cellprice1.text = [NSString stringWithFormat:@"Price:₹ %.02f",[prdprice1 floatValue]];
        cell.pricechange.text =[NSString stringWithFormat:@"Price:₹ %.02f",price1];
       [cell.qtybtn setTitle:[NSString stringWithFormat:@"%d",quantityItem] forState:UIControlStateNormal];

        cell.pricestrng = prdprice1;
        [cell.deletebtn addTarget:self action:@selector(deletecart:) forControlEvents:UIControlEventTouchUpInside];
        cell.deletebtn.tag = [indexPath row];
        [cell.qtybtn addTarget:self action:@selector(increaseQuantity:) forControlEvents:UIControlEventTouchUpInside];
        cell.qtybtn.tag = [indexPath row];
        [self.totalPrice setText:[NSString stringWithFormat:@"Rs.%.02f",totalAmountOfOrder]];
    }
    else{
        NSString*image = productdts.imagesUrl;
        
        NSCharacterSet*set = [NSCharacterSet URLQueryAllowedCharacterSet];
        [cell.cellimage sd_setImageWithURL:[NSURL URLWithString:[image stringByAddingPercentEncodingWithAllowedCharacters:set]]];

        NSString*prdname = productdts.productName;
        
        cell.cellname.text = prdname;
         int quantityItem = [gcd.quantity integerValue] ? [gcd.quantity intValue]: 1;
        NSString*prdprice = productdts.productPrice1;
        
        float price1 = (float)[prdprice floatValue]*quantityItem;
        
        totalAmountOfOrder = price1+ totalAmountOfOrder;
        
        cell.cellprice1.text = [NSString stringWithFormat:@"Price:₹ %.02f",[prdprice floatValue]];
        cell.pricechange.text =[NSString stringWithFormat:@"Price:₹ %.02f",price1];
        [self.totalPrice setText:[NSString stringWithFormat:@"Price:₹ %.02f",price1]];
        cell.pricestrng = prdprice;
        [cell.deletebtn addTarget:self action:@selector(deletecart:) forControlEvents:UIControlEventTouchUpInside];
        
        cell.deletebtn.tag = [indexPath row];
        [cell.qtybtn addTarget:self action:@selector(increaseQuantity:) forControlEvents:UIControlEventTouchUpInside];
        cell.qtybtn.tag = [indexPath row];
        [cell.qtybtn setTitle:[NSString stringWithFormat:@"%d",quantityItem] forState:UIControlStateNormal];
          [self.totalPrice setText:[NSString stringWithFormat:@"Rs.%.02f",totalAmountOfOrder]];
       }
        return cell;
  
}
-(NSString*)tableView:(UITableView *)tableView titleForFooterInSection:(NSInteger)section   {
    

    [[UILabel appearanceWhenContainedInInstancesOfClasses:@[[UITableViewHeaderFooterView class]]] setTextAlignment:NSTextAlignmentCenter];
    NSString *message = @" ";
   
    NSInteger numberOfRowsInSection = [self tableView:self.carttable numberOfRowsInSection:section];
    
    if (numberOfRowsInSection == 0) {
        message = @"No Products in the Cart !!!";
        [self.totalPrice setText:[NSString stringWithFormat:@"Rs.0"]];
    }
    
    return message;
}
-(void)getcartdetails{
    NSError*error;
    NSString*userid =[[LoginObject getInstance] login];
    if(userid==NULL){
        [self getOfflineData];
    }
    else{
       
        NSURL*url = [NSURL URLWithString:getcart];
        NSURL*url1 = [url URLByAppendingPathComponent:userid];
        NSData*data = [NSData dataWithContentsOfURL:url1];
        
        json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
        carttray = [[NSMutableArray alloc] init];
        for(int i=0;i<json.count;i++){
            NSString*cC_p_id = [[json objectAtIndex:i]valueForKey:@"c_p_id"];
            NSString*cCart = [[json objectAtIndex:i]valueForKey:@"cart"];
            NSNumber*cQuantity = [[json objectAtIndex:i] valueForKey:@"quantity"];
            productcategory*cProduct = [[productcategory alloc] init];
            [cProduct initWithDictionary:[[json objectAtIndex:i] valueForKey:@"product"]];
            GetCartdetails*cart = [[GetCartdetails alloc] initWithc_p_id:(NSString *)cC_p_id andcart:(NSString *)cCart andproduct:(productcategory *)cProduct andquantity:(NSNumber *)cQuantity];
            
            [carttray addObject:cart];
            
        }
    }
    
}

//- (IBAction)clearcart:(id)sender {
//     NSString*userid =[[LoginObject getInstance] login];
//    if(userid==NULL){
//        
//        [self clearCartFromLocalDb];
//        [self.clearcart setEnabled:NO];
//        [self.checkOut setEnabled:NO];
//        [self.totalPrice setText:[NSString stringWithFormat:@"Rs.0"]];
//         }
//    
//    else{
//        [self clearCartFromServer];
//        [self.clearcart setEnabled:NO];
//        [self.checkOut setEnabled:NO];
//        [self.totalPrice setText:[NSString stringWithFormat:@"Rs.0"]];
//    }
//}


-(void)clearCartFromLocalDb
{
    
    [[DBManager sharedAppManager] clearAllOfflineData];
    [self getOfflineData];
    [self.carttable reloadData];
}

-(void)clearCartFromServer
{
    NSError*error;
    NSString*userid =[[LoginObject getInstance] login];
    NSURL*url = [NSURL URLWithString:clearthecart];
    NSURL*url1 = [url URLByAppendingPathComponent:userid];
    NSData*data = [NSData dataWithContentsOfURL:url1];
    json2 = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    NSString * converted =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    NSLog(@"Data =%@",converted);
    
    if(converted!=NULL && converted) {
        [self.carttray removeAllObjects];
        [self.carttable reloadData];
    }
}

- (IBAction)checkout:(id)sender {
    
    [self createOrder];
    
    NSString*userid =[[LoginObject getInstance] login];
    if(userid){
        float width = [UIScreen mainScreen].bounds.size.width;
        float height = [UIScreen mainScreen].bounds.size.height;
        
        UIView*Websoc = [[UIView alloc] initWithFrame:CGRectMake(10,50,width-20,height-100)];
        Websoc.backgroundColor = [UIColor whiteColor];
        Websoc.layer.cornerRadius = 12.0;
        Websoc.layer.masksToBounds = YES;
        Websoc.layer.borderColor=Rgb2UIColor(255,105,180).CGColor;
        
        UILabel* yourorder = [[UILabel alloc] initWithFrame:CGRectMake(0,0,Websoc.frame.size.width,40)];
        yourorder.backgroundColor = Rgb2UIColor(102,205,170);
        yourorder.textColor = [UIColor whiteColor];
        yourorder.font = [UIFont boldSystemFontOfSize:15.0];
        yourorder.text = @"Your Order";
        yourorder.textAlignment = NSTextAlignmentCenter;
        [Websoc addSubview:yourorder];
        
        UIScrollView *backScrollView = [[UIScrollView alloc] init];
        [backScrollView setFrame:CGRectMake(0, 40, Websoc.frame.size.width, Websoc.frame.size.height-120)];
        [backScrollView setBackgroundColor:[UIColor clearColor]];
        [Websoc addSubview:backScrollView];
        float yaxis = 0;
        int totalOrderPrice = 0;
        for(int i=0; i<[carttray count]+1; i++)
        {
            UIView*proview = [[UIView alloc] initWithFrame:CGRectMake(0,yaxis,Websoc.frame.size.width,50)];
            proview.backgroundColor = [UIColor whiteColor];
            [backScrollView addSubview:proview];
            
            UILabel*name = [[UILabel alloc] initWithFrame:CGRectMake(10,0,(Websoc.frame.size.width/3)-20,50)];
            name.backgroundColor = [UIColor clearColor];
            name.font = [UIFont fontWithName:@"Arial" size:13];
            name.textAlignment = NSTextAlignmentLeft;
            [name setNumberOfLines:2];
            [proview addSubview:name];
            
            UILabel*quantityLabel = [[UILabel alloc] initWithFrame:CGRectMake(name.frame.origin.x+name.frame.size.width,0,Websoc.frame.size.width/3,50)];
            quantityLabel.backgroundColor = [UIColor clearColor];
            quantityLabel.font = [UIFont fontWithName:@"Arial" size:13];
            quantityLabel.textAlignment = NSTextAlignmentCenter;
            [proview addSubview:quantityLabel];
            
            UILabel*priceLabel = [[UILabel alloc] initWithFrame:CGRectMake(quantityLabel.frame.origin.x+quantityLabel.frame.size.width,0,Websoc.frame.size.width/3,50)];
            priceLabel.backgroundColor = [UIColor clearColor];
            priceLabel.font = [UIFont fontWithName:@"Arial" size:13];
            priceLabel.textAlignment = NSTextAlignmentCenter;
            [proview addSubview:priceLabel];
            if(i == 0)
            {
                name.font = [UIFont boldSystemFontOfSize:15.0];
                quantityLabel.font = [UIFont boldSystemFontOfSize:15.0];
                priceLabel.font = [UIFont boldSystemFontOfSize:15.0];
                
                name.text = @"Name";
                quantityLabel.text = @"Qty";
                priceLabel.text = @"Price";
            }
            else{
                
                NSIndexPath *indexPath  = [NSIndexPath indexPathForRow:i-1 inSection:0];
                cartviewcell *cell = (cartviewcell *)[self.carttable cellForRowAtIndexPath:indexPath];
                
                name.text = cell.cellname.text;
                quantityLabel.text = cell.qtybtn.titleLabel.text;
                
                float totalValue = [cell.pricestrng floatValue]*[quantityLabel.text intValue];
                priceLabel.text =[NSString stringWithFormat:@"%0.2f",totalValue];
                
                totalOrderPrice = totalOrderPrice + totalValue;
                
            }
            yaxis = yaxis+ 40;
        }
        UILabel*totalAmountLabel = [[UILabel alloc] initWithFrame:CGRectMake(Websoc.frame.size.width-200,Websoc.frame.size.height-40-50,100,50)];
        totalAmountLabel.backgroundColor = [UIColor clearColor];
        totalAmountLabel.font = [UIFont fontWithName:@"Arial" size:13];
        totalAmountLabel.textAlignment = NSTextAlignmentLeft;
        [totalAmountLabel setText:@"Total amount:"];
        [Websoc addSubview:totalAmountLabel];
        totalAmountLabel.font = [UIFont boldSystemFontOfSize:15.0];
        
        UILabel*totalPricelbl = [[UILabel alloc] initWithFrame:CGRectMake(totalAmountLabel.frame.size.width+totalAmountLabel.frame.origin.x+10,Websoc.frame.size.height-40-50,100,50)];
        totalPricelbl.backgroundColor = [UIColor clearColor];
        totalPricelbl.font = [UIFont fontWithName:@"Arial" size:13];
        totalPricelbl.textAlignment = NSTextAlignmentLeft;
        [totalPricelbl setText:[NSString stringWithFormat:@"Rs.%d",totalOrderPrice]];
        [Websoc addSubview:totalPricelbl];
        totalPricelbl.font = [UIFont boldSystemFontOfSize:15.0];
        
        
        UIButton*confirm = [UIButton buttonWithType:UIButtonTypeCustom];
        confirm.frame =CGRectMake(30,Websoc.frame.size.height-40,width-60,30);
        confirm.backgroundColor = Rgb2UIColor(102,205,170);
        [confirm setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [confirm setTitleColor:[[confirm titleColorForState:UIControlStateNormal] colorWithAlphaComponent:0.5] forState:UIControlStateHighlighted];
        confirm.titleLabel.font = [UIFont boldSystemFontOfSize:14.0];
        
        [confirm setTitle:@"CONFIRM" forState:UIControlStateNormal];
        confirm.layer.cornerRadius = 6.0;
        [confirm addTarget:self action:@selector(confirm:) forControlEvents:UIControlEventTouchUpInside];
        [Websoc addSubview:confirm];
        popup = [KLCPopup popupWithContentView:Websoc];
        [popup show];
        
    }
    else{
        float width = [UIScreen mainScreen].bounds.size.width;
        float height = [UIScreen mainScreen].bounds.size.height;
        
        UIView*Websoc = [[UIView alloc] initWithFrame:CGRectMake(80,50,width-80,height-300)];
        Websoc.backgroundColor = [UIColor whiteColor];
        Websoc.layer.cornerRadius = 12.0;
        Websoc.layer.masksToBounds = YES;
        Websoc.layer.borderColor=Rgb2UIColor(255,105,180).CGColor;
        
        UILabel*plslbl = [[UILabel alloc] initWithFrame:CGRectMake(30,Websoc.frame.size.height-320,width-150,30)];
        plslbl.text = @"Please Login";
        plslbl.backgroundColor = [UIColor clearColor];
        plslbl.font = [UIFont fontWithName:@"Arial Bold" size:16.0];
        plslbl.textColor =Rgb2UIColor(102,205,170);
        plslbl.textAlignment = NSTextAlignmentCenter;
        
        UIView * separator = [[UIView alloc] initWithFrame:CGRectMake(30,Websoc.frame.size.height-270,width-150,1)];
        separator.backgroundColor = [UIColor colorWithWhite:0.3 alpha:1];
        UIButton*Login = [UIButton buttonWithType:UIButtonTypeCustom];
        Login.frame =CGRectMake(30,Websoc.frame.size.height-250,width-150,50);
        Login.backgroundColor = Rgb2UIColor(102,205,170);
        [Login setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [Login setTitleColor:[[Login titleColorForState:UIControlStateNormal] colorWithAlphaComponent:0.5] forState:UIControlStateHighlighted];
        Login.titleLabel.font = [UIFont boldSystemFontOfSize:14.0];
        
        [Login setTitle:@"LOGIN" forState:UIControlStateNormal];
        Login.layer.cornerRadius = 6.0;
        [Login addTarget:self action:@selector(loginclicked:) forControlEvents:UIControlEventTouchUpInside];
        
        UILabel*orlabel = [[UILabel alloc] initWithFrame:CGRectMake(30,Websoc.frame.size.height-180,width-150,30)];
        orlabel.backgroundColor = [UIColor clearColor];
        orlabel.font = [UIFont fontWithName:@"Arial" size:15];
        orlabel.textAlignment = NSTextAlignmentCenter;
        orlabel.text = @"(OR)";
        
        
        UIButton*GuestAct = [UIButton buttonWithType:UIButtonTypeCustom];
        GuestAct.frame =CGRectMake(30,Websoc.frame.size.height-120,width-150,50);
        GuestAct.backgroundColor = Rgb2UIColor(102,205,170);
        [GuestAct setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [GuestAct setTitleColor:[[GuestAct titleColorForState:UIControlStateNormal] colorWithAlphaComponent:0.5] forState:UIControlStateHighlighted];
        GuestAct.titleLabel.font = [UIFont boldSystemFontOfSize:14.0];
        
        [GuestAct setTitle:@"GUEST" forState:UIControlStateNormal];
        GuestAct.layer.cornerRadius = 6.0;
        [GuestAct addTarget:self action:@selector(guestActclked:) forControlEvents:UIControlEventTouchUpInside];
        [Websoc addSubview:Login];
        [Websoc addSubview:GuestAct];
        [Websoc addSubview:orlabel];
        [Websoc addSubview:plslbl];
        
        [Websoc addSubview:separator];
        popup = [KLCPopup popupWithContentView:Websoc];
        
        [popup show];
    }
 
}

-(void)createOrder{
    order = [[Order alloc] init];
    order.totalAmount = *(&(totalAmountOfOrder));
    NSMutableArray*products = [[NSMutableArray alloc] init];
    for(int i=0;i<[carttray count];i++){
        orderProduct*op = [[orderProduct alloc] init];
        op.product = [[carttray objectAtIndex:i] valueForKey:@"product"];
        int quant = [[[carttray objectAtIndex:i] valueForKey:@"quantity"] intValue];
        op.quantity =quant;
        [products addObject:op];
    }
    order.products = products;
    NSLog(@"order:%@",order);
    [self.delegate order:self amtValue: *(&(totalAmountOfOrder)) andProducts:products];
    LoginObject*lo = [LoginObject getInstance];
    lo.totalAmt = *(&(totalAmountOfOrder));
    lo.pro = products;
}

-(NSMutableArray *)product
{
    NSMutableArray*products = [[NSMutableArray alloc] init];
    for(int i=0;i<[carttray count];i++){
        orderProduct*op = [[orderProduct alloc] init];
        op.product = [[carttray objectAtIndex:i] valueForKey:@"product"];
        int quant = [[[carttray objectAtIndex:i] valueForKey:@"quantity"] intValue];
        op.quantity =quant;
        [products addObject:op];
    }
    order.products = products;
    return products;
}
-(Order*)createorder{
    Order*ordr = [[Order alloc] init];
    ordr.totalAmount = *(&(totalAmountOfOrder));
    NSMutableArray*products = [[NSMutableArray alloc] init];
    for(int i=0;i<[carttray count];i++){
        orderProduct*op = [[orderProduct alloc] init];
        op.product = [[carttray objectAtIndex:i] valueForKey:@"product"];
        int quant = [[[carttray objectAtIndex:i] valueForKey:@"quantity"] intValue];
        op.quantity =quant;
        [products addObject:op];
    }
    ordr.products = products;
    return ordr;
}

-(IBAction)increaseQuantity:(id)sender{
    
    UIView*picker = [[UIView alloc] initWithFrame:CGRectMake(10,100,220,280)];
    picker.backgroundColor = [UIColor whiteColor];
    picker.translatesAutoresizingMaskIntoConstraints = NO;
    picker.layer.cornerRadius = 12.0;
    picker.layer.borderColor=Rgb2UIColor(255,105,180).CGColor;
    
    UIBezierPath *shadowPath = [UIBezierPath bezierPathWithRect:picker.bounds];
    picker.layer.masksToBounds = NO;
    picker.layer.shadowColor = [UIColor blackColor].CGColor;
    picker.layer.shadowOffset = CGSizeMake(0.0f, 5.0f);
    picker.layer.shadowOpacity = 0.5f;
    picker.layer.shadowPath = shadowPath.CGPath;
    
    popup = [KLCPopup popupWithContentView:picker];
    UILabel* choose = [[UILabel alloc] initWithFrame:CGRectMake(0,0,220,40)];
    choose.backgroundColor = Rgb2UIColor(205, 201, 201);
    choose.textColor = [UIColor blackColor];
    choose.font = [UIFont boldSystemFontOfSize:15.0];
    choose.text = @"Choose Quantity";
    choose.textAlignment = NSTextAlignmentJustified;
    [picker addSubview:choose];
    
    UIPickerView*integers = [[UIPickerView alloc] initWithFrame:CGRectMake(60,50,80,50)];
    integers.dataSource = self;
    integers.delegate =self;
    integers.showsSelectionIndicator =YES;
    
    UIButton*ok = [UIButton buttonWithType:UIButtonTypeCustom];
    ok.frame =CGRectMake(70,230,80,30);
    [ok setTag:((UIButton*)sender).tag];
    [ok setTitle:@"OK" forState:UIControlStateNormal];
[ok addTarget:self action:@selector(Okbtnclicked:) forControlEvents:UIControlEventTouchUpInside];
    [ok setBackgroundColor:[UIColor grayColor]];
    [picker addSubview:integers];
    [picker addSubview:ok];

    pickerData = [[NSMutableArray alloc] init];
    for (int i=1; i<=500; i++){
        [pickerData addObject:[NSString stringWithFormat:@"%d",i]];
    }
        [popup show];
}
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)thePickerView {
    
    return 1;
}
-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    
    return [pickerData count];
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    return [pickerData objectAtIndex:row];
}
- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component{
    
    number = [pickerData objectAtIndex:row];
     NSLog(@"number:%@",number);
    }

-(IBAction)Okbtnclicked:(id)sender{
    
    NSIndexPath*indexpath = [NSIndexPath indexPathForRow:((UIButton *)sender).tag inSection:0];
    
    cartviewcell *cell = (cartviewcell *)[self.carttable cellForRowAtIndexPath:indexpath];
    int num = [number intValue];
    NSLog(@"num:%d",num);
    totalAmountOfOrder = totalAmountOfOrder - ([cell.pricestrng floatValue]*[[cell.qtybtn.titleLabel text] floatValue]);
    float totalValue = [cell.pricestrng floatValue]*num;
    totalAmountOfOrder = totalAmountOfOrder + totalValue;
    cell.pricechange.text =[NSString stringWithFormat:@"Price:₹ %.02f",totalValue];
    [cell.qtybtn setTitle:[NSString stringWithFormat:@"%d",num] forState:UIControlStateNormal];
    [popup dismiss:YES];
    [self.totalPrice setText:[NSString stringWithFormat:@"Rs.%.02f",totalAmountOfOrder]];
    [self updateQuantityItemAtIndex:((cartviewcell *)sender).tag withQuantity:num];
}


-(void)updateQuantityItemAtIndex:(NSInteger)index withQuantity:(int)itemQuantity
{
     NSString*userid =[[LoginObject getInstance] login];
    if(userid==NULL){

        NSMutableDictionary *dict = (NSMutableDictionary*)[self.json objectAtIndex:index];
        [dict setObject:[NSString stringWithFormat:@"%d",itemQuantity] forKey:@"quantity"];
        [self.json replaceObjectAtIndex:index withObject:dict];
        [[DBManager sharedAppManager] deleteAndSaveOfflineProduct:self.json];
        
    }
    else{
        
        [self updateProductWithId:[self.carttray objectAtIndex:index] withQuantity:itemQuantity];
    }
}

-(void)confirm:(id)sender{
    
    UIView *Websoc = [[[[UIApplication sharedApplication] keyWindow] subviews] lastObject];
    [Websoc removeFromSuperview];
    UIActivityIndicatorView *spinner = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    spinner.center = CGPointMake(160, 240);
    spinner.hidesWhenStopped = YES;
    [self.view addSubview:spinner];
    [spinner startAnimating];
    
    
    dispatch_queue_t downloadQueue = dispatch_queue_create("downloader", NULL);
    dispatch_async(downloadQueue, ^{
        
        [NSThread sleepForTimeInterval:5];
        
        
        dispatch_async(dispatch_get_main_queue(), ^{
            [spinner stopAnimating];
        });
        
    });
    
    LocationTable  *ltc = [[LocationTable alloc] init];
    NSString*string = NSStringFromClass([ltc class]);
    ltc.classstrng = string;
    ltc.order = order;
    [self.navigationController pushViewController:ltc animated:YES];

    
}
-(IBAction)deletecart:(UIButton *)button{
    
    NSString*userid =[[LoginObject getInstance] login];
    NSLog(@"userId:%@",userid);
    if(userid==NULL){
        
        selectedIndexPath = [NSIndexPath indexPathForRow:button.tag inSection:0];
        [self.json removeObjectAtIndex:button.tag];
        [[DBManager sharedAppManager] deleteAndSaveOfflineProduct:self.json];
        [self.carttray removeObjectAtIndex:button.tag];
        [self removeTableArrayWithAnimation];
        
        
    }
    else{
        
        selectedIndexPath = [NSIndexPath indexPathForRow:button.tag inSection:0];
        [self updateProductWithId:[self.carttray objectAtIndex:button.tag] withQuantity:0];
    }
    
    
}
-(void)updateProductWithId:(GetCartdetails*)selectedProduct withQuantity:(int)quantityItem
{
    NSString*userid =[[LoginObject getInstance] login];
    NSMutableDictionary*post = [[NSMutableDictionary alloc] initWithCapacity:3];
    int value = quantityItem;
    NSString *prodId = [selectedProduct.product productId];
    NSLog(@"quantity:%d",value);
    NSLog(@"productid:%@",prodId);
    [post setValue:prodId forKey:@"productId"];
    [post setValue:userid forKey:@"userAccountId"];
    [post setValue:[NSNumber numberWithInt:value] forKey:@"quantity"];
    NSLog(@"Post:%@",post);
    NSError *serr;
    NSData *jsonData = [NSJSONSerialization
                        dataWithJSONObject:post options:NSJSONWritingPrettyPrinted error:&serr];
    NSLog(@"json:%@",jsonData);
    if (serr)
    {
        NSLog(@"Error generating json data for send dictionary...");
        NSLog(@"Error (%@), error: %@", post, serr);
        return;
    }
    
    NSLog(@"Successfully generated JSON for send dictionary");
    
    NSURL*url = [NSURL URLWithString:updatecart];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    // Set method, body & content-type
    request.HTTPMethod = @"POST";
    request.HTTPBody = jsonData;
    [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request setValue:@"application/json" forHTTPHeaderField:@"Accept"];
    
    [request setValue:
     [NSString stringWithFormat:@"%lu",
      (unsigned long)[jsonData length]] forHTTPHeaderField:@"Content-Length"];
    
    NSURLSession*session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask=[session dataTaskWithRequest:request completionHandler:^(NSData *data,
                                                                                    NSURLResponse *response,
                                                                                            NSError *error)
     
     {
         
         NSLog(@"Data:%@",data);
         NSString*result= [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
         NSLog(@"Response:%@",result);
         
         dispatch_async(dispatch_get_main_queue(), ^{
             
             if (quantityItem == 0) {
                 [self.carttable beginUpdates];
                 [self.carttray removeObject:selectedProduct];
                 [self removeTableArrayWithAnimation];
             }
             
         });
         
     }];
    [dataTask resume];
}
-(void)removeTableArrayWithAnimation
{
    
    [self.carttable deleteRowsAtIndexPaths:[NSArray arrayWithObject:selectedIndexPath] withRowAnimation:UITableViewRowAnimationFade];
    
    [self.carttable endUpdates];
    [self.carttable reloadData];
}

-(IBAction)loginclicked:(id)sender{
    
    loginViewController*lo = [[loginViewController alloc] init];
    NSString*string = NSStringFromClass([lo class]);
    
    lo.classstrng = string;
    [[SlideNavigationController sharedInstance] pushViewController:lo animated:YES];
    
    [popup dismiss:YES];
    
}
-(IBAction)guestActclked:(id)sender{
    RegisterViewController*rvc =[[RegisterViewController alloc] init];
    NSString*string = NSStringFromClass([rvc class]);
    NSLog(@"string:%@",string);
    rvc.classstrng = string;
    [[SlideNavigationController sharedInstance] pushViewController:rvc animated:YES];
    [popup dismiss:YES];
}

@end

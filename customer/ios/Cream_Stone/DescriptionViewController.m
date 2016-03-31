//
//  DescriptionViewController.m
//  example2
//
//  Created by Hariteja P on 07/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "DescriptionViewController.h"
#import "ViewController.h"
#import "CollectionViewController.h"
#import "productcategory.h"
#import "ImageViewCell.h"
#import "cartView.h"
#import "PaymentViewController.h"
#import "TableViewCell.h"
#import "SlideNavigationController.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import "LoginObject.h"
#import  "KLCPopup.h"
#import "RegisterViewController.h"
#import "loginViewController.h"
#import "OrderHIstoryViewController.h"
#import "LocationTable.h"
#import "PolicyViewController.h"
#import "OfferViewController.h"
#import "FeedBackVC.h"
#import "AboutUsViewController.h"
#import "DBManager.h"
#import "ConvertUtility.h"
#import "SearchViewController.h"
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]
#define updatecart @"http://52.74.237.28:8080/ecart/rest/cartrsrc/updatecart"
@interface DescriptionViewController ()
{
    KLCPopup*popup;
}
@end

@implementation DescriptionViewController
@synthesize cellImageView,cellimagename,price,producttext,imageurl,img,cellname,productprice1,productText,LabelSpecify,specifyview,baseAlert,addtoCart,Buynow,prodId,Quantity,product,Websoc,datePicker,Image,imageView,backbtn,ingredientsbtn,ID;

-(void)viewWillAppear:(BOOL)animated{
    NSString*userid = [[LoginObject getInstance] login];
   
    if(userid==NULL)
    {
        [self viewDidLoad];
        
    }
    else if(userid){
        [self addtoCart];
        
    }
    else{
        NSLog(@"no data");
    }
    
}
- (void)viewDidLoad {
    [super viewDidLoad];
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    self.imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,width, height)];
    Image = [UIImage imageNamed:@"bg.png"];
    self.imageView.image = Image;
    self.imageView.userInteractionEnabled = YES;
    
    
    self.navigationItem.rightBarButtonItem = UIBarButtonItemStylePlain;
    UIImage *image = [UIImage imageNamed:@"search-13-32.png"];
    CGRect frame = CGRectMake(0, 0, image.size.width, image.size.height);
    
    UIImage *image1 = [UIImage imageNamed:@"cart_icon.png"];
    CGRect frame1 = CGRectMake(0, 0, image1.size.width, image1.size.height);
    
  
    
    self.cartnumber = [[MKNumberBadgeView alloc] initWithFrame:CGRectMake(image1.size.width-5,0,24,23)];
    
    self.cartnumber.value = 0;
    self.cartnumber.fillColor = [UIColor blackColor];
    self.cartnumber.strokeColor = [UIColor yellowColor];
    self.cartnumber.textColor = [UIColor yellowColor];
    
    UIButton* button = [[UIButton alloc] initWithFrame:frame];
    [button setBackgroundImage:image forState:UIControlStateNormal];
    [button setShowsTouchWhenHighlighted:YES];
    
    UIButton* button1 = [[UIButton alloc] initWithFrame:frame1];
    [button1 setBackgroundImage:image1 forState:UIControlStateNormal];
    [button1 addSubview:self.cartnumber];
    [button1 setShowsTouchWhenHighlighted:YES];
    
    
    [button addTarget:self action:@selector(TableClicked:) forControlEvents:UIControlEventTouchDown];
    [button1 addTarget:self action:@selector(cartDetails:) forControlEvents:UIControlEventTouchDown];
    
    
    UIBarButtonItem* barButtonItem = [[UIBarButtonItem alloc] initWithCustomView:button];
    //UIBarButtonItem *fixedItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
   // fixedItem.width = 20.0f;
    UIBarButtonItem* barButtonItem1 = [[UIBarButtonItem alloc] initWithCustomView:button1];
    UIBarButtonItem *fixedItem2 = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
    fixedItem2.width =19.0f;
     [self.navigationItem setRightBarButtonItems:[NSArray arrayWithObjects:barButtonItem1,fixedItem2,barButtonItem,nil]];
    
    self.cellImageView=[[UIImageView alloc] initWithFrame:CGRectMake(20,70,width-40,height/3)];
     NSCharacterSet *set = [NSCharacterSet URLQueryAllowedCharacterSet];
    [self.cellImageView sd_setImageWithURL:[NSURL URLWithString:[imageurl stringByAddingPercentEncodingWithAllowedCharacters:set]]];
    
    
    self.backbtn = [[UIButton alloc] initWithFrame:CGRectMake(10,height/2.2, 30, 30)];
    [self.backbtn setTitle:@"←" forState:UIControlStateNormal];
    [self.backbtn addTarget:self action:@selector(Backbtn:) forControlEvents:UIControlEventTouchUpInside];
    self.backbtn.titleLabel.font = [UIFont fontWithName:@"Times New Roman" size:45.0];
    [self.backbtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
   
    
    self.cellimagename = [[UILabel alloc]initWithFrame:CGRectMake(50,height/2.2,width-20,30)];
    self.cellimagename.font = [UIFont fontWithName:@"Times New Roman" size:20.0];
    self.cellimagename.textColor = [UIColor whiteColor];
    self.cellimagename.textAlignment = NSTextAlignmentLeft;
    self.cellimagename.text = cellname;
    
    self.price = [[UILabel alloc] initWithFrame:CGRectMake(10,height/2,width-10,30)];
    self.price.font = [UIFont fontWithName:@"Arial" size:19.0];
    self.price.textAlignment =NSTextAlignmentLeft;
    self.price.textColor = [UIColor whiteColor];
    self.price.text = productprice1;
    
    self.producttext = [[UITextView alloc] initWithFrame:CGRectMake(0,height/1.7,width,60)];
    self.producttext.font =[UIFont fontWithName:@"Times New Roman" size:15.0];
    self.producttext.backgroundColor =Rgb2UIColor(248,248,255);
    self.producttext.textColor = [UIColor whiteColor];
    self.producttext.backgroundColor = [UIColor clearColor];
    self.producttext.editable =NO;
    self.producttext.scrollEnabled = NO;
    self.producttext.text = productText;
   
    self.LabelSpecify = [[UILabel alloc] initWithFrame:CGRectMake(0,height/1.4,width,30)];
    self.LabelSpecify.font = [UIFont fontWithName:@"Times New Roman" size:20.0];
    self.LabelSpecify.text = @"Ingredients";
    self.LabelSpecify.layer.cornerRadius = 5.0;
    self.LabelSpecify.layer.masksToBounds =YES;
    self.LabelSpecify.textColor = [UIColor whiteColor];
    self.LabelSpecify.backgroundColor = [UIColor clearColor];
    
    self.ingredientsbtn = [[UIButton alloc] initWithFrame:CGRectMake(width-50, height/1.4, 30, 30)];
    [self.ingredientsbtn setTitle:@">" forState:UIControlStateNormal];
[self.ingredientsbtn addTarget:self action:@selector(Ingredientbtn:) forControlEvents:UIControlEventTouchUpInside];
    self.ingredientsbtn.titleLabel.font = [UIFont fontWithName:@"Times New Roman" size:25.0];
    [self.ingredientsbtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    
    self.addtoCart = [[UIButton alloc] initWithFrame:CGRectMake(0,height-40,(width/2),40)];
    self.addtoCart.backgroundColor =Rgb2UIColor(255,165,0);
    [self.addtoCart setTitle:@"Add to Cart" forState:UIControlStateNormal];
    [self.addtoCart addTarget:self action:@selector(addtocart:) forControlEvents:UIControlEventTouchUpInside];
    
    self.Buynow = [[UIButton alloc] initWithFrame:CGRectMake(self.addtoCart.frame.size.width,height-40,width/2,40)];
    self.Buynow.backgroundColor =Rgb2UIColor(2,149,137);
    [self.Buynow setTitle:@"Buy Now" forState:UIControlStateNormal];
    [self.Buynow addTarget:self action:@selector(buynow:) forControlEvents:UIControlEventTouchUpInside];
    
    
    [self.imageView addSubview:self.addtoCart];
    [self.imageView addSubview:self.Buynow];
    [self.imageView addSubview:self.LabelSpecify];
    [self.imageView addSubview:self.ingredientsbtn];
    [self.imageView addSubview:self.cellImageView];
    [self.imageView addSubview:self.backbtn];
    [self.imageView addSubview:self.cellimagename];
    [self.imageView addSubview:self.price];
    [self.imageView addSubview:self.producttext];
    
    [self.view addSubview:self.imageView];
    
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (IBAction)backgroundtap:(id)sender {
    //[baseAlert dismissWithClickedButtonIndex:0 animated:YES];
    
}

- (IBAction)addtocart:(id)sender {
    
    
    NSString*str = [cellimagename.text stringByAppendingString:@" added to cart"];
    baseAlert = [UIAlertController alertControllerWithTitle: nil
                                                    message:str
                                             preferredStyle: UIAlertControllerStyleAlert];
    
    UIAlertAction *ok = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleDefault handler: nil];
    
    [baseAlert addAction:ok];
    [[SlideNavigationController sharedInstance] presentViewController:baseAlert animated:YES completion:nil];
    
    [self addToCartproduct];
}
-(void)addToCartproduct{
    NSString*userid = [[LoginObject getInstance] login];
    //ConvertUtility*cu  =[[ConvertUtility alloc] init];
    
    if(userid == NULL)
    {
      //  NSDictionary*dict = [cu dictionaryWithPropertiesOfObject:product];
        NSMutableDictionary*offlinedict = [[NSMutableDictionary alloc] init];
                [offlinedict setObject:self.cellname forKey:@"productName"];
                [offlinedict setObject:self.imageurl forKey:@"imagesUrl"];
                [offlinedict setObject:self.productprice1 forKey:@"productPrice1"];
                [offlinedict setObject:self.productText   forKey:@"productText"];
                [offlinedict setObject:self.prodId forKey:@"productId"];
                [offlinedict setObject:@"1" forKey:@"qty"];
        [[DBManager sharedAppManager] saveOflineItemToDataBase:offlinedict];
        
    }
    else {
        
        NSMutableDictionary*post = [[NSMutableDictionary alloc] initWithCapacity:3];
        Quantity = @"1";
        int value = [Quantity intValue];
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
        [session dataTaskWithRequest:request completionHandler:^(NSData *data,
                                                                 NSURLResponse *response,
                                                                 NSError *error)
         {
             
             NSLog(@"Data:%@",data);
             NSString*result= [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
             NSLog(@"Response:%@",result);
         }];
    }
}

- (IBAction)Ingredientbtn:(id)sender {


}
- (IBAction)buynow:(id)sender {
    
    cartView*cv = [[cartView alloc] init];
    cv.namestrng = cellname;
    cv.pricestrng = productprice1;
        [self addtocart:self];
    
    [[SlideNavigationController sharedInstance] presentViewController:cv animated:YES completion:nil];
    
    
}

- (IBAction)Backbtn:(id)sender {
      UICollectionViewFlowLayout *flowLayout = [[UICollectionViewFlowLayout alloc] init];
    CollectionViewController*cvc = [[CollectionViewController alloc] initWithCollectionViewLayout:flowLayout];
    cvc.ID = ID;
    [[SlideNavigationController sharedInstance] pushViewController:cvc animated:YES];
}

-(IBAction)LoginClicked:(id)sender{
    
    NSString*user = [[LoginObject getInstance] login];
    if(user==NULL){
        
        UIAlertController * alertController = [UIAlertController alertControllerWithTitle: nil
                                                                                  message: nil
                                                                           preferredStyle: UIAlertControllerStyleActionSheet];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Login" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            
            loginViewController* newView = [[loginViewController alloc]init];
            [[SlideNavigationController sharedInstance] pushViewController:newView animated:YES];
            
            
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Register" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            RegisterViewController*rc = [[RegisterViewController alloc] init];
            
            [[SlideNavigationController sharedInstance] pushViewController:rc animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"My orders" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            
            OrderHIstoryViewController*ohv = [[OrderHIstoryViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:ohv animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Offers" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            
            OfferViewController*ofc = [[OfferViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:ofc animated:YES];
            
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Stores" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            
            LocationTable*ltc = [[LocationTable alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:ltc animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Policy" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            PolicyViewController*pvc = [[PolicyViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:pvc animated:YES];
            
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Feedback" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
            FeedBackVC*fbv = [[FeedBackVC alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:fbv animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"About" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
            AboutUsViewController*acs = [[AboutUsViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:acs animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Cancel" style: UIAlertActionStyleCancel handler:^(UIAlertAction *action) {
            [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
            
        }]];
        UIPopoverPresentationController * popover = alertController.popoverPresentationController;
        popover.permittedArrowDirections = UIPopoverArrowDirectionUp;
        popover.sourceView = UITableViewStylePlain;
        popover.sourceRect = CGRectMake(200,215,100,57);
        
    [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];
    }
    else if(user){
        
        UIAlertController * alertController = [UIAlertController alertControllerWithTitle: nil
                                                                                  message: nil
                                                                           preferredStyle: UIAlertControllerStyleActionSheet];
        [alertController addAction: [UIAlertAction actionWithTitle:@"User Profile" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
//            Userprofile*up = [[Userprofile alloc] init];
//            [[SlideNavigationController sharedInstance] pushViewController:up animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Logout" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            [[LoginObject getInstance] logout];
            loginViewController* newView = [[loginViewController alloc]init];
            [newView setDelegate:self];
            [[SlideNavigationController sharedInstance] pushViewController:newView animated:YES];
            
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"My Orders" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            OrderHIstoryViewController*ovc = [[OrderHIstoryViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:ovc animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Offer" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            OfferViewController*ofc = [[OfferViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:ofc animated:YES];
            
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Stores" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            
            LocationTable*ltc = [[LocationTable alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:ltc animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Policy" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            PolicyViewController*pvc = [[PolicyViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:pvc animated:YES];
            
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Feedback" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
            FeedBackVC*fbv = [[FeedBackVC alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:fbv animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"About" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
            AboutUsViewController*acs = [[AboutUsViewController alloc] init];
            [[SlideNavigationController sharedInstance] pushViewController:acs animated:YES];
        }]];
        [alertController addAction: [UIAlertAction actionWithTitle: @"Cancel" style: UIAlertActionStyleCancel handler:^(UIAlertAction *action) {
            [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
            
        }]];
        UIPopoverPresentationController * popover = alertController.popoverPresentationController;
        popover.permittedArrowDirections = UIPopoverArrowDirectionUp;
        popover.sourceView = UITableViewStylePlain;
        popover.sourceRect = CGRectMake(200,215,100,57);
        
        [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];
    }
    else{
        NSLog(@"waste of time");
    }
}
-(IBAction)cartDetails:(id)sender{
    cartView*cv = [[cartView alloc] init];
    [[SlideNavigationController sharedInstance] pushViewController:cv animated:YES];
    
}

-(IBAction)TableClicked:(id)sender{
    NSString*userid = [[LoginObject getInstance] login];
    if(userid == NULL)
    {
        SearchViewController*svc = [[SearchViewController alloc] init];
        [[SlideNavigationController sharedInstance] pushViewController:svc animated:YES];
    }
    else{
        
    }
}
-(void)showview{
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    Websoc = [[UIView alloc] initWithFrame:CGRectMake(80,50,width-80,height-300)];
    Websoc.backgroundColor = [UIColor whiteColor];
    Websoc.layer.cornerRadius = 12.0;
    Websoc.layer.masksToBounds = YES;
    Websoc.layer.borderColor=Rgb2UIColor(255,105,180).CGColor;
    
    UILabel*plslbl = [[UILabel alloc] initWithFrame:CGRectMake(30,Websoc.frame.size.height-320,width-150,30)];
    plslbl.text = @"Please Login";
    plslbl.backgroundColor = [UIColor clearColor];
    plslbl.font = [UIFont fontWithName:@"Arial Bold" size:16.0];
    plslbl.textColor =Rgb2UIColor(2,149,137);
    plslbl.textAlignment = NSTextAlignmentCenter;
    
    UIView * separator = [[UIView alloc] initWithFrame:CGRectMake(30,Websoc.frame.size.height-270,width-150,1)];
    separator.backgroundColor = [UIColor colorWithWhite:0.3 alpha:1];
    UIButton*Login = [UIButton buttonWithType:UIButtonTypeCustom];
    Login.frame =CGRectMake(30,Websoc.frame.size.height-250,width-150,50);
    Login.backgroundColor = Rgb2UIColor(2,149,137);
    [Login setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [Login setTitleColor:[[Login titleColorForState:UIControlStateNormal] colorWithAlphaComponent:0.5] forState:UIControlStateHighlighted];
    Login.titleLabel.font = [UIFont boldSystemFontOfSize:14.0];
    
    [Login setTitle:@"LOGIN" forState:UIControlStateNormal];
    Login.layer.cornerRadius = 6.0;
    [Login addTarget:self action:@selector(loginClicked:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel*orlabel = [[UILabel alloc] initWithFrame:CGRectMake(30,Websoc.frame.size.height-180,width-150,30)];
    orlabel.backgroundColor = [UIColor clearColor];
    orlabel.font = [UIFont fontWithName:@"Arial" size:15];
    orlabel.textAlignment = NSTextAlignmentCenter;
    orlabel.text = @"(OR)";
    
    
    UIButton*GuestAct = [UIButton buttonWithType:UIButtonTypeCustom];
    GuestAct.frame =CGRectMake(30,Websoc.frame.size.height-120,width-150,50);
    GuestAct.backgroundColor = Rgb2UIColor(2,149,137);
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
-(IBAction)continuebtn:(id)sender{
    LocationTable  *lo = [[LocationTable alloc] init];
    NSString*string = NSStringFromClass([lo class]);
    
    lo.classstrng = string;
    [[SlideNavigationController sharedInstance] pushViewController:lo animated:YES];
    
    [popup dismiss:YES];
    
    
}
-(void)createTime
{
   self.datePicker = [[UIDatePicker alloc] init];
  self.datePicker.frame = CGRectMake(20, 150,(Websoc.frame.size.width)-20, 180.0f); // set frame as your need
    self.datePicker.datePickerMode = UIDatePickerModeTime;
    [self.datePicker addTarget:self action:@selector(dateChanged:) forControlEvents:UIControlEventValueChanged];
    [Websoc addSubview:self.datePicker];
    
    UILabel  * label1 = [[UILabel alloc] initWithFrame:CGRectMake(40,120,(Websoc.frame.size.width)-40,50)];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"hh:mm a"];
    NSString *currentTime = [dateFormatter stringFromDate:self.datePicker.date];
    label1.text = currentTime;
    [Websoc addSubview:label1];
}
- (void)dateChanged:(id)sender
{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"hh:mm a"];
    NSString *currentTime = [dateFormatter stringFromDate:self.datePicker.date];
    NSLog(@"%@", currentTime);
}
-(IBAction)cancel:(id)sender{
    
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
-(IBAction)loginClicked:(id)sender
{
    loginViewController  *lo = [[loginViewController alloc] init];
    NSString*string = NSStringFromClass([lo class]);
    
    lo.classstrng = string;
    [[SlideNavigationController sharedInstance] pushViewController:lo animated:YES];
    
    [popup dismiss:YES];
}
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"CartView"]) {
        cartView *vc = [segue destinationViewController];
        [[SlideNavigationController sharedInstance] pushViewController:vc animated:YES];
    }
}
@end

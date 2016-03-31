//
//  ViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "ViewController.h"
#import "CollectionViewController.h"
#import "List.h"
#import "productcategory.h"
#import "ImageViewCell.h"
#import "MenuSlideViewController.h"
#import "SlideNavigationController.h"
#import "BannerItems.h"
#import "loginViewController.h"
#import "PaymentViewController.h"
#import "RegisterViewController.h"
#import "TableViewCell.h"
#import "scrollimage.h"
#import "screenview.h"
#import "LoadingView.h"
#import "cartView.h"
#import "LoginObject.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import <CoreLocation/CoreLocation.h>
#import "AppDelegate.h"
#import "OrderHIstoryViewController.h"
#import "FeedBackVC.h"
#import "PolicyViewController.h"
#import "AboutUsViewController.h"
#import "LocationTable.h"
#import "OfferViewController.h"
#import "Order.h"
#import "User.h"
#import "SearchViewController.h"
extern NSString* userid;
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]
#define LOGGED_IN NO
@interface ViewController () <loginViewControllerDelegate>
@end
#define getDataURL @"http://52.74.237.28:8080/ecart/rest/homersrc/homecategories"
#define  caturl @"http://52.74.237.28:8080/ecart/rest/productrsrc/productbycategory"
#define BannerItemsUrl @"http://52.74.237.28:8080/ecart/rest/homersrc/homeads"
@implementation ViewController
{
    NSArray*images;
    User*user;
    
}
@synthesize mytable,itemsArray,json,IDArray,json1,BannerArray,DisplayImage,imageScroll,imagesI,json2,tableViewCell,imagesscroll,timer,catID,userId,cartnumber,imageView,image;

- (void)viewDidLoad {
       [super viewDidLoad];
    self.title = @"Fresh Bake";

    NSString *bundleIdentifier = [[NSBundle mainBundle] bundleIdentifier];
    NSLog(@"bundleidentifier:%@",bundleIdentifier);
        [self.mytable registerClass:[TableViewCell class] forCellReuseIdentifier:@"Cell"];
        [self retrieveData];
        [self retrieveData1];
        [self retrieveData2];
   self.navigationController.navigationBar.barTintColor = Rgb2UIColor(0,0,0);
   self.navigationController.navigationBar.titleTextAttributes =[NSDictionary dictionaryWithObject:[UIColor whiteColor] forKey:NSForegroundColorAttributeName];
   self.navigationItem.leftBarButtonItem = UIBarButtonItemStylePlain;
   // float width = [UIScreen mainScreen].bounds.size.width;
   // float height = [UIScreen mainScreen].bounds.size.height;
    
   // self.imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,width, height)];
     image = [UIImage imageNamed:@"bg.png"];
    self.imageView.image = image;
    self.imageView.userInteractionEnabled = YES;
    [self.mytable addSubview:self.imageView];
    
        UIImage *image1 = [UIImage imageNamed:@"search-13-32.png"];
        CGRect frame1 = CGRectMake(0, 0, image1.size.width, image1.size.height);
    
        UIImage *image2 = [UIImage imageNamed:@"3 Dots.png"];
        CGRect frame2 = CGRectMake(0, 0, image2.size.width, image2.size.height);
    
    self.cartnumber = [[MKNumberBadgeView alloc] initWithFrame:CGRectMake(image2.size.width-5,0,24,23)];
    
        self.cartnumber.value = 0;
        self.cartnumber.fillColor = [UIColor blackColor];
        self.cartnumber.strokeColor = [UIColor yellowColor];
        self.cartnumber.textColor = [UIColor yellowColor];
    
         UIButton* button1 = [[UIButton alloc] initWithFrame:frame1];
        [button1 setBackgroundImage:image1 forState:UIControlStateNormal];
        [button1 addSubview:self.cartnumber];
        [button1 setShowsTouchWhenHighlighted:YES];
    
        UIButton* button2 = [[UIButton alloc] initWithFrame:frame2];
       [button2 setBackgroundImage:image2 forState:UIControlStateNormal];
        [button2 setShowsTouchWhenHighlighted:YES];
    
    
    [button1 addTarget:self action:@selector(SearchBtn:) forControlEvents:UIControlEventTouchDown];
        [button2 addTarget:self action:@selector(LoginClicked:) forControlEvents:UIControlEventTouchDown];
    
    
        UIBarButtonItem *fixedItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
        fixedItem.width = 20.0f;
        UIBarButtonItem* barButtonItem1 = [[UIBarButtonItem alloc] initWithCustomView:button1];
        UIBarButtonItem *fixedItem2 = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
          fixedItem2.width =19.0f;
        UIBarButtonItem* barButtonItem2 = [[UIBarButtonItem alloc] initWithCustomView:button2];
        [self.navigationItem setRightBarButtonItems:[NSArray arrayWithObjects:barButtonItem2,fixedItem,barButtonItem1,nil]];
        
        UIBarButtonItem*menubutton1 = [[UIBarButtonItem alloc]initWithImage:[UIImage imageNamed:@"menu-icon.png"] style:UIBarButtonItemStylePlain target:self action:@selector(toggleLeftMenu)];
        menubutton1.tintColor = [UIColor whiteColor];
       // self.navigationController.navigationBar.backgroundColor = Rgb2UIColor(238,130,238);
        
        [SlideNavigationController sharedInstance].leftBarButtonItem = menubutton1;
        self.imagesscroll = [[UIScrollView alloc] initWithFrame:CGRectMake(self.view.frame.origin.x,60,self.view.frame.size.width,self.imagesscroll.frame.size.height)];
    
        self.imagesscroll.tag = 1;
        [self setupScrollView:self.imagesscroll];
        self.imagesscroll.translatesAutoresizingMaskIntoConstraints = YES;
        [self.view addSubview:self.imagesscroll];
        self.imageScroll = [[UIPageControl alloc] initWithFrame:CGRectMake(self.view.frame.origin.x,self.view.frame.origin.y,self.view.frame.size.width,self.imageScroll.frame.size.height)];
        [self.imagesscroll sizeToFit];
        self.imagesscroll.scrollEnabled = NO;
        [self.imageScroll setTag:3];
        [self.imageScroll  setNumberOfPages:json1.count];
    
        self.imageScroll.autoresizingMask=UIViewAutoresizingNone;
    
        [self.view addSubview:self.imagesscroll];
    //[self.view addSubview:self.imageView];
       [self.imagesscroll addSubview:self.imageScroll];
    [self.mytable performSelector:@selector(reloadData) withObject:nil afterDelay:2];
}
- (void)scrollViewDidScroll:(UIScrollView *)imagesScroll
{
    [imagesscroll setContentOffset: CGPointMake(0,imagesscroll.contentOffset.y)];
    // or if you are sure you wanna it always on left:
    // [aScrollView setContentOffset: CGPointMake(0, aScrollView.contentOffset.y)];
}

- (void)setupScrollView:(UIScrollView*)imagesscrollMain {
    
    for(int i=0;i<json1.count;i++){
        BannerItems*imagescroll = [BannerArray objectAtIndex:i];
        NSString*imageurl = imagescroll.imageURL;
        UIImage*img =[UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:imageurl]]];
        UIButton*button = [[UIButton alloc] initWithFrame:CGRectMake((i)*imagesscrollMain.frame.size.width, 0, imagesscrollMain.frame.size.width,imagesscrollMain.frame.size.height)];
        [button setImage:img forState:UIControlStateNormal];
        [button addTarget:self action:@selector(bannerbutton:) forControlEvents:UIControlEventTouchUpInside];
        button.tag = i;
        
        [imagesscrollMain addSubview:button];
        [button setUserInteractionEnabled:YES];
    }
    [imagesscrollMain setContentSize:CGSizeMake(imagesscrollMain.frame.size.width*json1.count, imagesscrollMain.frame.size.height)];
    
    [NSTimer scheduledTimerWithTimeInterval:3 target:self selector:@selector(scrollingTimer) userInfo:nil repeats:YES];
}
- (void)scrollingTimer {
    UIScrollView *imagesscrollMain = (UIScrollView*) [self.imagesscroll viewWithTag:1];
    
    UIPageControl *pgCtr = (UIPageControl*) [self.imageScroll viewWithTag:json1.count];
    
    CGFloat contentOffset = imagesscrollMain.contentOffset.x;
    
    int nextPage = (int)(contentOffset/imagesscrollMain.frame.size.width) + 1 ;
    
    if( nextPage!=json1.count)  {
        [imagesscrollMain scrollRectToVisible:CGRectMake(nextPage*imagesscrollMain.frame.size.width, 0, imagesscrollMain.frame.size.width, imagesscrollMain.frame.size.height) animated:YES];
        pgCtr.currentPage=nextPage;
    } else {
        [imagesscrollMain scrollRectToVisible:CGRectMake(0, 0, imagesscrollMain.frame.size.width, imagesscrollMain.frame.size.height) animated:YES];
        pgCtr.currentPage=0;
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return json.count;
}
-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{

    UILabel *headerLabel = [[UILabel alloc]init];
    headerLabel.tag = section;
    headerLabel.userInteractionEnabled = YES;
    headerLabel.backgroundColor = [UIColor whiteColor];
    headerLabel.text = [NSString stringWithFormat:@"%@",[[json objectAtIndex:section] objectForKey:@"name"]];
    headerLabel.frame = CGRectMake(0, 0, tableView.tableHeaderView.frame.size.width, tableView.tableHeaderView.frame.size.height);

    UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(handleTapBehind:)];
    tapGesture.cancelsTouchesInView = NO;
    [headerLabel addGestureRecognizer:tapGesture];
    
    return headerLabel;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    
    return  [[json objectAtIndex:section] objectForKey:@"name"];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 1;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 184;
}
- (void)handleTapBehind:(UITapGestureRecognizer *)sender
{
    if (sender.state == UIGestureRecognizerStateEnded)
    {
      
        NSIndexPath*indexpath = [NSIndexPath indexPathForRow:sender.view.tag inSection:json.count];
        List*obj = [IDArray objectAtIndex:indexpath.row];
        NSString*ID = obj.Id;
        UICollectionViewFlowLayout *flowLayout = [[UICollectionViewFlowLayout alloc] init];
     
//        [flowLayout setScrollDirection:UICollectionViewScrollDirectionVertical];
//        [flowLayout setItemSize:CGSizeMake(182,227)];
        [flowLayout setMinimumInteritemSpacing:5];
        [flowLayout setSectionInset : UIEdgeInsetsMake(10,10,10,10)];
        [flowLayout setMinimumLineSpacing:20];
        
CollectionViewController*vc = [[CollectionViewController alloc]initWithCollectionViewLayout:flowLayout];
        vc.ID = ID;
        [[SlideNavigationController sharedInstance] pushViewController:vc animated:YES];

       
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *cellIdentifier = @"Cell";
    //float width = [UIScreen mainScreen].bounds.size.width;
//    float height = [UIScreen mainScreen].bounds.size.height;
  
    TableViewCell *cell = (TableViewCell *)[self.mytable dequeueReusableCellWithIdentifier:cellIdentifier];
    cell=nil;
    cell = [[TableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
        [[NSBundle mainBundle] loadNibNamed:@"TableViewCell" owner:self options:nil];
        CGAffineTransform rotateTable = CGAffineTransformMakeRotation(-M_PI_2);
        
        tableViewCell.horizontalTableView.transform =rotateTable;
        tableViewCell.horizontalTableView.frame = CGRectMake(0, 0,375,184);
        tableViewCell.horizontalTableView.allowsSelection = YES;
        tableViewCell.horizontalTableView.delegate =tableViewCell;
    //tableViewCell.horizontalTableView.backgroundView = self.imageView;
        tableViewCell.productArray = [totalDictionary objectForKey:[[json objectAtIndex:indexPath.section] objectForKey:@"name"]];
    
        cell =tableViewCell;
    
    cell.selectedBackgroundView.backgroundColor=[UIColor clearColor];
    return cell;
    
}


-(void)retrieveData
{
    NSError*error;
    NSURL *url   = [NSURL URLWithString:getDataURL];
    NSData *data = [NSData dataWithContentsOfURL:url];
    
    json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    IDArray = [[NSMutableArray alloc] init];
    for (int i=0; i<json.count;i++)
    {
        NSString * cID       = [[json objectAtIndex:i] objectForKey:@"id"];
        NSString * cName     = [[json objectAtIndex:i] objectForKey:@"name"];
        NSString * cLevel    = [[json objectAtIndex:i] objectForKey:@"level"];
        NSString * cParentID = [[json objectAtIndex:i] objectForKey:@"parentId"];
        NSString * cImageUrl = [[json objectAtIndex:i] objectForKey:@"imageUrl"];
        
        
        List *myList = [[List alloc]initWithId:(NSString *) cID andname: (NSString *) cName andlevel: (NSString *) cLevel andparentId:(NSString *) cParentID andimageUrl: (NSString *) cImageUrl];
        [IDArray addObject:myList];
    }
    
}

-(void)retrieveData1{
    NSURL*url = [NSURL URLWithString:BannerItemsUrl];
    NSData*data = [NSData dataWithContentsOfURL:url];
    json1 = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];
    BannerArray = [[NSMutableArray alloc] init];
    for(int i=0;i<json1.count;i++)
    {
        NSString*cBannerId = [[json1 objectAtIndex:i] objectForKey:@"bannerId"];
        NSString*cBannerFlag = [[json1 objectAtIndex:i] objectForKey:@"bannerFlag"];
        NSString*cImageURL = [[json1 objectAtIndex:i] objectForKey:@"imageURL"];
        NSString*cOfferId = [[json1 objectAtIndex:i] objectForKey:@"offerId"];
        NSString*cCategoryId = [[json1 objectAtIndex:i] objectForKey:@"categoryId"];
        NSString*cProducts = [[json1 objectAtIndex:i] objectForKey:@"products"];
        
        BannerItems*Items = [[BannerItems alloc] initWithbannerID:(NSString *)cBannerId andbannerFlag:(NSString*)cBannerFlag andimageURL:(NSString *)cImageURL andofferId:(NSString *)cOfferId andcategoryId:(NSString *)cCategoryId andproducts:(NSString *)cProducts];
        
        [BannerArray addObject:Items];
        
        
    }
    
}
-(IBAction)LoginClicked:(id)sender{
    
    NSString*userid =[[LoginObject getInstance] login];
    if(userid==NULL){
        
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
        //  [[SlideNavigationController sharedInstance] performSegueWithIdentifier:@"OfferSegue" sender:self];
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
    else if(userid){
       
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
            //OfferViewController*ofc = [[OfferViewController alloc] init];
            [[SlideNavigationController sharedInstance] performSegueWithIdentifier:@"OfferView" sender:alertController];
            
            
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

-(IBAction)SearchBtn:(id)sender{
    SearchViewController*svc = [[SearchViewController alloc] init];
    [[SlideNavigationController sharedInstance] pushViewController:svc animated:YES];

}
- (BOOL)slideNavigationControllerShouldDisplayLeftMenu
{
    return YES;
}

-(void)retrieveData2{
 
    catID = [[NSMutableArray alloc] init];
    totalDictionary = [[NSMutableDictionary alloc] init];
    for (int j=0;j<json.count;j++){
    NSString*ID = [[json objectAtIndex:j]objectForKey:@"id"];
       
    NSURL*url = [NSURL URLWithString:caturl];
    NSURL*url3 = [url URLByAppendingPathComponent:ID];
    NSData*data = [NSData dataWithContentsOfURL:url3];
    //NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url3];
    json2 = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];
              
    
        imagesI = [[NSMutableArray alloc] init];
        for (int i=0; i<json2.count; i++) {

            NSString * cProductId      =  [[json2 objectAtIndex:i]objectForKey:@"productId"] ;
            NSString * cProductName    =  [[json2 objectAtIndex:i]objectForKey:@"productName"] ;
            NSString * cProductPrice1  =  [[json2 objectAtIndex:i]objectForKey:@"productPrice1"] ;
            NSString * cProductPrice2  =  [[json2 objectAtIndex:i]objectForKey:@"productPrice2"] ;
            NSString * cRating         =  [[json2 objectAtIndex:i]objectForKey:@"rating"];
            NSString * cAddedDate      =  [[json2 objectAtIndex:i]objectForKey:@"addedDate"] ;
            NSString * cProductDesc    =  [[json2 objectAtIndex:i]objectForKey:@"productDesc"] ;
            NSString * cOffer_product  =  [[json2 objectAtIndex:i]objectForKey:@"offer_product"];
            NSString * cCart_product   =  [[json2 objectAtIndex:i]objectForKey:@"cart_product"];
            NSString * cCategoryId     =  [[json2 objectAtIndex:i]objectForKey:@"categoryId"] ;
            NSString * cImageUrl       =  [[json2 objectAtIndex:i]objectForKey:@"imagesUrl"];
            NSString * cManufacturer   =  [[json2 objectAtIndex:i] objectForKey:@"manufacturer"];
            NSString * cQty            =  [[json2 objectAtIndex:i] objectForKey:@"qty"];
            
            
            productcategory*mycat = [[productcategory alloc] initWithproductId:(NSString *) cProductId andimagesUrl:(NSString *)cImageUrl andproductName:(NSString *)cProductName andproductPrice1:(NSString *)cProductPrice1 andproductPrice2:(NSString *)cProductPrice2 andrating:(NSString *)cRating andaddedDate:(NSString *) cAddedDate andproductDesc:(NSString *)cProductDesc andoffer_product:(NSString *)cOffer_product andcart_product:(NSString *)cCart_product andcategoryId:(NSString *) cCategoryId andmanufacturer: (NSString *) cManufacturer andqty: (NSString *)cQty];
                [imagesI addObject:mycat];
        
        }
            [totalDictionary setObject:[imagesI copy] forKey:[[json objectAtIndex:j]objectForKey:@"name"]];

        }
}

- (IBAction)bannerbutton:(id)sender {

    
    
    
}

- (CGSize)currentScreenSize {
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    CGSize screenSize = screenBounds.size;
    
    if ( NSFoundationVersionNumber <= NSFoundationVersionNumber_iOS_7_1 ) {
        UIInterfaceOrientation interfaceOrientation = [[UIApplication sharedApplication] statusBarOrientation];
        if ( UIInterfaceOrientationIsLandscape(interfaceOrientation) ) {
            screenSize = CGSizeMake(screenSize.height, screenSize.width);
        }
    }
    
    return screenSize;
}
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
 


}
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([segue.identifier isEqualToString:@"OfferView"])
    {
        OfferViewController*ofc = [[OfferViewController alloc] init];
        [[SlideNavigationController sharedInstance] pushViewController:ofc animated:YES];
    }
}
@end

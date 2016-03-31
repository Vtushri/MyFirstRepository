//
//  CollectionViewController.m
//  example2
//
//  Created by Hariteja P on 07/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "CollectionViewController.h"
#import "ViewController.h"
#import "List.h"
#import "productcategory.h"
#import "ImageViewCell.h"
#import "DescriptionViewController.h"
#import "productcategory.h"
#import "MenuSlideViewController.h"
#import "loginViewController.h"
#import "RegisterViewController.h"
#import "SlideNavigationController.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import "LoginObject.h"

#import "OrderHIstoryViewController.h"
#import "LocationTable.h"
#import "FeedBackVC.h"
#import "PolicyViewController.h"
#import "OfferViewController.h"
#import "AboutUsViewController.h"
#import "AppDelegate.h"
#define  caturl @"http://52.74.237.28:8080/ecart/rest/productrsrc/productbycategory"
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]


@interface CollectionViewController (){
    NSDictionary *imagesarray;
    
}
@property (strong, nonatomic) NSMutableArray *data;
@property (nonatomic,strong) NSMutableArray*images;
@end

@implementation CollectionViewController
@synthesize ID,images,json,imageView,image;
static NSString * const reuseIdentifier = @"Cell";
- (AppDelegate *)appDelegate
{
    return (AppDelegate *)[[UIApplication sharedApplication] delegate];
}
-(XMPPStream *)xmppStream{
    
    return [[self appDelegate] xmppStream];
    
}

-(void) viewWillAppear:(BOOL)animated{
    NSString*userid = [[LoginObject getInstance] login];
    
    userid = NULL;
    if(userid==NULL)
    {
        [self viewDidLoad];
        
    }
    else if(userid){
        [self loginAction:self];
        
    }
    else{
        NSLog(@"no data");
    }
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    image = [UIImage imageNamed:@"loginbg.png"];
    self.imageView.image = image;
    self.imageView.userInteractionEnabled = YES;
self.collectionView.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"bg.png"]];
     // [self.collectionView setBackgroundColor:[UIColor whiteColor]];
   // [self.collectionView.collectionViewLayout invalidateLayout];
    [self.collectionView reloadData];
    [self retrieveData2];
    // Register cell classes
    [self.collectionView registerClass:[ImageViewCell class] forCellWithReuseIdentifier:reuseIdentifier];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"3 Dots.png"]  style:UIBarButtonItemStylePlain target:self action:@selector(loginAction:)];
    self.slideOutAnimaionEnabled =YES;
  
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}
#pragma mark <UICollectionViewDataSource>

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {

    return 1;
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
      return images.count;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    ImageViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:reuseIdentifier forIndexPath:indexPath];

    productcategory*product= [images objectAtIndex:indexPath.item];
    NSString*imageurl = product.imagesUrl;
    NSCharacterSet *set = [NSCharacterSet URLQueryAllowedCharacterSet];
    [cell.cellImage sd_setImageWithURL:[NSURL URLWithString:[imageurl stringByAddingPercentEncodingWithAllowedCharacters:set]]];

    NSString*namecell = product.productName;
    cell.cellName.text = namecell;
    CGFloat price1 = (CGFloat)[product.productPrice1 floatValue];
    cell.cellPrice1.text =[NSString stringWithFormat:@"₹ %.02f",price1];
    return cell;

}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    
    ImageViewCell* cell = (ImageViewCell*)[collectionView cellForItemAtIndexPath:indexPath];
    cell.layer.borderWidth = 3;
    cell.layer.borderColor = [UIColor whiteColor].CGColor;
//    [[cell contentView] setFrame:[cell bounds]];
//    [[cell contentView] setAutoresizingMask:UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight];
    
    productcategory*product= [images objectAtIndex:indexPath.item];
    NSString*imageurl = product.imagesUrl;
    NSString*productname = product.productName;
    NSString*productDesc = product.productDesc;
    NSString*productID = product.productId;
    NSLog(@"productid:%@",productID);
    NSString*Qty = product.qty;
    float price1 = (float)[product.productPrice1 floatValue];
    DescriptionViewController*vc = [[DescriptionViewController alloc]init];
    vc.cellname =productname;
     vc.imageurl= imageurl;
    vc.productprice1 =[NSString stringWithFormat:@"₹ %.02f",price1];
    vc.productText = productDesc;
    vc.prodId = productID;
    vc.Quantity = Qty;
    vc.product = product;
    vc.ID = ID;
    [self.navigationController pushViewController:vc animated:YES];
   

}
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
   // float width = [UIScreen mainScreen].bounds.size.width;
    return CGSizeMake(self.collectionView.frame.size.width/2.4,200);
}
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionView *)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section
{
    return 5; // This is the minimum inter item spacing, can be more
}

-(void)retrieveData2{
    NSError*error;
    NSURL *url = [NSURL URLWithString:caturl];
    NSURL*url3 = [url URLByAppendingPathComponent:ID];
    NSData*data = [NSData dataWithContentsOfURL:url3];
    json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    NSLog(@"%@",json);
    images = [[NSMutableArray alloc] init];
    for (int i=0; i<json.count; i++) {
        NSString * cProductId = [[json objectAtIndex:i]objectForKey:@"productId" ] ;
        NSString * cProductName =[[json objectAtIndex:i]objectForKey:@"productName"] ;
        NSString * cProductPrice1 = [[json objectAtIndex:i]objectForKey:@"productPrice1"] ;
        NSString * cProductPrice2 = [[json  objectAtIndex:i] objectForKey:@"productPrice2"] ;
        NSString * cRating = [[json   objectAtIndex:i]objectForKey:@"rating"];
        NSString * cAddedDate = [[json  objectAtIndex:i]objectForKey:@"addedDate"] ;
        NSString * cProductDesc = [[json   objectAtIndex:i]objectForKey:@"productDesc"] ;
        NSString * cOffer_product =[[json objectAtIndex:i] objectForKey:@"offer_product"];
        NSString * cCart_product = [[json  objectAtIndex:i] objectForKey:@"cart_product"];
        NSString * cCategoryId = [[json objectAtIndex:i] objectForKey:@"categoryId"] ;
        NSString * cImageUrl = [[json objectAtIndex:i] objectForKey:@"imagesUrl"];
        NSString * cManufacturer   =  [[json objectAtIndex:i] objectForKey:@"manufacturer"];
        NSString * cQty            =  [[json objectAtIndex:i] objectForKey:@"qty"];
        
        productcategory*mycat = [[productcategory alloc] initWithproductId:(NSString *) cProductId andimagesUrl:(NSString *)cImageUrl andproductName:(NSString *)cProductName andproductPrice1:(NSString *)cProductPrice1 andproductPrice2:(NSString *)cProductPrice2 andrating:(NSString *)cRating andaddedDate:(NSString *) cAddedDate andproductDesc:(NSString *)cProductDesc andoffer_product:(NSString *)cOffer_product andcart_product:(NSString *)cCart_product andcategoryId:(NSString *) cCategoryId andmanufacturer: (NSString *) cManufacturer andqty: (NSString *)cQty];
        
            [images addObject:mycat];
       
    }
}


                           

-(IBAction)loginAction:(id)sender{
    
    LoginObject*lo1 = [LoginObject getInstance];
    NSDictionary*loginobj1 = lo1.dict;
    NSString*username = [loginobj1 objectForKey:@"userName"];
    NSLog(@"username:%@",username);
    NSString*user = [loginobj1 objectForKey:@"userAccountId"];
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





   @end

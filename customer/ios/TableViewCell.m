//
//  TableViewCell.m
//  example2
//
//  Created by Hariteja P on 28/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "TableViewCell.h"
#import "ViewController.h"
#import "CollectionViewController.h"
#import "DescriptionViewController.h"
#import "productcategory.h"
#import "scrollimage.h"
#import "SlideNavigationController.h"
#import "UIImageView+WebCache.h"
#import "KLCPopup.h"
#import "LoginObject.h"
#import "DBManager.h"
#define kBgQueue dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]
#define updatecart @"http://52.74.237.28:8080/ecart/rest/cartrsrc/updatecart"
@implementation TableViewCell
{
    KLCPopup*popup;
    NSMutableArray*pickerData;
    NSString*number;
}
@synthesize productArray,horizontalTableView,image,baseAlert,Quantity,prodId,classstring;

 static NSString *identifier = @"scrollimage";
- (NSString *) reuseIdentifier {
    return @"Cell";

}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return 1;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 165;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    return [productArray count];
    
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
   scrollimage *cell = [horizontalTableView dequeueReusableCellWithIdentifier:identifier];
   cell=nil;
   [horizontalTableView registerNib:[UINib nibWithNibName:identifier bundle:nil] forCellReuseIdentifier:identifier];
        
        cell = [horizontalTableView dequeueReusableCellWithIdentifier:identifier];
  
    CGRect frame = cell.contentView.frame;
    [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
    cell.contentView.transform = CGAffineTransformMakeRotation(-M_PI*1.5);
    cell.contentView.frame = frame;
   
    productcategory*pc = [productArray objectAtIndex:indexPath.row];
    NSString*images1 = pc.imagesUrl;
    NSString*name = pc.productName;
   
    float price1 = (float)[pc.productPrice1 floatValue];
    NSCharacterSet *set = [NSCharacterSet URLQueryAllowedCharacterSet];
    [cell.cellImage sd_setImageWithURL:[NSURL URLWithString:[images1 stringByAddingPercentEncodingWithAllowedCharacters:set]]];
    cell.cellImageName.text = name;
    cell.CellPrice.text = [NSString stringWithFormat:@"Price:₹ %.02f",price1];
[cell.cartbutton addTarget:self action:@selector(cartbtnclicked:) forControlEvents:UIControlEventTouchUpInside];
    [cell layoutIfNeeded];
       return cell;
    

}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    productcategory*product = [productArray objectAtIndex:indexPath.row];
    NSString*imageurl = product.imagesUrl;
    NSString*productname = product.productName;
    NSString*productDesc = product.productDesc;
    NSString*productid = product.productId;
    float price1 = (float)[product.productPrice1 floatValue];
    
    DescriptionViewController*vc = [[DescriptionViewController alloc]init];
    vc.cellname =productname;
    vc.imageurl= imageurl;
    vc.productprice1 =[NSString stringWithFormat:@"Price:₹ %.02f",price1];
    vc.productText = productDesc;
    vc.prodId = productid;
    vc.product = product;
    [[SlideNavigationController sharedInstance] pushViewController:vc animated:YES];
    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(IBAction)cartbtnclicked:(id)sender{
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
    [ok setTitle:@"OK" forState:UIControlStateNormal];
    [ok addTarget:self action:@selector(okbtnclicked:) forControlEvents:UIControlEventTouchUpInside];
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
-(IBAction)okbtnclicked:(id)sender{
   
   // NSString*userid = [[LoginObject getInstance] login];
    NSString*str = @"added to cart";
    baseAlert =  [UIAlertController alertControllerWithTitle: nil
                                                      message: str
                                                      preferredStyle: UIAlertControllerStyleAlert];
    UIAlertAction *ok = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleDefault handler: nil];
    
    [baseAlert addAction:ok];
    [[SlideNavigationController sharedInstance] presentViewController:baseAlert animated:YES completion:nil];

//    [self performSelector:@selector(byeAlertView:) withObject:baseAlert afterDelay:2];
//    [self.baseAlert removeFromSuperview];
    [self addtocart];
    
    [popup dismiss:YES];
}
-(void)addtocart
{
    NSString*userid = [[LoginObject getInstance] login];
    //ConvertUtility*cu  =[[ConvertUtility alloc] init];
    
    NSIndexPath *indexPath = [self.horizontalTableView indexPathForSelectedRow];
     productcategory*product = [productArray objectAtIndex:indexPath.row];
   // UITableViewCell* cell = [horizontalTableView cellForRowAtIndexPath:indexPath];
    
    //[horizontalTableView reloadRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
    //productcategory*product = [productArray objectAtIndex:indexPath.row];
    scrollimage *cell = [horizontalTableView dequeueReusableCellWithIdentifier:identifier];
    
    if(userid == NULL)
    {
        //  NSDictionary*dict = [cu dictionaryWithPropertiesOfObject:product];
        NSMutableDictionary*offlinedict = [[NSMutableDictionary alloc] init];
        [offlinedict setObject:cell.cellImageName forKey:@"productName"];
        [offlinedict setObject:cell.cellImage forKey:@"imagesUrl"];
        [offlinedict setObject:cell.CellPrice forKey:@"productPrice1"];
       // [offlinedict setObject:cell.cellText  forKey:@"productText"];
        [offlinedict setObject:product.productId forKey:@"productId"];
        [offlinedict setObject:number forKey:@"qty"];
        [[DBManager sharedAppManager] saveOflineItemToDataBase:offlinedict];
        
    }
    else {
        
        NSMutableDictionary*post = [[NSMutableDictionary alloc] initWithCapacity:3];
        Quantity = number;
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
//        [NSURLConnection sendAsynchronousRequest:request
//                                           queue:[NSOperationQueue mainQueue]
//                               completionHandler:^(NSURLResponse *r, NSData *data, NSError *error)
//         
//         
         {
             
             NSLog(@"Data:%@",data);
             NSString*dataresponse= [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
             NSLog(@"Response:%@",dataresponse);
         }];
    }

}
//-(void)byeAlertView:(UIAlertView *)alertView{
//    [baseAlert dismissWithClickedButtonIndex:0 animated:YES];
//}

@end

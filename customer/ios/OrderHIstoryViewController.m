//
//  OrderHIstoryViewController.m
//  example2
//
//  Created by Hariteja P on 03/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
@class Shipping;
@class Payment;
@class Stores;
@class UsrProfile;
#import "OrderHIstoryViewController.h"
#import "LoginObject.h"
#import "Shipping.h"
#import "Payment.h"
#import "Stores.h"
#import "UsrProfile.h"
#import "Order.h"
#import "OrderTableCell.h"


#define orderHistory @"http://52.74.237.28:8080/ecart/rest/orderrsrc/currentorders"
@interface OrderHIstoryViewController ()

@end

@implementation OrderHIstoryViewController
@synthesize json,OrderArray;
- (void)viewDidLoad {
    [super viewDidLoad];
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    self.OrderTable = [[UITableView alloc] initWithFrame:CGRectMake(0,64,width,height)];
    self.OrderTable.delegate =self;
    self.OrderTable.dataSource =self;
    [self.OrderTable registerClass:[OrderTableCell class] forCellReuseIdentifier:@"OrderTableCell"];
    
    [self jsondata];
    [self.OrderTable reloadData];
    [self.OrderTable setNeedsLayout];
    [self.OrderTable layoutIfNeeded];
    [self.view addSubview:self.OrderTable];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//- (void)viewWillAppear:(BOOL)animated {
//    [super viewWillAppear:animated];
//    [self.OrderTable reloadData];
//    
//}
#pragma mark -Table view protocols
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return 1;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return json.count;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 140;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
   static NSString *cellIdentifier = @"OrderTableCell";
    OrderTableCell*cell =[self.OrderTable dequeueReusableCellWithIdentifier:cellIdentifier forIndexPath:indexPath];
    if(cell==nil)
    {
    cell = [[OrderTableCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    cell=nil;
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"OrderTableCell" owner:self options:nil];
    cell = [nib objectAtIndex:0];
    cell =[tableView dequeueReusableCellWithIdentifier:cellIdentifier];
     cell.delegate =self;
   
    for (id oneObject in nib)
        if ([oneObject isKindOfClass:[OrderTableCell class]])
            cell = (OrderTableCell *)oneObject;

    Order*ordr = [OrderArray objectAtIndex:indexPath.row];
    NSString*ordrId = ordr.orderId;
    NSString*ordrtype = ordr.orderType;
    double total = ordr.totalAmount;
   long dlvrytme = ordr.deliveryTime;
    NSLog(@"deliverytime:%ld",dlvrytme);
    NSTimeInterval seconds = dlvrytme / 1000;
    NSDate *date = [NSDate dateWithTimeIntervalSince1970:seconds];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init] ;
    [dateFormatter setDateFormat:@"hh:mm a"];  //
    NSLog(@"result: %@", [dateFormatter stringFromDate:date]);
    NSString*result =[dateFormatter stringFromDate:date];
    cell.PlacetimeDply.text = @"";
    cell.DeliverytmeDply.text = result;
    cell.Ordernumber.text = ordrId;
    cell.OrderType.text = ordrtype;
    cell.Totalamt.text = [NSString stringWithFormat:@"Total amount:%.02f",total];
  
    [cell layoutIfNeeded];
    return cell;
}

-(void)jsondata{
    NSString*userid = [[LoginObject getInstance] login];
    if(userid == NULL)
    {
        
    }
    else{
        NSError*error;
        NSURL *url   = [NSURL URLWithString:orderHistory];
         NSURL*url1 = [url URLByAppendingPathComponent:userid];
        NSData *data = [NSData dataWithContentsOfURL:url1];
        json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
        NSLog(@"json:%@",json);
        OrderArray = [[NSMutableArray alloc] init];
        
        for(int i = 0;i<json.count ; i++)
        {
            NSString*OrderId = [[json objectAtIndex:i] objectForKey:@"orderId"];
            NSDate*Orderdate = [[json objectAtIndex:i] objectForKey:@"orderDate"];
            int Token = [[[json objectAtIndex:i] objectForKey:@"token"] intValue];
            NSString*Orderstatus = [[json objectAtIndex:i] objectForKey:@"orderStatus"];
            NSString*Ordertype = [[json objectAtIndex:i] objectForKey:@"orderType"];
           long Deliverytime = [[[json objectAtIndex:i] objectForKey:@"deliveryTime"] longValue];
            long Processingtime = [[[json objectAtIndex:i] objectForKey:@"processingTime"] longValue];
            NSMutableArray*Products = [[json objectAtIndex:i] objectForKey:@"products"];
            Shipping*Ship  = [[json objectAtIndex:i] objectForKey:@"shipping"];
            Payment*Pay = [[json objectAtIndex:i] objectForKey:@"payment"];
            UsrProfile*Userprofile = [[json objectAtIndex:i] objectForKey:@"userprofile"];
            Stores*Storeplace = [[json objectAtIndex:i] objectForKey:@"store"];
            double Shippingcharges =[[[json objectAtIndex:i] objectForKey:@"shippingCharges"] doubleValue];
            double Transactioncharges = [[[json objectAtIndex:i] objectForKey:@"transactionCharges"] doubleValue];
            double Totalamount = [[[json objectAtIndex:i] objectForKey:@"totalAmount"] doubleValue];
            double Taxes = [[[json objectAtIndex:i] objectForKey:@"taxes"] doubleValue];

    Order*ord = [[Order alloc] initWithorderId:(NSString *) OrderId andorderDate:(NSDate *) Orderdate andtoken:(int) Token andtaxes:(double) Taxes andorderStatus:(NSString *) Orderstatus andtotalAmount:(double) Totalamount andorderType:(NSString *) Ordertype anddeliveryTime:(long) Deliverytime andprocessingTime:(long) Processingtime andshipping:(Shipping *) Ship andpayment:(Payment *) Pay andstore:(Stores *) Storeplace andproducts:(NSMutableArray *) Products andshippingCharges:(double) Shippingcharges andtransactionCharges:(double) Transactioncharges anduserprofile:(UsrProfile *)Userprofile];
         
            [OrderArray addObject:ord];
        
        }
        
        
    }
}


@end

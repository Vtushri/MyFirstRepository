//
//  LocationTable.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
#import "LocationTable.h"
#import "LocationsViewController.h"
#import "Stores.h"
#import "Cartview.h"
#import "SlideNavigationController.h"
#import "MenuSlideViewController.h"
#import "LocationTableViewCell.h"
#import "LoadingView.h"
#import "XMPPFramework.h"
#import "XMPPMessage.h"
#import "XMPPStream.h"
#import "XMPPFramework.h"
#import "AppDelegate.h"
#import "XMPPMessage+XEP_0085.h"
#import "XMPPMessageDeliveryReceipts.h"
#import "OrderNotification.h"
#import "LoginObject.h"
#import "Order.h"
#import "Location.h"
#import "productcategory.h"
#import "ConvertUtility.h"
#import "ResponseMessage.h"
#import "XMPPMessage.h"
#import "XMPPRoster.h"

#import <GoogleMaps/GoogleMaps.h>
#import "StoresCell.h"
//@class OrderNotification;
@interface LocationTable ()
{
    CLLocation*deviceloc;
   
}
@end
#define getStores  @"http://52.74.237.28:8080/ecart/rest/storersrc/storebycity/hyderabad"
@implementation LocationTable
@synthesize json,locations,mytable,classstrng,order,user,_messageDelegate,locationManager,duration;


- (AppDelegate *)appDelegate
{
    return (AppDelegate *)[[UIApplication sharedApplication] delegate];
}
-(XMPPStream *)xmppStream{
    
    return [[self appDelegate] xmppStream];
    
}

- (void)viewDidLoad {
    [super viewDidLoad];
    if([classstrng isKindOfClass:[NSString class]]){
        if([CLLocationManager locationServicesEnabled]){
            [locationManager startUpdatingLocation];
            [self getAPIDirection];
            self.title = @"Select Stores";
            self.mytable = [[UITableView alloc] initWithFrame:CGRectMake(0, 0,self.view.frame.size.width, self.view.frame.size.height)];
            
            self.mytable.delegate =self;
            self.mytable.dataSource=self;
            [self.mytable registerClass:[LocationTableViewCell class] forCellReuseIdentifier:@"LocationCell"];
            [self.mytable reloadData];
            [self retrieveData];
            [self.view addSubview:self.mytable];
        }
        else{
        [self locationAlert];
        [self getAPIDirection];
        self.title = @"Select Stores";
        self.mytable = [[UITableView alloc] initWithFrame:CGRectMake(0, 0,self.view.frame.size.width, self.view.frame.size.height)];
        
        self.mytable.delegate =self;
        self.mytable.dataSource=self;
        [self.mytable registerClass:[LocationTableViewCell class] forCellReuseIdentifier:@"LocationCell"];
        [self.mytable reloadData];
        [self retrieveData];
        [self.view addSubview:self.mytable];
        }
    }
    else{
        self.title = @"Stores";
        self.mytable = [[UITableView alloc] initWithFrame:CGRectMake(0, 0,self.view.frame.size.width, self.view.frame.size.height)];
        
        self.mytable.delegate =self;
        self.mytable.dataSource=self;
        [self.mytable registerClass:[LocationTableViewCell class] forCellReuseIdentifier:@"LocationCell"];
        [self.mytable reloadData];
        [self retrieveData];
        [self.view addSubview:self.mytable];
    }

}
-(void)locationAlert{
    UIAlertController * alertController = [UIAlertController alertControllerWithTitle:@"Location"
                                                                              message: @"Your order is in pick up mode..\n\nEnable your location services for DriveThru"
                                                                       preferredStyle: UIAlertControllerStyleAlert];
    [alertController addAction: [UIAlertAction actionWithTitle:@"Continue" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
    
    }]];
    [alertController addAction: [UIAlertAction actionWithTitle:@"Enable" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
        NSURL *settingsURL = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
        [[UIApplication sharedApplication] openURL:settingsURL];
    }]];
    [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];

}
//- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
//{
//    if (buttonIndex==1) {
//        
//        NSURL *settingsURL = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
//        [[UIApplication sharedApplication] openURL:settingsURL];
//    }
//   
//}
- (void)requestAlwaysAuthorization
{
    CLAuthorizationStatus status = [CLLocationManager authorizationStatus];
    
    // If the status is denied or only granted for when in use, display an alert
    if (status == kCLAuthorizationStatusAuthorizedWhenInUse || status == kCLAuthorizationStatusDenied) {
        NSString *title;
        title = (status == kCLAuthorizationStatusDenied) ? @"Location services are off" : @"Background location is not enabled";
        NSString *message = @"To use background location you must turn on 'Always' in the Location Services Settings";
        
        UIAlertController * alertController = [UIAlertController alertControllerWithTitle: title
                                                                                  message: message
                                                                           preferredStyle: UIAlertControllerStyleAlert];
        UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"Cancel" style: UIAlertActionStyleCancel handler: nil];
        [alertController addAction:cancel];
        
        UIAlertAction *second = [UIAlertAction actionWithTitle: @"Settings"
                                                         style: UIAlertActionStyleDefault
                                                       handler: ^(UIAlertAction *action) {
                                                           NSURL *settingsURL = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
                                                           [[UIApplication sharedApplication] openURL:settingsURL];
                                                           
                                                       }];
        [alertController addAction: second];
       
        [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];

    }
    
    else if (status == kCLAuthorizationStatusNotDetermined) {
        [self.locationManager requestAlwaysAuthorization];
    }
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
   [self.mytable reloadData];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    // Return the number of sections.
    return 1;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
      return 150;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString*cellIdentifier = @"LocationCell";
    StoresCell *cell =[self.mytable dequeueReusableCellWithIdentifier:cellIdentifier];
    if(cell==nil){
        cell = [[StoresCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    cell=nil;
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"StoresCell" owner:self options:nil];
    cell =[tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    cell = [nib objectAtIndex:0];
 
    for (id oneObject in nib)
        if ([oneObject isKindOfClass:[StoresCell class]])
            cell = (StoresCell *)oneObject;
    Stores *location = [locations objectAtIndex:indexPath.row];
    NSDictionary *dict = @{
                           @"row": @(indexPath.row),
                           @"section": @(indexPath.section)
                           };

    indexpath = indexPath;
    NSNumber*latitude = location.latitude;
    NSNumber*longitude = location.longitude;
    NSUserDefaults*defaults = [NSUserDefaults standardUserDefaults];
    if (defaults) {
        [defaults setValue:latitude forKey:@"latitude"];
        [defaults setValue:longitude forKey:@"longitude"];
        [defaults setValue:dict forKey:@"location"];
        [defaults synchronize];
    }
   
    NSString*Name = location.name;
    NSString*city = location.storeCity;
    NSString*locAdd = location.storeAddress;
    [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
    cell.name.text = Name;
    cell.address.text = locAdd;
    cell.address.font = [UIFont fontWithName:@"Times New Roman" size:13];
    cell.address.numberOfLines = 4;
    cell.city.text = city;
    cell.pointerimage.image = [UIImage imageNamed:@"Google-Maps-Pointer-3.jpg"];
    cell.distance.text = nil;
    cell.time.text =nil;
   [cell layoutIfNeeded];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    if([classstrng isKindOfClass:[NSString class]]){
        LocationsViewController*lvc = [[LocationsViewController alloc] init];
        Stores *currentlocation = [locations objectAtIndex:indexPath.row];
         lvc.latitude= currentlocation.latitude;
        lvc.longitude=currentlocation.longitude;
        lvc.name =currentlocation.name;
        lvc.storeaddress =currentlocation.storeAddress;
        lvc.StoreId = currentlocation.storeId;
       
        [self sendmessagetoxmpp];
        //[self.navigationController pushViewController:lvc animated:YES];
        
        }
    else{
        LocationsViewController*lvc = [[LocationsViewController alloc] init];
        Stores *currentlocation = [locations objectAtIndex:indexPath.row];
        
        lvc.latitude= currentlocation.latitude;
        lvc.longitude=currentlocation.longitude;
        lvc.name =currentlocation.name;
        lvc.storeaddress =currentlocation.storeAddress;
        
        [self.navigationController pushViewController:lvc animated:YES];
    }

    
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return locations.count;
}
-(void)retrieveData
{
    NSError*error;
    
    NSURL * url = [NSURL URLWithString:getStores];
    NSData*data = [NSData dataWithContentsOfURL:url];
    
    json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    
    locations = [[NSMutableArray alloc] init];
    for(int i=0;i<json.count;i++)
    {
        NSString * cStoreId = [[json objectAtIndex:i] objectForKey:@"storeId"];
        NSString * cName = [[json objectAtIndex:i] objectForKey:@"name"];
        NSNumber * cLatitude = [[json objectAtIndex:i] objectForKey:@"latitude"];
        NSNumber * cLongitude = [[json objectAtIndex:i] objectForKey:@"longitude"];
        NSString * cStoreAddress = [[json objectAtIndex:i] objectForKey:@"storeAddress"];
        NSString * cStoreCity = [[json objectAtIndex:i] objectForKey:@"storeCity"];
        NSString * cOrder = [[json objectAtIndex:i] objectForKey:@"order"];
        NSMutableArray * cUser = [[json objectAtIndex:i] objectForKey:@"user"];
        
        
        Stores *mylocations = [[Stores alloc] initWithstoreId:(NSString *) cStoreId andname:(NSString *) cName andlatitude:(NSNumber *) cLatitude andlongitude:(NSNumber *) cLongitude andstoreAddress:(NSString *) cStoreAddress andstoreCity:(NSString *) cStoreCity andorder:(NSString *) cOrder anduser:(NSMutableArray *) cUser];
              
        [locations addObject:mylocations];
    }
}

-(void)getAPIDirection{
    if([CLLocationManager locationServicesEnabled]){
        
        AppDelegate *appDelegate=(AppDelegate *)[UIApplication sharedApplication].delegate;
        CLLocation *currentLocation=appDelegate.locationManager.location;
        CLLocationCoordinate2D coordinates;
        coordinates.latitude = currentLocation.coordinate.latitude;
        coordinates.longitude = currentLocation.coordinate.longitude;
       NSUserDefaults*defaults = [NSUserDefaults standardUserDefaults];
      //  Stores*loca = [locations objectAtIndex:row1.row];
        NSNumber*latu = [defaults valueForKey:@"latitude"];
        NSNumber*longu = [defaults valueForKey:@"longitude"];
       
      //  NSNumber*latu = loca.latitude;
      //  NSNumber*longu = loca.longitude;
    //Replace origin values with coordinates lat/long before installing on device
        
  NSString *urlString = [NSString stringWithFormat:
                           @"%@?origin=%f,%f&destination=%@,%@&sensor=false",
                           @"https://maps.googleapis.com/maps/api/directions/json",
                           17.426565, 78.406808,
                           latu,
                         longu];
    NSLog(@"Url string:%@",urlString);
    NSURL *directionsURL = [NSURL URLWithString:urlString];
   
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:directionsURL];
    NSURLSession*session = [NSURLSession sharedSession];
        [session dataTaskWithRequest:request completionHandler:^(NSData *data,
                                                                 NSURLResponse *response,
                                                                 NSError *error)
               {
                if(error)
                       {
                          NSLog(@"check your connection");
                       }
                  else
                      {
          NSDictionary*apidata = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];
           
          NSDictionary *arr=apidata[@"routes"][0][@"legs"];
                 
          NSMutableArray *loc=[[NSMutableArray alloc]init];
                 
         for (NSIndexPath *path in [self.mytable indexPathsForVisibleRows])
                    {
         StoresCell *cell = (StoresCell *)[self.mytable cellForRowAtIndexPath:path];
             
          loc=[[arr valueForKey:@"distance"]valueForKey:@"text"];
          _dist=loc[0];
          cell.distance.text = [NSString stringWithFormat:@"Distance:%@",loc[0]];
                        cell.distance.textColor = [UIColor greenColor];
                        cell.distance.textAlignment = NSTextAlignmentCenter;
          loc=[[arr valueForKey:@"duration"]valueForKey:@"text"];
          duration=loc[0];
          cell.time.text = [NSString stringWithFormat:@"Time:%@",loc[0]];
                        cell.time.textColor = [UIColor greenColor];
                        cell.time.textAlignment = NSTextAlignmentCenter;
                    }
              }
       }];
   }
    else{
        for (NSIndexPath *path in [self.mytable indexPathsForVisibleRows])
        {
            StoresCell *cell = (StoresCell *)[self.mytable cellForRowAtIndexPath:path];
            cell.distance.text = nil;
            cell.time.text =nil;
        }
    }
}
- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation{
    NSLog(@"oldLocation %f %f",newLocation.coordinate.latitude,newLocation.coordinate.longitude);
    [locationManager stopUpdatingLocation];
    CLLocation *location = [locationManager location];
     float longitude=location.coordinate.longitude;
     float latitude=location.coordinate.latitude;
    deviceloc = [[CLLocation alloc] initWithLatitude:latitude longitude:longitude];
    NSLog(@"user:%@",deviceloc);
}
-(void)sendmessagetoxmpp{
    
    NSIndexPath*index = [self.mytable indexPathForSelectedRow];
    Stores *currentlocation = [locations objectAtIndex:index.row];
    NSString*storeid = currentlocation.storeId;
    ConvertUtility*cu= [[ConvertUtility alloc] init];
    
    AppDelegate *appDelegate=(AppDelegate *)[UIApplication sharedApplication].delegate;
    CLLocation *currentLocation=appDelegate.locationManager.location;
    CLLocationCoordinate2D coordinates;
    coordinates.latitude = currentLocation.coordinate.latitude;
    coordinates.longitude = currentLocation.coordinate.longitude;
    NSString*lati = [NSString stringWithFormat:@"%f",coordinates.latitude];
    NSString*longi = [NSString stringWithFormat:@"%f",coordinates.longitude];
    Location*loc  =[[Location alloc] initWithlatitude:lati andlongitude:longi andaltitude:nil];
     User*usr = [[LoginObject getInstance]getuser];
    NSLog(@"user:%@",usr);
    NSDictionary*dict = [[LoginObject getInstance] dict];
    
    NSTimeInterval seconds = [[NSDate date] timeIntervalSince1970];
    long  Arrivaltime = [[NSNumber numberWithDouble:seconds]longValue] + 3600;
    NSString*userid = [[NSUserDefaults standardUserDefaults] valueForKey:@"userAccountId"];
    NSString *host = [userid stringByAppendingString:@"@52.74.237.28/Smack"];
    XMPPJID * roomJID = [XMPPJID jidWithString:host];
    
    [[[self appDelegate ]xmppRoster] addUser:roomJID withNickname:storeid groups:nil subscribeToPresence:YES];
    AppDelegate*ap = [[AppDelegate alloc] init];
    ap.odr = order;
    [self.delegate order:order];
    OrderNotification*ordnotify  = [[OrderNotification alloc] init];
    ordnotify.order = order;
    ordnotify.messageType = [ordnotify converttoString:NOTIFY];
    ordnotify.orderType = [ordnotify convertToString:DRIVETHRU];
    ordnotify.location = loc;
    ordnotify.arrivalTime = Arrivaltime;
    
    NSMutableDictionary*orderDict = [cu dictionaryWithPropertiesOfObject:ordnotify];
    [orderDict setObject:dict forKey:@"from"];
   
    NSError *serr;
    
    NSData *jsonData = [NSJSONSerialization
                        dataWithJSONObject:orderDict options:NSJSONWritingPrettyPrinted error:&serr];
    if (serr)
    {
        NSLog(@"Error generating json data for send dictionary...");
        NSLog(@"Error (%@), error: %@", jsonData, serr);
        return;
    }
   
    NSLog(@"Successfully generated JSON for send dictionary");
    
    NSString * converted =[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        if(converted> 0)
        {
    
            NSXMLElement *body = [NSXMLElement elementWithName:@"body"];
    
            [body setStringValue:converted];
            NSString *host = [storeid stringByAppendingString:@"@52.74.237.28"];
            NSXMLElement *message = [NSXMLElement elementWithName:@"message"];
            [message addAttributeWithName:@"type"stringValue:@"chat"];
            [message addAttributeWithName:@"to"stringValue:host];
            [message addChild:body];
            
        NSXMLElement * thread = [NSXMLElement elementWithName:@"thread" stringValue:@"conversation"];
            [message addChild:thread];
            [message addAttributeWithName:@"id" stringValue:[self.xmppStream generateUUID]];
            [[self appDelegate].xmppStream sendElement:message];
          
        }
}

    @end

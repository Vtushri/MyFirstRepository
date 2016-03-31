//
//  LocationsViewController.h
//  example2
//
//  Created by Hariteja P on 28/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Stores.h"
#import <MapKit/MapKit.h>
@interface LocationsViewController : UIViewController<MKMapViewDelegate,CLLocationManagerDelegate>
@property (nonatomic,strong) IBOutlet UIView *MyMap;

@property (nonatomic,strong) IBOutlet UIButton *navButton;
@property (nonatomic,strong) NSDictionary*locationdic;
@property(nonatomic,strong) NSNumber*latitude;
@property (nonatomic,strong)NSNumber *longitude;
@property(nonatomic, strong) CLLocationManager *locationManager;
@property (weak, readonly) UIActivityIndicatorView *spinner;
@property (weak, readonly) UIActivityIndicatorView *currentLocationActivityIndicatorView;
@property (nonatomic,strong) NSString*name;
@property (nonatomic,strong) NSString*storeaddress;
@property (nonatomic,strong) NSString*StoreId;
@property (nonatomic,strong) NSString*classstrng1;
-(IBAction)myButton:(id)sender;
@end

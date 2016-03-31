//
//  LocationsViewController.m
//  example2
//
//  Created by Hariteja P on 28/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//
#import "AppDelegate.h"
#import "LocationsViewController.h"
#import "MapView.h"
#import "LocationTable.h"
#import <CoreLocation/CoreLocation.h>
#import "cartView.h"
#import "Location.h"
#import "LoginObject.h"

@import GoogleMaps;

@interface LocationsViewController ()<CLLocationManagerDelegate,GMSMapViewDelegate>
{
    GMSMapView *mapView_;
}
@property (nonatomic,strong) UILabel*longitudeLabel;
@property (nonatomic,strong) UILabel*latitudeLabel;

@end

@implementation LocationsViewController
@synthesize name,storeaddress,locationManager,MyMap,navButton,locationdic;

- (AppDelegate *)appDelegate
{
    
    return (AppDelegate *)[[UIApplication sharedApplication] delegate];
}
- (XMPPStream *)xmppStream {
    return [[self appDelegate] xmppStream];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    locationManager = [[CLLocationManager alloc] init];
    [locationManager setDelegate:self];
    [locationManager setDistanceFilter:kCLDistanceFilterNone];
    [locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
    
    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0)
        [self.locationManager requestWhenInUseAuthorization];
    [locationManager startUpdatingLocation];
    
    [self requestAlwaysAuthorization];
    
    navButton = [[UIButton alloc] initWithFrame:CGRectMake(269,28,90,34)];
    [navButton setTitle:@">>BACK" forState:UIControlStateNormal];
    [navButton setUserInteractionEnabled:YES];
    [navButton setTintColor:[UIColor purpleColor]];
    [navButton addTarget:self action:@selector(myButton:)forControlEvents:UIControlEventTouchDown];
    [navButton setBackgroundColor:[UIColor orangeColor]];
    
    GMSCameraPosition *camera = [GMSCameraPosition cameraWithLatitude:[self.latitude doubleValue]
                                                            longitude:[self.longitude doubleValue]
                                                                 zoom:14];
    mapView_ = [GMSMapView mapWithFrame:CGRectZero camera:camera];
    
    mapView_.delegate =self;
    mapView_.settings.compassButton = YES;
    mapView_.settings.myLocationButton = YES;
//    [mapView_ addObserver:self
//               forKeyPath:@"myLocation"
//                  options:NSKeyValueObservingOptionNew
//                  context:NULL];
    dispatch_async(dispatch_get_main_queue(), ^{
        mapView_.myLocationEnabled = YES;
    });
    self.view = mapView_;
    
    GMSMarker *marker = [[GMSMarker alloc] init];
    marker.position = CLLocationCoordinate2DMake([self.latitude doubleValue],[self.longitude doubleValue]);
    marker.title = name;
    marker.snippet = storeaddress;
    marker.map = mapView_;
    CLLocation *myLocation = mapView_.myLocation;
   NSLog(@"latitude%f longitude %f",myLocation.coordinate.latitude, myLocation.coordinate.longitude);
    [self.MyMap addSubview:navButton];
   
 }

- (void)requestAlwaysAuthorization
{
    CLAuthorizationStatus status = [CLLocationManager authorizationStatus];
    
    // If the status is denied or only granted for when in use, display an alert
    if (status == kCLAuthorizationStatusAuthorizedWhenInUse || status == kCLAuthorizationStatusDenied) {
        NSString *title;
        title = (status == kCLAuthorizationStatusDenied) ? @"Location" : @"Background location is not enabled";
        NSString *message = @"Enable your location to find your distance from stores";
        
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


- (void)mapView:(MKMapView *)mapView didUpdateUserLocation:(MKUserLocation *)userLocation
{

[mapView setRegion:MKCoordinateRegionMake(userLocation.coordinate,MKCoordinateSpanMake(0.1f, 0.1f))animated:YES];
    
}
-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [mapView_ addObserver:self forKeyPath:@"myLocation" options:NSKeyValueObservingOptionNew context: nil];
}

-(void)viewWillDisappear:(BOOL)animated{
    [super viewWillDisappear:animated];
    [mapView_ removeObserver:self forKeyPath:@"myLocation"];
}

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if ([keyPath isEqualToString:@"myLocation"] && [object isKindOfClass:[GMSMapView class]])
    {
        NSLog(@"KVO triggered. Location Latitude: %f Longitude: %f",mapView_.myLocation.coordinate.latitude,mapView_.myLocation.coordinate.longitude);
    }
}

- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation {
    NSLog(@"oldLocation %f %f",newLocation.coordinate.latitude,newLocation.coordinate.longitude);
    NSLog(@"newLocation %f %f", [self.latitude doubleValue], [self.longitude doubleValue]);
    [locationManager stopUpdatingLocation];
    CLLocation *location = [locationManager location];
    float longitude=location.coordinate.longitude;
    float latitude=location.coordinate.latitude;
    CLLocation *user = [[CLLocation alloc] initWithLatitude:latitude longitude:longitude];
    CLLocation*store = [[CLLocation alloc] initWithLatitude:[self.latitude doubleValue] longitude:[self.longitude doubleValue]];
   float distInMeter = [user distanceFromLocation:store];
   float distInMile = 0.000621371192 * distInMeter;
    NSString *distance = [NSString stringWithFormat:@"%f",distInMile];
    
    NSLog(@"distance:%@",distance);
}
-(void)locationManager:(CLLocationManager *)manager didChangeAuthorizationStatus:(CLAuthorizationStatus)status
{
    if(status==kCLAuthorizationStatusAuthorizedAlways || status==kCLAuthorizationStatusAuthorizedWhenInUse){
        //self.MyMap.showsUserLocation =YES;
    }
}

- (void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    NSLog(@"Error while getting core location : %@",[error localizedFailureReason]);
    [manager stopUpdatingLocation];
    NSLog(@"error%@",error);
    switch([error code])
    {
        case kCLErrorNetwork:
        {
            UIAlertController * alertController = [UIAlertController alertControllerWithTitle:@"Error"
                                                                                      message: @"Please check your Network Connection or you are not in Airplane Mode"
                                                                               preferredStyle: UIAlertControllerStyleAlert];
            UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"ok" style: UIAlertActionStyleCancel handler: nil];
            [alertController addAction:cancel];
            
           [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];
          
        }
            break;
        case kCLErrorDenied:{
            UIAlertController * alertController = [UIAlertController alertControllerWithTitle:@"Error"
                                                                                      message: @"User has denied to use your current location"
                                                                               preferredStyle: UIAlertControllerStyleAlert];
            UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"ok" style: UIAlertActionStyleCancel handler: nil];
            [alertController addAction:cancel];
            
            [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];
                    }
            break;
        default:
        {
            UIAlertController * alertController = [UIAlertController alertControllerWithTitle:@"Error"
                                                                                      message: @"Unknown Network error"
                                                                               preferredStyle: UIAlertControllerStyleAlert];
            UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"ok" style: UIAlertActionStyleCancel handler: nil];
            [alertController addAction:cancel];
            
            [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];
        }
            break;
    }
}


- (IBAction)myButton:(id)sender {
    
   
    cartView*vc1 = [[cartView alloc] init];
    [self.navigationController pushViewController:vc1 animated:YES];
    
}


@end

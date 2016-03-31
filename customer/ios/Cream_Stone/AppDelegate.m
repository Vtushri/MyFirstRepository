//
//  AppDelegate.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "GCDAsyncSocket.h"
#import "AppDelegate.h"
#import "loginViewController.h"
#import "SlideNavigationController.h"
#import "screenview.h"
#import "MenuTabViewController.h"
#import "LoginObject.h"
#import "SlideNavigationContorllerAnimatorSlideAndFade.h"
#import "SlideNavigationContorllerAnimatorSlide.h"
#import "SlideNavigationContorllerAnimatorFade.h"
#import "SlideNavigationContorllerAnimatorScaleAndFade.h"
#import "XMPPStream.h"
#import "XMPPFramework.h"
#import "DDLog.h"
#import "DDTTYLogger.h"
#import "XMPP.h"
#import "XMPPLogging.h"
#import "XMPPPresence.h"
#import <CFNetwork/CFNetwork.h>
#import "LocationsViewController.h"
#import "LocationTable.h"
#import <GoogleMaps/GoogleMaps.h>
#import "DBManager.h"
#import "ResponseMessage.h"
#import "Order.h"
#import "OrderNotification.h"
#import "XMPPMessage.h"
#import "PaymentViewController.h"
#import "PayPalMobile.h"
#import "ConvertUtility.h"
#import <FBSDKCoreKit/FBSDKCoreKit.h>


#if DEBUG
static const int ddLogLevel = LOG_LEVEL_VERBOSE;
#else
static const int ddLogLevel = LOG_LEVEL_INFO;
#endif

#define xmpphost_name @"52.74.237.28"
#define PostOrder @"http://52.74.237.28:8080/ecart/rest/orderrsrc/create_order"
#define statuscheck @"http://52.74.237.28:8080/ecart/rest/userrsrc/isregistered"
#define kPayPalEnvironment PayPalEnvironmentSandbox
@interface AppDelegate (){
    Order*order;
    NSMutableArray*Response;
    CLLocation *user;
}
@property(nonatomic,strong)SlideNavigationContorllerAnimatorFade*fade;
@property(nonatomic,strong)SlideNavigationContorllerAnimatorSlide*slide;
@property(nonatomic, strong, readwrite) PayPalConfiguration *payPalConfig;
@property (nonatomic,strong) IBOutlet UIView*successView;
@end

@implementation AppDelegate
@synthesize locationManager,xmppStream,password,xmppStorage;


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    
        [GMSServices provideAPIKey:@"AIzaSyCgBGKPE-OeWUCpIz2rRJ-LSKbOHJQUOtk"];
    
    [[FBSDKApplicationDelegate sharedInstance] application:application
                             didFinishLaunchingWithOptions:launchOptions];
    [PayPalMobile initializeWithClientIdsForEnvironments:@{PayPalEnvironmentSandbox :@"APP-80W284485P519543T"}];
    [self getCurrentlocation];
    
    MenuTabViewController*leftMenu = [[MenuTabViewController alloc] init];
    id<SlideNavigationContorllerAnimator>reveal;
    [SlideNavigationController sharedInstance].leftMenu = leftMenu;
    [SlideNavigationController sharedInstance].menuRevealAnimationDuration = .18;
    [[SlideNavigationController sharedInstance] setMenuRevealAnimator:reveal];
    self.fade = [[SlideNavigationContorllerAnimatorFade alloc] initWithMaximumFadeAlpha:0.8 andFadeColor:[UIColor blackColor]];
    self.slide = [[SlideNavigationContorllerAnimatorSlide alloc] initWithSlideMovement:100];
   
    [SlideNavigationController sharedInstance].enableShadow = YES;

    xmppStream = [[XMPPStream alloc] init];
    [self.xmppStream addDelegate:self delegateQueue:dispatch_get_main_queue()];
    self.xmppStream.hostName = @"52.74.237.28";
    self.xmppStream.hostPort = 5222;
   
    [self connect];
    self.xmppStream.enableBackgroundingOnSocket =YES;
   
    
    // Set up payPalConfig
    _payPalConfig = [[PayPalConfiguration alloc] init];
    _payPalConfig.acceptCreditCards = YES;
    _payPalConfig.merchantName = @"FreshBake";
    _payPalConfig.merchantPrivacyPolicyURL = [NSURL URLWithString:@"https://www.paypal.com/webapps/mpp/ua/privacy-full"];
    _payPalConfig.merchantUserAgreementURL = [NSURL URLWithString:@"https://www.paypal.com/webapps/mpp/ua/useragreement-full"];
    _payPalConfig.languageOrLocale = [NSLocale preferredLanguages][0];
    
    
    _payPalConfig.payPalShippingAddressOption = PayPalShippingAddressOptionPayPal;
    
    self.environment = kPayPalEnvironment;
    
    NSLog(@"PayPal iOS SDK version: %@", [PayPalMobile libraryVersion]);
    
      return YES;
      
}
//+ (BOOL)allowsAnyHTTPSCertificateForHost:(NSString *)host {
//    return YES;
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
//        [alertController addAction: [UIAlertAction actionWithTitle: @"Settings" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
//
//            NSURL *settingsURL = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
//            [[UIApplication sharedApplication] openURL:settingsURL];
//
//        }]];
        [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];
        
    }
    
    else if (status == kCLAuthorizationStatusNotDetermined) {
        [self.locationManager requestAlwaysAuthorization];
    }
}
- (void)disconnect
{
    [self goOffline];
    [xmppStream disconnect];
}

- (BOOL)connect {
    NSString*userid = [[NSUserDefaults standardUserDefaults] valueForKey:@"userAccountId"];
    NSString *host = [userid stringByAppendingString:@"@52.74.237.28/Smack"];
    NSString*pwd = @"123456";
    NSLog(@"host:%@",host);
    if (![xmppStream isDisconnected]) {
        return YES;
       
    }
    if (host == nil ||pwd == nil) {
        return NO;
    }
    
    [self.xmppStream setMyJID:[XMPPJID jidWithString:host]];
    
    
    NSError *error = nil;
    if (![xmppStream connectWithTimeout:XMPPStreamTimeoutNone error:&error])
    {
        UIAlertController * alertController = [UIAlertController alertControllerWithTitle: @"Error Connecting"
                                                                                  message: @"See Console for Error Details"
                                                                           preferredStyle: UIAlertControllerStyleAlert];
        [alertController addAction: [UIAlertAction actionWithTitle:@"ok" style: UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            
            
        }]];
        [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];
        DDLogError(@"Error connecting: %@", error);
        
        return NO;
    }
    [self goOnline];
     return YES;
}
- (void)xmppStreamDidAuthenticate:(XMPPStream *)sender

{
    isauthenticate=YES;
    
    DDLogVerbose(@"%@: %@", THIS_FILE, THIS_METHOD);
    
    [self goOnline];
}
- (void)xmppStream:(XMPPStream *)sender didNotAuthenticate:(NSXMLElement *)error
{
    DDLogVerbose(@"%@: %@", THIS_FILE, THIS_METHOD);
}
- (void)xmppStreamDidConnect:(XMPPStream *)sender {
    NSError *error = nil;
    NSString*pwd =@"123456";
    if (![self.xmppStream authenticateWithPassword:pwd error:&error]) {
        
        UIAlertController * alert = [UIAlertController alertControllerWithTitle:@"Error"
                                                                        message:[NSString stringWithFormat:@"Can't authenticate %@", [error localizedDescription]]
                                                                 preferredStyle: UIAlertControllerStyleAlert];
        UIAlertAction *ok = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleCancel handler: nil];
        
        [alert addAction:ok];
        [[SlideNavigationController sharedInstance] presentViewController:alert animated:YES completion:nil];
    }
    [self.xmppStream sendElement:[XMPPPresence presence]];
    //[self goOnline];
    
}

- (void)xmppStreamDidDisconnect:(XMPPStream *)sender withError:(NSError *)error
{
    DDLogVerbose(@"%@: %@", THIS_FILE, THIS_METHOD);
    
    if (!isXmppConnected)
    {
        DDLogError(@"Unable to connect to server. Check xmppStream.hostName");
    }
}
- (void)goOffline
{
    XMPPPresence *presence = [XMPPPresence presenceWithType:@"unavailable"];
    
    [[self xmppStream] sendElement:presence];
}

- (void)goOnline
{
    XMPPPresence *presence = [XMPPPresence presence];
    [[self xmppStream] sendElement:presence];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if(alertView.tag == 100) {
        if (buttonIndex == 1) {
        // Send the user to the Settings for this app
        NSURL *settingsURL = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
        [[UIApplication sharedApplication] openURL:settingsURL];
    }
    }
    else if(alertView.tag == 110 || alertView.tag==120 || alertView.tag == 130) {
        if(buttonIndex == 1){
            [self payment];
        }
    }
    
}

- (void)xmppRoster:(XMPPRoster *)sender didReceivePresenceSubscriptionRequest:(XMPPPresence *)presence{
    NSString*userid = [[NSUserDefaults standardUserDefaults] valueForKey:@"userAccountId"];
    NSString *host = [userid stringByAppendingString:@"@52.74.237.28/Smack"];
    XMPPJID*jiD = [XMPPJID jidWithString:host];
    [self.xmppRoster acceptPresenceSubscriptionRequestFrom:[presence from] andAddToRoster:YES];
    NSLog(@"presence:%@",presence);
    
    [self.xmppRoster acceptPresenceSubscriptionRequestFrom:jiD andAddToRoster:NO];
    
    
}
- (void)xmppStream:(XMPPStream *)sender didReceivePresence:(XMPPPresence *)presence
{
    NSXMLElement *showStatus = [presence elementForName:@"status"];
    NSString *presenceString = [showStatus stringValue];
    NSString *customMessage = [[presence elementForName:@"show"]stringValue];
    
    NSLog(@"Presence : %@, and presenceMessage: %@",presenceString,customMessage);
}
- (void)xmppStream:(XMPPStream *)sender didReceiveError:(id)error
{
    DDLogVerbose(@"%@: %@", THIS_FILE, THIS_METHOD);
}
- (void)xmppStream:(XMPPStream *)sender didSendMessage:(XMPPMessage *)message{
    //NSLog(@"messageSent=%@",message);
}

-(void)xmppStream:(XMPPStream *)sender didReceiveMessage:(XMPPMessage *)message{
    
    
    OrderNotification*ordn = [[OrderNotification alloc] init];
    if ([message isChatMessageWithBody])
    {
        NSString *body = [[message elementForName:@"body"] stringValue];
        NSString *from = [[message attributeForName:@"from"] stringValue];
        NSLog(@"Data:%@",body);
        NSMutableDictionary *m = [[NSMutableDictionary alloc] init];
        [m setObject:body forKey:@"msg"];
        [m setObject:from forKey:@"sender"];
        
        NSData *data = [body dataUsingEncoding:NSUTF8StringEncoding];
      NSMutableArray* json = [NSJSONSerialization JSONObjectWithData:data options:0 error:nil];
      
           int Available =[[json  valueForKey:@"available"] intValue];
           NSString*ordertype = [json valueForKey:@"orderType"];
           long processingtime = [[json valueForKey:@"processingTime"] longValue];
           long delivery = [[json valueForKey:@"deliveryTime"] longValue];

         NSDate*deliveryformat =[NSDate dateWithTimeIntervalSince1970:[[NSNumber numberWithLong:delivery] doubleValue]];
        NSLog(@"delveryDate:%@",deliveryformat);

        NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
        [formatter setDateFormat:@"hh:mm p"];
        NSString*deliverystatus = [NSString stringWithFormat:@"%@",[formatter stringFromDate:deliveryformat]];
        NSLog(@"Deliverytime:%@", [formatter stringFromDate:deliveryformat]);
        
        ResponseMessage*respmsg = [[ResponseMessage alloc] initWithavailable:(int)Available andorderType:(NSString *)ordertype andprocessingTime:(long)processingtime anddeliveryTime:(NSDate *)deliveryformat];
        order = [[Order alloc] init];
        
       if ([[UIApplication sharedApplication] applicationState] == UIApplicationStateActive)
        {
            NSLog(@"Applications are in active state");
           if(Available==1)
                {
                 order.processingTime = respmsg.processingTime;
                 order.deliveryTime = respmsg.deliveryTime;
                 order.orderType =respmsg.orderType;
                 
                    UIAlertController * alert = [UIAlertController alertControllerWithTitle: nil
                                                                                    message:@"Available"
                                                                             preferredStyle: UIAlertControllerStyleAlert];
                   UIAlertAction *ok = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleDefault handler: nil];
                    
                    [alert addAction:ok];
                    [[SlideNavigationController sharedInstance] presentViewController:alert animated:YES completion:nil];
                    LoginObject*lo = [LoginObject getInstance];
                    double amt = lo.totalAmt;
                    NSMutableArray*pro = lo.pro;
                    order.totalAmount =amt;
                    order.products = pro;
                    ConvertUtility*cu = [[ConvertUtility alloc] init];
                    NSDictionary*dict = [cu dictionaryWithPropertiesOfObject:order];
                    NSLog(@"dictionary:%@",dict);
                    
                    
                   [self orderrequest];
                   
                  //[self payment];
                    
                }

            else if(!(respmsg.processingTime)== 0)
                {
                    
                    order.processingTime = respmsg.processingTime;
                    order.deliveryTime =deliveryformat;
                    order.orderType =[ordn convertToString:PICKUP];
                    
                    LoginObject*lo = [LoginObject getInstance];
                    double amt = lo.totalAmt;
                    NSMutableArray*pro = lo.pro;
                    order.totalAmount =amt;
                    order.products = pro;
                    ConvertUtility*cu = [[ConvertUtility alloc] init];
                    NSDictionary*dict = [cu dictionaryWithPropertiesOfObject:order];
                    NSLog(@"dictionary:%@",dict);
                   
                    CLAuthorizationStatus status = [CLLocationManager authorizationStatus];
                    
                    // If the status is denied or only granted for when in use, display an alert
                    if (status == kCLAuthorizationStatusAuthorizedWhenInUse || status == kCLAuthorizationStatusDenied)
                    {
                        UIAlertController * alert = [UIAlertController alertControllerWithTitle: @"Order DriveThru"
                                                                                        message:[NSString stringWithFormat:@"You Order is not available for DriveThru at specified time...But you can pickup at %@",deliverystatus]
                                                                                 preferredStyle: UIAlertControllerStyleAlert];
                        
                        UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"Cancel" style: UIAlertActionStyleCancel handler: nil];
                        
                        [alert addAction:cancel];
                        
                        UIAlertAction *ok = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleDefault handler: nil];
                        
                        [alert addAction:ok];
                        [[SlideNavigationController sharedInstance] presentViewController:alert animated:YES completion:nil];
                    }
                    
                    else{
                        
                        UIAlertController * alert = [UIAlertController alertControllerWithTitle: @"Order Pickup"
                                                                                        message:[NSString stringWithFormat:@"You Order is not available for Pickup at specified time...But you can pickup at %@",deliverystatus]
                                                                          preferredStyle: UIAlertControllerStyleAlert];
                        
                        UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"Cancel" style: UIAlertActionStyleCancel handler: nil];
                        
                        [alert addAction:cancel];
                        
                        UIAlertAction *ok = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleDefault handler: nil];
                        
                        [alert addAction:ok];
                        [[SlideNavigationController sharedInstance] presentViewController:alert animated:YES completion:nil];
                    }
                }
                else{
                    UIAlertController * alert = [UIAlertController alertControllerWithTitle: nil
                                                                                     message: @"This Order is not available"
                                                                             preferredStyle: UIAlertControllerStyleAlert];
                    
                    UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"Cancel" style: UIAlertActionStyleCancel handler: nil];
                    
                    [alert addAction:cancel];
                    
                    UIAlertAction *ok = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleDefault handler: nil];
                                         
                    [alert addAction:ok];
                    [[SlideNavigationController sharedInstance] presentViewController:alert animated:YES completion:nil];
                }
        }
    }
    else{
        NSLog(@"Message Body is Not Found");
    }
}
//-(void)byeAlertView1:(UIAlertView *)alert1{
//    [alert1 dismissWithClickedButtonIndex:0 animated:YES];
//}
//-(void)byeAlertView2:(UIAlertView *)alert2{
//    [alert2 dismissWithClickedButtonIndex:0 animated:YES];
//}
//-(void)byeAlertView3:(UIAlertView *)alert3{
//    [alert3 dismissWithClickedButtonIndex:0 animated:YES];
//}
//-(void)byeAlertView4:(UIAlertView *)alert4{
//    [alert4 dismissWithClickedButtonIndex:0 animated:YES];
//}

- (NSManagedObjectContext *)managedObjectContext_roster{
    return [xmppRosterStorage mainThreadManagedObjectContext];

}
-(void)payment{
    self.resultText = nil;
    LoginObject*lo = [LoginObject getInstance];
    double amt = lo.totalAmt;
    NSString*total = [NSString stringWithFormat:@"%f",amt];
    NSLog(@"total=%@",total);
    PayPalPayment *payment = [[PayPalPayment alloc] init];
    NSString*userid = [[NSUserDefaults standardUserDefaults] valueForKey:@"userAccountId"];
    payment.currencyCode = @"USD";
    payment.amount = [[NSDecimalNumber alloc] initWithString:total];
    payment.shortDescription = userid;
    payment.paymentDetails = nil;
    payment.items =nil;
   // payment.intent = PayPalPaymentIntentSale;
    
    if (!payment.processable) {
        NSLog(@"payment is not possible");
    }
    PayPalPaymentViewController *paymentViewController;
    paymentViewController = [[PayPalPaymentViewController alloc] initWithPayment:payment
                                                                   configuration:self.payPalConfig                                                                                 delegate:self];
   [[SlideNavigationController sharedInstance] presentViewController:paymentViewController animated:YES completion:nil];
}
-(void)payPalPaymentViewController:(PayPalPaymentViewController *)paymentViewController didCompletePayment:(PayPalPayment *)completedPayment
{
     NSLog(@"PayPal Payment Success!");
    [self verifyCompletedPayment:completedPayment];
    [self showSuccess];
    self.resultText = [completedPayment description];
    [self sendCompletedPaymentToServer:completedPayment];
    // Dismiss the PayPalPaymentViewController.
    [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
}
- (void)payPalPaymentDidCancel:(PayPalPaymentViewController *)paymentViewController {
    NSLog(@"PayPal Payment Canceled");
    self.resultText = nil;

    self.successView.hidden = YES;
   
    [[SlideNavigationController sharedInstance] dismissViewControllerAnimated:YES completion:nil];
}

//-(void)payPalPaymentViewController:(PayPalPaymentViewController *)paymentViewController willCompletePayment:(PayPalPayment *)completedPayment completionBlock:(PayPalPaymentDelegateCompletionBlock)completionBlock
//{
//    
//    
//}
- (void)verifyCompletedPayment:(PayPalPayment *)completedPayment
{
    
    NSData *confirmation = [NSJSONSerialization dataWithJSONObject:completedPayment.confirmation
                                                           options:0
                                                             error:nil];
    NSLog(@"confirmation:%@",confirmation);
}
- (void)viewWillAppear:(BOOL)animated {
  
    // Preconnect to PayPal early
    [self setPayPalEnvironment:self.environment];
}

- (void)setPayPalEnvironment:(NSString *)environment {
    self.environment = environment;
    [PayPalMobile preconnectWithEnvironment:environment];
}


- (void)sendCompletedPaymentToServer:(PayPalPayment *)completedPayment {
    // TODO: Send completedPayment.confirmation to server
    NSLog(@"Here is your proof of payment:\n\n%@\n\nSend this to your server for confirmation and fulfillment.", completedPayment.confirmation);
    
}


- (void)showSuccess {
    self.successView.hidden = NO;
    self.successView.alpha = 1.0f;
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationDuration:0.5];
    [UIView setAnimationDelay:2.0];
    self.successView.alpha = 0.0f;
    [UIView commitAnimations];
}
-(void)orderrequest{
    cartView*cart = [[cartView alloc] init];
   
    order.products = [cart product];
    
}
-(void)order:(Order *)ordr{
    NSLog(@"Apporder:%@",ordr);
}
-(void)getCurrentlocation{
    if(self.locationManager == nil){
    locationManager = [[CLLocationManager alloc] init];
    [locationManager setDelegate:self];
    [locationManager setDistanceFilter:kCLDistanceFilterNone];
    [locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
    }
//    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0)
//        [self.locationManager requestWhenInUseAuthorization];
//    [locationManager startUpdatingLocation];
//    [self requestAlwaysAuthorization];
    if([CLLocationManager locationServicesEnabled])
    {
        [self.locationManager startUpdatingLocation];
    }
}

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    return [[FBSDKApplicationDelegate sharedInstance] application:application
                                                          openURL:url
                                                sourceApplication:sourceApplication
                                                       annotation:annotation];
}
- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
  
}
- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
         [FBSDKAppEvents activateApp];

}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:
}
-(void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation{
   
        //Location timestamp is within the last 15.0 seconds, let's use it!
    
            //Location seems pretty accurate, let's use it!
            NSLog(@"latitude %+.6f, longitude %+.6f\n",
                  newLocation.coordinate.latitude,
                  newLocation.coordinate.longitude);
            NSLog(@"Horizontal Accuracy:%f", newLocation.horizontalAccuracy);
            
            //Optional: turn off location services once we've gotten a good location
            [manager stopUpdatingLocation];
       
   
}

@end

//
//  loginViewController.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "loginViewController.h"
#import "ViewController.h"
#import "ForgotPasswordVC.h"
#import "SlideNavigationController.h"
#import "RegisterViewController.h"
#import "LoginObject.h"
#import "DBManager.h"
#import "ConvertUtility.h"
#import "Cart.h"
#import "LocationTable.h"
#import "KeychainItemWrapper.h"
#import <QuartzCore/QuartzCore.h>
#import "M13Checkbox.h"

#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]
#define kOFFSET_FOR_KEYBOARD 80.0

#define getLogin @"http://52.74.237.28:8080/ecart/rest/userrsrc/userdetails"

@interface loginViewController ()

@end

@implementation loginViewController
@synthesize LoginImage,MobileNo,Password,showpassword,loginimg,labelshowpwd,textusr,textpwd,login,checkboxButton,delegate,forgotpwd,Register,scrollfeilds,imageView,image;


- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor =  [UIColor whiteColor];
    
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    self.imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,width, height)];
    image = [UIImage imageNamed:@"loginbg.png"];
    self.imageView.image = image;
    self.imageView.userInteractionEnabled = YES;
    
    self.LoginImage = [[UIImageView alloc] initWithFrame:CGRectMake(80,80,width/2,width/2)];
    [self.LoginImage setImage:[UIImage imageNamed:@"logo.png"]];
    [self.imageView addSubview:self.LoginImage];
    
    
    self.MobileNo = [[UITextField alloc] initWithFrame:CGRectMake(20,(width/2)+100,width-40,50)];
    self.MobileNo.delegate =self;
    self.MobileNo.borderStyle = UITextBorderStyleBezel;
    self.MobileNo.backgroundColor = [UIColor whiteColor];
    self.MobileNo.placeholder = @"Mobile No";
    self.MobileNo.textAlignment = NSTextAlignmentCenter;
    [self.MobileNo setKeyboardType:UIKeyboardTypeNumberPad];
    
    self.Password = [[UITextField alloc] initWithFrame:CGRectMake(20,(width/2)+151,width-40,50)];
    self.Password.delegate =self;
    self.Password.borderStyle = UITextBorderStyleBezel;
     self.Password.backgroundColor = [UIColor whiteColor];
    self.Password.textAlignment = NSTextAlignmentCenter;
    self.Password.placeholder = @"Password";
    self.Password.secureTextEntry = YES;
    
    self.checkboxButton = [[UIButton alloc] initWithFrame:CGRectMake(20,(width/2)+210,20,20)];
    [self.checkboxButton addTarget:self action:@selector(checkboxSelected:) forControlEvents:UIControlEventTouchUpInside];
    [self.checkboxButton.titleLabel setFont:[UIFont systemFontOfSize:14.0f]];
    [self.checkboxButton setTitle:@"" forState:UIControlStateNormal];
    [self.checkboxButton setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
    self.checkboxButton.backgroundColor = [UIColor clearColor];
    
    self.labelshowpwd = [[UILabel alloc] initWithFrame:CGRectMake(55,(width/2)+210,150,25)];
    self.labelshowpwd.text = @"Show password";
    self.labelshowpwd.textAlignment =NSTextAlignmentLeft;
    self.labelshowpwd.font = [UIFont fontWithName:@"Arial Bold" size:17.0f];
    self.labelshowpwd.textColor = [UIColor whiteColor];
    
    self.Password.rightViewMode = UITextFieldViewModeAlways;
    [self.checkboxButton addTarget:self action:@selector(hideShow:) forControlEvents:UIControlEventTouchUpInside];
    checkboxSelected = 0;
    [self.checkboxButton setBackgroundImage:[UIImage imageNamed:@"checkbox.png"]
                                   forState:UIControlStateNormal];
    [self.checkboxButton setBackgroundImage:[UIImage imageNamed:@"checkbox-pressed.png"]
                                   forState:UIControlStateSelected];
    [self.checkboxButton setBackgroundImage:[UIImage imageNamed:@"checkbox-checked.png"]
                                   forState:UIControlStateHighlighted];
    self.checkboxButton.adjustsImageWhenHighlighted=YES;
   
    
    self.login =[[UIButton alloc] initWithFrame:CGRectMake(20,(width/2)+235,width-40,45)];
    self.login.backgroundColor = Rgb2UIColor(255,140,0);
    [self.login setTitle:@"LOGIN" forState:UIControlStateNormal];
    self.login.titleLabel.font = [UIFont fontWithName:@"Arial" size:20.0];
    [self.login addTarget:self action:@selector(login:) forControlEvents:UIControlEventTouchUpInside];
    [self.login setEnabled:YES];
    
    self.forgotpwd = [[UIButton alloc] initWithFrame:CGRectMake(20,(width/2)+282,150,30)];
    [self.forgotpwd addTarget:self action:@selector(forgotpwdbtn:) forControlEvents:UIControlEventTouchUpInside];
    [self.forgotpwd setTitle:@"Forgot password?" forState:UIControlStateNormal];
    [self.forgotpwd setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    self.forgotpwd.backgroundColor = [UIColor clearColor];
    
    self.Register = [[UIButton alloc] initWithFrame:CGRectMake(20,(width/2)+320,width-40,45)];
    [self.Register addTarget:self action:@selector(registerme:) forControlEvents:UIControlEventTouchUpInside];
    [self.Register setTitle:@"REGISTER" forState:UIControlStateNormal];
    [self.Register setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    self.Register.layer.cornerRadius = 8;
    self.Register.layer.borderWidth = 5;
    self.Register.layer.borderColor = [UIColor whiteColor].CGColor;
    self.Register.backgroundColor = [UIColor clearColor];
    
    UITapGestureRecognizer *singleFingerTap =
    [[UITapGestureRecognizer alloc] initWithTarget:self
                                            action:@selector(backgroundTap:)];
    [self.imageView addGestureRecognizer:singleFingerTap];
    
   // self.scrollfeilds = [[UIScrollView alloc] initWithFrame:CGRectMake(0,0,width,height)];
    
    
    [self.imageView addSubview:self.login];
    [self.imageView addSubview:self.checkboxButton];
    
    [self.imageView addSubview:self.labelshowpwd];
    [self.imageView addSubview:self.MobileNo];
    [self.imageView addSubview:self.Password];
    [self.imageView addSubview:self.forgotpwd];
    [self.imageView addSubview:self.Register];
    [self.view addSubview:self.imageView];
   
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}
- (IBAction)login:(id)sender{
    
    NSInteger success = 0;
    @try {
        
        if([[self.MobileNo text] isEqualToString:@""] || [[self.Password text] isEqualToString:@""] ) {
            dispatch_async(dispatch_get_main_queue(), ^{
                
            [self alertStatus:@"Please enter Email and Password" :@"Sign in Failed!"];
        });
        }
        
        else {
            
            NSMutableDictionary*post =[[NSMutableDictionary alloc] initWithCapacity:2];
            
            [[NSUserDefaults standardUserDefaults] setValue:[self.MobileNo text] forKey:@"mobile"];
            [[NSUserDefaults standardUserDefaults] setValue:[self.Password text] forKey:@"password"];
            [[NSUserDefaults standardUserDefaults] synchronize];
            
            NSString*mobile = [[NSUserDefaults standardUserDefaults] stringForKey:@"mobile"];
             NSString*passwrd = [[NSUserDefaults standardUserDefaults] stringForKey:@"password"];
            KeychainItemWrapper *keychainItem = [[KeychainItemWrapper alloc] initWithIdentifier:@"YourAppLogin" accessGroup:nil];
            [keychainItem setObject:[self.MobileNo text] forKey:(__bridge id)(kSecValueData)];
            [keychainItem setObject:[self.Password text] forKey:(__bridge id)(kSecAttrAccount)];
          
            
            [post setValue:mobile forKey:@"mobileNo"];
            [post setValue:passwrd forKey:@"password"];
            
            NSError *serr;
            
            NSData *jsonData = [NSJSONSerialization
                                dataWithJSONObject:post options:NSJSONWritingPrettyPrinted error:&serr];
            
            if (serr)
            {
                NSLog(@"Error generating json data for send dictionary...");
                NSLog(@"Error (%@), error: %@", post, serr);
                return;
            }
            
            NSLog(@"Successfully generated JSON for send dictionary");
            
            
            NSURL*url = [NSURL URLWithString:getLogin];
            
            NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
            
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
                 if (!data)
                 {
                      dispatch_async(dispatch_get_main_queue(), ^{
                     [self alertStatus:@"login Failed" :@""];
                      });
                }
                 else{
                     NSError *deserr;
                     
                     ResponseDict = [NSJSONSerialization
                                                   
                                                   JSONObjectWithData:data
                                                   
                                                   options:kNilOptions
                                                   
                                                   error:&deserr];
                    
                     NSLog(@"so, here's the responseDict\n\n\n%@\n\n\n", ResponseDict);
                    
                     if(ResponseDict==NULL)
                     {
                          dispatch_async(dispatch_get_main_queue(), ^{
                         [self alertStatus:@"Wrong Username or Password\nPlease try again" :@""];
                          });
                        }
                     else{
                         LoginObject*obj = [LoginObject getInstance];
                         obj.dict=ResponseDict;
                        
                         User*usrobj = [[User alloc] initWithDictionary:ResponseDict];
                         [usrobj dictionaryWithPropertiesOfObject];
                         obj.user = usrobj;
                           
                         NSDictionary*dictionary = [ResponseDict objectForKey:@"userprofile"];
                         NSLog(@"dictionary:%@",dictionary);
                         NSString*useraccountId = [dictionary objectForKey:@"userAccountId"];
                         NSLog(@"useraccountid:%@",useraccountId);
                         //[[NSUserDefaults standardUserDefaults] setValue:ResponseDict forKey:@"User"];
                    [[NSUserDefaults standardUserDefaults] setObject:useraccountId forKey:@"userAccountId"];
                         [[NSUserDefaults standardUserDefaults] synchronize];
                         dispatch_async(dispatch_get_main_queue(), ^{
                        [[SlideNavigationController sharedInstance] popViewControllerAnimated:YES];
                         });
                        }
                 }
                 
             }];
            [dataTask resume];
        }
    }
    @catch (NSException * e) {
        NSLog(@"Exception: %@", e);
         dispatch_async(dispatch_get_main_queue(), ^{
        [self alertStatus:@"Sign in Failed." :@"Error!"];
         });
    }
    if (success) {
        dispatch_async(dispatch_get_main_queue(), ^{
        [self performSegueWithIdentifier:@"login_success" sender:self];
        });
    }
}
- (void) alertStatus:(NSString *)msg :(NSString *)title
{
    UIAlertController * alertController = [UIAlertController alertControllerWithTitle: title
                                           
                                                                              message: msg
                                           
                                                                       preferredStyle: UIAlertControllerStyleAlert];
    
    UIAlertAction *cancel = [UIAlertAction actionWithTitle: @"Ok" style: UIAlertActionStyleCancel handler: nil];
    
    [alertController addAction:cancel];
    [[SlideNavigationController sharedInstance] presentViewController:alertController animated:YES completion:nil];

}
- (void) viewWillAppear:(BOOL)animated {
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    [super viewWillAppear:animated];
    NSLog(@"Registering for keyboard events");
    
    // Register for the events
    [[NSNotificationCenter defaultCenter]
     addObserver:self
     selector:@selector (keyboardDidShow:)
     name: UIKeyboardDidShowNotification
     object:nil];
    [[NSNotificationCenter defaultCenter]
     addObserver:self
     selector:@selector (keyboardDidHide:)
     name: UIKeyboardDidHideNotification
     object:nil];
    
    // Setup content size
    scrollfeilds.contentSize = CGSizeMake(width,height);
    
    //Initially the keyboard is hidden
    keyboardVisible = NO;
}

-(void) viewWillDisappear:(BOOL)animated {
    NSLog (@"Unregister for keyboard events");
    [[NSNotificationCenter defaultCenter]
     removeObserver:self];
}

-(void) keyboardDidShow: (NSNotification *)notif {
    NSLog(@"Keyboard is visible");
    // If keyboard is visible, return
    if (keyboardVisible) {
        NSLog(@"Keyboard is already visible. Ignore notification.");
        return;
    }
    
    // Get the size of the keyboard.
    NSDictionary* info = [notif userInfo];
    NSValue* aValue = [info objectForKey:UIKeyboardFrameEndUserInfoKey];
    CGSize keyboardSize = [aValue CGRectValue].size;
    
    // Save the current location so we can restore
    // when keyboard is dismissed
    offset = scrollfeilds.contentOffset;
    
    // Resize the scroll view to make room for the keyboard
    CGRect viewFrame = scrollfeilds.frame;
    viewFrame.size.height -= keyboardSize.height;
    scrollfeilds.frame = viewFrame;
    
    
    CGRect textFieldRect = [activeField frame];
    textFieldRect.origin.y += 10;
    [scrollfeilds scrollRectToVisible:textFieldRect animated:YES];
    // Keyboard is now visible
    keyboardVisible = YES;
}

-(void) keyboardDidHide: (NSNotification *)notif {
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    if (!keyboardVisible) {
        NSLog(@"Keyboard is already hidden. Ignore notification.");
        return;
    }
    
    
    scrollfeilds.frame = CGRectMake(0, 0, width,height);
    
    scrollfeilds.contentOffset = offset;
    
    
    keyboardVisible = NO;
    
}

-(BOOL) textFieldShouldBeginEditing:(UITextField*)textField {
    activeField = textField;
    
    return YES;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}


- (void)hideShow:(id)sender

{
    
    UIButton *hideShow = (UIButton *)self.Password.leftView;
    
    if (!self.Password.secureTextEntry)
        
    {
        
        self.Password.secureTextEntry = YES;
        
        [hideShow setImage:[UIImage imageNamed:@"checkbox-checked.png"] forState:UIControlStateNormal];
        
    }
    
    else
        
    {
        
        self.Password.secureTextEntry = NO;
        
        [hideShow setImage:[UIImage imageNamed:@"checkbox.png"] forState:UIControlStateNormal];
        
    }
    
    [self.Password becomeFirstResponder];
    
}




-(void)checkboxSelected:(id)sender
{
    checkboxSelected = !checkboxSelected;
    [checkboxButton setSelected:checkboxSelected];
}


- (IBAction)backgroundTap:(id)sender{
    
    [self.view endEditing:YES];
    
}

- (IBAction)checkbox:(id)sender{
    if (checkboxSelected == 0){
        
        [checkboxButton setSelected:YES];
        checkboxSelected = 1;
    }
    else {
        [checkboxButton setSelected:NO];
        checkboxSelected = 0;
        
    }
    
}
-(IBAction)forgotpwdbtn:(id)sender{
    ForgotPasswordVC*fvc = [[ForgotPasswordVC alloc] init];
    [[SlideNavigationController sharedInstance] pushViewController:fvc animated:YES];
    
    
}
-(IBAction)registerme:(id)sender{
    
    RegisterViewController*rvc = [[RegisterViewController alloc] init];
    [[SlideNavigationController sharedInstance]pushViewController:rvc animated:YES];
    
}
- (void)saveLoginData {
    if(![[self.MobileNo text] isEqualToString:@""])
        [[NSUserDefaults standardUserDefaults] setObject:[self.MobileNo text] forKey:@"app_Mobile"];
    if(![[self.Password text] isEqualToString:@""])
        [[NSUserDefaults standardUserDefaults] setObject:[self.Password text] forKey:@"app_password"];
    
    if(isAuthenticated)
        [[NSUserDefaults standardUserDefaults] setObject:@"1" forKey:@"app_authenticated_flag"];
    else
        [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"app_authenticated_flag"];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

- (void)clearLoginData {
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"app_Mobile"];
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"app_password"];
    
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"wpcom_authenticated_flag"];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

- (BOOL)authenticate {
    if([[LoginObject getInstance]user:[self.MobileNo text] andpassword:[self.Password text]] == YES) {
        isAuthenticated = YES;
        [self saveLoginData];
    }
    else {
        isAuthenticated = NO;
        [self clearLoginData];
    }
    return isAuthenticated;
}
@end



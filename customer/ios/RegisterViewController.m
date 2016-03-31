//
//  RegisterViewController.m
//  example2
//
//  Created by Hariteja P on 22/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "RegisterViewController.h"
#import "SlideNavigationController.h"
#import "loginViewController.h"
#import "screenview.h"
#import "LoginObject.h"
#import "M13Checkbox.h"
#import "PolicyViewController.h"

#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]
#define queryString @"http://52.74.237.28:8080/ecart/rest/userrsrc/userregistration"
#define statuscheck @"http://52.74.237.28:8080/ecart/rest/userrsrc/isregistered"
#define SCROLLVIEW_CONTENT_HEIGHT 736
#define SCROLLVIEW_CONTENT_WIDTH  414
@interface RegisterViewController ()
{
    BOOL checkboxSelected;
    BOOL keyboardIsShown;
    
}

@end

@implementation RegisterViewController
@synthesize Fullname,Mobileno,Password,checkboxbutton,Showpwd,ScrollView,signUp,Reset,EmailId,classstrng,imageView,image;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;
    
    NSLog(@"string:%@",classstrng);
    if([classstrng isKindOfClass:[NSString class]]){
        
        self.view.backgroundColor = [UIColor whiteColor];
        
        
        CALayer *border1 = [CALayer layer];
        CGFloat borderWidth = 2;
        UILabel*label = [[UILabel alloc] initWithFrame:CGRectMake(0,80,width,30)];
        label.textAlignment = NSTextAlignmentCenter;
        label.text = @"Enter Details";
        label.textColor = Rgb2UIColor(2,149,137);
        [self.view addSubview:label];
        
        self.Fullname = [[UITextField alloc] initWithFrame:CGRectMake(10,120,width-20,30)];
        Fullname.delegate =self;
        self.Fullname.layer.borderColor=[UIColor blackColor].CGColor;
        border1.borderColor = Rgb2UIColor(46,139,87).CGColor;
        border1.frame = CGRectMake(0, self.Fullname.frame.size.height - borderWidth,self.Fullname.frame.size.width,self.Fullname.frame.size.height);
        border1.borderWidth = borderWidth;
        self.Fullname.placeholder = @"FullName*";
        [self.Fullname.layer addSublayer:border1];
        self.Fullname.layer.masksToBounds = YES;
        
        CALayer *border2 = [CALayer layer];
        CGFloat borderWidth2 = 2;
        self.Mobileno= [[UITextField alloc] initWithFrame:CGRectMake(10,200,width-20,30)];
        self.Mobileno.keyboardType= UIKeyboardTypeNumberPad;
        Mobileno.delegate =self;
        border2.borderColor = Rgb2UIColor(46,139,87).CGColor;
        border2.frame = CGRectMake(0, self.Mobileno.frame.size.height - borderWidth,self.Mobileno.frame.size.width,self.Mobileno.frame.size.height);
        border2.borderWidth = borderWidth2;
        self.Mobileno.placeholder = @"Mobile Number*";
        [self.Mobileno.layer addSublayer:border2];
        self.Mobileno.layer.masksToBounds = YES;
        
        CALayer *border3 = [CALayer layer];
        CGFloat borderWidth3 = 3;
        self.EmailId= [[UITextField alloc] initWithFrame:CGRectMake(10,280,width-20,30)];
        //self.EmailId.keyboardType= UIKeyboardTypeNumberPad;
        EmailId.delegate =self;
        border3.borderColor = Rgb2UIColor(46,139,87).CGColor;
        border3.frame = CGRectMake(0, self.EmailId.frame.size.height - borderWidth,self.EmailId.frame.size.width,self.EmailId.frame.size.height);
        border3.borderWidth = borderWidth3;
        self.EmailId.placeholder = @"EmailId";
        [self.EmailId.layer addSublayer:border3];
        self.EmailId.layer.masksToBounds = YES;
        
        
        self.Reset = [[UIButton alloc]initWithFrame:CGRectMake(10,height-210,(width/2)-20,30)];
        [self.Reset setTitle:@"Back" forState:UIControlStateNormal];
        self.Reset.backgroundColor =Rgb2UIColor(2,149,137);
        [self.Reset addTarget:self action:@selector(Registerme:) forControlEvents:UIControlEventTouchUpInside];
        
        self.signUp = [[UIButton alloc]initWithFrame:CGRectMake(width-((width/2)-20)-10,height-210,(width/2)-20,30)];
        [self.signUp setTitle:@"Submit" forState:UIControlStateNormal];
        self.signUp.backgroundColor =Rgb2UIColor(2,149,137);
        [self.signUp addTarget:self action:@selector(Resetme:) forControlEvents:UIControlEventTouchUpInside];
        
        
        UITapGestureRecognizer *singleFingerTap =
        [[UITapGestureRecognizer alloc] initWithTarget:self
                                                action:@selector(backgroundTap2:)];
        [self.view addGestureRecognizer:singleFingerTap];
        
        
        [self.view addSubview:self.Fullname];
        [self.view addSubview:self.Mobileno];
        [self.view addSubview:self.EmailId];
        [self.view addSubview:self.signUp];
        [self.view addSubview:self.Reset];
        
    }
    else{
        
        self.view.backgroundColor = [UIColor whiteColor];
        
        self.imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,width, height)];
        image = [UIImage imageNamed:@"loginbg.png"];
        self.imageView.image = image;
        self.imageView.userInteractionEnabled = YES;

        
        self.Fullname = [[UITextField alloc] initWithFrame:CGRectMake(20,80,width-40,50)];
        Fullname.delegate =self;
        self.Fullname.placeholder = @"Full name";
        self.Fullname.borderStyle = UITextBorderStyleBezel;
        self.Fullname.textAlignment = NSTextAlignmentCenter;
        self.Fullname.backgroundColor = [UIColor whiteColor];
        
             
    self.Mobileno= [[UITextField alloc] initWithFrame:CGRectMake(20,(self.Fullname.frame.origin.y)+53,width-40,50)];
       self.Mobileno.borderStyle = UITextBorderStyleBezel;
       self.Mobileno.textAlignment= NSTextAlignmentCenter;
       self.Mobileno.keyboardType= UIKeyboardTypeNumberPad;
       Mobileno.delegate =self;
       self.Mobileno.backgroundColor = [UIColor whiteColor];
       self.Mobileno.placeholder = @"Mobile Number*";
       
        
       self.EmailId= [[UITextField alloc] initWithFrame:CGRectMake(20,(self.Mobileno.frame.origin.y)+53,width-40,50)];
       self.EmailId.keyboardType =UIKeyboardTypeAlphabet;
       EmailId.delegate =self;
       self.EmailId.borderStyle = UITextBorderStyleBezel;
       self.EmailId.textAlignment = NSTextAlignmentCenter;
       self.EmailId.backgroundColor = [UIColor whiteColor];
       self.EmailId.placeholder = @"Email Id*";
       
        
        self.Password= [[UITextField alloc] initWithFrame:CGRectMake(20,(self.EmailId.frame.origin.y)+53,width-40,50)];
        Password.delegate =self;
        self.Password.borderStyle = UITextBorderStyleBezel;
        self.Password.textAlignment = NSTextAlignmentCenter;
        self.Password.placeholder = @"Password*";
        self.Password.secureTextEntry =YES;
        self.Password.backgroundColor = [UIColor whiteColor];
        
        self.checkboxbutton = [[UIButton alloc] initWithFrame:CGRectMake(20,(self.Password.frame.origin.y)+53,30,30)];
        [self.checkboxbutton setImage:[UIImage imageNamed:@"checkbox.png"] forState:UIControlStateNormal];
        [self.checkboxbutton addTarget:self action:@selector(checkboxSelected:) forControlEvents:UIControlEventTouchUpInside];
   
        
    self.Showpwd = [[UILabel alloc] initWithFrame:CGRectMake(60,(self.Password.frame.origin.y)+53,width,30)];
        self.Showpwd.text = @"I agree the terms and conditions";
        self.Showpwd.textColor = [UIColor whiteColor];
        self.Showpwd.textAlignment =NSTextAlignmentLeft;
        self.Showpwd.font = [UIFont fontWithName:@"Arial Rounded MT Bold" size:15.0f];
        self.Showpwd.userInteractionEnabled = YES;
        UITapGestureRecognizer *tapGestureRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(labelTapped)];
        tapGestureRecognizer.numberOfTapsRequired = 1;
        [self.Showpwd addGestureRecognizer:tapGestureRecognizer];
        
        self.Reset = [[UIButton alloc]initWithFrame:CGRectMake(20,(self.Showpwd.frame.origin.y)+50,width-40,50)];
        [self.Reset setTitle:@"REGISTER" forState:UIControlStateNormal];
         self.Reset.backgroundColor = Rgb2UIColor(255,140,0);
        self.Reset.titleLabel.font = [UIFont fontWithName:@"Arial" size:20.0];
    [self.Reset addTarget:self action:@selector(Registerme:) forControlEvents:UIControlEventTouchUpInside];
        
        
        self.signUp = [[UIButton alloc]initWithFrame:CGRectMake(10,height-140,width-20,50)];
        [self.signUp setTitle:@"LOGIN" forState:UIControlStateNormal];
        self.signUp.backgroundColor =Rgb2UIColor(2,149,137);
        [self.signUp addTarget:self action:@selector(Resetme:) forControlEvents:UIControlEventTouchUpInside];
        self.signUp.layer.cornerRadius = 8;
        self.signUp.layer.borderWidth = 5;
        self.signUp.layer.borderColor = [UIColor whiteColor].CGColor;
        self.signUp.backgroundColor = [UIColor clearColor];
        
      
        UITapGestureRecognizer *singleFingerTap =
        [[UITapGestureRecognizer alloc] initWithTarget:self
                                                action:@selector(backgroundTap2:)];
        [self.view addGestureRecognizer:singleFingerTap];
      
        [self.imageView addSubview:self.Fullname];
        [self.imageView addSubview:self.Mobileno];
        [self.imageView addSubview:self.EmailId];
        [self.imageView addSubview:self.Password];
        [self.imageView addSubview:self.checkboxbutton];
        [self.imageView addSubview:self.Showpwd];
        [self.imageView addSubview:self.signUp];
        [self.imageView addSubview:self.Reset];
        [self.view addSubview:self.imageView];
        
    }

   }
- (void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    float width = [UIScreen mainScreen].bounds.size.width;
    float height = [UIScreen mainScreen].bounds.size.height;

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
    
   ScrollView.contentSize = CGSizeMake(width,height);
    
    keyboardVisible = NO;
}

-(void) viewWillDisappear:(BOOL)animated {
    NSLog (@"Unregister for keyboard events");
    [[NSNotificationCenter defaultCenter]
     removeObserver:self];
}

-(void) keyboardDidShow: (NSNotification *)notif {
    NSLog(@"Keyboard is visible");
   
    if (keyboardVisible) {
        NSLog(@"Keyboard is already visible. Ignore notification.");
        return;
    }
    
       NSDictionary* info = [notif userInfo];
    NSValue* aValue = [info objectForKey:UIKeyboardFrameEndUserInfoKey];
    CGSize keyboardSize = [aValue CGRectValue].size;
   
    offset = ScrollView.contentOffset;
    
    
    CGRect viewFrame = ScrollView.frame;
    viewFrame.size.height -= keyboardSize.height;
    ScrollView.frame = viewFrame;
    
    
    CGRect textFieldRect = [activeField frame];
    textFieldRect.origin.y += 10;
    [ScrollView scrollRectToVisible:textFieldRect animated:YES];
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
    ScrollView.frame = CGRectMake(0, 0, width,height);
    
    ScrollView.contentOffset = offset;
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
- (void)checkChangedValue:(id)sender
{
    NSLog(@"Changed Value");
}



-(void)checkboxSelected:(id)sender
{
    checkboxSelected = !checkboxSelected;
    UIButton* check = (UIButton*) sender;
    if (checkboxSelected == NO)
        [check setImage:[UIImage imageNamed:@"checkbox.png"] forState:UIControlStateNormal];
    else
        [check setImage:[UIImage imageNamed:@"checkbox-checked.png"] forState:UIControlStateNormal];
}


- (IBAction)backgroundTap2:(id)sender {
    
    [self.view endEditing:YES];
}

- (IBAction)Registerme:(id)sender {
    
    NSURL*url = [NSURL URLWithString:statuscheck];
    NSURL*url3 = [url URLByAppendingPathComponent:[self.Mobileno text]];
    NSData*data = [NSData dataWithContentsOfURL:url3];
    NSString * status =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];

    NSInteger success = 0;
     if([classstrng isKindOfClass:[NSString class]]){
         @try {
             if([Fullname.text isEqualToString:@""] || [Mobileno.text isEqualToString:@""])
             {
                 dispatch_async(dispatch_get_main_queue(), ^{
                 [self alertStatus:@"Please Fill all the mandatory Fields" :@""];
                 });
                 
             }else if(Mobileno.text.length<10 || Mobileno.text.length>10)
             {
                 dispatch_async(dispatch_get_main_queue(), ^{
                 [self alertStatus:@"Please enter a valid mobile number...." :@""];
                 });
             }
             else if ([status  isEqual: @"true"]){
                 dispatch_async(dispatch_get_main_queue(), ^{
                 [self alertStatus:@"Mobile number is already registered..." :@""];
                 });
             }
             else{
                 NSMutableDictionary*dict =[[NSMutableDictionary alloc] initWithCapacity:12];
                 
                 [dict setValue:NULL forKey:@"userAccountId"];
                 [dict setValue:NULL forKey:@"userRole"];
                 [dict setValue:[self.Fullname text] forKey:@"fullName"];
                 [dict setValue:[self.Mobileno text] forKey:@"mobileNo"];
                 [dict setValue:[self.EmailId text] forKey:@"emailId"];
                 [dict setValue:NULL forKey:@"token"];
                 [dict setValue:NULL forKey:@"cart"];
                 [dict setValue:NULL forKey:@"userprofile"];
                 
                 NSLog(@"dictionary%@",dict);
                 NSError *serr;
                 
                 NSData *jsonData = [NSJSONSerialization
                                     dataWithJSONObject:dict options:NSJSONWritingPrettyPrinted error:&serr];
                 
                 if (serr)
                 {
                     NSLog(@"Error generating json data for send dictionary...");
                     NSLog(@"Error (%@), error: %@", dict, serr);
                     return;
                 }
                 
                 NSLog(@"Successfully generated JSON for send dictionary");
                 
                 
                 NSURL*url = [NSURL URLWithString:queryString];
                 
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
                      if (!data)
                      {
                          dispatch_async(dispatch_get_main_queue(), ^{
                          [self alertStatus:@"Registration Failed" :@""];
                          });
                      }
                      else{
                          dispatch_async(dispatch_get_main_queue(), ^{
                          [self alertStatus:@"Registered Successfully" :@""];
                          });
                      }
                      
                  }];
                 
             }
         }
         @catch (NSException * e){
             NSLog(@"Exception: %@",e);
         }
         if(success){
             dispatch_async(dispatch_get_main_queue(), ^{
             [self performSegueWithIdentifier:@"Your Details are Submitted" sender:self];
             });
        }
     }
     else{
   
    @try {
        if([Fullname.text isEqualToString:@""] || [Password.text isEqualToString:@""]  || [Mobileno.text isEqualToString:@""])
    {
        dispatch_async(dispatch_get_main_queue(), ^{
        [self alertStatus:@"Please Fill all the mandatory Fields" :@""];
        });
    }
       else if(Fullname.text.length<6){
           dispatch_async(dispatch_get_main_queue(), ^{
            [self alertStatus:@"Username Should be of Length 6" :@""];
           });
        }
    else{
        
        NSMutableDictionary*dict =[[NSMutableDictionary alloc] initWithCapacity:12];
  
        [dict setValue:NULL forKey:@"userAccountId"];
        [dict setValue:NULL forKey:@"userRole"];
        [dict setValue:[self.Password text] forKey:@"password"];
        [dict setValue:[self.Fullname text] forKey:@"fullName"];
        [dict setValue:[self.Mobileno text] forKey:@"mobileNo"];
        [dict setValue:NULL forKey:@"token"];
        [dict setValue:NULL forKey:@"cart"];
        [dict setValue:NULL forKey:@"userprofile"];
        
        NSLog(@"%@",dict);
        NSError *serr;
        
        NSData *jsonData = [NSJSONSerialization
                            dataWithJSONObject:dict options:NSJSONWritingPrettyPrinted error:&serr];
        
        if (serr)
        {
            NSLog(@"Error generating json data for send dictionary...");
            NSLog(@"Error (%@), error: %@", dict, serr);
            return;
        }
        
        NSLog(@"Successfully generated JSON for send dictionary");
       
       
        NSURL*url = [NSURL URLWithString:queryString];
        
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
             if (!data)
             {
                 dispatch_async(dispatch_get_main_queue(), ^{
                 [self alertStatus:@"Registration Failed" :@""];
              });
            }
            else
            {
               dispatch_async(dispatch_get_main_queue(), ^{
                   [self alertStatus:@"Registered Successfully" :@""];
               });
            }
         
         }];
        
   }
   }
        @catch (NSException * e){
            NSLog(@"Exception: %@",e);
        }
        if(success){
        dispatch_async(dispatch_get_main_queue(), ^{
        [self performSegueWithIdentifier:@"Your are  Registered" sender:self];
        });
        }
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

-(void)labelTapped
{
    PolicyViewController*pvc = [[PolicyViewController alloc] init];
    [[SlideNavigationController sharedInstance] pushViewController:pvc animated:YES];
}

- (IBAction)Resetme:(id)sender {
//    for (UIView *view in [self.ScrollView subviews]) {
//        if ([view isKindOfClass:[UITextField class]]) {
//            UITextField *textField = (UITextField *)view;
//            textField.text = @"";
//        }
//    }
    loginViewController*lvc = [[loginViewController alloc] init];
    [[SlideNavigationController sharedInstance] pushViewController:lvc animated:YES];

}

@end

//
//  loginViewController.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Security/Security.h>
#import "User.h"
@class loginViewController;
@protocol loginViewControllerDelegate
@end

@interface loginViewController : UIViewController<UITextFieldDelegate>
{
        BOOL checkboxSelected;
        BOOL keyboardVisible;
        BOOL isAuthenticated;
        CGPoint offset;
        UITextField *activeField;
        NSDictionary *ResponseDict;
        NSMutableArray*Login; 
}

@property (nonatomic, weak) id <loginViewControllerDelegate> delegate;

@property (strong, nonatomic) IBOutlet UIImageView *LoginImage;
@property (weak, nonatomic) IBOutlet UILabel *showpassword;
@property (retain, nonatomic) IBOutlet UITextField *MobileNo;
@property (retain, nonatomic) IBOutlet UITextField *Password;
@property (nonatomic,strong) UIImage*loginimg;
@property (nonatomic,strong) UILabel*labelshowpwd;
@property (nonatomic,strong) UITextField *textusr;
@property (nonatomic,strong) UITextField *textpwd;
@property (nonatomic,strong) IBOutlet UIButton*login;
@property (nonatomic,strong) IBOutlet UIButton*forgotpwd;
@property (nonatomic,strong) IBOutlet UIButton*Register;
@property (nonatomic,strong) IBOutlet UIButton*checkboxButton;
@property (nonatomic,strong) UIScrollView*scrollfeilds;
@property (nonatomic,strong) NSString*classstrng;
@property (nonatomic,strong) IBOutlet UIImageView*imageView;
@property (nonatomic,strong) UIImage*image;
- (IBAction)login:(id)sender;
- (IBAction)backgroundTap:(id)sender;
- (IBAction)checkbox:(id)sender;
-(IBAction)forgotpwdbtn:(id)sender;
-(IBAction)registerme:(id)sender;


@end

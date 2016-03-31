//
//  RegisterViewController.h
//  example2
//
//  Created by Hariteja P on 22/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RegisterViewController : UIViewController<UITextFieldDelegate>
{
    BOOL keyboardVisible;
    CGPoint offset;
    UITextField *activeField;
    
}
@property (strong, nonatomic) IBOutlet UITextField *Fullname;
@property (strong, nonatomic) IBOutlet UITextField *Mobileno;
@property (strong, nonatomic) IBOutlet UITextField *EmailId;
@property (strong, nonatomic) IBOutlet UITextField *Password;

@property (strong, nonatomic) IBOutlet UIButton *checkboxbutton;

@property (strong, nonatomic) IBOutlet UILabel *Showpwd;

@property (strong, nonatomic) IBOutlet UIButton *signUp;
@property (strong, nonatomic) IBOutlet UIButton *Reset;
@property (strong, nonatomic) IBOutlet UIScrollView *ScrollView;
- (IBAction)backgroundTap2:(id)sender;
- (IBAction)Registerme:(id)sender;
@property (nonatomic,strong) NSString*classstrng;
- (IBAction)Resetme:(id)sender;

@property (nonatomic,strong) IBOutlet UIImageView*imageView;
@property (nonatomic,strong) UIImage*image;

@end

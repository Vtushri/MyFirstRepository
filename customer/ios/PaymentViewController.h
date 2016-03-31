//
//  PaymentViewController.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PayPalMobile.h"

@interface PaymentViewController : UIViewController<PayPalPaymentDelegate>


@property (nonatomic, strong, readwrite) PayPalConfiguration *payPalConfiguration;
@property (nonatomic,strong,readwrite) NSString*environment;


@end

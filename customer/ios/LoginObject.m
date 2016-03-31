//
//  LoginObject.m
//  Cream_Stone
//
//  Created by Hariteja P on 25/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "LoginObject.h"
#import "ViewController.h"
#import "SlideNavigationController.h"
@implementation LoginObject
@synthesize dict,user,totalAmt,pro;

static LoginObject *instance = nil;

+(LoginObject *)getInstance
{
    @synchronized(self)
    {
        if(instance==nil)
        {
            instance= [LoginObject new];
        }
    }
    return instance;
}
-(NSString *)login{
    User*usr = user;
 
//    NSDictionary*dic = dict;
//    NSDictionary*userprofile = [dic objectForKey:@"userprofile"];
  //  NSString*userid = [userprofile objectForKey:@"userAccountId"];
    NSString*userid = [usr valueForKey:@"userAccountId"];
    
    return userid;
    
}
- (void)logout{
    User*usr = user;
    NSString*userid = [usr valueForKey:@"userAccountId"];

    if(user)
    {
        userid = nil;
    }
    
    self.user = nil;
    
}
-(User *)getuser{
    User*usr = user;
    
    if(usr){
        return usr;
    }
    return nil;
}
-(BOOL)user:(NSString *)mobile andpassword:(NSString *)pwd
{
    User*usr = user;
    NSString*mobileNo = [usr valueForKey:@"mobileNo"];
    mobile = mobileNo;
    NSString*Pwd = [usr valueForKey:@"password"];
    pwd = Pwd;
    return YES;
}
-(double)getTotalAmt
{
    return totalAmt;
}


@end

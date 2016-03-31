//
//  LoginObject.h
//  Cream_Stone
//
//  Created by Hariteja P on 25/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"
@interface LoginObject : NSObject
{
    NSDictionary*dict;
    User*user;
    double totalAmt;
    NSMutableArray*pro;
}
@property(nonatomic,retain)NSDictionary *dict;
@property (nonatomic,strong) User*user;
@property (nonatomic) double totalAmt;
@property (nonatomic,strong) NSMutableArray*pro;

+(LoginObject*)getInstance;

-(NSString *) login;
- (void)logout;
-(User *)getuser;
-(BOOL)user:(NSString *)mobile andpassword:(NSString *)pwd;
-(double)getTotalAmt;
@end

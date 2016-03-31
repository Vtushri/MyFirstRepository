//
//  Card.h
//  Cream_Stone
//
//  Created by Hariteja P on 07/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Card : NSObject

@property (nonatomic,strong) NSString*cardId;
@property (nonatomic,strong) NSString*cardHolderName;
@property (nonatomic,strong) NSString*cardNumber;
@property (nonatomic) int*cvv;
@property (nonatomic,strong) NSDate*expiryDate;


-(id)initWithcardId:(NSString *) cardid andcardHolderName:(NSString *) CardholderName andcardNumber:(NSString *) cardnumber andcvv:(int *)CVV andexpiryDate:(NSDate *) Expirydate;
@end

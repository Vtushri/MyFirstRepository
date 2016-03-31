//
//  Stores.h
//  CreameStone
//
//  Created by Hariteja P on 11/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Stores : NSObject

@property (nonatomic,strong) NSString *storeId;
@property (nonatomic,strong) NSString *name;
@property (nonatomic,strong) NSNumber *latitude;
@property (nonatomic,strong) NSNumber *longitude;
@property (nonatomic,strong) NSString *storeAddress;
@property (nonatomic,strong) NSString *storeCity;
@property (nonatomic,strong) NSString *order;
@property (nonatomic,strong) NSMutableArray *user;


-(id)initWithstoreId:(NSString *) cStoreId andname:(NSString *) cName andlatitude:(NSNumber *) cLatitude andlongitude:(NSNumber *)cLongitude andstoreAddress:(NSString *) cStoreAddress andstoreCity:(NSString *) cStoreCity andorder:(NSString *) cOrder anduser:(NSMutableArray *) cUser;
-(NSMutableDictionary *) toDictionary;
@end
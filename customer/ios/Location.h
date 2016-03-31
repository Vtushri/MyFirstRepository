//
//  Location.h
//  Cream_Stone
//
//  Created by Hariteja P on 08/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Location : NSObject

@property (nonatomic,strong) NSString*latitude;
@property (nonatomic,strong) NSString*longitude;
@property (nonatomic,strong) NSString*altitude;

-(id)initWithlatitude:(NSString *)Latitude andlongitude:(NSString *)Longitude andaltitude:(NSString *)Altitude;


@end

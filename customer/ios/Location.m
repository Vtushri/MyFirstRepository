//
//  Location.m
//  Cream_Stone
//
//  Created by Hariteja P on 08/10/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "Location.h"

@implementation Location
@synthesize latitude,longitude,altitude;

-(id)initWithlatitude:(NSString *)Latitude andlongitude:(NSString *)Longitude andaltitude:(NSString *)Altitude{
    
    self =[super init];
    if(self){
        
        latitude = Latitude;
        longitude= Longitude;
        altitude = Altitude;
    
    }
    return  self;
    
    
}
@end

//
//  MapView.h
//  CreameStone
//
//  Created by Hariteja P on 11/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <Foundation/Foundation.h>
#import <MapKit/MapKit.h>

@interface MapView : NSObject<MKAnnotation>
@property (nonatomic) CLLocationCoordinate2D coordinate;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *subtitle;

@end

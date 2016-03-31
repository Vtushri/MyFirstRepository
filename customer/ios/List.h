//
//  List.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface List : NSObject

@property (nonatomic,strong) NSString *Id;
@property (nonatomic,strong) NSString *name;
@property (nonatomic,strong) NSString *level;
@property (nonatomic,strong) NSString *parentId;
@property (nonatomic,strong) NSString *imageUrl;


-(id)initWithId:(NSString *) cID andname: (NSString *) cName andlevel: (NSString *) cLevel andparentId:(NSString *) cParentID andimageUrl: (NSString *) cImageUrl;
@end

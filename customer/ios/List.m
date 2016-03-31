//
//  List.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "List.h"

@implementation List
@synthesize Id,imageUrl,parentId,level,name;


-(id)initWithId:(NSString *) cId andname: (NSString *) cName andlevel: (NSString *) cLevel andparentId:(NSString *) cParentID andimageUrl: (NSString *) cImageUrl
{
    self =[super init];
    if(self)
    {
        Id = cId;
        name = cName;
        parentId = cParentID;
        level = cLevel;
        imageUrl = cImageUrl;
        
    }
    return  self;
}

@end

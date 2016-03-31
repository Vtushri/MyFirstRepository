//
//  DBManeger.h
//  MobiMedic
//
//  Created by CNU on 12/06/15.
//  Copyright (c) 2015 CNU. All rights reserved.
//


#import <Foundation/Foundation.h>
#import "productcategory.h"
#import "User.h"
@interface DBManager : NSObject{
    
}
+(id)sharedAppManager;
-(void)saveOflineItemToDataBase:(NSDictionary*)dictionary;
-(NSMutableArray*)getSavedDataFromOfflineDB;
-(void)clearAllOfflineData;
-(void)deleteAndSaveOfflineProduct:(NSArray *)remainingArray;
//-(void)getLoginstatus:(id)object;
@end

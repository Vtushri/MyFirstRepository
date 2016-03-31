//  DBManeger.m
//  MobiMedic
//
//  Created by CNU on 12/06/15.
//  Copyright (c) 2015 CNU. All rights reserved.
//

#import "DBManager.h"
#import "productcategory.h"

static DBManager *_sharedDBManager;


@implementation DBManager


+(id)sharedAppManager
{
	if(_sharedDBManager == nil)
	{
		_sharedDBManager = [[DBManager alloc] init];
	}
	return _sharedDBManager;
}
-(void)saveOflineItemToDataBase:(NSDictionary*)dictionary
{
 
    NSMutableArray *savedArray = [self getSavedDataFromOfflineDB];
    
    if (savedArray == nil) {
        
        savedArray = [[NSMutableArray alloc] init];
        
    }
    
    [savedArray addObject:dictionary];
    [savedArray writeToFile:[self getOflineFilePath] atomically:YES];
    
}

-(NSMutableArray*)getSavedDataFromOfflineDB
{
    
    NSMutableArray *savedArray = [[NSMutableArray alloc]initWithContentsOfFile:[self getOflineFilePath]];
   
    return savedArray;
  
}

-(NSString*)getOflineFilePath
{
    
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:@"saveDict.plist"];
    return filePath;
    
}

-(void)clearAllOfflineData
{
    
    NSMutableArray *savedArray = [[NSMutableArray alloc] init];
    [savedArray writeToFile:[self getOflineFilePath] atomically:YES];

}

-(void)deleteAndSaveOfflineProduct:(NSArray *)remainingArray
{
    
    [remainingArray writeToFile:[self getOflineFilePath] atomically:YES];
    
}
//-(void)getLoginstatus:(id)object{
//    NSMutableArray *savedArray = [self getSavedDataFromOfflineDB];
//    
//    if (savedArray == nil) {
//        
//        savedArray = [[NSMutableArray alloc] init];
//        
//    }
//   
//    [savedArray addObject:object];
//    
//    [savedArray writeToFile:[self getOflineFilePath] atomically:YES];
//}


@end

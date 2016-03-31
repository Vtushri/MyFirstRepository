//
//  CollectionViewController.h
//  example2
//
//  Created by Hariteja P on 07/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "loginViewController.h"
@interface CollectionViewController : UICollectionViewController<loginViewControllerDelegate,UICollectionViewDelegateFlowLayout>


@property(nonatomic,strong) NSString *ID;
@property(nonatomic,strong) NSMutableArray*json;
@property (nonatomic,strong) IBOutlet UIImageView*imageView;
@property (nonatomic,strong) UIImage * image;
@property BOOL slideOutAnimaionEnabled;
-(void)retrieveData2;
@end

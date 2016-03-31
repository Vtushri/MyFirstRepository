//
//  ImageViewCell.h
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ImageViewCell : UICollectionViewCell
@property (nonatomic,strong) IBOutlet UIImageView*cellImage;
@property (nonatomic,strong) IBOutlet UILabel*cellName;
@property (nonatomic,strong) IBOutlet UILabel*cellPrice1;
@property (nonatomic,strong) IBOutlet UILabel*cellPrice2;
@property (nonatomic,strong) IBOutlet UIButton*cartbtn;
@property (nonatomic,strong) UIImage*img;
@property (nonatomic,strong) NSString*name;
@property (nonatomic,strong) NSString*price1;
@end

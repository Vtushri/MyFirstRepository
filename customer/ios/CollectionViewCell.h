//
//  CollectionViewCell.h
//  Cream_Stone
//
//  Created by Hariteja P on 03/11/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CollectionViewCell : UICollectionViewCell
@property (strong, nonatomic) IBOutlet UIImageView *cellImage;
@property (strong, nonatomic) IBOutlet UILabel *cellName;

@property (strong, nonatomic) IBOutlet UILabel *cellPrice;
@property (strong, nonatomic) IBOutlet UIButton *cartBtn;
- (IBAction)cartbtnckd:(id)sender;
@end

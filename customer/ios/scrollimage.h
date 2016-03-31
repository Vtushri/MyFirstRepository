//
//  scrollimage.h
//  example2
//
//  Created by Hariteja P on 28/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface scrollimage : UITableViewCell
@property (strong, nonatomic) IBOutlet UIImageView *cellImage;
@property (strong, nonatomic) IBOutlet UILabel *cellImageName;
@property (strong, nonatomic) IBOutlet UILabel *CellPrice;
@property (strong, nonatomic) IBOutlet UIButton *cartbutton;
@property (strong,nonatomic) IBOutlet UITextView*cellText;
@property (strong,nonatomic) NSString*prodId;
@property (nonatomic,strong) NSString*ImagesUrl;
@property (nonatomic,strong) UIImage *imageview;

- (IBAction)cartbtnclicked:(id)sender;
//-(void)showPicker;

@end

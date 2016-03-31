//
//  scrollimage.m
//  example2
//
//  Created by Hariteja P on 28/07/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "scrollimage.h"
#import "ViewController.h"
#import "TableViewCell.h"
#import "CollectionViewController.h"
#import "UIImageView+WebCache.h"
#import "PaymentViewController.h"
#import "SlideNavigationController.h"
#import "KLCPopup.h"
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]

@implementation scrollimage
@synthesize imageview,cellImage,cellImageName,ImagesUrl,cartbutton;


-(id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if(self)
    {
        
        self.cellImage.layer.borderWidth = 2;
        self.cellImage.layer.borderColor = [UIColor blackColor].CGColor;
        
        self.layer.borderWidth = 3;
    }
    
    return self;
    
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
     
        if (self.CellPrice)
            self.CellPrice.text = @"";
        
        if (self.cellImageName)
            self.cellImageName.text = @"";
     
        self.cellImageName.layer.borderWidth = 2;
        self.cellImageName.layer.borderColor = [UIColor blackColor].CGColor;
        
        self.CellPrice.layer.borderWidth = 2;
        self.CellPrice.layer.borderColor = [UIColor blackColor].CGColor;
   
        
    
    }
    return self;
}
- (void)setBounds:(CGRect)bounds {
    [super setBounds:bounds];
    self.contentView.frame = bounds;
}
- (IBAction)cartbtnclicked:(id)sender {

}

@end

//
//  ImageViewCell.m
//  Cream_Stone
//
//  Created by Hariteja P on 20/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "ImageViewCell.h"
#import "CollectionViewController.h"
#import "ViewController.h"
#define Rgb2UIColor(r, g, b)  [UIColor colorWithRed:((r) / 255.0) green:((g) / 255.0) blue:((b) / 255.0) alpha:1.0]
@implementation ImageViewCell
@synthesize cellImage,cellName,cellPrice1,name,img,price1,cartbtn,cellPrice2;


- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
         self.backgroundColor = [UIColor whiteColor];
       // float width = [UIScreen mainScreen].bounds.size.width;
        //float height = [UIScreen mainScreen].bounds.size.height;
        self.contentView.frame = self.bounds;
        self.contentView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
        
        self.layer.borderColor = [UIColor whiteColor].CGColor;
        self.layer.borderWidth = 3.0f;
        self.layer.shadowColor = [UIColor blackColor].CGColor;
        self.layer.shadowRadius = 3.0f;
        self.layer.shadowOffset = CGSizeMake(0.0f, 2.0f);
        self.layer.shadowOpacity = 0.5f;
        
        self.cellImage = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,self.contentView.frame.size.width,136)];
        self.cellImage.contentMode = UIViewContentModeScaleAspectFill;
        self.cellImage.clipsToBounds = YES;
        
        self.cellName = [[UILabel alloc] initWithFrame:CGRectMake(5,self.cellImage.frame.origin.y+145,self.contentView.frame.size.width,23)];
        self.cellName.textColor = [UIColor blackColor];
        self.cellName.textAlignment = NSTextAlignmentLeft;
        self.cellName.font = [UIFont systemFontOfSize:13];
       
        self.cellPrice1 = [[UILabel alloc] initWithFrame:CGRectMake(5,self.cellName.frame.origin.y+23,self.contentView.frame.size.width,21)];
        self.cellPrice1.textColor = [UIColor blackColor];
        self.cellPrice1.textAlignment = NSTextAlignmentLeft;
        
        self.cellPrice2 = [[UILabel alloc] initWithFrame:CGRectMake(5,self.cellPrice1.frame.origin.y+21,self.contentView.frame.size.width/2,21)];
        self.cellPrice2.textColor = [UIColor blackColor];
        self.cellPrice2.textAlignment = NSTextAlignmentLeft;

        
        self.cartbtn = [[UIButton alloc] initWithFrame:CGRectMake(self.cellPrice2.frame.size.width+15,self.cellName.frame.origin.y+21,self.contentView.frame.size.width-70,25)];
        [self.cartbtn setImage:[UIImage imageNamed:@"Shopping Cart-32.png"] forState:UIControlStateNormal];
        
        [self.contentView addSubview:self.cellImage];
        [self.contentView addSubview:self.cellName];
        [self.contentView addSubview:self.cellPrice1];
        [self.contentView addSubview:self.cellPrice2];
        [self.contentView addSubview:self.cartbtn];
    }
    return self;
}
- (void)setBounds:(CGRect)bounds {
    [super setBounds:bounds];
    self.contentView.frame = bounds;
}
- (void) layoutSubviews {
   

}
@end


//
//  LocationTableViewCell.m
//  example2
//
//  Created by Hariteja P on 12/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "LocationTableViewCell.h"
#import "LocationTable.h"
#import <objc/runtime.h>
@implementation LocationTableViewCell
@synthesize label1,label2,label3,labelA,labelB,labelC;

- (void)layoutSubviews {
    
    [super layoutSubviews];
    [self layoutIfNeeded];
    
    CGRect textFrame = self.textLabel.frame;
    
    [self.detailTextLabel sizeToFit];
    self.detailTextLabel.textColor = [UIColor brownColor];
    CGFloat x = textFrame.origin.x;
    CGFloat y = textFrame.origin.y + textFrame.size.height;
    
    CGSize detailSize = self.detailTextLabel.frame.size;
    CGRect newFrame = CGRectMake(x, y, detailSize.width, detailSize.height);
//    self.label3.text = labelC;
    
    self.detailTextLabel.frame = newFrame;
}
+ (void)load
{
    if ([UIDevice currentDevice].systemVersion.intValue >= 8)
    {
        /* Swizzle the prepareForReuse method */
        Method original = class_getInstanceMethod(self, @selector(prepareForReuse));
       // Method replace  = class_getInstanceMethod(self, @selector(_detailfix_prepareForReuse));
      //  method_exchangeImplementations(original, replace);
        
        
        Method fixawake = class_getInstanceMethod(self, @selector(_detailfix_super_awakeFromNib));
        if (!class_addMethod(self, @selector(awakeFromNib), method_getImplementation(fixawake), method_getTypeEncoding(fixawake)))
        {
            original = class_getInstanceMethod(self, @selector(_detailfix_awakeFromNib));
           // replace = class_getInstanceMethod(self, @selector(awakeFromNib));
           // method_exchangeImplementations(original, replace);
        }
    }
}

- (void)__detailfix_addDetailAsSubviewIfNeeded
{
    /*
     * UITableViewCell seems to return nil if the cell style does not have a detail.
     * If it returns non-nil, force add it as a contentView subview so that it gets
     * view layout processing at the right times.
     */
    UILabel *detailLabel = self.detailTextLabel;
    if (detailLabel != nil && detailLabel.superview == nil)
    {
        [self.contentView addSubview:detailLabel];
    }
}

- (void)_detailfix_super_awakeFromNib
{
    [super awakeFromNib];
    [self __detailfix_addDetailAsSubviewIfNeeded];
}

- (void)_detailfix_awakeFromNib
{
    [self _detailfix_awakeFromNib];
    [self __detailfix_addDetailAsSubviewIfNeeded];
}

//- (void)_detailfix_prepareForReuse
//{
//    [self _detailfix_prepareForReuse];
//    [self __detailfix_addDetailAsSubviewIfNeeded];
//}
- (void)_detailfix_layoutSubviews
{
    /*
     * UITableViewCell seems to return nil if the cell type does not have a detail.
     * If it returns non-nil, force add it as a contentView subview so that it gets
     * view layout processing at the right times.
     */
    UILabel *detailLabel = self.detailTextLabel;
    if (detailLabel != nil && detailLabel.superview == nil)
    {
        [self.contentView addSubview:detailLabel];
    }
    
    [self _detailfix_layoutSubviews];
}

@end

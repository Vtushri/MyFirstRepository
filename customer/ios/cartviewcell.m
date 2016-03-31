//
//  cartviewcell.m
//  Cream_Stone
//
//  Created by Hariteja P on 01/09/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import "cartviewcell.h"
#import "LoginObject.h"
@implementation cartviewcell
@synthesize cellimage,cellname,cellprice1,qtylable,pricechange,image,pickerData,pricestrng,parcedData,num,numb;

+ (NSString *)reuseIdentifier {
    return @"Cell";
}
-(id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if(self)
    {
        
    }
    return self;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        if(self.cellname)
            self.cellname.text = @" ";
        if(self.cellprice1)
            self.cellprice1.text =@" ";
        
        
    }
    return self;
}
- (void)awakeFromNib {
    
    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];
    
    
}
- (IBAction)deletecart:(id)sender {
}

-(void)updateQuantity
{
    
            
}
@end

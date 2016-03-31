//
//  OrderTableCell.m
//  Cream_Stone
//
//  Created by Hariteja P on 05/11/15.
//  Copyright © 2015 Zetagile. All rights reserved.
//

#import "OrderTableCell.h"

@implementation OrderTableCell
@synthesize Ordernumber,PlacedTime,PlacetimeDply,OrderType,DeliveryTime,DeliverytmeDply,Totalamt,delegate;
- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        if(self.Ordernumber)
      self.Ordernumber.text = @"";
        if(self.OrderType)
            self.OrderType.text =@"";
    }
    return self;
}
+(NSString *)reuseIdentifier{
    
    return @"OrderTableCell";
}
@end

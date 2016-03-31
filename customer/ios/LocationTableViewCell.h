//
//  LocationTableViewCell.h
//  example2
//
//  Created by Hariteja P on 12/08/15.
//  Copyright (c) 2015 Zetagile. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LocationTableViewCell : UITableViewCell

@property (nonatomic,strong) IBOutlet UILabel*label1;
@property (strong, nonatomic) IBOutlet UILabel *label2;

@property (strong, nonatomic) IBOutlet UILabel *label3;
@property (nonatomic,strong) NSString*labelA;
@property (nonatomic,strong) NSString*labelB;
@property (nonatomic,strong) NSString*labelC;


@end

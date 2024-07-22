//
//  ResultEmotionCell.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.07.2024.
//

import UIKit
import PinLayout

final class ResultEmotionCell: UITableViewCell {
    
    static var reusableIdentifier = "ResultEmotionCell"
    
    public var title: String = "" {
        didSet{
            self.setTitle()
        }
    }
    
    public var amount: String = "" {
        didSet{
            self.setAmount()
        }
    }
    
    private lazy var upperSpacerView: UIView = UIView()
    private lazy var contentWrapperView: UIView = UIView()
    
    private lazy var emotionName: UILabel = {
        let label = UILabel()
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 20)
        label.text = "Anger"
        
        return label
    }()
    
    private lazy var emotionAmount: UILabel = {
        let label = UILabel()
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 20)
        label.text = "6"
        label.textAlignment = .right
        
        return label
    }()
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.selectionStyle = .none
        contentView.layer.masksToBounds = true
        
        setupView()
    }
    
    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {
        self.backgroundColor = .clear
        contentWrapperView.backgroundColor = UIColor(resource: .color2)
        contentWrapperView.layer.cornerRadius = 12
        
        [upperSpacerView, contentWrapperView].forEach(self.contentView.addSubview)
        [emotionName, emotionAmount].forEach(contentWrapperView.addSubview)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        upperSpacerView.pin
            .top()
            .width(of: self)
            .height(8)
        
        contentWrapperView.pin
            .below(of: upperSpacerView)
            .left(16)
            .right(16)
            .height(62)
        
        emotionName.pin
            .vCenter()
            .left(15)
            .width(of: self)
            .sizeToFit(.width)
        
        emotionAmount.pin
            .vCenter()
            .right(25)
            .width(of: self)
            .sizeToFit(.width)
    }
    
    private func setTitle(){
        self.emotionName.text = self.title
    }
    
    private func setAmount(){
        self.emotionAmount.text = self.amount
    }
}



//
//  EmotionCell.swift
//  DearDiary
//
//  Created by Алёна Максимова on 18.06.2024.
//

import UIKit
import PinLayout

final class EmotionCell: UITableViewCell {
    
    static var reusableIdentifier = "EmotionCell"
    
    public var title: String = "" {
        didSet{
            self.setTitle()
        }
    }
    
    public var date: String = "" {
        didSet{
            self.setDate()
        }
    }
    
    private lazy var upperSpacerView: UIView = UIView()
    private lazy var contentWrapperView: UIView = UIView()
    
    private lazy var emotionName: UILabel = {
        let label = UILabel()
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 25)
        label.text = "Anger"
        
        return label
    }()
    
    private lazy var emotionDate: UILabel = {
        let label = UILabel()
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 13)
        label.text = "16.06.2024"
        
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
        [emotionName, emotionDate].forEach(contentWrapperView.addSubview)
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
            .top(4)
            .left(15)
            .width(of: self)
            .sizeToFit(.width)
        
        emotionDate.pin
            .below(of: emotionName)
            .left(15)
            .width(of: self)
            .sizeToFit(.width)
    }
    
    private func setTitle(){
        self.emotionName.text = self.title
    }
    
    private func setDate(){
        self.emotionDate.text = self.date
    }
}


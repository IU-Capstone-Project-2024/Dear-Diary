//
//  EmotionChartView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit
import PinLayout

final class EmotionChartView: UIView {
        
    private(set) lazy var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color3)
        view.layer.cornerRadius = 24
        
        return view
    }()
    
    private lazy var label: UILabel = {
        let label = UILabel()
        label.text = "Emotions statistics for"
        label.textColor = UIColor(resource: .color1)
        label.textAlignment = .center
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        label.numberOfLines = 2
        
        return label
    }()
    
    private lazy var durationLabel: UILabel = {
        let label = UILabel()
        label.text = "Today"
        label.textColor = UIColor(resource: .color1)
        label.textAlignment = .center
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 20)
        label.numberOfLines = 2
        
        return label
    }()
    
    public lazy var circularProgressView = CircularProgressView()
    public var emotionLabels: [UILabel] = []
    
    private(set) lazy var backButton: UIButton = {
        let button = UIButton()
        button.setTitle("Back to the main page", for: .normal)
        button.titleLabel?.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        button.setTitleColor(UIColor(resource: .color4), for: .normal)
        button.backgroundColor = UIColor(resource: .color1)
        button.layer.cornerRadius = 5
        
        return button
    }()
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor(resource: .color4)
        setupView()
    }
    
    public required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {
        [contentView].forEach(self.addSubview)
        [label, durationLabel, circularProgressView, backButton].forEach(contentView.addSubview)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
       
        contentView.pin
            .top(self.pin.safeArea)
            .horizontally(20)
            .bottom(self.pin.safeArea)
        
        label.pin
            .hCenter()
            .top(51)
            .width(60%)
            .sizeToFit(.width)
        
        durationLabel.pin
            .below(of: label)
            .marginTop(8)
            .hCenter()
            .width(60%)
            .sizeToFit(.width)
        
        circularProgressView.pin
            .vCenter(-60)
            .hCenter()
            .width(1)
            .height(1)
                
        backButton.pin
            .bottom(15)
            .left(15)
            .right(15)
            .height(45)
    }
}



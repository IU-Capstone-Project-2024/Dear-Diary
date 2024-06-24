//
//  NewEmotionDescriptionView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 22.06.2024.
//

import UIKit
import PinLayout

final class NewEmotionDescriptionView: UIView {
    
    private(set) lazy var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color3)
        view.layer.cornerRadius = 24
        
        return view
    }()
    
    private lazy var label: UILabel = {
        let label = UILabel()
        label.text = "Your emotion is"
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 25)
        
        return label
    }()
    
    private lazy var emotionLabel: UILabel = {
        let label = UILabel()
        label.text = "Anger"
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 25)
        
        return label
    }()
    
    private lazy var recommendationLabel: UILabel = {
        let label = UILabel()
        label.text = "Some recommendations or whatever"
        label.textColor = UIColor(resource: .color1)
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        label.numberOfLines = 0
        
        return label
    }()
    
    private(set) lazy var backButton: UIButton = {
        let button = UIButton()
        button.setTitle("Back to the main page", for: .normal)
        button.titleLabel?.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        button.setTitleColor(UIColor(resource: .color1), for: .normal)
        button.layer.borderWidth = 1
        button.layer.borderColor = UIColor(resource: .color1).cgColor
        button.layer.cornerRadius = 5
        
        return button
    }()
    
    private(set) lazy var saveButton: UIButton = {
        let button = UIButton()
        button.setTitle("Save my emotion", for: .normal)
        button.titleLabel?.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        button.setTitleColor(UIColor(resource: .color4), for: .normal)
        button.backgroundColor = UIColor(resource: .color1)
        button.layer.cornerRadius = 5
        
        return button
    }()
    
    private lazy var resultView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color2)
        view.layer.cornerRadius = 12
        
        return view
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
        [resultView, backButton, saveButton].forEach(contentView.addSubview)
        [label, emotionLabel, recommendationLabel].forEach(resultView.addSubview)
        
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
       
        contentView.pin
            .top(self.pin.safeArea)
            .horizontally(20)
            .bottom(self.pin.safeArea)
        
        saveButton.pin
            .bottom(15)
            .left(15)
            .right(15)
            .height(45)
        
        backButton.pin
            .above(of: saveButton)
            .marginBottom(15)
            .left(15)
            .right(15)
            .height(45)
        
        
        resultView.pin
            .top(15)
            .horizontally(15)
            .above(of: backButton)
            .marginBottom(18)
        
        label.pin
            .top(15)
            .left(15)
            .width(70%)
            .sizeToFit(.width)
        
        emotionLabel.pin
            .below(of: label)
            .marginTop(8)
            .left(15)
            .width(70%)
            .sizeToFit(.width)
        
        emotionLabel.pin
            .below(of: label)
            .marginTop(8)
            .left(15)
            .width(70%)
            .sizeToFit(.width)
        
        recommendationLabel.pin
            .below(of: emotionLabel)
            .marginTop(8)
            .left(15)
            .width(70%)
            .bottom(15)
            .sizeToFit(.width)
            
    }
}


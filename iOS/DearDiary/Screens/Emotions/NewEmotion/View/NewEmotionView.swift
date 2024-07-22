//
//  NewEmotionView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 21.06.2024.
//

import UIKit
import PinLayout

final class NewEmotionView: UIView {
    
    private(set) lazy var activityIndicator = UIActivityIndicatorView(style: .large)
    
    private(set) lazy var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color3)
        view.layer.cornerRadius = 24
        
        return view
    }()
    
    private lazy var label: UILabel = {
        let label = UILabel()
        label.text = "What emotion do I feel?"
        label.textColor = UIColor(resource: .color1)
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 20)
        label.numberOfLines = 2
        
        return label
    }()
    
    private lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.text = "Short feature description"
        label.textColor = UIColor(resource: .color1)
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 15)
        label.numberOfLines = 2
        
        return label
    }()
    
    private lazy var image: UIImageView = {
        let image = UIImageView()
        image.image = UIImage(named: "image 1")
        image.layer.cornerRadius = 12
        
        return image
    }()
    
    var placeholderText = "Describe what you feel and Diary will tell what emotion is it..."
    
    private(set) lazy var textView: UITextView = {
        let textView = UITextView()
        textView.font = UIFont(name: "PlayfairDisplay-Regular", size: 18)
        textView.textColor = UIColor(resource: .color4)
        textView.backgroundColor = UIColor(resource: .color2)
        textView.layer.cornerRadius = 12
        textView.text = "Describe what you feel and Diary will tell what emotion is it..."
        textView.textAlignment = .left

        return textView
    }()
    
    private(set) lazy var analyzeButton: UIButton = {
        let button = UIButton()
        button.isEnabled = false
        button.setTitle("Let’s find out!", for: .normal)
        button.titleLabel?.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        button.setTitleColor(UIColor(resource: .color4), for: .normal)
        button.backgroundColor = UIColor(resource: .unactive)
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
        [label, descriptionLabel, image, textView, analyzeButton, activityIndicator].forEach(contentView.addSubview)
        
        activityIndicator.hidesWhenStopped = true
        activityIndicator.color = UIColor(resource: .color4)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
       
        contentView.pin
            .top(self.pin.safeArea)
            .horizontally(20)
            .bottom(self.pin.safeArea)
        
        activityIndicator.pin
            .center()
        
        label.pin
            .top(15)
            .left(15)
            .width(50%)
            .sizeToFit(.width)
        
        descriptionLabel.pin
            .below(of: label)
            .marginTop(8)
            .left(15)
            .width(50%)
            .sizeToFit(.width)
        
        image.pin
            .top(15)
            .right(15)
            .width(40%)
            .height(contentView.frame.width * 0.36)
        
        analyzeButton.pin
            .bottom(15)
            .left(15)
            .right(15)
            .height(45)
        
        textView.pin
            .below(of: image)
            .left()
            .right()
            .margin(18, 15, 18, 15)
            .above(of: analyzeButton)
    }
}

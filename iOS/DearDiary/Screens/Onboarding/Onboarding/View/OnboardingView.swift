//
//  OnboardingView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 06.07.2024.
//

import UIKit
import PinLayout

final class OnboardingView: UIView {
    
    private(set) lazy var imageView: UIImageView = {
        let image = UIImageView()
        image.contentMode = .scaleAspectFill
        
        return image
    }()
    
    private(set) lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 18)
        label.textColor = .black
        label.numberOfLines = 0
        
        return label
    }()
    
    private(set) lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 18)
        label.textColor = .black
        label.numberOfLines = 0
        
        return label
    }()
    
    private(set) lazy var nextButton: UIButton = {
        let button = UIButton()
        button.setTitle("Next", for: .normal)
        button.titleLabel?.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        button.setTitleColor(UIColor(resource: .color4), for: .normal)
        button.backgroundColor = UIColor(resource: .color1)
        button.layer.cornerRadius = 5
        button.isHidden = true
        
        return button
    }()
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }
    
    public required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {
        [imageView, titleLabel, descriptionLabel, nextButton].forEach(self.addSubview)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        titleLabel.pin
            .center()
            .width(80%)
            .sizeToFit(.width)
        
        descriptionLabel.pin
            .below(of: titleLabel)
            .width(80%)
            .hCenter()
            .marginTop(8)
            .sizeToFit(.width)
        
        imageView.pin
            .above(of: titleLabel)
            .width(40%)
            .height(self.frame.width * 0.4)
            .hCenter()
            .marginBottom(20)
        
        nextButton.pin
            .bottom(50)
            .left(15)
            .right(15)
            .height(45)
    }
}

//
//  EmotionView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit
import PinLayout

final class EmotionsView: UIView {
    
    private(set) lazy var emotionRecognizerView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 24
        view.backgroundColor = UIColor(resource: .color3)
        
        return view
    }()
    
    private lazy var image: UIImageView = {
        let image = UIImageView()
        image.image = UIImage(named: "image 1")
        image.layer.cornerRadius = 12
        
        return image
    }()
    
    private lazy var questionLabel: UILabel = {
        let label = UILabel()
        label.text = "What emotion do I feel?"
        label.numberOfLines = 2
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 16)
        label.textColor = UIColor(resource: .color1)
        
        return label
    }()
    
    private lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.text = "Short feature description"
        label.numberOfLines = 2
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 13)
        label.textColor = UIColor(resource: .color1)
        
        return label
    }()
    
    private(set) lazy var emotionView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 24
        view.backgroundColor = UIColor(resource: .color3)
        
        return view
    }()
    
    private lazy var recentEmotionImage: UIImageView = {
        let image = UIImageView()
        image.image = UIImage(named: "image 2")
        image.layer.cornerRadius = 12
        
        return image
    }()
    
    private lazy var recentEmotionLabel: UILabel = {
        let label = UILabel()
        label.text = "My recent emotions"
        label.textAlignment = .center
        label.numberOfLines = 2
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 16)
        label.textColor = UIColor(resource: .color1)
        
        return label
    }()
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }
    
    public required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {
        [emotionRecognizerView, emotionView].forEach(self.addSubview)
        [questionLabel, descriptionLabel, image].forEach(emotionRecognizerView.addSubview)
        [recentEmotionLabel, recentEmotionImage].forEach(emotionView.addSubview)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        emotionRecognizerView.pin
            .top(12)
            .left(20)
            .width(60%)
            .height(112)
        
        emotionView.pin
            .top(12)
            .right(20)
            .width(25%)
            .height(112)
        
        questionLabel.pin
            .top(15)
            .left(10)
            .width(50%)
            .sizeToFit(.width)
        
        descriptionLabel.pin
            .below(of: questionLabel)
            .marginTop(4)
            .left(10)
            .width(50%)
            .sizeToFit(.width)
        
        image.pin
            .vCenter()
            .right(15)
            .width(89)
            .height(82)
        
        recentEmotionLabel.pin
            .top(18)
            .width(90%)
            .hCenter()
            .sizeToFit(.width)
        
        recentEmotionImage.pin
            .below(of: recentEmotionLabel)
            .marginTop(4)
            .width(40)
            .height(40)
            .hCenter()
    }
}

//
//  QuestionaryView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit
import PinLayout

final class QuestionaryView: UIView {
    
    var options: [OptionView] = []
    private(set) lazy var activityIndicator = UIActivityIndicatorView(style: .large)
    
    private(set) lazy var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color3)
        view.layer.cornerRadius = 24
        
        return view
    }()
    
    private lazy var label: UILabel = {
        let label = UILabel()
        label.text = "What do you hope to do in the Dear Diary?"
        label.textColor = UIColor(resource: .color1)
        label.textAlignment = .center
        label.font = UIFont(name: "PlayfairDisplay-Medium", size: 18)
        label.numberOfLines = 2
        
        return label
    }()
    
    private(set) lazy var nextButton: UIButton = {
        let button = UIButton()
        button.isEnabled = false
        button.setTitle("Finish", for: .normal)
        button.titleLabel?.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        button.setTitleColor(UIColor(resource: .color4), for: .normal)
        button.backgroundColor = UIColor(resource: .unactive)
        button.layer.cornerRadius = 5
        
        return button
    }()
    
    private lazy var option1: OptionView = OptionView()
    private lazy var option2: OptionView = OptionView()
    private lazy var option3: OptionView = OptionView()
    
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
        options = [option1, option2, option3]
        [label, option1, option2, option3, nextButton, activityIndicator].forEach(contentView.addSubview)
        
        option1.title = "Self-improvement"
        option2.title = "Self-reflection"
        option3.title = "Other"
        
        for option in options {
            option.checkbox.isChecked = false
            option.addTarget(self, action: #selector(toggle(_:)))
        }
        
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
            .hCenter()
            .vCenter(-80)
            .width(60%)
            .sizeToFit(.width)
        
        option1.pin
            .below(of: label)
            .marginTop(34)
            .width(80%)
            .height(36)
            .hCenter()
        
        option2.pin
            .below(of: option1)
            .marginTop(17)
            .width(80%)
            .height(36)
            .hCenter()
        
        option3.pin
            .below(of: option2)
            .marginTop(17)
            .width(80%)
            .height(36)
            .hCenter()
        
        nextButton.pin
            .bottom(15)
            .left(15)
            .right(15)
            .height(45)
    }
    
    @objc func toggle(_ sender: OptionView) {
        sender.checkbox.isChecked = !sender.checkbox.isChecked
        
        for option in options {
            if option.checkbox.isChecked == true {
                nextButton.isEnabled = true
                nextButton.backgroundColor = UIColor(resource: .color1)
                break
            }
            
            nextButton.isEnabled = false
            nextButton.backgroundColor = UIColor(resource: .unactive)
        }
    }
}

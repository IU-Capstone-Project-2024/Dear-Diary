//
//  OptionView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 16.06.2024.
//

import UIKit
import PinLayout

final class OptionView: UIView {
    
    private(set) lazy var checkbox = CheckBoxView()
    
    public var title:String = ""{
        didSet{
            self.setTitle()
        }
    }
    
    private lazy var textLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 1
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 18)
        
        return label
    }()
    
    private weak var target: AnyObject?
    private var action: Selector?
    
    public func addTarget(_ target: AnyObject, action: Selector) {
        self.target = target
        self.action = action
    }
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }
    
    public required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {
        self.backgroundColor = UIColor(resource: .color2)
        self.layer.cornerRadius = 5
        
        [checkbox, textLabel].forEach(self.addSubview)
        
        checkbox.singleChoice = false
        checkbox.addTarget(self, action: #selector(executeExternalFunc), for: .touchUpInside)
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(executeExternalFunc))
        self.addGestureRecognizer(tapGesture)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        checkbox.pin
            .vCenter()
            .left(9)
            .width(22)
            .height(22)
            .sizeToFit(.width)
        
        textLabel.pin
            .vCenter()
            .right(of: checkbox)
            .marginLeft(37)
            .width(70%)
            .sizeToFit(.width)
        
    }
    
    @objc func executeExternalFunc(){
         guard let target = target,
               let action = action else { return }
         _ = target.perform(action, with: self)
     }
    
    private func setTitle() {
        textLabel.text = title
    }
}


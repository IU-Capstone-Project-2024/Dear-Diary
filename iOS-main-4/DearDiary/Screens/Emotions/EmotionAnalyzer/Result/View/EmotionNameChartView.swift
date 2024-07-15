//
//  EmotionNameChartView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit

final class EmotionNameChartView: UILabel {
    
    public var title: String = "" {
        didSet {
            self.setTitle()
        }
    }
    
    public var background: UIColor = UIColor(resource: .color1) {
        didSet {
            self.setBackgroundColor()
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        commonInit()
    }
    
    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func commonInit(){
        self.textAlignment = .center
        self.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        self.textColor = UIColor(resource: .color4)
        self.layer.cornerRadius = 10
        
        setTitle()
        setBackgroundColor()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
    }
    
    
    private func setTitle(){
        self.text = title
    }
    
    private func setBackgroundColor(){
        self.backgroundColor = background
    }
}

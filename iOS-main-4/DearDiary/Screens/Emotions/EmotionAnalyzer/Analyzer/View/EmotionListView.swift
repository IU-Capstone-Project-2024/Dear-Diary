//
//  EmotionListView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 18.06.2024.
//

import UIKit
import PinLayout

final class EmotionListView: UIView {
    
    private(set) lazy var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color3)
        view.layer.cornerRadius = 24
        
        return view
    }()
    
    private(set) lazy var noEmotionsLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.text = "No emotions to analyze yet"
        label.textColor = UIColor(resource: .color1)
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        
        return label
    }()
    
    private(set) lazy var emotionListTableView: UITableView = {
        let tableView = UITableView()
        tableView.backgroundColor = .clear
        tableView.separatorStyle = .none
        tableView.register(EmotionCell.self, forCellReuseIdentifier: EmotionCell.reusableIdentifier)
        
        return tableView
    }()
    
    private(set) lazy var analyzeButton: UIButton = {
        let button = UIButton()
        button.setTitle("Next", for: .normal)
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
        [noEmotionsLabel, analyzeButton, emotionListTableView].forEach(contentView.addSubview)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        contentView.pin
            .top(self.pin.safeArea)
            .horizontally(20)
            .bottom(self.pin.safeArea)
        
        noEmotionsLabel.pin
            .center()
            .width(70%)
            .sizeToFit(.width)
        
        analyzeButton.pin
            .bottom(15)
            .left(15)
            .right(15)
            .height(45)
        
        emotionListTableView.pin
            .top(15)
            .above(of: analyzeButton)
            .width(of: contentView)
            .marginHorizontal(15)
        
    }
}

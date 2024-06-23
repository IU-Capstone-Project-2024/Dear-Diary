//
//  OldNoteView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit
import PinLayout

final class OldNoteView: UIView {
    
    private(set) lazy var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color3)
        view.layer.cornerRadius = 24
        
        return view
    }()
    
    private(set) lazy var textView: UITextView = {
        let textView = UITextView()
        textView.font = UIFont(name: "PlayfairDisplay-Regular", size: 18)
        textView.textColor = UIColor(resource: .color4)
        textView.backgroundColor = UIColor(resource: .color2)
        textView.layer.cornerRadius = 12
        textView.text = "Dear Diary!\n\nSomething written by user..."
        textView.textAlignment = .left
        textView.isEditable = false

        return textView
    }()
    
    private(set) lazy var deleteNoteButton: UIButton = {
        let button = UIButton()
        button.setTitle("Burn my note!", for: .normal)
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
        [textView, deleteNoteButton].forEach(contentView.addSubview)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        contentView.pin
            .top(self.pin.safeArea)
            .horizontally(20)
            .bottom(self.pin.safeArea)
        
        deleteNoteButton.pin
            .bottom(15)
            .left(15)
            .right(15)
            .height(45)
        
        textView.pin
            .top()
            .left()
            .right()
            .margin(18, 15, 18, 15)
            .above(of: deleteNoteButton)
    }
}

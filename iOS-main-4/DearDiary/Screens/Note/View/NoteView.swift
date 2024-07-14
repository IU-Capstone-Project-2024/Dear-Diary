//
//  NoteView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit
import PinLayout

final class NoteView: UIView {
    
    private(set) lazy var activityIndicator = UIActivityIndicatorView(style: .large)
    
    private(set) lazy var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color3)
        view.layer.cornerRadius = 24
        
        return view
    }()
    
    private lazy var contentWrapperView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor(resource: .color2)
        view.layer.cornerRadius = 12
        
        return view
    }()
    
    var placeholderText = "Dear Diary!\n\nStart writing about how you feel or get any help from Diary"
    
    private(set) lazy var textView: UITextView = {
        let textView = UITextView()
        textView.showsVerticalScrollIndicator = false
        textView.font = UIFont(name: "PlayfairDisplay-Regular", size: 18)
        textView.textColor = UIColor(resource: .color4)
        textView.backgroundColor = .clear
        textView.layer.cornerRadius = 12
        textView.text = "Dear Diary!\n\nStart writing about how you feel or get any help from Diary"
        textView.textAlignment = .left

        return textView
    }()
    
    private(set) lazy var helpButton: UIButton = {
        let button = UIButton()
        button.isEnabled = false
        button.setBackgroundImage(UIImage(resource: .help), for: .normal)
        
        return button
    }()
    
    private(set) lazy var saveNoteButton: UIButton = {
        let button = UIButton()
        button.isEnabled = false
        button.setTitle("Save my note", for: .normal)
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
        [contentWrapperView, helpButton, saveNoteButton, activityIndicator].forEach(contentView.addSubview)
        [textView].forEach(contentWrapperView.addSubview)
        
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
        
        helpButton.pin
            .right()
            .top(52)
            .width(46)
            .height(67)
        
        saveNoteButton.pin
            .bottom(15)
            .left(15)
            .right(15)
            .height(45)
        
        contentWrapperView.pin
            .top()
            .left()
            .right()
            .margin(18, 15, 18, 15)
            .above(of: saveNoteButton)
        
        textView.pin
            .all()
            .marginRight(46)
    }
}

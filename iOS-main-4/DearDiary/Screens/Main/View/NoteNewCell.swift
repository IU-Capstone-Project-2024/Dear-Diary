//
//  NoteNewCell.swift
//  DearDiary
//
//  Created by Алёна Максимова on 16.06.2024.
//

import UIKit
import PinLayout

final class NoteNewCell: UICollectionViewCell {
    
    static let reuseIdentifier = "NoteNewCell"
    
    private lazy var noteImage:UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
        
        imageView.image = UIImage(resource: .plusButton)
        
        return imageView
    }()
    
    private lazy var noteName: UILabel = {
        let label = UILabel()
        label.textColor = UIColor(resource: .color4)
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 20)
        label.text = "Add new note"
        label.textAlignment = .center
        
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
        self.backgroundColor = UIColor(resource: .color2)
        self.layer.cornerRadius = 16
        
        self.contentView.addSubview(noteImage)
        self.contentView.addSubview(noteName)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        noteImage.pin
            .vCenter()
            .hCenter()
            .height(30%)
            .width(30%)
        
        noteName.pin
            .bottom(12)
            .hCenter()
            .width(of: self)
            .sizeToFit(.width)
        
    }
}



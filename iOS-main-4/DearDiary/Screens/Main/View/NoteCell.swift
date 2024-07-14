//
//  NoteCell.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit
import PinLayout

final class NoteCell: UICollectionViewCell {
    
    static let reuseIdentifier = "NoteCell"
    
    public var title: String = "" {
        didSet {
            self.setTitle()
        }
    }
    
    public var image: UIImage = UIImage(resource: .noPhoto) {
        didSet {
            self.setImage()
        }
    }
    
    public var date: String = "" {
        didSet {
            self.setDate()
        }
    }
    
    private(set) lazy var noteImage:UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
        
        imageView.image = UIImage(resource: .noPhoto)
        
        return imageView
    }()
    
    private lazy var noteName: UILabel = {
        let label = UILabel()
        label.textColor = UIColor(resource: .color1)
        label.font = UIFont(name: "PlayfairDisplay-Bold", size: 15)
        label.text = "Note name"
        
        return label
    }()
    
    private lazy var noteDate: UILabel = {
        let label = UILabel()
        label.textColor = UIColor(resource: .color1)
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 13)
        label.text = "16.06.2024"
        
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
        self.contentView.addSubview(noteDate)
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        noteImage.image = UIImage(resource: .noPhoto)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        noteImage.pin
            .top()
            .left()
            .right()
            .height(70%)
            .width(of: self)
        
        noteName.pin
            .below(of: noteImage)
            .left(8)
            .width(of: self)
            .sizeToFit(.width)
        
        noteDate.pin
            .below(of: noteName)
            .left(8)
            .width(of: self)
            .sizeToFit(.width)
        
        noteImage.roundTopCorners(radius: 16)
    }
    
    private func setTitle(){
        self.noteName.text = title
    }
    
    private func setImage(){
        self.noteImage.image = image
    }
    
    private func setDate(){
        self.noteDate.text = date
    }
}


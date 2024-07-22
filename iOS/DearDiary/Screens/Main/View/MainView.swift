//
//  MainView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit
import PinLayout

final class MainView: UIView {
    
    private lazy var affirmationView: UILabel = {
        let label = UILabel()
        label.backgroundColor = UIColor(resource: .color3)
        label.layer.cornerRadius = 24
        label.layer.masksToBounds = true
        label.textAlignment = .center
        label.text = PositiveAffirmations.shared.getRandomAffirmation()
        label.font = UIFont(name: "PlayfairDisplay-Regular", size: 20)
        label.numberOfLines = 2
        
        return label
    }()
    
    private(set) lazy var upperView: EmotionsView = EmotionsView()
    
    private(set) lazy var notesCollectionView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
    
        layout.scrollDirection = .vertical
        
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        collectionView.backgroundColor = .clear
        collectionView.showsHorizontalScrollIndicator = false
        collectionView.showsVerticalScrollIndicator = false
        collectionView.isPagingEnabled = true
        collectionView.contentInsetAdjustmentBehavior = .never
        collectionView.register(NoteCell.self, forCellWithReuseIdentifier: NoteCell.reuseIdentifier)
        collectionView.register(NoteNewCell.self, forCellWithReuseIdentifier: NoteNewCell.reuseIdentifier)
                
        return collectionView
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
        [upperView, notesCollectionView, affirmationView].forEach(self.addSubview)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        upperView.pin
            .top(self.pin.safeArea)
            .width(of: self)
            .height(136)
        
        affirmationView.pin
            .bottom(self.pin.safeArea)
            .hCenter()
            .height(66)
            .width(90%)
        
        notesCollectionView.pin
            .below(of: upperView)
            .above(of: affirmationView)
            .marginBottom(15)
            .width(90%)
            .hCenter()
        
    }
}

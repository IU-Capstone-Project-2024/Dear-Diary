//
//  MainViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit

final class MainViewController: UIViewController {
    
    private var mainView: MainView {
        return view as! MainView
    }
    
    override func loadView() {
        super.loadView()
        self.view = MainView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Dear Diary"
        self.navigationItem.hidesBackButton = true
        
        mainView.notesCollectionView.delegate = self
        mainView.notesCollectionView.dataSource = self
        
        addTargets()
    }
    
    func addTargets() {
        let emotionListGesture = UITapGestureRecognizer(target: self, action: #selector(goToEmotionListPage))
        self.mainView.upperView.emotionView.addGestureRecognizer(emotionListGesture)
        
        let newEmotionGesture = UITapGestureRecognizer(target: self, action: #selector(goToNewEmotionPage))
        self.mainView.upperView.emotionRecognizerView.addGestureRecognizer(newEmotionGesture)
    }
    
    @objc func goToEmotionListPage() {
        let view = EmotionListViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
    
    @objc func goToNewEmotionPage() {
        let view = NewEmotionViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
}

// MARK: UICollectionViewDataSource & UICollectionViewDelegate
extension MainViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        10
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        var cell = UICollectionViewCell()
        if indexPath.row == 0 {
            cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: NoteNewCell.reuseIdentifier,
                for: indexPath) as! NoteNewCell
            
        } else {
            cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: NoteCell.reuseIdentifier,
                for: indexPath) as! NoteCell
        }
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        let sectionInsets = UIEdgeInsets(top: 16.0, left: 16.0, bottom: 20.0, right: 16.0)
        
        let paddingSpace = sectionInsets.left + sectionInsets.right + 8 * (2 - 1)
        let availableWidth = collectionView.bounds.width - paddingSpace
        let widthPerItem = availableWidth / 2
        return CGSize(width: widthPerItem, height: widthPerItem)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
        
        if indexPath.row == 0 {
            let view = NoteViewController()
            self.navigationController?.pushViewController(
                view,
                animated: true
            )
        } else {
            let view = OldNoteViewController()
            self.navigationController?.pushViewController(
                view,
                animated: true
            )
        }
    }
    
}

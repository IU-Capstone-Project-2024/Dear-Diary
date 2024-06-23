//
//  NewEmotionDescriptionViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 22.06.2024.
//

import UIKit

final class NewEmotionDescriptionViewController: UIViewController {
    
    private var newEmotionDescriptionView: NewEmotionDescriptionView {
        return view as! NewEmotionDescriptionView
    }
    
    override func loadView() {
        super.loadView()
        self.view = NewEmotionDescriptionView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "New emotion"
        
        addTargets()
    }
    
    func addTargets() {
        newEmotionDescriptionView.backButton.addTarget(self, action: #selector(back), for: .touchUpInside)
        newEmotionDescriptionView.saveButton.addTarget(self, action: #selector(saveAndGoBack), for: .touchUpInside)
    }
    
    @objc func back() {
        let view = MainViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
    
    @objc func saveAndGoBack() {
        print("Emotion was saved.")
        
        let view = MainViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
    
}

//
//  NewEmotionDescriptionViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 22.06.2024.
//

import UIKit

final class NewEmotionDescriptionViewController: UIViewController {
    
    public var emotion = ""
    public var recommendation = ""
    
    private var newEmotionDescriptionView: NewEmotionDescriptionView {
        return view as! NewEmotionDescriptionView
    }
    
    override func loadView() {
        super.loadView()
        self.view = NewEmotionDescriptionView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        newEmotionDescriptionView.title = emotion
        newEmotionDescriptionView.recommendation = recommendation
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "New emotion"
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        
        addTargets()
    }
    
    func addTargets() {
        newEmotionDescriptionView.backButton.addTarget(self, action: #selector(back), for: .touchUpInside)
        newEmotionDescriptionView.saveButton.addTarget(self, action: #selector(saveAndGoBack), for: .touchUpInside)
    }
    
    @objc func back() {
        self.navigationController?.popToRootViewController(animated: true)
    }
    
    @objc func saveAndGoBack() {
        print("Emotion was saved.")
        CoreDataManager.shared.createEmotion(title: newEmotionDescriptionView.emotionLabel.text ?? "", date: Date.now)
        
        self.navigationController?.popToRootViewController(animated: true)
    }
    
}

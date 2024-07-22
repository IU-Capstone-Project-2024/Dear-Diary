//
//  NewEmotionViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 22.06.2024.
//

import UIKit

final class NewEmotionViewController: UIViewController {
        
    private var newEmotionView: NewEmotionView {
        return view as! NewEmotionView
    }
    
    override func loadView() {
        super.loadView()
        self.view = NewEmotionView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "New emotion"
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        self.navigationItem.leftBarButtonItem = .init(image: UIImage(systemName: "chevron.backward"), style: .plain, target: self, action: #selector(doNotSaveEmotion))
        
        self.newEmotionView.textView.delegate = self
        
        addTargets()
    }
    
    func addTargets() {
        newEmotionView.analyzeButton.addTarget(self, action: #selector(analyze), for: .touchUpInside)
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(finishEditing))
        view.addGestureRecognizer(tapGesture)
    }
    
    @objc func analyze() {
        newEmotionView.activityIndicator.startAnimating()
        
        BasePresenter.shared.getEmotion(note: newEmotionView.textView.text, completion: { [weak self] emotions in
            self?.newEmotionView.activityIndicator.stopAnimating()
            
            if emotions.isEmpty {
                print("Failed to fetch emotion data")
            } else {
                let emotion = emotions[0]
                let recommendation = emotions[1]
                print("Emotion: \(emotion)")
                print("Recommendation: \(recommendation)")
                
                let view = NewEmotionDescriptionViewController()
                view.emotion = emotion
                view.recommendation = recommendation
                
                self?.navigationController?.pushViewController(
                    view,
                    animated: true
                )
            }
        })

    }
        
    @objc private func finishEditing() {
        view.endEditing(true)
    }
    
    @objc private func doNotSaveEmotion() {
        let alert = UIAlertController(
            title: "Your notes will not be saved. Are you sure?",
            message: nil,
            preferredStyle: .alert
        )
                
        let actionCancel = UIAlertAction(title: "No", style: .cancel)
        
        let actionDelete = UIAlertAction(title: "Yes", style: .destructive) { _ in
            self.navigationController?.popViewController(animated: true)
        }
        
        alert.addAction(actionDelete)
        alert.addAction(actionCancel)
        
        self.present(alert, animated: true)
    }
}

extension NewEmotionViewController: UITextViewDelegate {
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.text == self.newEmotionView.placeholderText {
            textView.text = ""
            
            newEmotionView.analyzeButton.isEnabled = true
            newEmotionView.analyzeButton.backgroundColor = UIColor(resource: .color1)
        }
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        if textView.text.isEmpty {
            textView.text = self.newEmotionView.placeholderText
            newEmotionView.analyzeButton.isEnabled = false
            newEmotionView.analyzeButton.backgroundColor = UIColor(resource: .unactive)
        }
    }
}

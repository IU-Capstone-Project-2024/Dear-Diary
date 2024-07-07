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
}

extension NewEmotionViewController: UITextViewDelegate {
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.text == self.newEmotionView.placeholderText {
                textView.text = ""
            }
        }
        
        func textViewDidEndEditing(_ textView: UITextView) {
            if textView.text.isEmpty {
                textView.text = self.newEmotionView.placeholderText
            }
        }
}

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
        
        self.newEmotionView.textView.delegate = self
        
        addTargets()
    }
    
    func addTargets() {
        newEmotionView.analyzeButton.addTarget(self, action: #selector(analyze), for: .touchUpInside)
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(finishEditing))
        view.addGestureRecognizer(tapGesture)
    }
    
    @objc func analyze() {
        let view = NewEmotionDescriptionViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
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

//
//  NoteViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit

final class NoteViewController: UIViewController {
    
    private var noteView: NoteView {
        return view as! NoteView
    }
    
    override func loadView() {
        super.loadView()
        self.view = NoteView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "New note"
        self.noteView.textView.delegate = self
        
        addTargets()
    }
    
    func addTargets() {
        self.noteView.saveNoteButton.addTarget(self, action: #selector(saveAndGoBack), for: .touchUpInside)
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(finishEditing))
        view.addGestureRecognizer(tapGesture)
    }
    
    @objc func saveAndGoBack() {
        print("Note was saved.")
        
        let view = MainViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
    
    @objc private func finishEditing() {
        view.endEditing(true)
    }
}

extension NoteViewController: UITextViewDelegate {
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.text == self.noteView.placeholderText {
                textView.text = ""
            }
        }
        
        func textViewDidEndEditing(_ textView: UITextView) {
            if textView.text.isEmpty {
                textView.text = self.noteView.placeholderText
            }
        }
}

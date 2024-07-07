//
//  NoteViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit
import CoreData

final class NoteViewController: UIViewController {
    
    var noteTitle = "New note"
    
    private var noteView: NoteView {
        return view as! NoteView
    }
    
    override func loadView() {
        super.loadView()
        self.view = NoteView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = noteTitle
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        self.navigationItem.rightBarButtonItem = .init(image: UIImage(resource: .dislike), style: .plain, target: self, action: #selector(report))
        
        
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

        DispatchQueue.main.async {
            BasePresenter.shared.getNoteCoverUrl(imageId: "") { [weak self] imageUrl, noteId in
                if let imageUrl = imageUrl, let noteId = noteId {
                    self?.nameNote(noteId, imageUrl)
                } else {
                    print("Failed to get NoteCover imageUrl")
                }
            }
        }
    }
    
    @objc private func finishEditing() {
        view.endEditing(true)
    }
    
    @objc private func report() {
        print("Report bad answer.")
    }
    
    private func nameNote(_ id: String, _ url: String) {
        let alert = UIAlertController(title: "New name",
                                      message: "Name this note",
                                      preferredStyle: .alert)
        
        let saveAction = UIAlertAction(title: "Save",
                                       style: .default) { (action: UIAlertAction!) -> Void in
            
            let textField = alert.textFields![0]
            self.noteTitle = textField.text ?? "Name"
            
            CoreDataManager.shared.createNote(id, title: self.noteTitle, date: Date.now, url: url, text: self.noteView.textView.text)
            self.navigationController?.popToRootViewController(animated: true)
        }
        
        alert.addTextField {
            (textField: UITextField!) -> Void in
        }
        
        alert.addAction(saveAction)
        present(alert, animated: true, completion: nil)
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

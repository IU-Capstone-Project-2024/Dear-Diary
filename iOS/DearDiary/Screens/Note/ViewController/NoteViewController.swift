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
    private var lastAgentResponse: String?
    private var noteEntries: [NoteEntry] = []
    private var lastTextViewPosition: UITextPosition?
    
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
        self.navigationItem.leftBarButtonItem = .init(image: UIImage(systemName: "chevron.backward"), style: .plain, target: self, action: #selector(doNotSaveNote))
        self.navigationItem.rightBarButtonItem = .init(image: UIImage(resource: .dislike), style: .plain, target: self, action: #selector(report))
        
        self.noteView.textView.delegate = self
        addTargets()
    }
    
    func addTargets() {
        self.noteView.saveNoteButton.addTarget(self, action: #selector(saveAndGoBack), for: .touchUpInside)
        self.noteView.helpButton.addTarget(self, action: #selector(getHelp), for: .touchUpInside)
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(finishEditing))
        self.noteView.addGestureRecognizer(tapGesture)
    }
    
    func displayNoteEntries() {
        let attributedText = NSMutableAttributedString()
        
        let userColor = UIColor(resource: .color4)
        let agentColor = UIColor(resource: .color1)
        let font = UIFont(name: "PlayfairDisplay-Regular", size: 18)
        
        for entry in noteEntries {
            let color = entry.agent == "user" ? userColor : agentColor
            let entryText = NSAttributedString(string: "\(entry.text)\n", attributes: [.foregroundColor: color, .font: font!])
            attributedText.append(entryText)
        }
        
        let defaultText = NSAttributedString(string: "\n", attributes: [.foregroundColor: userColor, .font: font!])
        attributedText.append(defaultText)
        
        self.noteView.textView.attributedText = attributedText
    }
    
    @objc func getHelp() {
        self.noteView.activityIndicator.startAnimating()
        view.endEditing(true)
        BasePresenter.shared.getNoteResponse(noteEntries: noteEntries) { [weak self] answer in
            self?.noteView.activityIndicator.stopAnimating()
            
            if let answer = answer {
                let botEntry = NoteEntry(agent: "bot", text: answer)
                self?.noteEntries.append(botEntry)
                self?.lastAgentResponse = answer
                self?.displayNoteEntries()
                self?.noteView.helpButton.isEnabled = false
            } else {
                print("Failed to get response")
            }
        }
    }
    
    @objc func saveAndGoBack() {
        print("Note was saved.")
        
        DispatchQueue.main.async {
            BasePresenter.shared.getNoteCoverUrl(imageId: "") { [weak self] imageUrl, noteId in
                guard let self = self else { return }
                if let imageUrl = imageUrl, let noteId = noteId {
                    self.nameNote(noteId, imageUrl)
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
        
        DispatchQueue.main.async {
            BasePresenter.shared.getNoteTitle(note: self.noteView.textView.text) { noteTitle in
                alert.textFields![0].text = noteTitle
            }
        }
        
        let saveAction = UIAlertAction(title: "Save",
                                       style: .default) { (action: UIAlertAction!) -> Void in
            
            let textField = alert.textFields![0]
            self.noteTitle = textField.text ?? "Name"
            
            CoreDataManager.shared.createNote(id, title: self.noteTitle, date: Date(), url: url, text: self.noteView.textView.text)
            self.navigationController?.popToRootViewController(animated: true)
        }
        
        alert.addTextField {
            (textField: UITextField!) -> Void in
        }
        
        alert.addAction(saveAction)
        present(alert, animated: true, completion: nil)
    }
    
    @objc private func doNotSaveNote() {
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

extension NoteViewController: UITextViewDelegate {
    
    func textViewDidChange(_ textView: UITextView) {
        noteView.helpButton.isEnabled = true
    }
        
    func textViewDidBeginEditing(_ textView: UITextView) {
        
        if textView.text == self.noteView.placeholderText {
            textView.text = ""
            
            noteView.saveNoteButton.isEnabled = true
            noteView.saveNoteButton.backgroundColor = UIColor(resource: .color1)
        }
        
        // Capture the last position where editing began
        lastTextViewPosition = textView.endOfDocument
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        if textView.text.isEmpty {
            textView.text = self.noteView.placeholderText
            
            noteView.helpButton.isEnabled = false
            noteView.saveNoteButton.isEnabled = false
            noteView.saveNoteButton.backgroundColor = UIColor(resource: .unactive)
        } else {
            // Capture the user input from the last editing position
            if let lastTextViewPosition = lastTextViewPosition {
                let start = lastTextViewPosition
                let end = textView.endOfDocument
                guard let range = textView.textRange(from: start, to: end) else { return }
                if let userInput = textView.text(in: range) {
                    let userEntry = NoteEntry(agent: "user", text: userInput)
                    noteEntries.append(userEntry)
                    noteView.helpButton.isEnabled = true
                    displayNoteEntries()
                }
            }
        }
    }
}


//
//  OldNoteViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit

final class OldNoteViewController: UIViewController {
    
    var noteID = ""
    var note = Note()
    
    private var oldNoteView: OldNoteView {
        return view as! OldNoteView
    }
    
    override func loadView() {
        super.loadView()
        self.view = OldNoteView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        note = CoreDataManager.shared.fetchNote(with: noteID) ?? Note()
        
        self.title = note.title
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        
        oldNoteView.textView.text = note.text
        
        addTargets()
    }
    
    func addTargets() {
        oldNoteView.deleteNoteButton.addTarget(self, action: #selector(deleteNote), for: .touchUpInside)
    }
    
    @objc func deleteNote() {
        let alert = UIAlertController(
            title: "Your note will be deleted. Are you sure?",
            message: nil,
            preferredStyle: .alert
        )
                
        let actionCancel = UIAlertAction(title: "Cancel", style: .cancel)
        
        let actionDelete = UIAlertAction(title: "Delete", style: .destructive) { (action: UIAlertAction!) -> Void in
            
            CoreDataManager.shared.deleteNote(with: self.noteID)
            self.navigationController?.popViewController(animated: true)
        }
        
        alert.addAction(actionCancel)
        alert.addAction(actionDelete)
        
        self.present(alert, animated: true)
        
    }
}

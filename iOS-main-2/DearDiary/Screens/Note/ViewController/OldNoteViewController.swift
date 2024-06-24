//
//  OldNoteViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit

final class OldNoteViewController: UIViewController {
    
    private var oldNoteView: OldNoteView {
        return view as! OldNoteView
    }
    
    override func loadView() {
        super.loadView()
        self.view = OldNoteView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "My notes"
        
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
        
        let actionDelete = UIAlertAction(title: "Delete", style: .destructive)
        
        alert.addAction(actionCancel)
        alert.addAction(actionDelete)
        
        self.present(alert, animated: true)
        
    }
}

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
        
        addTargets()
    }
    
    func addTargets() {
        
    }
}

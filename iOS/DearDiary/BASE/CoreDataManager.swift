//
//  CoreDataManager.swift
//  DearDiary
//
//  Created by Алёна Максимова on 02.07.2024.
//

import UIKit
import CoreData

public final class CoreDataManager: NSObject {
    
    public static let shared = CoreDataManager()
    private override init() {}
    
    private var appDelegate: AppDelegate {
        UIApplication.shared.delegate as! AppDelegate
    }
    
    private var context: NSManagedObjectContext {
        appDelegate.persistentContainer.viewContext
    }
    
// MARK: - Note requests
    
    public func createNote(_ id: String?, title: String, date: Date, url: String?, text: String) {
        guard let noteEntityDescription = NSEntityDescription.entity(forEntityName: "Note", in: context) else { return }
        
        let note = Note(entity: noteEntityDescription, insertInto: context)
        note.id = id
        note.title = title
        note.date = date
        note.image = url
        note.text = text
        
        appDelegate.saveContext()
    }
    
    public func fetchNotes() -> [Note] {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Note")
        
        do {
            return (try? context.fetch(fetchRequest) as? [Note]) ?? []
        }
    }
    
    public func fetchNote(with id: String)-> Note? {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Note")
        
        do {
            let notes = try? context.fetch(fetchRequest) as? [Note]
            return notes?.first(where: { $0.id == id })
        }
    }
    
    public func updataNote(with id: String, title: String) {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Note")
        
        do {
            guard let notes = try? context.fetch(fetchRequest) as? [Note],
                  let note = notes.first(where: { $0.id == id }) else { return }
            
            note.title = title
        }
        
        appDelegate.saveContext()
    }
    
    public func deletaAllNotes() {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Note")
        
        do {
            let notes = try? context.fetch(fetchRequest) as? [Note]
            notes?.forEach { context.delete($0) }
        }
        
        appDelegate.saveContext()
    }
    
    public func deleteNote(with id: String) {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Note")
        
        do {
            guard let notes = try? context.fetch(fetchRequest) as? [Note],
                  let note = notes.first(where: { $0.id == id }) else { return }
            context.delete(note)
        }
        
        appDelegate.saveContext()
    }
    
    
    
// MARK: - Emotion requests
    
    public func createEmotion(title: String, date: Date) {
        guard let emotionEntityDescription = NSEntityDescription.entity(forEntityName: "Emotion", in: context) else { return }
        
        let emotion = Emotion(entity: emotionEntityDescription, insertInto: context)
        emotion.title = title
        emotion.date = date
        
        appDelegate.saveContext()
    }
    
    public func fetchEmotions() -> [Emotion] {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Emotion")
        
        do {
            return (try? context.fetch(fetchRequest) as? [Emotion]) ?? []
        }
    }
}

//
//  Note+CoreDataProperties.swift
//  DearDiary
//
//  Created by Алёна Максимова on 02.07.2024.
//
//

import Foundation
import CoreData

@objc(Note)
public class Note: NSManagedObject {}

extension Note {
    @NSManaged public var title: String?
    @NSManaged public var date: Date?
    @NSManaged public var image: String?
    @NSManaged public var text: String?
    @NSManaged public var id: String?

}

extension Note : Identifiable {}

//
//  Emotion+CoreDataProperties.swift
//  DearDiary
//
//  Created by Алёна Максимова on 03.07.2024.
//
//

import Foundation
import CoreData

@objc(Emotion)
public class Emotion: NSManagedObject {}

extension Emotion {

    @NSManaged public var title: String?
    @NSManaged public var date: Date?

}

extension Emotion : Identifiable {}

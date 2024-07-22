//
//  Extensions.swift
//  DearDiary
//
//  Created by Алёна Максимова on 07.07.2024.
//

import UIKit

extension UIImageView {
    func roundTopCorners(radius: CGFloat) {
        let path = UIBezierPath(roundedRect: bounds,
                                byRoundingCorners: [.topLeft, .topRight],
                                cornerRadii: CGSize(width: radius, height: radius))
        
        let mask = CAShapeLayer()
        mask.path = path.cgPath
        layer.mask = mask
    }
}

extension Date {
    static func startOfDay() -> Date {
        return Calendar.current.startOfDay(for: Date())
    }
    
    static func startOfWeek() -> Date {
        let now = Date()
        let startOfWeek = Calendar.current.dateInterval(of: .weekOfYear, for: now)?.start
        return startOfWeek ?? now
    }
    
    static func startOfMonth() -> Date {
        let now = Date()
        let startOfMonth = Calendar.current.dateInterval(of: .month, for: now)?.start
        return startOfMonth ?? now
    }
}

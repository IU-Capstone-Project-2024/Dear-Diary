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

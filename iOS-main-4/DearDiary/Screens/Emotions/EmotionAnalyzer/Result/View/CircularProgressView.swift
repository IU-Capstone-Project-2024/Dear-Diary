//
//  CircularProgressView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit
import PinLayout

class CircularProgressView: UIView {
    
    private var progressLayers: [CAShapeLayer] = []
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    func setProgress(with segments: [(value: Double, color: UIColor)]) {
        // Remove previous layers
        progressLayers.forEach { $0.removeFromSuperlayer() }
        progressLayers.removeAll()
        
        var startAngle = -CGFloat.pi / 2
        
        for segment in segments {
            let progressLayer = CAShapeLayer()
            let circlePath = UIBezierPath(arcCenter: center, radius: 100, startAngle: startAngle, endAngle: startAngle + CGFloat(segment.value * 2 * Double.pi), clockwise: true)
            progressLayer.path = circlePath.cgPath
            progressLayer.fillColor = UIColor.clear.cgColor
            progressLayer.strokeColor = segment.color.cgColor
            progressLayer.lineWidth = 40.0
            progressLayer.lineCap = .round
            progressLayer.strokeEnd = 1.0
            layer.addSublayer(progressLayer)
            progressLayers.append(progressLayer)
            
            startAngle += CGFloat(segment.value * 2 * Double.pi)
        }
    }
}


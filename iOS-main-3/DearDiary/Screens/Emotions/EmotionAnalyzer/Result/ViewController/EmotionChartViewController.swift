//
//  EmotionChartViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit
import PinLayout

final class EmotionChartViewController: UIViewController {
    
    private var emotionChartView: EmotionChartView {
        return view as! EmotionChartView
    }
    
    override func loadView() {
        super.loadView()
        self.view = EmotionChartView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "My emotions"
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        
        addTargets()
        
        let segments: [(value: Double, color: UIColor, label: String)] = [
            (0.4, UIColor(resource: .color2), "Anger"),
            (0.6, UIColor(resource: .color1), "Happiness")
        ]
        
        setProgress(with: segments)
        setupLabels(with: segments)
    }
    
    func addTargets() {
        emotionChartView.backButton.addTarget(self, action: #selector(back), for: .touchUpInside)
    }
    
    @objc func back() {
        self.navigationController?.popToRootViewController(animated: true)
    }
    
    private func setupLabels(with segments: [(value: Double, color: UIColor, label: String)]) {
        
        for segment in segments {
            let label = EmotionNameChartView()
            label.title = segment.label
            label.background = segment.color
            
            emotionChartView.addSubview(label)
            emotionChartView.emotionLabels.append(label)
        }
        emotionChartView.setNeedsLayout()
    }
    
    private func setProgress(with segments: [(value: Double, color: UIColor, label: String)]) {
        let progressSegments = segments.map { ($0.value, $0.color) }
        emotionChartView.circularProgressView.setProgress(with: progressSegments)
    }
    
    override func viewDidLayoutSubviews() {
            super.viewDidLayoutSubviews()
            
        
            
        var previousLabelBottom: CGFloat = emotionChartView.circularProgressView.frame.maxY + 20
            
        for label in emotionChartView.emotionLabels {
                label.pin
                    .top(previousLabelBottom)
                    .hCenter()
                    .width(200)
                    .height(40)
                
                previousLabelBottom = label.frame.maxY + 10
            }
        }
}

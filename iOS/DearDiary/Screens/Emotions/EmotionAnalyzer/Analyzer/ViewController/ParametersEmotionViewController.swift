//
//  ParametersEmotionViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit

enum TimeRange: String {
    case today = "Today"
    case lastWeek = "Last week"
    case lastMonth = "Last month"
}

final class ParametersEmotionViewController: UIViewController {
    
    var emotions: [Emotion] = []
    
    private var parametersEmotionView: ParametersEmotionView {
        return view as! ParametersEmotionView
    }
    
    override func loadView() {
        super.loadView()
        self.view = ParametersEmotionView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "My emotions"
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        addTargets()
    }
    
    func addTargets() {
        parametersEmotionView.analyzeButton.addTarget(self, action: #selector(chartPage), for: .touchUpInside)
    }
    
    func filterEmotions(emotions: [Emotion], by timeRange: TimeRange) -> [String] {
        let startDate: Date
        
        switch timeRange {
        case .today:
            startDate = Date.startOfDay()
        case .lastWeek:
            startDate = Date.startOfWeek()
        case .lastMonth:
            startDate = Date.startOfMonth()
        }
        
        let filteredEmotions = emotions.filter { $0.date ?? Date() >= startDate }
        return filteredEmotions.compactMap { $0.title }
    }
    
    @objc func chartPage() {
        parametersEmotionView.activityIndicator.startAnimating()
        
        BasePresenter.shared.getEmotionAnalisis(emotions: filterEmotions(emotions: emotions, by: TimeRange(rawValue: self.parametersEmotionView.currentOption) ?? .today), completion: { [weak self] length, emotions in
            self?.parametersEmotionView.activityIndicator.stopAnimating()
            
            if let length = length, let emotions = emotions {
                let view = EmotionChartViewController()
                view.emotions = emotions
                view.option = self?.parametersEmotionView.currentOption ?? "Today"
                
                self?.navigationController?.pushViewController(
                    view,
                    animated: true
                )
            } else {
                print("Failed to get emotion analysis.")
            }
        })
    }
}

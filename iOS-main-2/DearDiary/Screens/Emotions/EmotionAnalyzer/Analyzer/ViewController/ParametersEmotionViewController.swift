//
//  ParametersEmotionViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit

final class ParametersEmotionViewController: UIViewController {
    
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
        
        addTargets()
    }
    
    func addTargets() {
        parametersEmotionView.analyzeButton.addTarget(self, action: #selector(chartPage), for: .touchUpInside)
    }
    
    @objc func chartPage() {
        let view = EmotionChartViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
}

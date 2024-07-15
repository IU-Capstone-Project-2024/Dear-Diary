//
//  QuestionaryViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit

final class QuestionaryViewController: UIViewController {
    
    private var onboardingView: QuestionaryView {
        return view as! QuestionaryView
    }
    
    override func loadView() {
        super.loadView()
        self.view = QuestionaryView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Dear Diary"
        
        addTargets()
    }
    
    func addTargets() {
        onboardingView.nextButton.addTarget(self, action: #selector(navigateToMainPage), for: .touchUpInside)
    }
    
    @objc func navigateToMainPage() {
        UserDefaults.standard.hasCompletedOnboarding = true
        
        if let windowScene = view.window?.windowScene {
            let mainViewController = MainViewController()
            let transition = CATransition()
            transition.type = .fade
            transition.duration = 0.3
            windowScene.windows.first?.layer.add(transition, forKey: kCATransition)
            windowScene.windows.first?.rootViewController = BaseNavigationController(rootViewController: mainViewController)
        }
    }
}

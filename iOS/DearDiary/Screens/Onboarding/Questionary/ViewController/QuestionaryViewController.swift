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
        
        var options: [String] = []
        for option in onboardingView.options {
            if option.checkbox.isChecked == true {
                options.append(option.title)
            }
        }
        
        onboardingView.activityIndicator.startAnimating()
        
        BasePresenter.shared.onboarding(usage: options, completion: { [weak self] success in
            self?.onboardingView.activityIndicator.stopAnimating()
            
            if success {
                if let windowScene = self?.view.window?.windowScene {
                    let mainViewController = MainViewController()
                    let transition = CATransition()
                    transition.type = .fade
                    transition.duration = 0.3
                    windowScene.windows.first?.layer.add(transition, forKey: kCATransition)
                    windowScene.windows.first?.rootViewController = BaseNavigationController(rootViewController: mainViewController)
                }
            }
        })
    }
}

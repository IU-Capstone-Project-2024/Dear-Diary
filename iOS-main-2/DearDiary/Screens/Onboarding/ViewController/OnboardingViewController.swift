//
//  OnboardingViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 15.06.2024.
//

import UIKit

final class OnboardingViewController: UIViewController {
    
    private var onboardingView: OnboardingView {
        return view as! OnboardingView
    }
    
    override func loadView() {
        super.loadView()
        self.view = OnboardingView()
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
        let view = MainViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
}

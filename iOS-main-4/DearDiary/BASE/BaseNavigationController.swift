//
//  BaseNavigationController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 24.06.2024.
//

import UIKit

class BaseNavigationController: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()
        setUpBackButton()
    }
    
    private func setUpBackButton(){
        self.navigationBar.backIndicatorImage = UIImage(resource: .back)

        self.navigationBar.backIndicatorTransitionMaskImage = UIImage(resource: .back)
        
        self.navigationBar.tintColor = .init(resource: .color1)
        
    }

}

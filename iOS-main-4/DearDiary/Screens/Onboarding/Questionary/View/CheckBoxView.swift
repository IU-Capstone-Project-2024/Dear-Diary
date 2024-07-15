//
//  CheckBoxView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 16.06.2024.
//

import UIKit

final class CheckBoxView: UIButton {
    
    // Images
    var checkedImage = UIImage()
    var uncheckedImage = UIImage()
    
    public var singleChoice: Bool = false {
        didSet {
            if singleChoice == true {
                checkedImage = UIImage(resource: .circleChecked)
                uncheckedImage = UIImage(resource: .circleUnchecked)
            } else {
                checkedImage = UIImage(resource: .boxChecked)
                uncheckedImage = UIImage(resource: .boxUnchecked)
            }
        }
    }
    
    
    // Bool property
    var isChecked: Bool = false {
        didSet {
            if isChecked == true {
                self.setImage(checkedImage, for: .normal)
            } else {
                self.setImage(uncheckedImage, for: .normal)
            }
        }
    }
}

//
//  CheckBoxView.swift
//  DearDiary
//
//  Created by Алёна Максимова on 16.06.2024.
//

import UIKit

final class CheckBoxView: UIButton {
    // Images
    let checkedImage = UIImage(named: "checked")
    let uncheckedImage = UIImage(named: "unchecked")
    
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

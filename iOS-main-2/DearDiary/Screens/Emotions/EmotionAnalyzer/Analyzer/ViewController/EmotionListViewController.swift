//
//  EmotionListViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 18.06.2024.
//

import UIKit

final class EmotionListViewController: UIViewController {
    
    private var emotionListView: EmotionListView {
        return view as! EmotionListView
    }
    
    override func loadView() {
        super.loadView()
        self.view = EmotionListView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "My emotions"
        
        emotionListView.emotionListTableView.delegate = self
        emotionListView.emotionListTableView.dataSource = self
        addTargets()
    }
    
    func addTargets() {
        emotionListView.analyzeButton.addTarget(self, action: #selector(parametersPage), for: .touchUpInside)
    }
    
    @objc func parametersPage() {
        let view = ParametersEmotionViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
}

extension EmotionListViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard let cell = tableView.dequeueReusableCell(withIdentifier: EmotionCell.reusableIdentifier, for: indexPath) as? EmotionCell else {
            fatalError("Failed to dequeue EmployeeCell")
        }
        
        cell.title = "Anger"
        cell.date = "18.06.2024"
        
        return cell
    }
}

extension EmotionListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 70
    }
}


//
//  EmotionListViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 18.06.2024.
//

import UIKit

final class EmotionListViewController: UIViewController {
    
    var emotions = [Emotion]()
    
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
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        
        emotions = CoreDataManager.shared.fetchEmotions()
        
        if emotions.isEmpty {
            emotionListView.noEmotionsLabel.isHidden = false
            emotionListView.analyzeButton.isEnabled = false
            emotionListView.analyzeButton.backgroundColor = UIColor(resource: .unactive)
            
        } else {
            emotionListView.noEmotionsLabel.isHidden = true
            emotionListView.analyzeButton.isEnabled = true
            emotionListView.analyzeButton.backgroundColor = UIColor(resource: .color1)
        }
        
        emotionListView.emotionListTableView.delegate = self
        emotionListView.emotionListTableView.dataSource = self
        addTargets()
    }
    
    func addTargets() {
        emotionListView.analyzeButton.addTarget(self, action: #selector(parametersPage), for: .touchUpInside)
    }
    
    @objc func parametersPage() {
        let view = ParametersEmotionViewController()
        view.emotions = emotions
        
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
}

extension EmotionListViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return emotions.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard let cell = tableView.dequeueReusableCell(withIdentifier: EmotionCell.reusableIdentifier, for: indexPath) as? EmotionCell else {
            fatalError("Failed to dequeue EmployeeCell")
        }
        
        let emotion = emotions[indexPath.row]
        cell.title = emotion.value(forKey: "title") as? String ?? "Anger"
        
        let noteDate = emotion.value(forKey: "date") as? Date ?? Date.now
        let outputFormatter = DateFormatter()
        outputFormatter.dateFormat = "dd.MM.yyyy"
        cell.date = outputFormatter.string(from: noteDate)
        
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


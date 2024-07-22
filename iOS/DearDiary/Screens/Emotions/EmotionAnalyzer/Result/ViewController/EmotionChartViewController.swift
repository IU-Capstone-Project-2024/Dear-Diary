//
//  EmotionChartViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 23.06.2024.
//

import UIKit
import PinLayout

final class EmotionChartViewController: UIViewController {
    
    var emotions: [EmotionAnalisis] = []
    var option: String = ""
    
    private var emotionChartView: EmotionChartView {
        return view as! EmotionChartView
    }
    
    override func loadView() {
        super.loadView()
        self.view = EmotionChartView()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "My emotions"
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        
        emotionChartView.emotionListTableView.delegate = self
        emotionChartView.emotionListTableView.dataSource = self
        addTargets()
        
        emotionChartView.duration = option
    }
    
    func addTargets() {
        emotionChartView.backButton.addTarget(self, action: #selector(back), for: .touchUpInside)
    }
    
    @objc func back() {
        self.navigationController?.popToRootViewController(animated: true)
    }
    
}

extension EmotionChartViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return emotions.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard let cell = tableView.dequeueReusableCell(withIdentifier: ResultEmotionCell.reusableIdentifier, for: indexPath) as? ResultEmotionCell else {
            fatalError("Failed to dequeue ResultEmotionCell")
        }
        
        let emotion = emotions[indexPath.row]
        cell.title = emotion.emotion
        cell.amount = String(emotion.count)
        
        return cell
    }
}

extension EmotionChartViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 70
    }
}

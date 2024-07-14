//
//  OnboardingViewController.swift
//  DearDiary
//
//  Created by Алёна Максимова on 06.07.2024.
//

import UIKit
import PinLayout

class OnboardingViewController: UIViewController {

    private let scrollView = UIScrollView()
    private let pageControl = UIPageControl()
    private let pages = [
        OnboardingPage(imageName: "1", title: "Welcome!", description: "Hey! Now we will briefly explain you how to use our service."),
        OnboardingPage(imageName: "2", title: "Create a Note", description: "Press “Add new note”."),
        OnboardingPage(imageName: "3", title: "Get Assistance from AI", description: "Press the “life preserver” icon and ask whatever you need."),
        OnboardingPage(imageName: "4", title: "Understand Your Emotions", description: "Press this button, describe your condition, and get an answer."),
        OnboardingPage(imageName: "5", title: "View Recent Emotions and Statistics", description: "Find your recent emotions and get statistics for a chosen period."),
        OnboardingPage(imageName: "6", title: "Check Existing Notes", description: "Just press on a note card to view."),
        OnboardingPage(imageName: "7", title: "Privacy Assurance", description: "Your privacy is our main priority. None of your thoughts or data will be sent elsewhere.")
    ]

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Dear Diary"
        self.navigationItem.backBarButtonItem = .init(title: nil, style: .plain, target: nil, action: nil)
        self.view.backgroundColor = UIColor(resource: .color4)
        
        setupScrollView()
        setupPageControl()
    }

    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        setupPages()
    }

    private func setupScrollView() {
        scrollView.isPagingEnabled = true
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.delegate = self
        view.addSubview(scrollView)
        
        scrollView.pin.all()
    }

    private func setupPageControl() {
        pageControl.numberOfPages = pages.count
        pageControl.currentPage = 0
        pageControl.tintColor = .black
        pageControl.pageIndicatorTintColor = UIColor.lightGray
        pageControl.currentPageIndicatorTintColor = UIColor.black
        view.addSubview(pageControl)
        
        pageControl.pin.bottom(50).hCenter().width(200).height(50)
    }

    private func setupPages() {
        scrollView.contentSize = CGSize(width: view.frame.width * CGFloat(pages.count), height: view.frame.height)
        
        for (index, page) in pages.enumerated() {
            let pageView = OnboardingView()
            pageView.imageView.image = UIImage(named: page.imageName)
            pageView.titleLabel.text = page.title
            pageView.descriptionLabel.text = page.description
            scrollView.addSubview(pageView)
            
            if index == pages.count - 1 {
                pageView.nextButton.isHidden = false
                pageView.nextButton.addTarget(self, action: #selector(navigateToQuestionaryPage), for: .touchUpInside)
            }
            
            pageView.pin
                .top()
                .bottom()
                .left(view.frame.width * CGFloat(index))
                .width(view.frame.width)
                .height(view.frame.height)
        }
    }
    
    @objc func navigateToQuestionaryPage() {
        let view = QuestionaryViewController()
        self.navigationController?.pushViewController(
            view,
            animated: true
        )
    }
}

extension OnboardingViewController: UIScrollViewDelegate {
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let pageIndex = round(scrollView.contentOffset.x / view.frame.width)
        pageControl.currentPage = Int(pageIndex)
        scrollView.contentOffset.y = 0
        
        if Int(pageIndex) == pages.count - 1 {
            pageControl.isHidden = true
        } else if Int(pageIndex) == pages.count - 2 {
            pageControl.isHidden = false
        }
    }
}

struct OnboardingPage {
    let imageName: String
    let title: String
    let description: String
}


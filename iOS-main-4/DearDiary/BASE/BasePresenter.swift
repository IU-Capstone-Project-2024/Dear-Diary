//
//  BasePresenter.swift
//  DearDiary
//
//  Created by Алёна Максимова on 06.07.2024.
//

import UIKit
import Alamofire

struct NoteCoverResponse: Codable {
    let imageUrl: String
    let imageId: String
}

struct NoteEmotionResponse: Codable {
    let emotion: String
    let recommendation: String
}

struct NoteTitleResponse: Codable {
    let title: String
}

struct NoteEntry: Codable {
    let agent: String
    let text: String
}

struct NoteRequest: Codable {
    let note: [NoteEntry]
}

struct NoteResponse: Codable {
    let answer: String
}


import Alamofire
import UIKit

public final class BasePresenter {
    
    public static let shared = BasePresenter()
    
    private init() {}
    
    func awakeServer() {
        let url = DearDiaryURL.base + DearDiaryURL.status
        
        AF.request(url, method: .get).response { response in
            switch response.result {
            case .success:
                print("Server awaked.")
            case .failure(let error):
                print("Error: \(error.localizedDescription)")
            }
        }
    }
    
    // Method to download an image from a given URL
    func downloadImage(from imageUrl: String, completion: @escaping (UIImage?) -> Void) {
        guard let url = URL(string: imageUrl) else {
            print("Invalid URL")
            completion(nil)
            return
        }
        
        AF.download(url).responseData { response in
            switch response.result {
            case .success(let data):
                if let image = UIImage(data: data) {
                    completion(image)
                } else {
                    print("Failed to convert data to UIImage")
                    completion(nil)
                }
            case .failure(let error):
                print("Error downloading image: \(error.localizedDescription)")
                completion(nil)
            }
        }
    }
    
    // Method to fetch NoteCover imageUrl based on imageId
    func getNoteCoverUrl(imageId: String, completion: @escaping (String?, String?) -> Void) {
        let url = DearDiaryURL.base + DearDiaryURL.noteCover
        var parameters: [String: String] = [:]
            
            if !imageId.isEmpty {
                parameters["image_id"] = imageId
            }
        
        AF.request(url, method: .put, parameters: parameters, encoding: JSONEncoding.default).responseDecodable(of: NoteCoverResponse.self) { response in
            switch response.result {
            case .success(let noteCoverResponse):
                print("Image URL: \(noteCoverResponse.imageUrl)")
                print("Image ID: \(noteCoverResponse.imageId)")
                completion(noteCoverResponse.imageUrl, noteCoverResponse.imageId)
            case .failure(let error):
                print("Error: \(error.localizedDescription)")
                completion(nil, nil)
            }
        }
    }
    
    // Method to fetch NoteEmotion emotion based on note
    func getEmotion(note: String, completion: @escaping ([String]) -> Void) {
        let url = DearDiaryURL.base + DearDiaryURL.emotion
        let parameters: [String: String] = ["note": note]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default).responseDecodable(of: NoteEmotionResponse.self) { response in
            switch response.result {
            case .success(let noteCoverResponse):
                completion([noteCoverResponse.emotion, noteCoverResponse.recommendation])
            case .failure(let error):
                print("Error: \(error.localizedDescription)")
                completion([])
            }
        }
    }
    
    // Method to fetch NoteTitle title based on note
    func getNoteTitle(note: String, completion: @escaping (String) -> Void) {
        let url = DearDiaryURL.base + DearDiaryURL.noteTitle
        let parameters: [String: String] = ["note": note]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default).responseDecodable(of: NoteTitleResponse.self) { response in
            switch response.result {
            case .success(let NoteTitleResponse):
                completion(NoteTitleResponse.title)
            case .failure(let error):
                print("Error: \(error.localizedDescription)")
                completion("New name")
            }
        }
    }
    
    // Method to fetch NoteResponse answer based on note
    func getNoteResponse(noteEntries: [NoteEntry], completion: @escaping (String?) -> Void) {
        let url = DearDiaryURL.base + DearDiaryURL.help
        let parameters = NoteRequest(note: noteEntries)
        
        AF.request(url, method: .post, parameters: parameters, encoder: JSONParameterEncoder.default).responseDecodable(of: NoteResponse.self) { response in
            switch response.result {
            case .success(let noteResponse):
                completion(noteResponse.answer)
            case .failure(let error):
                print("Error: \(error.localizedDescription)")
                completion(nil)
            }
        }
    }
}

extension BasePresenter {
    struct DearDiaryURL {
        static let base = "https://dear-diary-capstone.onrender.com/"
        static let status = "status"
        static let help = "respondToNote"
        static let noteCover = "noteCover"
        static let noteTitle = "noteTitle"
        static let emotion = "emotion"
    }
}


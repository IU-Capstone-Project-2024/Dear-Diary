//
//  PositiveAffirmations.swift
//  DearDiary
//
//  Created by Алёна Максимова on 06.07.2024.
//

import Foundation

public final class PositiveAffirmations {
    
    public static let shared = PositiveAffirmations()
    
    private init() {}
    
    let affirmations: [String] = [
        "I am worthy of love and respect.",
        "I am capable of achieving my goals.",
        "I am grateful for my journey.",
        "I am growing stronger every day.",
        "I am proud of my progress.",
        "I trust my intuition.",
        "I am confident in my abilities.",
        "I embrace my uniqueness.",
        "I am open to new opportunities.",
        "I am surrounded by positive energy.",
        "I choose to be happy.",
        "I am at peace with myself.",
        "I am resilient and can overcome challenges.",
        "I am deserving of all good things.",
        "I am filled with positive thoughts.",
        "I am in control of my life.",
        "I am grateful for today.",
        "I believe in myself.",
        "I am a positive thinker.",
        "I am worthy of success.",
        "I am in charge of my happiness.",
        "I am a magnet for positivity.",
        "I am becoming a better version of myself.",
        "I am worthy of my dreams.",
        "I am full of potential.",
        "I am creating my own destiny.",
        "I am grateful for my body.",
        "I am a source of inspiration.",
        "I am proud of who I am becoming.",
        "I am open to change.",
        "I am a beacon of light.",
        "I am grateful for my health.",
        "I am strong and courageous.",
        "I am thankful for my support system.",
        "I am free to be myself.",
        "I am worthy of all the good in life.",
        "I am grateful for my talents.",
        "I am living my best life.",
        "I am kind to myself and others.",
        "I am in harmony with the universe.",
        "I am a beautiful person.",
        "I am open to abundance.",
        "I am capable of great things.",
        "I am enough just as I am.",
        "I am grateful for my experiences.",
        "I am committed to my personal growth.",
        "I am filled with love and compassion.",
        "I am worthy of inner peace.",
        "I am grateful for my wisdom.",
        "I am a work in progress.",
        "I am strong in mind, body, and spirit.",
        "I am a positive influence on others.",
        "I am deserving of joy.",
        "I am grateful for each moment.",
        "I am in tune with my inner self.",
        "I am worthy of my dreams and aspirations.",
        "I am constantly learning and growing.",
        "I am grateful for my resilience.",
        "I am at peace with my past.",
        "I am open to love and kindness.",
        "I am a creator of my own destiny.",
        "I am thankful for my blessings.",
        "I am proud of my achievements.",
        "I am a reflection of positivity.",
        "I am worthy of a wonderful life.",
        "I am grateful for the love in my life.",
        "I am open to new possibilities.",
        "I am deserving of a bright future.",
        "I am in alignment with my purpose.",
        "I am a positive thinker and achiever.",
        "I am grateful for my courage.",
        "I am embracing my true self.",
        "I am worthy of great things.",
        "I am at peace with where I am.",
        "I am a source of happiness.",
        "I am grateful for my journey so far.",
        "I am open to positive changes.",
        "I am surrounded by love.",
        "I am grateful for today’s opportunities.",
        "I am deserving of all my desires.",
        "I am a beacon of positivity.",
        "I am in control of my thoughts.",
        "I am grateful for my inner strength.",
        "I am capable of amazing things.",
        "I am worthy of love and kindness.",
        "I am open to new experiences.",
        "I am thankful for my life.",
        "I am growing every day.",
        "I am deserving of all the good things in life.",
        "I am grateful for my journey of self-discovery.",
        "I am a positive force in the world.",
        "I am worthy of all my accomplishments.",
        "I am open to endless possibilities.",
        "I am grateful for my unique journey.",
        "I am a source of inspiration to others.",
        "I am worthy of my dreams and goals.",
        "I am embracing my journey with gratitude.",
        "I am filled with positive energy.",
        "I am thankful for my inner peace.",
        "I am confident in my path forward."
    ]
    
    public func getRandomAffirmation() -> String {
        return affirmations.randomElement() ?? "I am worthy of love and respect."
    }
    
}

//
//  FactBook.swift
//  FunFacts
//
//  Created by Navneeth Ramprasad on 2/6/15.
//  Copyright (c) 2015 Navneeth Ramprasad. All rights reserved.
//

import Foundation
struct FactBook
{
let factsArray = [
    "Ants stretch when they wake up in the morning.",
    "Ostriches can run faster than horses.",
    "Olympic gold medals are actually made mostly of silver.",
    "You are born with 300 bones; by the time you are an adult you will have 206.",
    "It takes about 8 minutes for light from the Sun to reach Earth.",
    "Some bamboo plants can grow almost a meter in just one day.",
    "The state of Florida is bigger than England.",
    "Some penguins can leap 2-3 meters out of the water.",
    "On average, it takes 66 days to form a new habit.",
    "Mammoths still walked the earth when the Great Pyramid was being built.",
    "You breathe on average about 5 million times a year.",
    "Months that begin on a Sunday always have a Friday the 13th in them.",
    "You are born with 300 bones, by the time you are an adult you will have 206.",
    "The average lead pencil will write a line about 35 miles long or write approximately 50,000 English words.",
    "One fourth of the bones in your body are in your feet.",
    "The average person spends 2 weeks of their lifetime waiting for the light to change from red to green.",
    "It takes more calories to eat a piece of celery than the celery has in it.",
    "The present population is expected to rise to 15 Billion by the year 2080.",
    "The largest recorded snowflake was 15 inches wide and 8 inches thick.",
    "The tip of a bullwhip moves so fast that the sound it makes is actually a tiny sonic boom.",
    "Native Americans used to name their children after the first thing they saw as they left their tepees after their children were born, hence the names Sitting Bull and Running Water.",
    "The Matami Tribe of West Africa play their own version of football, instead of a normal football they use a human skull.",
        "Coca-Cola would be green if the food colorant wasn't added.",
    "During the 17th Century, the Sultan of Turkey ordered his hole harem of women to be drowned and replaced with a new one.",
    "Coffins used for cremation are usually made with plastic handles.",
    "'Almost' is the longest word in the English language with all the letters in alphabetical order.",
    "Human thigh bones are stronger than concrete.",
    "Cockroaches can live several weeks with their heads cut off.",
    "It is impossible to sneeze with your eyes open. We dare you, give it a try!",
    "A Ten Gallon Hat will only hold 3/4 of a Gallon.",
    "Of all the words in the English language, the word 'SET' has the most definitions.",
        "It is against the law to burp, or sneeze inside a church in Nebraska.",
    "In 1386 a pig in France was executed by public hanging for the murder of a child.",
    "Earth is the only planet not named after a god.",
    "The world's oldest piece of chewing gum is over 9,000 years old!",
    "Scientists have tracked butterflies travelling over 3,000 miles.",
    "The silkworm consumes 86,000 times its own weight in 56 days.",
    "If removed from the stress of the modern world, the average human would sleep about 10 hours a day.",
    "To produce a single pound of honey, a single bee would have to visit 2 million flowers.",
    "A colony of 500 bats can eat approximately 250,000 insects in an hour.",
    "One in Five adults believe that aliens are hiding in our planet disguised as humans.",
    "Travelling masseuses in ancient Japan were required by law to be blind.",
    "The bloodhound is the only animal whose evidence is admissible in court.",
    "James Fixx, the man who popularized jogging in America died of a heart attack while running.",
    "The average American spends about a year and a half of his or her life watching commercials on television. What are you doing?! Get out and spend that year and a half doing something productive!",
    "Ancient Greeks practiced a form (ineffective) of birth control that consisted of having a woman hold her breath, making her squat, and sneezing.",
    "The FDA permits up to 5 whole insects per 100 grams of apple butter.",
    "There are no naturally occurring blue foods, even blueberries are purple!",
    "The skeleton of Jeremy Bentham is present in all the important meetings of the University of London.",
    "The elephant is the only mammal that can't jump!",
    "Just like fingerprints, everyone's tongue is different.",
    "The longest recorded flight of a chicken is 13 seconds.",
    "400 Quarter Pounders can be made from a single cow.",
    "Only 38% of Americans eat breakfast every day.",
    "111,111,111 x 111,111,111 = 12,345,678,987,654,321",
    "11% of the World is left handed.",
    "A 'Jiffy' is the scientific name for 1/100th  of a second.",
    "A Boeing 747's wingspan is longer than the Wright brothers' first flight.",
    "A broken clock is right two times a day.",
    "A duck's quack doesn't echo anywhere, no one knows why."
    
    ]
    
    func randomFact() -> String
    {
        var unsignedArrayCount = UInt32(factsArray.count)
        var unsignedRandomNumber = arc4random_uniform(unsignedArrayCount)
        var randomNumber = Int(unsignedRandomNumber)
        return factsArray[randomNumber]
    }
}
// Playground - noun: a place where people can play

import UIKit

var number = 2

var isPrime = true
if number == 1
{
    isPrime = false
}
if number != 2 && number != 1
{

for var i = 2; i < number; i++
{
    if number % i == 0
    {
        isPrime = false
    }
    
}
}

println(isPrime)
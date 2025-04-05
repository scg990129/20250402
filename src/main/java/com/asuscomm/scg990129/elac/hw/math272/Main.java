// Student: Shun Hoi, Yeung #900494756
// Class Math 272 #14052
// Date 20250402 Version 01
// Homework: Programming Assignment: Quadratic Primes

// Programming Assignment: Quadratic Primes
//Directions for discussion:  Please post your solution by providing the algorithm that produces the correct answer.  Please include a link to the repl.it.  implementation of the algorithm in your favorite programming language.  Be sure to provide a helpful, or critical comment to at least one of the posts of your classmates:  How would you improve their algorithm?  What aspect of their algorithm is an improvement over your algorithm?
//Leonhard Euler discovered the remarkable quadratic formula:
//
//N^2+n+41
//
//It turns out that the formula will produce 40 primes for the consecutive integer values 0≤n≤39 .  This means that
//0^2+0+41=41 ,  1^2+1+41=43 , ..., 39^2+39+41=1601  are all prime numbers.  However, when n=40,40^2+40+41=40(40+1)+41 is divisible by 41,
//and when n=41,41^2+41+41 is evidently also divisible by 41, so these numbers are no longer prime.
//
//The incredible formula n^2−79n+1601 was also discovered, which produced 80 primes for the consecutive values 0≤n≤79 .
//
//Considering quadratic polynomials of the form
//
//N^2+an+b, where |a|<1000 and |b|≤1000, where |n| is the absolute value of n, an integer.
//
//Find the coefficients a, and b, for the quadratic expression that produces the maximum number of primes for consecutive values of n, starting with n=0.

// Remark 1:
// Since the test number n  start with 0, b will initial determinate whether it produces the first prime,
// As well, based on the definition of prime:
// Definition 2.1.4: Prime and composite numbers.
// https://learn.zybooks.com/zybook/LAMISSIONMATH272SargsyanSpring2025/chapter/2/section/1?content_resource_id=108897972
// =>An integer n is prime if and only if n>1, and the only positive integers that divide n are 1 and n .
// therefore, b must be prime number and b>=2:
// **** we should start b from 2 to 1000 (included) based on [|b|≤1000] and [prime >1] and [equation]
// I will make a singleton of PrimeBitSet to store the calculated prime.

// Remark 2:
// by N^2+an+b, we know when n=1, 1+a+b, since prime should be odd expect 2.
// there is 2 cases to deal with
// when b=2 or multiple of 2, "a" should be a even number so that n^2+an+b can be odd.
// Proof.
// Since n is even integer, there is a number w such that n=2w.
// Since a is even integer, there is a number u such that a=2u.
// Putting 2w for n and 2u for a into the function n^2+an+b, we can get:
// n^2+an+b=(2w)^2+2u+2=2(w^2+u+1), since w and u is integer, w^2+u+1 must be an integer.
// When b = 2, since n^2+an+b is multiple of 2 with some integer, it produces not a prime number.
// Therefore, b=2 is not a good choice because it at most produces 1 prime, where b=prime in above condition. ■

// Remark 3
// for the equation n^2+an+b, n and a and b cannot be the same number:
// n^2+an+b, when a=b=n, n^2+n^2+n=n^5 this is not a prime number
// This knowledge can reduce the work load, so that we can only use the prime smaller than b max.
// based on the limited conditions, 1000, 999 and 998 cannot be prime, so that we should test to upper boundary 997 for |n|, |a|, and |b|
// as well, we can test with descending order, because once the max prime number, called p for example, is found, restrict the b and n range from -p(excluded) to p(excluded).

// Remark 4
// Therefore, the test case should be
// 3<b<997
// |a|=<996, test -997<=a<=997
// -997<n<997

// Remark 5
// if we have prime list from 3 to 997,





package com.asuscomm.scg990129.elac.hw.math272;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.*;

// This program is Run in Java 8

public class Main {
    public static void main(String[] args) {
        long i = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());

        BitSetPrimes primes = BitSetPrimes.getInstance();
        try{

            Thread.sleep(1000*100*10);
        }catch (InterruptedException e){}

        System.out.println(primes);
        System.out.println("time: "+ (System.currentTimeMillis() - i));
    }





}
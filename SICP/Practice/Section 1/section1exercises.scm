(define (main)
        (println "AUTHOR: Sara Ann saraabrackett@westminster.net")
    )

; exercise 1.2
; expected answer -0.02
(inspect (/ (+ 5.0 4 (- 2 (- 3 (+ 6 (/ 5 4))))) (* 3 (- 6 2) (- 2 7))))

; exercise 1.3
(define (square x) (* x x))
(define (sumOfSquares x y) (+ (square x) (square y)))
(define (twolargeSumOfSquares a b c)
  (cond ((not (or (> a b) (> a c))) (sumOfSquares b c))
  (cond (> b c) (sumOfSquares a b))
	(else (sumOfSquares a c)))
)
;; test:
(inspect (twolargeSumOfSquares 2 3 4))
; 25

; exercise 1.4
(define (a-plus-abs-b a b)
  ((if (> b 0) + -) a b))

(inspect (a-plus-abs-b 2 -1))
; 3

(inspect (a-plus-abs-b -4 -4))
; 0

(inspect (a-plus-abs-b 3 32))
; 35

; the function  a-plus-abs-b adds the value of the variable a to the absolute value of the variable b. that means that when b is negative, the function makes it positive 

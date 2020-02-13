(define (main)
        (println "AUTHOR: Sara Ann saraabrackett@westminster.net")
    )

; exercise 1.2
; expected answer -0.02
(inspect (/ (+ 5.0 4 (- 2 (- 3 (+ 6 (/ 5 4))))) (* 3 (- 6 2) (- 2 7))))

; exercise 1.3
(define (sumOfSquares x y) (+(* x x) (* y y)))
(define (twolargeSumOfSquares a b c) (
    (cond ((and (> a c) (> b c))) (sumOfSquares a b))
    (cond (> a b) (sumOfSquares a c))
    (else (b c))
))
(inspect (twolargeSumOfSquares 2 3 4))


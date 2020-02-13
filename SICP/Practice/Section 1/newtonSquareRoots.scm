(define (square x) (* x x) )
(define (average x y) (/ (+ x y) 2))

(define count 0)

(define (improve guess x)
    (average guess (/ x guess)))

(define (sqrt-iter iterationCount guess x)
    (println "guess:" guess " guess squared:" (square guess))
    (println " iteration:" (+ iterationCount 1))
    (if (good-enough? guess x)
        guess
        (sqrt-iter (+ iterationCount 1) (improve guess x) x)))

(define (good-enough? guess x)
    (< (abs (- (square guess) x)) 0.001)
    )

(inspect (sqrt-iter 0 1.0 23))


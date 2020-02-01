; section 1.1.2: naming and the enviroment
(define size 2)
(inspect size)

(define pi 3.14149)
(inspect pi)
(define radius 20)
(inspect radius)
(inspect (* pi (* radius radius)))

; section 1.1.3: evaluating combinations
(inspect (* (+ 2 (* 4 6) (+ 3 5 7))))

; section 1.1.4: compound procedures
(define (square x) (* x x))
(inspect (square 21))
(inspect (square (+ 2 5)))
(inspect (square 3))

(define (sum-of-squares x y) 
    (+ (square x) (square y)))
(inspect (sum-of-squares 3 4))

(define (f a) 
    (sum-of-squares (+ a 1) (* a 2)))
(inspect (f 5))

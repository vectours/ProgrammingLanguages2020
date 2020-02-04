(define (main)
        (println "AUTHOR: Sara Ann saraabrackett@westminster.net")
    )

; PHONE NUMBER EXERCISE

; target phone # is 404-820-7394
    (inspect (/ 999 999))
    (inspect (/(* 404 2) 2))
    (inspect (+ 100 526 10 184))
    (inspect (- 8505 1111))

; COMPOUND NUMBER EXERCISE

; (100 * (199 + (100 - 28) ) / ( (785 + 82) * 18)
    (inspect (/ (* 100 (+ 199 (- 100 28))) (* (+ 785 82) 18)))

; (((67 + 8) / (2 + 3)) * ((286 - 200)) / (7 * (4 + 8)))
    (inspect (* (/ (+ 67 8) (+ 2 3)) (/ (- 286 200) (* 7 (+ 4 8)))) )

;  (((20 * 8) + (83 - 3)) * (25 / (15 - 10)))
    (inspect (* (+ (* 20 8) (- 83 3)) (/ 25 (- 15 10)) ))

;  ((((35 + 2) * 2) + 6) / (10 - (4 / (7 - 5))))
    (inspect (/(+ (* (+ 35 2) 2) 6) (- 10 (/ 4 (- 7 5)))))
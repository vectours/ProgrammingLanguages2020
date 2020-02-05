(define x (cons 1 (cons 3 (cons (cons 7 8) 19))))
(inspect x)

; trying to access one
(inspect (car x))

; trying to access 3
(inspect (car (cdr x)))
(inspect (cadr x))

; trying to print 19
(inspect (cdddr x))

; trying to print 7
(inspect (car (car (cdr (cdr x)))))

; trying to print 8
(inspect (cdr (car (cdr (cdr x)))))
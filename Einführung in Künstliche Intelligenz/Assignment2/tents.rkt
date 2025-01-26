#lang racket
(require 2htdp/image)

;; Gruppe: Ole Elija Dziewas, Marius Ludwig Bachmeier, Meike Valentina Bauer

;; Number of rows and columns
(define board-size 6)

;; Placeholder values / not actually used
(define mud -1)
(define grass 0)
(define tree 1)
(define tent 2)

(define all-board-positions
  (for*/list ([row board-size]
              [col board-size])
    (list row col)))    

;; Play online at https://www.puzzle-tents.com/?e=MDoxMiw1NTksMTI0
(define start-board
  (vector mud tree mud mud mud mud  ; a vector representing the board
          tree mud mud mud tree mud
          tree mud mud mud mud tree
          mud mud tree mud mud mud
          tree mud mud mud mud mud
          mud mud mud tree mud mud))

; the tent count constraints for rows and columns
(define row-constraints
  (list 3 0 0 2 0 2))
(define col-constraints
  (list 2 0 2 1 1 1))

(define (board-cell board row col)
  (vector-ref board (+ (* row board-size) col)))

; returns a board that is marked with the given value at the given position.
(define (board-marked board row col value)
  (if (not (or
            (equal? (board-cell board row col) tree)
            (equal? (board-cell board row col) tent)))
      (let ([v (vector-copy board)])
        (vector-set! v (+ (* row board-size) col) value)
        v)
      (raise-arguments-error 'board-marked "this position is already occupied")))

(define (adjacent-positions pos no-diagonals)
  (filter (lambda (c)
            (and
             (not (equal? c pos)) ; we don't want to have the position itself
             (>= (first c) 0) ; is it in the board?
             (>= (second c) 0)
             (< (first c) board-size)
             (< (second c) board-size)
             (if no-diagonals
                 (or (= (first c) (first pos)) (= (second c) (second pos)))
                 #t)))
          (for*/list
              ([row '(-1 0 1)]
               [col '(-1 0 1)])
            (list (+ (first pos) row) (+ (second pos) col)))))

(define (tent-positions board)
  (filter (lambda (c)
            (equal? (board-cell board (first c) (second c)) tent))
          all-board-positions))

(define (obj-count-in-row board row obj)
  (foldl
   (lambda (val acc)
     (if (equal? val obj)
         (+ acc 1)
         (+ acc 0)))
   0
   (for*/list
       ([col (range board-size)])
     (board-cell board row col))))

(define (obj-count-in-col board col obj)
  (foldl
   (lambda (val acc)
     (if (equal? val obj)
         (+ acc 1)
         (+ acc 0)))
   0
   (for*/list
       ([row (range board-size)])
     (board-cell board row col))))

(define (constraint-adjacent-to-tree board)
  (andmap
   (lambda (te)
     (ormap
      (lambda (tr)
        (equal? (board-cell board (first tr) (second tr)) tree))
      (adjacent-positions te #t)))
   (tent-positions board)))

(define (constraint-not-adjacent-to-tent board)
   (andmap
   (lambda (te)
     (andmap
      (lambda (field)
        (not(equal? (board-cell board (first field) (second field)) tent)))
      (adjacent-positions te #f)))
   (tent-positions board)))

(define (constraint-tent-counts-rows board)
  (for/and ([i row-constraints]
        [j (range board-size)]
        #:when (not (= (obj-count-in-row board j tent) i)))
       #f)) 
    

(define (constraint-tent-counts-cols board)
  (for/and ([i col-constraints]
        [j (range board-size)]
        #:when (not (= (obj-count-in-col board j tent) i)))
       #f))

(define (all-constraints-met? board)
  (and (constraint-adjacent-to-tree board)
       (constraint-not-adjacent-to-tent board)
       (constraint-tent-counts-rows board)
       (constraint-tent-counts-cols board)))

;; Hint: The function vector-member may be helpful
(define (expand-state state) 
   (let* ([pos (vector-member mud state)]
          [row (quotient pos board-size)]
          [col (remainder pos board-size)]
          [mud-to-grass (board-marked state row col grass)]
          [mud-to-tent (board-marked state row col tent)])
     (if (and (constraint-adjacent-to-tree mud-to-tent) (constraint-not-adjacent-to-tent mud-to-tent))
         (list mud-to-tent mud-to-grass)
         (list mud-to-grass))))

(define (solve-tents state)
  (if (not (vector-member mud state)) ; all variables are assigned
      (if (all-constraints-met? state) state #f) 
      (ormap solve-tents (expand-state state))))


;; some pictures for a nice display
(define +cell-size+ 30)
(define (cell-to-image cell)
  (cond
    [(equal? cell mud) (rectangle +cell-size+ +cell-size+ "solid" "Linen")]
    [(equal? cell grass) (rectangle +cell-size+ +cell-size+ "solid" "Light Green")]
    [(equal? cell tree) (overlay(above (ellipse 30 20 "solid" "Dark Green")
                             (rectangle 5 10 "solid" "brown"))
                      (rectangle +cell-size+ +cell-size+ "solid" "Light Green"))]
    [(equal? cell tent) (overlay (triangle 25 "solid" "Tan")
                      (rectangle +cell-size+ +cell-size+ "solid" "Light Green"))]
    [else (rectangle +cell-size+ +cell-size+ "solid" "black")]))

;; Prints the board as nice pictures
(define (show board)
  (apply above
         (for*/list ([y board-size])
           (apply beside (for*/list ([x board-size])
                           (frame (cell-to-image (board-cell board y x))))))))
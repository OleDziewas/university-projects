#lang racket

(require data/heap)

;define states
(define *start-state* '((1 2 3) () ()))
(define *end-state* '(() () (1 2 3)))

;;;;; Functions already known from the previous sheet ;;;;;

;checks if list elems are sorted in ascending order ->returns bool 
(define (sorted? lst)
  (or (empty? lst)                        ; lst is empty => return true
      (empty? (rest lst))                 ; lst contains only one element => return true
      (and (< (first lst) (second lst))   ; compare the first two elements
           (sorted? (rest lst)))))        ; and recursively check rest of lst


;checks if all stacks are sorted? and thus if state is admissible -> returns bool
(define (admissible-state? state)
  (andmap sorted? state))


;moves disk in a given state -> returns result state after disk movement
(define (move-disc state from to)
  (if (= from to)
      state                                                                                      ;if from=to return state with no changes
      (let ([from-peg (list-ref state from)]                                                     ;bind variables (by called index) to respective state
            [to-peg (list-ref state to)])
        (if (empty? from-peg)                                                                    ; if the 'from' peg is empty
            state                                                                                ; change nothing
            (let* ([new-state (list-set state from (rest from-peg))]                             ;modify start-tower (indexed by "from") of new-state to the rest of original tower (the disk that was moved is no longer there) 
                   [new-state (list-set new-state to (append (list (first from-peg)) to-peg))])  ;modify goal-tower (indexed by "to") of new-state (same tower of start state + the disk that was moved there)
              new-state)))))                                                                     ;returns the new state 


;given a state -> returns all possible follow-up states 
(define (expand-state state)
  (remove-duplicates (filter admissible-state?          ;only check admissible states and remove duplicate entires
          (for*/list ([from (list 0 1 2)]               ;loops over lists to find all possible moves
                      [to (list 0 1 2)])
              (move-disc state from to)))))             ;get the result state after move was made (happens for all possible moves)



;executes depth-first-search --> returns path of moves that lead from start-state to goal-state [one line per move in format: '((state before move)(state after move)) ]
(define (dfs start end path visited)
  (if (equal? start end)
      (reverse path)                                                         ;if goal is reached return the path in reverse order
      (ormap (lambda (s)                                                     ;ormaps lambda(s) to all elems of states-to-visit output
                (dfs s end (cons (list start s) path) (cons s visited))) ;recursive function call and update of variables "path" and "visited" 
                 (states-to-visit start visited))))                          ;lambda(s) is done for each elem of this list (list contains the states that are to be visited next)

;given a start-state and the already visited states -> returns list with follow-up-states on start-state that were not visited yet
(define (states-to-visit s visited)
  (filter (lambda (sv)                     ;filters out only values of list that lambda(sv) applies to
            (not (member sv visited)))     ;lambda(sv) applies only to states that are not part of "visited"
          (remove s (expand-state s))))     ;puts all follow-up states of "s" in a list and removes "s" from this list

;;;;; End of already known functions ;;;;;

(define (trivial-heuristic state)
  0)

(define (heuristic state)
  (let ([n (+
   (+(unordered-elements (third state))(length (second state)))
   (length (first state))
  )])
    (if (< n 2)(append 0)(-(expt 2 n) 2))
    )
  )

(define (unordered-elements lst)
   (cond
    [(empty? lst) 0]
    [(equal? 1 (length lst)) 1]
    [(and(sorted? lst)(equal? (last (first *start-state*))(last lst))) 0] 
    [else (+ (unordered-elements (rest lst)) 1)]
     )
  )

; Find the smallest element in a list by using a heap
(define (smallest? lst)
  (let ([heap (make-heap <=)])
    (heap-add-all! heap lst)
    (heap-min heap)))

(define (terminal-state queue-elem)
  (first (first queue-elem)))

(define path first)
(define f-star second)


; Build a priority queue element as a 2-ary list containing a path (first element is the current state) and f*
(define (make-queue-elem path heur)
  (list path (+ (- (length path) 1) (heur (first path)))))

(define (visited-states-on-path path)
  (if (empty? path) path (append (list(first path)) (visited-states-on-path (rest path)) )))

(define (astar-helper queue end heur)
  (let* ([here  (first (heap-min queue))]
         [succs (states-to-visit (path here) (visited-states-on-path here))])
    (heap-remove-min! queue)
    (map
       (lambda (y)
             (heap-add! queue (make-queue-elem (append (list y) here) heur)))
        succs)
    (if (equal? (path here) end)
        (reverse here)
        (astar-helper queue end heur))))

(define (astar start end heur)
  (let ([queue (make-heap (lambda (x y) (<= (f-star x) (f-star y))))])
    (heap-add! queue (make-queue-elem (list start) heur))
    (astar-helper queue end heur)))
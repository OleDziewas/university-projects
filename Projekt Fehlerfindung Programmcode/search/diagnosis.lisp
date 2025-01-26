;;;
;;;
;;; einfache Diagnose durch Suche nach nicht-korrekten Komponenten
;;;
;;;

;; Komponenten werden über ihr Verhalten mit def-transform spezifiziert, je nach Fahlermodell
;; (ok steht für korrektes Verhalten)
(defparameter *components-and-modes* ()
  "all possible error states of a component")

(defmacro return-and-state (value new-state)
  `(values ,value ,new-state))

(defmacro def-transform (component mode lambda-list &body code)
  (let ((c-var (gensym))
        (m-var (gensym))
        (arg-var (gensym)))
    (let ((cm (assoc component *components-and-modes*)))
      (if cm
          (pushnew mode (cdr cm))
          (push (list component mode) *components-and-modes*)))
    `(defmethod transition ((,c-var (eql ',component)) (,m-var (eql ',mode)) state ,arg-var)
       (declare (ignore ,c-var ,m-var) (ignorable state))
       (destructuring-bind ,lambda-list ,arg-var
         ,(when (member 'void lambda-list) '(declare (ignore void)))
         ,@code))))

;(defmethod transition :around (component error-mode state args)
;  (declare (ignore component error-mode state args)
;  (unless (eql state :broken) (call-next-method)))

(defun diagnose (testcases)
  "runs diagnoses on list of tests"
  (let* ((call-sequences (loop for case in testcases collecting (mapcar #'first case))) ; Sequenz von Aufrufen für alles Tests (z.B. (create push pop isempty))
         (faults (mapcar #'(lambda (test) (check test ())) testcases)) ; Liste des ersten Fehlers als Cons-Zelle (position-in-sequence . component) oder nil
         (fcs (remove nil ; Liste von Komponenten, die möglicherweise das Problem ekrlären (werden bis zum Fehler einschließlich aufgerufen)
                      (remove-duplicates (apply #'append 
                                                (mapcar #'(lambda (fault call-sequence)
                                                            (when fault
                                                              (subseq call-sequence 0 (+ 1 (car fault)))))
                                                        faults call-sequences))))))
    (format t "~&~d test~:p failed." (count-if-not #'null faults))
    (when fcs 
      (format t "~&possibly erroneous components: ~a" fcs)
      (format t "~&searching for single fault...")
      (let ((single-fault? nil))
        (loop for c in fcs do
          (loop for em in (cdr (assoc c *components-and-modes*)) do
            (if (eql em 'ok) ; kein Fehlermodell, anstelle probieren wir, ob ein totales Versagen die Fehler erklärt
                (when (every #'(lambda (fault call-sequence)
                                 (let ((p (position c call-sequence)))
                                   ;(format t "~&fault: ~a, call-sequence: ~a, faulty component: ~a (pos=~a)" fault call-sequence c p)
                                   (or (null fault)
                                       (and p (<= p (car fault))))))
                             faults call-sequences)
                  (setq single-fault? t)
                  (format t "~&Component ~a may be broken completely." c))
                (when (every #'(lambda (test)
                                 (null (check test (list (cons c em)))))
                             testcases)
                  (setq single-fault? t)
                  (format t "~&Error model ~a for component ~a explains the fault." em c)))))
        (unless single-fault?
          (format t "~&There's no single root fault."))))))

(defun check (test error-models)
  "runs a check with assumed error models (can be nil if no errors assumed, otherwise assoc list of (component . error)"
  (let ((state ()))
    (loop for (call args observation) in test 
      for pos from 0 ; count number of calls until fault occurs
      finally (return nil) 
      do
      (let ((em (cdr (assoc call error-models))))
        (if (eql 'broken em) ; is this component faulty and could explain all faults?
            (return-from check nil)
            (multiple-value-bind (expectation new-state) (transition call (or em 'ok) state args)
              (setq state new-state)
              (unless (equal expectation observation)
                (return-from check (cons pos call)))))))))


;;
;; Spezifikation der Metoden als Transformation (hidden) state X input --> (output, state)
;;
;; Syntax: (def-transform <name-der-funktion> <fehlermodell> <lambda-liste der Eingabe> code)
;;         state : lokale Variable, die im Codeblock an den Status gebunden ist
;;         return-and-state: Makro für Rückgabe des Tupels (output, state)
;;

(def-transform create ok (n)
  (return-and-state 'void `((:capacity . ,n) (:stack . ()))))

(def-transform create too-small (n)
  (return-and-state 'void `((:capacity . ,(- n 1)) (:stack . ()))))

(def-transform isempty ok (void)
  (let ((stack (cdr (assoc :stack state))))
    (if (null (assoc :stack state)) 
        'fault ; Stack war nicht initiallisiert
        (return-and-state (null stack) state))))

(def-transform isfull ok (void)
  (let ((capacity (cdr (assoc :capacity state))))
    (if (null capacity) 
        'fault ; Stack war nicht initiallisiert
        (return-and-state (= capacity 0) state))))

(def-transform push ok (x)
  (let ((capacity (cdr (assoc :capacity state))))
    (cond ((null capacity) (return-and-state 'fault ())) ; Stack war nicht initialisiert
          ((< capacity 1)  (return-and-state 'fault ())) ; Stack ist voll
          (t  (let ((stack (cdr (assoc :stack state))))
                (return-and-state 'void `((:capacity . ,(- capacity 1)) (:stack . ,(cons x stack)))))))))

(def-transform pop ok (void)
  (let ((capacity (cdr (assoc :capacity state)))
        (stack (cdr (assoc :stack state))))
    (cond ((null stack) (return-and-state 'fault ())) ;; Stack war nicht initialisiert oder leer
          (t  (return-and-state (first stack) `((:capacity . ,(+ capacity 1)) (:stack . ,(rest stack))))))))
  
;;
;; Beispiel für Liste von Tests
;;
;; Syntax eines einzelnen Test: ( (<name-der-funktion> <liste-der-argumente> <rückgabe>) ...)
;;
(defparameter *tests* '(((create (3) void) (push ("a") void) (push ("b") void) (push ("c") fault))
                        ((create (3) void) (push ("a") void) (push ("b") void) (pop (void) "b"))
                        ((create (3) void) (isempty (void) t))
                        ((create (3) void) (push ("a") void) (push ("b") void) (isfull (void) t))))
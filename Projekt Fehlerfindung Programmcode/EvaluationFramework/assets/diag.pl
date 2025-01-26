ab(create).
ab(pop).
ab(isfull).
ab(isempty).

:- initialization(main, main).

anysize(0).
anysize(1).
anysize(2).
anysize(3).
anysize(4).

anystack(stack_(push_(S, _), N)) :- anysize(N), anystack(stack_(S, N)).
anystack(stack_(create_, N)) :- anysize(N).

size(create_, 0).
size(push_(S, _), N) :- size(S, K), N is K + 1, !.

create(C, stack_(create_, C)) :- anysize(C).
create(C, stack_(_, C)) :- ab(create), anysize(C).

push(stack_(_, N1), _, error) :- ab(push), anysize(N1).
push(stack_(_, N1), _, stack_(_, N2)) :- ab(push), anysize(N1), anysize(N2).
push(stack_(S, C), E, stack_(push_(S, E), C)) :- anysize(N), anysize(C), size(S, N), N < C.
push(stack_(S, C), _, error) :- anysize(N), anysize(C), size(S, N), N >= C.

pop(stack_(_, N1), _, error) :- ab(pop), anysize(N1).
pop(stack_(_, N1), _, stack_(_, N2)) :- ab(pop), anysize(N1), anysize(N2).
pop(stack_(push_(S, E), C), E, stack_(S, C)) :- anysize(C).
pop(stack_(create_, C), error, error) :- anysize(C).

isempty(stack_(_, N), _) :- ab(isempty), anysize(N).
isempty(stack_(create_, C), true) :- anysize(C).
isempty(stack_(push_(_, _), C), false) :- anysize(C).

isfull(stack_(_, N), _) :- ab(isfull), anysize(N).
isfull(stack_(S, C), true) :- size(S, N), anysize(N), anysize(C), N >= C.
isfull(stack_(S, C), false) :- size(S, N), anysize(N), anysize(C), N < C.
main :-
  create(1, V0), pop(V0, '-', V1), !,
  create(1, V2), push(V2, 'w', V3), V3 \= error, push(V3, 'q', V4), V4 \= error, !,
  create(1, V5), push(V5, 'g', V6), V6 \= error, push(V6, 'd', V7), V7 \= error, !,
  create(1, V8), push(V8, 't', V9), V9 \= error, push(V9, 'z', V10), V10 \= error, !,
  create(1, V11), push(V11, 'j', V12), V12 \= error, push(V12, 'h', V13), V13 \= error, !,
  create(1, V14), push(V14, 'd', V15), V15 \= error, push(V15, 'r', V16), V16 \= error, !,
  create(1, V17), push(V17, 't', V18), V18 \= error, pop(V18, 't', V19), push(V19, 'd', V20), V20 \= error, push(V20, 'i', V21), V21 \= error, !,
  write("Success"), halt.
main :- write("Fail"), halt.
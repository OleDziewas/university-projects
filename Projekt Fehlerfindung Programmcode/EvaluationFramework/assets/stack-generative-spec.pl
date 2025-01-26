:- initialization(main, main).

% Instantiation of function calls with parameters
instantiate_params([], []).
instantiate_params([P | L], [X | I]) :- instantiate_type(X, P, 0), instantiate_params(L, I).

instantiate_func(F, [[F, O]]) :- type([[F, _]], P1, _), exclude(complex_type, P1, P2), instantiate_params(P2, O).

% Stack specific helper functions
capacity([[create, [N]]], N) :- !.
capacity([_ | S], N) :- capacity(S, N).

size([[create, _]], 0).
size([[pop, _] | S], 0) :- size(S, 0), !.
size([[pop, _] | S], N) :- size(S, M), N is M - 1, !.
size([[push, _] | S], N) :- size(S, M), capacity(S, C), M == C, N is M, !.
size([[push, _] | S], N) :- size(S, M), N is M + 1, !.
size([[_, _] | S], N) :- size(S, N), !.

% Generic helper functions
execute_times(I, _, 0, I) :- !.
execute_times(I, F, N, O) :- M is N - 1, instantiate_func(F, [C]), execute_times([C | I], F, M, O).

% Specification type definitions
type([[create, _] | _],  [int],         [stack]).
type([[push, _] | _],    [stack, char], [stack]).
type([[pop, _] | _],     [stack],       [stack, char]).
type([[isempty, _] | _], [stack],       [stack, bool]).
type([[isfull, _] | _],  [stack],       [stack, bool]).

% Type instantiation rules
complex_type(stack).

instantiate_type(X, int, 0) :- random(1, 5, X), !.
instantiate_type(X, char, 0) :- random(97, 123, N), atom_codes(X, [N]), !.
instantiate_type(X, bool, 0) :- random(0, 2, X), !.

instantiate_type([[isempty, []] | C], stack, 0) :- instantiate_func(create, C).
instantiate_type([[pop, []] | C], stack, 0) :- instantiate_func(create, C).
instantiate_type([[isfull, []] | S], stack, 0) :- instantiate_func(create, C), capacity(C, N), execute_times(C, push, N, S).
instantiate_type([[isfull, []] | S], stack, 0) :- instantiate_func(create, C), capacity(C, N), random(0, N, M), execute_times(C, push, M, S).
instantiate_type(S, stack, 0) :- instantiate_func(create, C), capacity(C, N), M is N + 1, execute_times(C, push, M, S).

instantiate_type([[isempty, []] | S], stack, R) :- R \= 0, T is R - 1, instantiate_type(X, stack, T), execute_times(X, push, 1, S).
instantiate_type([[pop, []] | S], stack, R) :- R \= 0, T is R - 1, instantiate_type(X, stack, T), execute_times(X, push, 1, S), size(S, N), capacity(S, C), N < C.
instantiate_type([[pop, []] | S], stack, R) :- R \= 0, T is R - 1, instantiate_type(X, stack, T), execute_times(X, push, 1, S), size(S, N), capacity(S, C), N == C.

% Generate test cases for recursion depth 0 and 1
main :-
    bagof(X0, instantiate_type(X0, stack, 0), X0s),
    bagof(X1, instantiate_type(X1, stack, 1), X1s),
    append(X0s, X1s, X),
    print(X),
    halt.

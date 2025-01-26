% Given a plan (i.e. a list of operators) and an initial state,
% this predicate will execute the plan and verify that the 
% resulting final state is a valid goal state.
validate_plan(Plan, InitialState, GoalState) :- apply_plan(Plan,InitialState,FinalState), subset(GoalState, FinalState).

% Given a plan (i.e. a list of operators) and an initial state,
% this predicate will execute the plan such that the resulting
% state is stored in the last argument.
apply_plan([], InitialState, FinalState) :- InitialState = FinalState.
apply_plan(Plan, InitialState, FinalState) :- [Operator|Tail] = Plan, gamma(InitialState, Operator, NextState), apply_plan(Tail, NextState, FinalState). 

% Apply a ground operator (= action a) in a given State, 
% to get the resulting NextState.
gamma(State, Operator, NextState) :- preC(Operator, Preconditions), nef(Operator, NegativeEffects), pef(Operator, PositiveEffects), subset(Preconditions, State) ,subtract(State, NegativeEffects, Result), union(Result, PositiveEffects, NextState).

preC(Operator, Preconditions) :- Operator = operator(_, Preconditions, _, _).
nef(Operator, NegativeEffects) :- Operator = operator(_, _, _, NegativeEffects).
pef(Operator, PositiveEffects) :- Operator = operator(_,_,PositiveEffects,_).
% We define a planning operator using
% 1. its name
% 2. the list of preconditions
% 3. the list of positive effects
% 4. the list of negative effects
operator(drink-water, [cup-full], [happy], [cup-full]).
operator(buy-bottle, [money], [water-bottle], []).
operator(refill-cup, [water-bottle], [cup-full], []).

validate_plan([operator(buy-bottle, [money], [water-bottle], []),
                operator(refill-cup, [water-bottle], [cup-full], []),
                operator(drink-water, [cup-full], [happy], [cup-full])]
              , [money], [happy]).
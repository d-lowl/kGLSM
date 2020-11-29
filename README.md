# kGLSM
[![](https://jitpack.io/v/d-lowl/kGLSM.svg)](https://jitpack.io/#d-lowl/kGLSM)

## Build
* `./gradlew build` builds into .class files.
* `./gradlew jar` builds jar file for the library.

### Run in Jupyter Notebook
This repository has [examples](notebook) written in Jupyter Notebooks. Before running, jar must be built first and requirements need to be installed (primarily `kotlin-jupyter-kernel``)
```
./gradlew jar
pip install -r requirements.txt
jupyter-notebook
```

## Motivation
This library is to serve as a framework for performing Stochastic Local Search (SLS) based on Generalised Local Search Machines (GLSM). It was shown that best performing SLS (on a variety of classes of problems) are combinations of multiple pure SLS algorithms. The idea of GLSMs is a unified way to build a represent SLS algorithms that may (or may not be) combinations of pure strategies themselves, using Finite State Automata.

## GLSM components
A GLSM consists of:

* [State machine](src/main/kotlin/com/sihvi/glsm/sls/StateMachine.kt) with [Search Strategies](src/main/kotlin/com/sihvi/glsm/strategy/Strategy.kt) as nodes and [Transition Predicates](src/main/kotlin/com/sihvi/glsm/transitionpredicate/TransitionPredicate.kt) as transitions between them
* [Memory](src/main/kotlin/com/sihvi/glsm/memory/Memory.kt) that may hold current and best solutions, steps performed and other task or search strategy dependent information
* [Search Space](src/main/kotlin/com/sihvi/glsm/space/SearchSpace.kt) definition, and possible operations on it 
* [Problem](src/main/kotlin/com/sihvi/glsm/problem/Problem.kt) definition, or in other words cost function

_Note: In SLS 2005, the last two components are treated as inputs to GLSM instead, but for the sake of convinience I will be sticking to the definition above. Subject to change later._

## Notes
This is WIP, the signatures most likely will be changed in the future, until I settle down on what works best. More algorithms, search space supports, and model problems are yet to be added.

## References
[SLS 2005] [H. H. Hoos and Stützle Thomas, _Stochastic local search: foundations and applications._ Amsterdam: Morgan Kaufmann Publishers, 2005.](https://www.elsevier.com/books/stochastic-local-search/hoos/978-1-55860-872-6) -- Main reference for SLS algorithms and GLSM framework. If not explicitly stated otherwise, you can assume that it's from this book.

[SURVEY 2012] Parejo, J.A., Ruiz-Cortés, A., Lozano, S. et al. _Metaheuristic optimization frameworks: a survey and benchmarking._ Soft Comput 16, 527–561 (2012). [https://doi.org/10.1007/s00500-011-0754-8](https://doi.org/10.1007/s00500-011-0754-8) -- a good review and a benchmark of existing libraries for metaheuristics/SLS algorithms. More comparisons with this approach against the pre-existing once are to come.

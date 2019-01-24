# SATSOLVER.
An attempt at solving the SAT problem using only plain Java in-built libraries.<br> 
Relatively Messy.
### Requirements
Java
## Usage

Run the following after replacing line 18 of SatSolvertest.java with your file name.<br>
```
javac SatSolvertest.java
java SatSolvertest.java
```
Implements DPLL at the end. Does NOT implement any 'rules' for boolean clauses with greater then 2 literals within. <br>
Cheats in processing by some logic before implementing DPLL, reducing number of clauses that require heavy processing via DPLL's brute force work.<br>

# 🐜 Ant Colony Optimization for the Traveling Salesman Problem (Java)

This project implements the **Traveling Salesman Problem (TSP)** using **Ant Colony Optimization (ACO)** in Java.  
It was developed for the **Object-Oriented Programming (OOP) 2022/23** course at *Instituto Superior Técnico (IST)*.  

The project simulates ants traversing a weighted graph, laying pheromones, and gradually converging toward optimal Hamiltonian cycles.  

---

## ✨ Features

- Implementation of **Ant Colony Optimization (ACO)** for the TSP  
- Modular **object-oriented design** with packages for events, graph structures, distributions, and ants  
- Simulation of **pheromone evaporation** and **ant movement** as discrete stochastic events  
- Supports:
  - **Random graph generation** with Hamiltonian cycle guaranteed  
  - **Custom graph input** from adjacency matrix files  
- Periodic reporting of:
  - Current simulation time  
  - Number of move/evaporation events  
  - Best Hamiltonian cycle found so far  
  - Top candidate cycles discovered  

---

## 📂 Project Structure
src/
├── main/ # Entry point (Main.java)
├── ao/ # Ant Colony Optimization core classes
├── graph/ # Graph data structures (edges, weighted graphs, etc.)
├── eventqueue/ # Discrete event simulation system
├── distribution/ # Random distributions (exponential, RNG strategies)
└── ...


---

## ⚙️ Compilation & Execution

### 1. Compile
From the project root:

```bash
javac */*.java */*/*.java


This compiles all .java files into .class files.

2. Create JAR

Bundle everything into an executable JAR:

jar cfe project.jar main.Main $(find . -name "*.class")


Now you can run with:

java -jar project.jar ...

🚀 Usage

The program can be run in two ways:

1. Random graph generation
java -jar project.jar -r n a n1 α β δ η ρ γ ν τ


n → number of nodes

a → max edge weight

n1 → nest node

α, β, δ → ant movement parameters

η, ρ → pheromone evaporation parameters

γ → pheromone deposit parameter

ν → number of ants

τ → simulation final time

Example:

java -jar project.jar -r 5 10 1 1.0 1.0 0.2 2.0 10.0 0.5 200 300.0

2. Input from file
java -jar project.jar -f path/to/input.txt


The input file format:

n n1 α β δ η ρ γ ν τ
0 a12 a13 ... a1n
a21 0 a23 ... a2n
... ... ... ... ...
an1 an2 an3 ... 0


Example:

5 1 1.0 1.0 0.2 2.0 10.0 0.5 200 300.0
0 3 6 6 2
3 0 3 2 5
6 3 0 0 0
6 2 0 0 1
2 5 0 1 0

📊 Simulation Output

Every τ/20 steps, the program prints:

Present instant

Number of move events

Number of evaporation events

Top candidate cycles found so far

Best Hamiltonian cycle (if any)

Example:

Observation 10:
Present instant: 150.0
Number of move events: 1234
Number of evaporation events: 456
Top candidate cycles:
{1,3,2,4,5}:14
{1,3,2,5,4}:21
Best Hamiltonian cycle: {1,5,4,2,3}:14

📚 Academic Context

This project was developed as part of the Object-Oriented Programming (OOP) course at Instituto Superior Técnico, 2022/23.

It demonstrates the application of swarm intelligence and discrete event simulation to a classical NP-complete problem.

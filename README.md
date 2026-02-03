# ARSW Matrix Game

## Objective

This game is inspired by the movie "The Matrix". It runs on a grid (matrix) where multiple actors interact with opposing goals.

## Actors and Roles

- Neo: His objective is to reach the phones and avoid being caught by the agents.
- Agent: The agent's objective is to catch Neo and prevent him from reaching the phones.
- Telephone: Static object located in one of the matrix's corners.
- Wall: Static object to add complexity to the game.

## Project Structure

- `Enunciado.txt` — Original exercise statement or specification (if present).
- `src/` — Java source files:
  - `Agent.java` — Implementation of an agent (game actor).
  - `Neo.java` — Implementation of the Neo actor (likely with its own behavior).
  - `Matrix.java` — Represents the game board (matrix); contains logic to manage it.
  - `Game.java` — Main class that orchestrates the game execution (contains `main`).

## Requirements

- Java JDK 8 or newer installed (recommended: Java 11+).
- Shell: `zsh` or `bash` (instructions use common shell commands).

## How to compile and run

Clone the project:
```bash
git clone https://github.com/daniel-pm19/ARSW-MatrixGame.git
```

Compile the project:

```bash
mkdir -p bin
javac -d bin src/*.java
java -cp bin Game
```

Alternatively, quick compile and run from the `src` folder:

```bash
cd src
javac *.java
java Game
```

## Game images

Whe Neo win:

![alt text](/img/image1.png)

When Neo lose:

![alt text](/img/image.png)





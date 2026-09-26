# ♟️ Java Chess Game

A fully functional **two-player desktop Chess application built with Java Swing**, implementing chess piece movement, legal move validation, special rules, game-state detection, and important draw conditions.

The project was built from scratch to explore **Object-Oriented Programming, event-driven GUI development, game-state management, rule validation, and handling complex edge cases in Java**.

---

## 📸 Demo

> Add a screenshot or GIF of the running game here.

```text
[ Chess Game Screenshot / GIF ]
```

---

## 🚀 Project Overview

This project is a desktop-based Chess game designed for **two human players sharing the same computer**.

Unlike a basic board-movement project, the application focuses on implementing the underlying rules that make a Chess game valid — including checking whether moves are legal, detecting threats to the King, determining game-ending conditions, and handling special Chess rules.

The application combines:

* ♟️ Chess game logic
* 🧠 Move validation
* 🖥️ Java Swing GUI
* 🔄 Turn management
* ⚠️ Check and checkmate detection
* 🏰 Castling
* 👑 Pawn promotion
* 🤝 Draw-condition detection
* 🎯 Legal-move and check highlighting

---

## ✨ Features

### ♟️ Complete Piece Movement

The game implements movement logic for all standard Chess pieces:

* ♙ Pawn
* ♖ Rook
* ♘ Knight
* ♗ Bishop
* ♕ Queen
* ♔ King

Each piece follows its corresponding movement constraints, with move validation handled by the game logic.

---

### 🔄 Turn-Based Gameplay

* Two-player local gameplay
* White moves first
* Players alternate turns
* Moves are validated according to the current game state
* Invalid moves are rejected

---

### ⚔️ Check Detection

The game identifies when a King is under attack and updates the game state accordingly.

This requires evaluating whether opposing pieces can legally attack the King after a move.

---

### ♛ Checkmate Detection

The game determines whether a player is in **checkmate** by evaluating:

1. Whether the King is currently in check.
2. Whether the player has any legal move available.
3. Whether moving another piece can remove the threat.

If no legal escape exists, the game recognizes the position as checkmate.

---

### 🤝 Stalemate Detection

The game also handles positions where:

* The current player is **not in check**
* The current player has **no legal moves**

Such positions are recognized as stalemate.

---

### 🏰 Castling

The implementation supports Chess castling, including the relevant conditions required for the move.

Both:

* Kingside castling
* Queenside castling

are supported.

---

### 👑 Pawn Promotion

When a pawn reaches the opposite end of the board, the player can promote it to an available piece according to the implemented promotion logic.

---

### ⚖️ Draw Conditions

The game includes additional draw detection for:

* **50-move rule**
* **Insufficient material**

This goes beyond simple piece movement and requires tracking and evaluating the overall game state.

---

### 🎯 Visual Feedback

The Swing interface provides visual feedback for gameplay, including:

* Selected pieces
* Legal moves
* Check situations
* Current game state

This makes the rule engine easier to interact with through the GUI.

---

# 🧠 Technical Highlights

One of the main goals of this project was to translate a complex real-world rule system into maintainable Java code.

### Object-Oriented Design

The project uses separate classes for the Chess pieces and game components, including:

```text
Piece
├── Pawn
├── Rook
├── Knight
├── Bishop
├── Queen
└── King
```

This structure allows each piece to encapsulate its own movement behaviour while the broader game logic manages the board and overall state.

---

### Game-State Management

A Chess move cannot be validated only by checking whether a piece can physically move to a square.

The application also needs to consider:

```text
Player Turn
      ↓
Piece Movement
      ↓
Destination Validation
      ↓
King Safety
      ↓
Check Detection
      ↓
Legal Move
      ↓
Game-State Update
      ↓
Checkmate / Stalemate / Draw Detection
```

This makes the project a practical exercise in **state management and rule-based programming**.

---

### Edge-Case Handling

Chess contains many situations where a seemingly valid movement becomes illegal because of the resulting board state.

The project therefore handles cases involving:

* King safety
* Check
* Checkmate
* Stalemate
* Castling
* Pawn promotion
* 50-move draw condition
* Insufficient material

These cases were an important part of implementing the game beyond basic movement mechanics.

---

# 🛠️ Tech Stack

| Technology                      | Purpose                           |
| ------------------------------- | --------------------------------- |
| **Java**                        | Core application and game logic   |
| **Java Swing**                  | Desktop graphical user interface  |
| **AWT**                         | GUI/event handling support        |
| **Object-Oriented Programming** | Piece and game-logic organization |

---

# 📁 Project Structure

The core project is organized around the game and individual Chess pieces:

```text
CHESS_GAME/
│
├── Chess_game/
│   ├── Game.java
│   ├── Board.java
│   ├── Piece.java
│   ├── Pawn.java
│   ├── Rook.java
│   ├── Bishop.java
│   ├── Knight.java
│   ├── Queen.java
│   └── King.java
│
├── Chess_game.jar
│
├── README.md
│
├── index.html
│
└── ppt
```

> The exact source-file organization may evolve as the project is extended.

---

# ▶️ How to Run

## Option 1 — Run the JAR

A compiled JAR is included in the repository.

Make sure Java is installed on your system, then run:

```bash
java -jar Chess_game.jar
```

---

## Option 2 — Run from Source

### 1. Clone the repository

```bash
git clone https://github.com/Nikhilreddy8599/CHESS_GAME.git
```

### 2. Navigate into the project

```bash
cd CHESS_GAME
```

### 3. Open the source in a Java IDE

Recommended IDEs include:

* IntelliJ IDEA
* Eclipse
* VS Code with Java extensions

### 4. Run the main game class

Run the project's main Java entry point from your IDE.

---

# 🎮 How to Play

1. Launch the application.
2. White makes the first move.
3. Select a Chess piece.
4. Select the destination square.
5. The game validates the move.
6. If valid, the board state is updated.
7. The turn switches to the other player.
8. Continue until the game reaches:

   * Checkmate
   * Stalemate
   * A supported draw condition

---

# 🧩 Core Concepts Demonstrated

This project provided practical experience with:

* Object-Oriented Programming
* Classes and inheritance
* Encapsulation
* Polymorphism
* Event-driven programming
* GUI development
* State management
* Conditional logic
* Algorithmic thinking
* Rule validation
* Edge-case handling
* Debugging complex interactions

---

# 💡 Key Learning

Building a Chess game demonstrated an important software-engineering principle:

> **Complexity often comes not from implementing individual operations, but from correctly handling the interactions between them.**

For example, validating a King move requires more than checking whether the King can move one square. The resulting board position must also be evaluated to ensure that the King is not left under attack.

This project therefore helped strengthen my ability to break a complex problem into smaller components and reason about interactions between different parts of a system.

---

# 🔮 Future Improvements

The project can be extended with additional features such as:

* 🤖 Chess AI opponent
* 📜 Move history and Chess notation
* ↩️ Undo / Redo
* ⏱️ Chess timer / clock
* 💾 Save and load game state
* 🎨 Further UI/UX improvements
* 🌐 Online multiplayer
* 🧪 Automated unit testing
* 📊 Game statistics

---

# 📌 Project Status

**Core Chess gameplay: Implemented**

The current version focuses on building a functional local Chess experience with rule validation and important game-ending conditions.

Further improvements can extend the project toward a more complete Chess platform.

---

# 🤝 Contributing

Contributions and suggestions are welcome.

If you would like to improve the project:

1. Fork the repository.
2. Create a feature branch.

```bash
git checkout -b feature/your-feature
```

3. Make your changes.
4. Commit your changes.

```bash
git commit -m "Add your feature"
```

5. Push the branch.

```bash
git push origin feature/your-feature
```

6. Open a Pull Request.

---

# 👨‍💻 Author

**Nikhil Reddy**

B.Tech Student | Java | Data Structures & Algorithms | Software Engineering

GitHub:
https://github.com/Nikhilreddy8599

---

# ⭐ If You Like This Project

If you found this project interesting, consider giving the repository a ⭐ on GitHub.

---

## 📄 License

This project is available for learning and development purposes.


# Christmas Trivia Maze

**A retro-inspired maze game with trivia questions and festive 8-bit music!**

This project was developed by a team of three students from the University of Washington. The goal was to create a fun, interactive maze game with trivia questions, holiday-themed music, and pixel art inspired by late '80s RPGs. The game features a save/load functionality, various gameplay elements, and a custom GUI.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Design Patterns](#design-patterns)
- [Game Logic](#game-logic)
- [Future Work](#future-work)
- [Screenshots](#screenshots)
- [Credits](#credits)
- [Acknowledgements](#acknowledgements)

## Features

- **Maze Representation & Traversal:**
    - Each room holds one trivia question.
    - Players can traverse the maze in any direction, including backtracking.
    - Includes a debug mode to check win conditions.

- **Trivia Questions:**
    - Questions are stored in an SQLite database.
    - Three types of question responses, with a 'Gift' functionality available to assist with difficult challenges.
    - Player interaction through multiple answer types.

- **Save/Load Functionality:**
    - Allows saving and loading of game states with multiple save files.
    - All state information is stored in the `Maze` and `Player` classes and serialized into a save file.

- **GUI & Pixel Art:**
    - Custom-designed GUI inspired by '80s RPGs by **Cai Spidel**.
    - No borders and clean shading with transparent layers for a retro style.
    - Rooms are 16x16, menu screens are 96x54.
    - Custom font and borders designed for a unique look.

- **Music & Sound:**
    - Six holiday-themed 8-bit songs.
    - Control over music and sound effect volumes.

- **Interactive UI:**
    - Main menu, preparation screen, gameplay panel, and result screens.
    - Customizable music settings and player statistics available.

- **Serialization:**
    - Utilizes a `SerialWrapper` class to manage game state and player health/hints for easy storage and retrieval.

## Architecture

This project uses the **Model-View-Controller (MVC)** design pattern, though due to Java Swing's design, some controller logic is housed in the View.

- **Model:** Manages state and logic for the maze, questions, and player interactions.
- **View:** Displays the UI and interacts with the player.
- **Controller:** Facilitates communication between the Model and the View.

The game also uses a **Factory Method** to create trivia question objects and a **Singleton Pattern** for managing audio and music settings.

## Design Patterns

- **Factory Method:** Encapsulates the creation of trivia question objects. The factory is responsible for retrieving data and creating question objects, which makes it easily maintainable.
- **Singleton Pattern:** Used for audio/music management, ensuring that there's a single instance of the Music Controller to handle volume settings and playback.
- **Inheritance & Polymorphism:** Used for managing trivia questions generically and allowing code reuse across different types of questions.

## Game Logic

- The game logic is divided into multiple classes such as `Maze`, `Player`, `Question`, and `MusicFactory`.
- **Maze Logic:** Each room contains a trivia question, and the maze is designed for full traversal, including the ability to backtrack.
- **Question Logic:** Trivia questions are drawn from an SQLite database, and players can interact with the questions in various ways based on their answer type.
- **Health & Hints:** The player’s health and hint information is stored in the `Player` class, allowing for an engaging gameplay experience.

## Future Work

- **Multiplayer Support:** Introduce a global leaderboard and multiplayer mechanics to make the game more competitive.
- **More Trivia Questions:** Expand the database to include a larger variety of trivia questions for greater replayability.
- **Maze Customization:** Allow players to customize the size of the maze for varied gameplay sessions.
- **Enhanced UI:** Refine the menu design to ensure consistency across all panels.

## Screenshots

### Maze
<img src="/src/main/resources/readme/maze.png" width="500">

- The core gameplay environment of the Christmas Trivia Maze.
- Features 16x16 pixel rooms with trivia questions and retro 8-bit decorations.
- Free traversal in all directions, including backtracking.
- Clean, transparent shading with no hard borders to fit the nostalgic RPG style.

### Text-Input Question
<img src="/src/main/resources/readme/text-input.png" width="350">

- A trivia question presented to the player in each maze room.
- Players input answers to progress through the maze.
- Different types of question responses, including text-input, multiple-choice, and short-answer formats.
- Players are given a specific number of hints or gifts after each question, helping with difficult challenges.


### Results Screen
<img src="/src/main/resources/readme/result.png" width="650">

- Displays key statistics after completing a round, such as health and hint usage.
- Provides insight into the player's performance in the maze.
- Save/load functionality allows players to track stats and improve gameplay.
- Tracks progress to make each playthrough more meaningful and strategic.

## Credits

- **Team Members:**
  - Jacob Klymenko
  - Mathew Miller
  - Cai Spidel
- **Art & Design:** 
  - All pixel art assets were created by Cai Spidel.
  - GUI design were developed by Mathew Miller and Jacob Klymenko.
- **Music:**
  - All rights to the music are owned by the respective creators **chefelf** and **Iwasbored**.

## Acknowledgements

- Special thanks to our professor and peers for their feedback and support during the development process.
- Inspired by classic 8-bit RPGs and retro video games.


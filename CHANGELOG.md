# Changes
## Controller
- Made change to Controller in order to abstract out the setting up players outside of run.
## GameResult Enum
- Changed names of Enum class to match the instructions.
## Board
- Now has empty constructor to set the array to null.
## Generic Player
- Now handles if a boards array is null and assigns values to it in setup.
## View
- Game results changed so the message had to change for grammar.
## Tests
- Relied on the game message so they were also updated.
## AiPlayer
- Changed constructor to get rid of board array set up.
- modified and added a setup method that calls the super and then modifies the board array.
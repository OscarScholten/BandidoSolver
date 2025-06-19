# Additional Requirements for Bandido Solver

## Card and Board Handling
- Cards are rectangular (2 wide x 1 high) and can be rotated in 0, 90, 180, or 270 degrees (clockwise). The rotation must be included in the output.
- The position of a card is defined by its top-left coordinate on the board.
- The board is unbounded, but practical maximum size is about 70x2 in each direction.
- The start card (difficulty) is specified as a parameter and is placed at the center.

## Game Logic
- No overlap of cards is allowed.
- No connection mismatches are allowed (tunnels must connect properly).
- Floating cards are not allowed; each new card must connect at least one exit to an existing card on the board.
- The solver should stop at the first solution found (no need to enumerate all solutions).

## Deck Handling
- The deck is shuffled using a random seed. The program should print the seed used and allow a command-line option to specify the seed for reproducibility.
- The deck order should be included in the output.
- It should be possible to specify a custom deck (e.g., a list of card IDs, possibly less than the full deck) for testing. If both a custom deck and a seed are provided, the seed shuffles only the custom deck unless the deck order is fixed as given.

## Simulation and Output
- The solver should support any number of players (initial implementation for 2, but generic for N).
- All players know each other's hands (perfect information for current hands, not for future draws).
- The move log should be a sequence of moves (player order is fixed and alternates), with each move including card ID, position (top-left), and orientation (degrees).
- Output should be in JSON format and include:
  - The seed used for shuffling
  - The deck (order of card IDs)
  - Whether the game can be solved
  - Number of cards played
  - The order in which cards are played (move log)

## Unit Testing
- There should be a test that validates that using the same random seed generates the same deck, and different seeds generate different decks.
- There should be tests that show the game logic can detect illegal placements (overlap, connection mismatches, floating cards).
- There should be tests that verify the solver can handle decks of arbitrary size (e.g., 10 cards) and correctly report solvable/unsolvable for such cases.

## Command-line Parameters
- The program should accept parameters for:
  - Random seed
  - Difficulty level (start card)
  - Number of players
  - Custom deck (list of card IDs)

## Miscellaneous
- If the solver cannot find a solution, the output should still include the attempted deck and seed, with a flag indicating unsolvable and an empty move log.
- The start card is specified separately from the custom deck (difficulty parameter).
- All four rotations are legal for every card unless otherwise specified (start card may be fixed in orientation if required).

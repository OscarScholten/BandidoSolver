export class Game {
    readonly board: string;
    readonly deck: string;
    readonly playerCount: number;
    readonly cardCount: number;
    constructor(board: string, deck: string, playerCount = 2, cardCount = 3) {
        this.board = board;
        this.deck = deck;
        this.playerCount = playerCount;
        this.cardCount = cardCount;
    }
}

export class SolveResult {
    success: boolean;
    board: string;
}

export function solve(game: Game): SolveResult {
    return {success: false, board: ''};
}
/* tile elements (from https://www.w3.org/TR/xml-entity-names/025.html)
 * 2501 ━ BOX DRAWINGS HEAVY HORIZONTAL
 * 2578 ╸ BOX DRAWINGS HEAVY LEFT
 * 257A ╺ BOX DRAWINGS HEAVY RIGHT
 * 250F ┏ BOX DRAWINGS HEAVY DOWN AND RIGHT
 * 2513 ┓ BOX DRAWINGS HEAVY DOWN AND LEFT
 * 2533 ┳ BOX DRAWINGS HEAVY DOWN AND HORIZONTAL
 * 2517 ┗ BOX DRAWINGS HEAVY UP AND RIGHT
 * 251B ┛ BOX DRAWINGS HEAVY UP AND LEFT
 * 253B ┻ BOX DRAWINGS HEAVY UP AND HORIZONTAL
 * 2523 ┣ BOX DRAWINGS HEAVY VERTICAL AND RIGHT
 * 252B ┫ BOX DRAWINGS HEAVY VERTICAL AND LEFT
 * 254B ╋ BOX DRAWINGS HEAVY VERTICAL AND HORIZONTAL
 */

class TileData {
    readonly up: boolean;
    readonly right: boolean;
    readonly down: boolean;
    readonly left: boolean;
    constructor(up: boolean, right: boolean, down: boolean, left: boolean) {
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }
}

const tileData = new Map<string, TileData>([
    ['━', new TileData(false, true, false, true)],
    ['╸', new TileData(false, false, false, true)],
    ['╺', new TileData(false, true, false, false)],
    ['┏', new TileData(false, true, true, false)],
    ['┓', new TileData(false, false, true, true)],
    ['┳', new TileData(false, true, true, true)],
    ['┗', new TileData(true, true, false, false)],
    ['┛', new TileData(true, false, false, true)],
    ['┻', new TileData(true, true, false, true)],
    ['┣', new TileData(true, true, true, false)],
    ['┫', new TileData(true, false, true, true)],
    ['╋', new TileData(true, true, true, true)]
]);

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
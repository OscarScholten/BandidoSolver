import { Game, SolveResult, solve } from '../src/index';

describe('testing solving', () => {
    test('solving an empty board is a success', () => {
        const game = new Game('', '');
        const solveResult = solve(game);
        expect(solveResult.success).toBe(true);
    });
    test('solving a board with no open tiles is a success', () => {
        const game = new Game('╺╸', '');
        const solveResult = solve(game);
        expect(solveResult.success).toBe(true);
    });
    test('solving a board with open tiles and no deck cannot be done', () => {
        const game = new Game('━━', '');
        const solveResult = solve(game);
        expect(solveResult.success).toBe(false);
    });
    test('solving a board with open tiles and a basic deck is a success', () => {
        const game = new Game('━━', '━━ ━━ ━━ ━━ ━╸ ━╸');
        const solveResult = solve(game);
        expect(solveResult.success).toBe(true);
        expect(solveResult.board).toBe('╺━━━━╸');
    });
});
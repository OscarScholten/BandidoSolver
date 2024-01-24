import { Game, SolveResult, solve } from '../src/index';

describe('testing solving', () => {
    test('solving an empty deck is not possible', () => {
        const game = new Game('', '');
        const solveResult = solve(game);
        expect(solveResult.success).toBe(false);
    });
});
package edu.rpi.legup.puzzle.sudoku;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.sudoku.rules.*;
import edu.rpi.legup.ui.boardview.BoardView;
import edu.rpi.legup.ui.boardview.ElementView;

public class Sudoku extends Puzzle
{
    private SudokuView boardView;

    /**
     * Sudoku Constructor
     */
    public Sudoku()
    {
        super();

        this.name = "Sudoku";

        this.importer = new SudokuImporter(this);
        this.exporter = new SudokuExporter(this);

        this.factory = new SudokuCellFactory();

        basicRules.add(new AdvancedDeductionBasicRule());
        basicRules.add(new LastCellForNumberBasicRule());
        basicRules.add(new LastNumberForCellBasicRule());

        caseRules.add(new PossibleCellCaseRule());
        caseRules.add(new PossibleNumberCaseRule());

        contradictionRules.add(new NoSolutionContradictionRule());
        contradictionRules.add(new RepeatedNumberContradictionRule());
    }

    public BoardView getBoardView()
    {
        return boardView;
    }

    /**
     * Initializes the game board
     */
    @Override
    public void initializeView()
    {
        boardView = new SudokuView((SudokuBoard)currentBoard);
    }

    /**
     * Generates a random edu.rpi.legup.puzzle based on the difficulty
     *
     * @param difficulty level of difficulty (1-10)
     *
     * @return board of the random edu.rpi.legup.puzzle
     */
    @Override
    public Board generatePuzzle(int difficulty)
    {
        return null;
    }

    /**
     * Determines if the current board is a valid state
     *
     * @param board board to check for validity
     *
     * @return true if board is valid, false otherwise
     */
    @Override
    public boolean isBoardComplete(Board board)
    {
        SudokuBoard sudokuBoard = (SudokuBoard) board;
        TreeTransition transition = new TreeTransition(null, sudokuBoard);

        for(ContradictionRule rule : contradictionRules)
        {
            if(rule.checkContradiction(transition) == null)
            {
                return false;
            }
        }

        for(Element element : sudokuBoard.getElementData())
        {
            SudokuCell cell = (SudokuCell)element;
            if(cell.getData() == 0)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Callback for when the board element changes
     *
     * @param board the board that has changed
     */
    @Override
    public void onBoardChange(Board board)
    {

    }
}

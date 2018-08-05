package edu.rpi.legup.puzzle.fillapix;

import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.puzzle.fillapix.rules.*;
import edu.rpi.legup.ui.boardview.BoardView;
import edu.rpi.legup.ui.boardview.ElementView;

import java.awt.*;

public class Fillapix extends Puzzle
{
    private FillapixView boardView;

    /**
     * Fillapix Constructor
     */
    public Fillapix()
    {
        super();

        this.name = "Fillapix";

        this.importer = new FillapixImporter(this);
        this.exporter = new FillapixExporter(this);

        this.factory = new FillapixCellFactory();

        //basicRules.add(new AdvancedDeductionBasicRule());
        basicRules.add(new FinishWithBlackBasicRule());
        basicRules.add(new FinishWithWhiteBasicRule());

        caseRules.add(new BlackOrWhiteCaseRule());

        contradictionRules.add(new TooFewBlackCellsContradictionRule());
        contradictionRules.add(new TooManyBlackCellsContradictionRule());
    }

    /**
     * Initializes the game board
     */
    @Override
    public void initializeView()
    {
        boardView = new FillapixView((FillapixBoard) currentBoard);
    }

    @Override
    public Board generatePuzzle(int difficulty)
    {
        return null;
    }

    @Override
    public boolean isPuzzleComplete()
    {
        return false;
    }

    @Override
    public boolean isBoardComplete(Board board)
    {
        return false;
    }

    @Override
    public void onBoardChange(Board board)
    {

    }

    public BoardView getBoardView()
    {
        return boardView;
    }
}
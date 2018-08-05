package edu.rpi.legup.ui.boardview;

import edu.rpi.legup.controller.BoardController;
import edu.rpi.legup.controller.ElementController;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.CaseBoard;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.observer.IBoardListener;
import edu.rpi.legup.ui.DynamicViewer;

import java.util.ArrayList;

public abstract class BoardView extends DynamicViewer implements IBoardListener
{
    protected Board board;
    protected ArrayList<ElementView> elementViews;
    protected ElementController elementController;
    protected ElementSelection selection;

    protected Pane boardContent;

    /**
     * BoardView Constructor - creates a BoardView object using
     * the controller handle the edu.rpi.legup.ui events
     *
     * @param boardController controller that handles the ui events
     */
    public BoardView(BoardController boardController, ElementController elementController)
    {
        super(boardController);
        this.board = null;
        this.elementViews = new ArrayList<>();
        this.elementController = elementController;
        this.selection = new ElementSelection();

        elementController.setBoardView(this);

        boardContent = new Pane();
        getChildren().add(boardContent);
    }

    /**
     * Initializes the initial dimension of the viewport for the BoardView
     */
    public abstract void initSize();

    /**
     * Gets the dimension of the board view
     *
     * @return dimension of the board view
     */
    protected abstract Dimension2D getProperSize();

    /**
     * Gets the ElementView from the puzzleElement index or
     * null if out of bounds
     *
     * @param index index of the ElementView
     * @return ElementView at the specified index
     */
    public abstract ElementView getElement(int index);

    /**
     * Sets the ElementView list
     *
     * @param elements ElementView list
     */
    public void setElementViews(ArrayList<ElementView> elements)
    {
        elementViews = elements;
    }

    /**
     * Gets the ElementView from the location specified or
     * null if one does not exists at that location
     *
     * @param point location on the viewport
     * @return ElementView at the specified location
     */
    public abstract ElementView getElement(Point2D point);

    /**
     * Gets the ElementSelection for this BoardView
     *
     * @return the ElementSelection
     */
    public ElementSelection getSelection()
    {
        return selection;
    }

    /**
     * Gets the board associated with this view
     *
     * @return board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * Sets the board associated with this view
     *
     * @param board board
     */
    public void setBoard(Board board)
    {
        if(this.board != board)
        {
            this.board = board;

            if(board instanceof CaseBoard)
            {
                CaseBoard caseBoard = (CaseBoard)board;
                Board baseBoard = caseBoard.getBaseBoard();

                for(ElementView elementView: elementViews)
                {
                    PuzzleElement puzzleElement = baseBoard.getPuzzleElement(elementView.getPuzzleElement());
                    elementView.setPuzzleElement(puzzleElement);
                    elementView.setShowCasePicker(true);
                    elementView.setCaseRulePickable(caseBoard.isPickable(elementView.getPuzzleElement()));
                }
            }
            else
            {
                for(ElementView elementView: elementViews)
                {
                    elementView.setPuzzleElement(board.getPuzzleElement(elementView.getPuzzleElement()));
                    elementView.setShowCasePicker(false);
                }
            }
        }
    }

    /**
     * Board puzzleElement has changed
     *
     * @param board board to update the BoardView
     */
    @Override
    public void onBoardChanged(Board board)
    {
        setBoard(board);
    }

    /**
     * Gets the amount of puzzle elements for this board
     *
     * @return the amount of puzzle elements for this board
     */
    public int getElementCount()
    {
        return elementViews.size();
    }

    /**
     * Gets the PuzzleElements associated with the BoardView
     *
     * @return list of PuzzleElements
     */
    public ArrayList<ElementView> getElementViews()
    {
        return elementViews;
    }

    public ElementController getElementController()
    {
        return elementController;
    }

    public void drawBoard()
    {
        for(ElementView element: elementViews)
        {
//            element.draw(graphics2D);
        }
    }

    /**
     * Called when the board puzzleElement changed
     *
     * @param puzzleElement puzzleElement of the puzzleElement that changed
     */
    @Override
    public void onBoardDataChanged(PuzzleElement puzzleElement)
    {
        
    }
}

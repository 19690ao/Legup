package edu.rpi.legup.puzzle.lightup.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.CaseBoard;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.CaseRule;
import edu.rpi.legup.model.rules.RegisterRule;
import edu.rpi.legup.model.rules.RuleType;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.lightup.LightUp;
import edu.rpi.legup.puzzle.lightup.LightUpBoard;
import edu.rpi.legup.puzzle.lightup.LightUpCell;
import edu.rpi.legup.puzzle.lightup.LightUpCellType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RegisterRule(puzzleName = LightUp.class, ruleType = RuleType.CASE)
public class SatisfyNumberCaseRule extends CaseRule
{

    public SatisfyNumberCaseRule()
    {
        super("Satisfy Number", "The different ways a blocks number can be satisfied.","images/lightup/cases/SatisfyNumber.png");
    }

    @Override
    public CaseBoard getCaseBoard(Board board)
    {
        LightUpBoard lightUpBoard = (LightUpBoard) board.copy();
        CaseBoard caseBoard = new CaseBoard(lightUpBoard, this);
        lightUpBoard.setModifiable(false);
        for(PuzzleElement data: lightUpBoard.getPuzzleElements())
        {
            if(((LightUpCell)data).getType() == LightUpCellType.NUMBER)
            {
                caseBoard.addPickableElement(data);
            }
        }
        return caseBoard;
    }

    /**
     * Gets the possible cases at a specific location based on this case rule
     *
     * @param board        the current board state
     * @param puzzleElement puzzleElement to determine the possible cases for
     *
     * @return a list of elements the specified could be
     */
    @Override
    public ArrayList<Board> getCases(Board board, PuzzleElement puzzleElement)
    {
        LightUpBoard lightUpBoard = (LightUpBoard)board;
        LightUpCell cell = (LightUpCell) puzzleElement;
        int x = cell.getX();
        int y = cell.getY();

        List<LightUpCell> openSpots = new ArrayList<>();

        int numNeeded = cell.getData();

        LightUpCell checkCell = lightUpBoard.getCell(x + 1, y);
        if(checkCell != null)
        {
            if(checkCell.getType() == LightUpCellType.UNKNOWN && !cell.isLite())
            {
                openSpots.add(checkCell);
            }
            else if(checkCell.getType() == LightUpCellType.BULB)
            {
                numNeeded--;
            }
        }
        checkCell = lightUpBoard.getCell(x, y + 1);
        if(checkCell != null)
        {
            if(checkCell.getType() == LightUpCellType.UNKNOWN && !cell.isLite())
            {
                openSpots.add(checkCell);
            }
            else if(checkCell.getType() == LightUpCellType.BULB)
            {
                numNeeded--;
            }
        }
        checkCell = lightUpBoard.getCell(x - 1, y);
        if(checkCell != null)
        {
            if(checkCell.getType() == LightUpCellType.UNKNOWN && !cell.isLite())
            {
                openSpots.add(checkCell);
            }
            else if(checkCell.getType() == LightUpCellType.BULB)
            {
                numNeeded--;
            }
        }
        checkCell = lightUpBoard.getCell(x, y - 1);
        if(checkCell != null)
        {
            if(checkCell.getType() == LightUpCellType.UNKNOWN && !cell.isLite())
            {
                openSpots.add(checkCell);
            }
            else if(checkCell.getType() == LightUpCellType.BULB)
            {
                numNeeded--;
            }
        }

        ArrayList<Board> cases = new ArrayList<>();
        if(numNeeded == 0)
        {
            return cases;
        }

        generateCases(lightUpBoard, cell.getData(), openSpots, cases);

        return cases;
    }

    private void generateCases(final LightUpBoard board, final int num, List<LightUpCell> openSpots, List<Board> cases)
    {
        if(num > openSpots.size())
        {
            return;
        }

        for(int i = 0; i < openSpots.size(); i++)
        {
            LightUpCell c = openSpots.get(i);
            LightUpBoard newCase = board.copy();
            LightUpCell newCell = c.copy();
            int x = c.getX();
            int y = c.getY();

            newCell.setData(-4);
            newCase.setCell(x, y, newCell);
            newCase.addModifiedData(newCell);

            generateCases(board, num, openSpots, cases, newCase, i);
        }
    }

    private void generateCases(final LightUpBoard board, final int num, List<LightUpCell> openSpots, List<Board> cases, LightUpBoard curBoard, int index)
    {
        if(num <= curBoard.getModifiedData().size())
        {
            cases.add(curBoard);
            return;
        }

        for(int i = index + 1; i < openSpots.size(); i++)
        {
            LightUpCell c = openSpots.get(i);
            int x = c.getX();
            int y = c.getY();

            LightUpCell cc = curBoard.getCell(x, y);
            if (!curBoard.getModifiedData().contains(cc))
            {
                LightUpBoard newCase = board.copy();
                LightUpCell newCell = c.copy();

                for(PuzzleElement mod : curBoard.getModifiedData())
                {
                    LightUpCell modCell = (LightUpCell)mod.copy();
                    int mx = modCell.getX();
                    int my = modCell.getY();

                    modCell.setData(-4);

                    newCase.setCell(mx, my, modCell);
                    newCase.addModifiedData(modCell);
                }

                newCell.setData(-4);

                newCase.setCell(x, y, newCell);
                newCase.addModifiedData(newCell);

                generateCases(board, num, openSpots, cases, newCase, i);
            }
        }
    }

    /**
     * Checks whether the transition logically follows from the parent node using this rule
     *
     * @param transition transition to check
     *
     * @return null if the child node logically follow from the parent node, otherwise error message
     */
    @Override
    public String checkRule(TreeTransition transition)
    {
        return null;
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific puzzleElement index using this rule
     *
     * @param transition   transition to check
     * @param puzzleElement index of the puzzleElement
     *
     * @return null if the child node logically follow from the parent node at the specified puzzleElement,
     * otherwise error message
     */
    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement)
    {
        return null;
    }

    /**
     * Checks whether the child node logically follows from the parent node using this rule
     * and if so will perform the default application of the rule
     *
     * @param transition transition to apply default application
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    @Override
    public boolean doDefaultApplication(TreeTransition transition)
    {
        return false;
    }

    /**
     * Checks whether the child node logically follows from the parent node at the
     * specific puzzleElement index using this rule and if so will perform the default application of the rule
     *
     * @param transition   transition to apply default application
     * @param puzzleElement
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    @Override
    public boolean doDefaultApplicationAt(TreeTransition transition, PuzzleElement puzzleElement)
    {
        return false;
    }
}

package edu.rpi.legup.puzzle.sudoku.rules;

import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.model.rules.BasicRule;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.sudoku.SudokuBoard;
import edu.rpi.legup.puzzle.sudoku.SudokuCell;

public class AdvancedDeductionBasicRule extends BasicRule
{

    public AdvancedDeductionBasicRule()
    {
        super("Advanced Deduction",
                "Use of group logic deduces more answers by means of forced by Location and forced by Deduction",
                "images/sudoku/AdvancedDeduction.png");
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific element index using this rule
     *
     * @param transition transition to check
     * @param element equivalent element
     * @return null if the child node logically follow from the parent node at the specified element,
     * otherwise error message
     */
    public String checkRuleRawAt(TreeTransition transition, Element element)
    {
        SudokuBoard initialBoard = (SudokuBoard) transition.getParents().get(0).getBoard();
        SudokuBoard finalBoard = (SudokuBoard) transition.getBoard();

        SudokuCell cell = (SudokuCell) finalBoard.getElementData(element);
        int index = cell.getIndex();
        int groupSize = initialBoard.getWidth();
        int groupDim = (int)Math.sqrt(groupSize);
        int rowIndex = index / groupSize;
        int colIndex = index % groupSize;
        int relX = rowIndex / groupDim;
        int relY = colIndex % groupDim;
        int groupNum = rowIndex / groupDim * groupDim + colIndex / groupDim;
        boolean[][] possible = new boolean[groupDim][groupDim];
        for(int y = 0; y < groupDim; y++)
        {
            for(int x = 0; x < groupDim; x++)
            {
                SudokuCell c = initialBoard.getCell(groupNum, x, y);
                if(c.getData() == cell.getData() && x != relX && y != relY )
                {
                    return "Duplicate value in sub region";
                }
                possible[y][x] = c.getData() == 0;
            }
        }
        for(int y = 0; y < groupDim; y++)
        {
            for(int x = 0; x < groupSize; x++)
            {
                SudokuCell r = initialBoard.getCell(x, (groupNum / groupDim) * groupDim + y);
                SudokuCell c = initialBoard.getCell((groupNum % groupDim) * groupDim + y, x);
                if(r.getData() == cell.getData())
                {
                    for(int i = 0; i < groupDim; i++)
                    {
                        possible[y][i] = false;
                    }
                }
                if(c.getData() == cell.getData())
                {
                    for(int i = 0; i < groupDim; i++)
                    {
                        possible[i][y] = false;
                    }
                }
            }
        }
        boolean isForced = false;
        for(int y = 0; y < groupDim; y++)
        {
            for(int x = 0; x < groupDim; x++)
            {
                if(possible[y][x] && !isForced)
                {
                    isForced = true;
                }
                else if(possible[y][x])
                {
                    return "Not forced";
                }
            }
        }
        if(!isForced)
        {
            return "Not forced";
        }
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
     * specific element index using this rule and if so will perform the default application of the rule
     *
     * @param transition   transition to apply default application
     * @param element equivalent element
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    @Override
    public boolean doDefaultApplicationAt(TreeTransition transition, Element element)
    {
        return false;
    }
}

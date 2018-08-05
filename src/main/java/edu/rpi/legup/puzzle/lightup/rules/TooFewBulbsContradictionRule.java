package edu.rpi.legup.puzzle.lightup.rules;

import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.model.rules.RegisterRule;
import edu.rpi.legup.model.rules.RuleType;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.lightup.LightUp;
import edu.rpi.legup.puzzle.lightup.LightUpBoard;
import edu.rpi.legup.puzzle.lightup.LightUpCell;
import edu.rpi.legup.puzzle.lightup.LightUpCellType;

import java.awt.*;


@RegisterRule(puzzleName = LightUp.class, ruleType = RuleType.CONTRADICTION)
public class TooFewBulbsContradictionRule extends ContradictionRule
{

    public TooFewBulbsContradictionRule()
    {
        super("Too Few Bulbs", "There cannot be less bulbs around a block than its number states.", "images/lightup/contradictions/TooFewBulbs.png");
    }

    /**
     * Checks whether the transition has a contradiction at the specific element index using this rule
     *
     * @param transition   transition to check contradiction
     * @param element index of the element
     *
     * @return null if the transition contains a contradiction at the specified element,
     * otherwise error message
     */
    @Override
    public String checkContradictionAt(TreeTransition transition, Element element)
    {
        LightUpBoard board = (LightUpBoard) transition.getBoard();
        LightUpCell cell = (LightUpCell)board.getElementData(element);
        if(cell.getType() != LightUpCellType.NUMBER)
        {
            return "Does not contain a contradiction";
        }

        Point location = cell.getLocation();

        int bulbs = 0;

        LightUpCell up = board.getCell(location.x, location.y + 1);
        if(up != null && up.getType() == LightUpCellType.BULB)
        {
            bulbs++;
        }
        LightUpCell down = board.getCell(location.x, location.y - 1);
        if(down != null && down.getType() == LightUpCellType.BULB)
        {
            bulbs++;
        }
        LightUpCell right = board.getCell(location.x + 1, location.y);
        if(right != null && right.getType() == LightUpCellType.BULB)
        {
            bulbs++;
        }
        LightUpCell left = board.getCell(location.x - 1, location.y);
        if(left != null && left.getType() == LightUpCellType.BULB)
        {
            bulbs++;
        }

        if((up == null || up.getType() == LightUpCellType.UNKNOWN) &&
                (down == null || down.getType() == LightUpCellType.UNKNOWN) &&
                (right == null || right.getType() == LightUpCellType.UNKNOWN) &&
                (left == null || left.getType() == LightUpCellType.UNKNOWN))
        {
            return "Number does not contain a contradiction";
        }
        else if(bulbs < cell.getData())
        {
            return null;
        }
        return "Number does not contain a contradiction";
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
     * @param element
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

package edu.rpi.legup.puzzle.nurikabe.rules;

import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.model.rules.BasicRule;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.nurikabe.NurikabeBoard;
import edu.rpi.legup.puzzle.nurikabe.NurikabeCell;
import edu.rpi.legup.puzzle.nurikabe.NurikabeType;

public class UnreachableBasicRule extends BasicRule
{
    public UnreachableBasicRule()
    {
        super("Unreachable white region","A cell must be black if it cannot be reached by any white region","images/nurikabe/rules/Unreachable.png");
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific element index using this rule
     *
     * @param transition   transition to check
     * @param element equivalent element
     *
     * @return null if the child node logically follow from the parent node at the specified element,
     * otherwise error message
     */
    @Override
    protected String checkRuleRawAt(TreeTransition transition, Element element)
    {
        ContradictionRule contraRule = new CantReachWhiteContradictionRule();

        NurikabeBoard destBoardState = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoardState = (NurikabeBoard) transition.getParents().get(0).getBoard();

        NurikabeCell cell = (NurikabeCell) destBoardState.getElementData(element);

        if(cell.getType() != NurikabeType.BLACK)
        {
            return "Only black cells are allowed for this rule!";
        }

        NurikabeBoard modified = origBoardState.copy();
        NurikabeCell modCell = (NurikabeCell) modified.getElementData(element);
        modCell.setData(NurikabeType.WHITE.toValue());

        if(contraRule.checkContradiction(new TreeTransition(null, modified)) == null)
        {
            return null;
        }
        else
        {
            return "This is not the only way for black to escape!";
        }
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

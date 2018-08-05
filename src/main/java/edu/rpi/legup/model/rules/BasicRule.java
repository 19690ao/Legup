package edu.rpi.legup.model.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.model.tree.TreeTransition;

import static edu.rpi.legup.model.rules.RuleType.BASIC;

public abstract class BasicRule extends Rule
{
    /**
     * BasicRule Constructor - creates a new basic rule
     *
     * @param ruleName name of the rule
     * @param description description of the rule
     * @param imageName file name of the image
     */
    public BasicRule(String ruleName, String description, String imageName)
    {
        super(ruleName, description, imageName);
        this.ruleType = BASIC;
    }

    /**
     * Checks whether the transition logically follows from the parent node using this rule
     *
     * @param transition transition to check
     *
     * @return null if the child node logically follow from the parent node, otherwise error message
     */
    public String checkRule(TreeTransition transition)
    {
        Board finalBoard = transition.getBoard();

        if(!finalBoard.isModified())
        {
            return "State must be modified";
        }
        else if(transition.getParents().size() != 1 ||
                transition.getParents().get(0).getChildren().size() != 1)
        {
            return "State must have only 1 parent and 1 child";
        }
        else
        {
            return checkRuleRaw(transition);
        }
    }

    /**
     * Checks whether the transition logically follows from the parent node using this rule.
     * This method is the one that should overridden in child classes
     *
     * @param transition transition to check
     *
     * @return null if the child node logically follow from the parent node, otherwise error message
     */
    public String checkRuleRaw(TreeTransition transition)
    {
        Board finalBoard = transition.getBoard();
        for(Element element: finalBoard.getModifiedData())
        {
            String checkStr = checkRuleAt(transition, element);
            if(checkStr != null)
            {
                return checkStr;
            }
        }
        return null;
    }

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific element index using this rule
     *
     * @param transition transition to check
     * @param element equivalent element
     *
     * @return null if the child node logically follow from the parent node at the specified element,
     * otherwise error message
     */
    public String checkRuleAt(TreeTransition transition, Element element)
    {
        Board finalBoard = transition.getBoard();

        if(!finalBoard.getElementData(element).isModified())
        {
            return "Element must be modified";
        }
        else if(transition.getParents().size() != 1 ||
                transition.getParents().get(0).getChildren().size() != 1)
        {
            return "State must have only 1 parent and 1 child";
        }
        else
        {
            return checkRuleRawAt(transition, element);
        }
    }
}

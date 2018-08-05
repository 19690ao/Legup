package edu.rpi.legup.model.rules;

import javafx.scene.image.Image;
import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.model.tree.TreeTransition;

public abstract class Rule
{
    protected String ruleName;
    protected String description;
    protected String imageName;
    protected Image image;
    protected RuleType ruleType;
    protected boolean isAiUsable;

    /**
     * Rule Constructor - creates a new rule
     *
     * @param ruleName name of the rule
     * @param description description of the rule
     * @param imageName file name of the image
     */
    public Rule(String ruleName, String description, String imageName)
    {
        this.imageName = imageName;
        this.ruleName = ruleName;
        this.description = description;
        isAiUsable = true;
        loadImage();
    }

    /**
     * Checks whether the transition logically follows from the parent node using this rule
     *
     * @param transition transition to check
     *
     * @return null if the child node logically follow from the parent node, otherwise error message
     */
    public abstract String checkRule(TreeTransition transition);

    /**
     * Checks whether the transition logically follows from the parent node using this rule.
     * This method is the one that should overridden in child classes
     *
     * @param transition transition to check
     *
     * @return null if the child node logically follow from the parent node, otherwise error message
     */
    protected abstract String checkRuleRaw(TreeTransition transition);

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
    public abstract String checkRuleAt(TreeTransition transition, Element element);

    /**
     * Checks whether the child node logically follows from the parent node
     * at the specific element index using this rule
     * This method is the one that should overridden in child classes
     *
     * @param transition transition to check
     * @param element equivalent element
     *
     * @return null if the child node logically follow from the parent node at the specified element,
     * otherwise error message
     */
    protected abstract String checkRuleRawAt(TreeTransition transition, Element element);

    /**
     * Checks whether the child node logically follows from the parent node using this rule
     * and if so will perform the default application of the rule
     *
     * @param transition transition to apply default application
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    public abstract boolean doDefaultApplication(TreeTransition transition);

    /**
     * Checks whether the child node logically follows from the parent node at the
     * specific element index using this rule and if so will perform the default application of the rule
     *
     * @param transition transition to apply default application
     * @param element equivalent element
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    public abstract boolean doDefaultApplicationAt(TreeTransition transition, Element element);

    /**
     * Loads the image file
     */
    private void loadImage()
    {
        if(imageName != null)
        {
            image = new Image(ClassLoader.getSystemResourceAsStream(imageName));
        }
    }

    /**
     * Gets the name of the rule
     *
     * @return name of the rule
     */
    public String getRuleName()
    {
        return ruleName;
    }

    /**
     * Sets the rule name
     *
     * @param ruleName new name of the rule
     */
    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }

    /**
     * Gets the description of the rule
     *
     * @return the description of the rule
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gets the image image of the rule
     *
     * @return image image of the rule
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * Gets the rule type
     *
     * @return rule type
     */
    public RuleType getRuleType()
    {
        return ruleType;
    }
}
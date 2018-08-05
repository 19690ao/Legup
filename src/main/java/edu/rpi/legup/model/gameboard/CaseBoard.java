package edu.rpi.legup.model.gameboard;

import edu.rpi.legup.model.rules.CaseRule;

import java.util.HashSet;
import java.util.Set;

public class CaseBoard extends Board
{
    private Board baseBoard;
    private CaseRule caseRule;
    private Set<Element> pickableElements;

    public CaseBoard(Board baseBoard, CaseRule caseRule)
    {
        this.baseBoard = baseBoard;
        this.caseRule = caseRule;
        this.pickableElements = new HashSet<>();
    }

    public void addPickableElement(Element element)
    {
        pickableElements.add(element);
    }

    public void removePickableElement(Element element)
    {
        pickableElements.remove(element);
    }

    public boolean isPickable(Element element)
    {
        return pickableElements.contains(element);
    }

    public Board getBaseBoard()
    {
        return baseBoard;
    }

    public void setBaseBoard(Board baseBoard)
    {
        this.baseBoard = baseBoard;
    }

    public CaseRule getCaseRule()
    {
        return caseRule;
    }

    public void setCaseRule(CaseRule caseRule)
    {
        this.caseRule = caseRule;
    }

    public int getCount()
    {
        return pickableElements.size();
    }

    public CaseBoard copy()
    {
        return null;
    }
}

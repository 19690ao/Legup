package edu.rpi.legup.puzzle.sudoku;

import edu.rpi.legup.model.gameboard.GridCell;

import java.awt.*;

public class SudokuCell extends GridCell
{
    private int groupIndex;

    /**
     * SudokuCell Constructor - creates a new Sudoku cell to hold the element
     *
     * @param value value of the sudoku cell
     * @param location location of the cell on the board
     * @param groupIndex index of the group the cell is in on the board
     */
    public SudokuCell(int value, Point location, int groupIndex)
    {
        super(value, location);
        this.groupIndex = groupIndex;
    }

    private SudokuCell(Object data, Point location, int groupIndex)
    {
        this(((Integer)data).intValue(), location, groupIndex);
    }

    @Override
    public Integer getData()
    {
        return (Integer)data;
    }


    /**
     * Gets the group index of the cell
     *
     * @return group index of the cell
     */
    public int getGroupIndex()
    {
        return groupIndex;
    }

    /**
     * Performs a deep copy on the SudokuCell
     *
     * @return a new copy of the SudokuCell that is independent of this one
     */
    @Override
    public SudokuCell copy()
    {
        SudokuCell copy = new SudokuCell(data, (Point)location.clone(), groupIndex);
        copy.setIndex(index);
        copy.setModifiable(isModifiable);
        copy.setGiven(isGiven);
        return copy;
    }
}

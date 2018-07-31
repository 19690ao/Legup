package edu.rpi.legup.puzzle.masyu;

import edu.rpi.legup.app.GameBoardFacade;
import edu.rpi.legup.controller.ElementController;
import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.ui.boardview.BoardView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static edu.rpi.legup.app.GameBoardFacade.getInstance;

public class MasyuController extends ElementController
{

    private MasyuElementView mousePressedCell;
    private MasyuElementView mouseDraggedCell;
    private List<MasyuElementView> masyuLine;

    public MasyuController()
    {
        super();
        this.mousePressedCell = null;
        this.mouseDraggedCell = null;
        this.masyuLine = new ArrayList<>();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
//        BoardView boardView = getInstance().getLegupUI().getBoardView();
//        mousePressedCell = (MasyuElementView)boardView.getElement(e.getPoint());
//        masyuLine.add(mousePressedCell);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        BoardView boardView = getInstance().getLegupUI().getBoardView();
//        MasyuElementView elementView = (MasyuElementView)boardView.getElement(e.getPoint());
//        Puzzle puzzle = GameBoardFacade.getInstance().getPuzzleModule();
//        if(mousePressedCell != null && elementView != null)
//        {
//            if(mouseDraggedCell == null)
//            {
//                mouseDraggedCell = elementView;
//                Point p1 = mousePressedCell.getElement().getLocation();
//                Point p2 = mouseDraggedCell.getElement().getLocation();
//
//                if(Math.abs(p1.x - p2.x) == 1 ^ Math.abs(p1.y - p2.y) == 1)
//                {
//                    masyuLine.add(elementView);
//                    MasyuLine newLine = new MasyuLine(mousePressedCell.getElement(), mouseDraggedCell.getElement());
//                    puzzle.notifyBoardListeners(listener -> listener.onBoardDataChanged(newLine));
//                }
//            }
//            else if(mouseDraggedCell != elementView)
//            {
//                Point p1 = mouseDraggedCell.getElement().getLocation();
//                Point p2 = elementView.getElement().getLocation();
//
//                if(Math.abs(p1.x - p2.x) == 1 ^ Math.abs(p1.y - p2.y) == 1)
//                {
//                    masyuLine.add(elementView);
//                    MasyuLine newLine = new MasyuLine(mouseDraggedCell.getElement(), elementView.getElement());
//                    puzzle.notifyBoardListeners(listener -> listener.onBoardDataChanged(newLine));
//                }
//                mouseDraggedCell = elementView;
//            }
//        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
//        mousePressedCell = null;
//        mouseDraggedCell = null;
//        masyuLine.clear();
    }

    @Override
    public void changeCell(MouseEvent e, Element element)
    {
        if(element instanceof MasyuCell)
        {
            MasyuCell cell = (MasyuCell)element;
            if(e.getButton() == MouseEvent.BUTTON1) {
                if (cell.getData() == 2) {
                    cell.setData(0);
                } else {
                    cell.setData(cell.getData() + 1);
                }
            }
            else if(e.getButton() == MouseEvent.BUTTON3) {
                if (cell.getData() == 0) {
                    cell.setData(2);
                } else {
                    cell.setData(cell.getData() - 1);
                }
            }
        }
    }
}

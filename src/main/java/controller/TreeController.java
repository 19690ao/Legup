package controller;

import model.Puzzle;
import model.tree.Tree;
import ui.boardview.BoardView;
import ui.treeview.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static app.GameBoardFacade.getInstance;

public class TreeController extends Controller
{
    /**
     * TreeController Constructor - creates a controller object to listen
     * to ui events from a TreePanel
     */
    public TreeController()
    {

    }

    /**
     * Mouse Clicked event - no default action
     *
     * @param e MouseEvent object
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    /**
     * Mouse Pressed event - sets the cursor to the move cursor and stores
     * info for possible panning
     *
     * @param e MouseEvent object
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        super.mousePressed(e);
    }

    /**
     * Mouse Released event - sets the cursor back to the default cursor and reset
     * info for panning
     *
     * @param e MouseEvent object
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        super.mouseReleased(e);

        TreeView treeView = (TreeView)viewer;
        Point point = null;//treeView.getActualPoint(e.getPoint());
        TreeElementView elementView = treeView.getTreeElementView(point);
        Puzzle puzzle = getInstance().getPuzzleModule();
        TreeViewSelection selection = treeView.getSelection();
        if(elementView != null)
        {
            if(e.isShiftDown())
            {
                selection.addToSelection(elementView);
            }
            else if(e.isControlDown())
            {
                selection.toggleSelection(elementView);
            }
            else
            {
                selection.newSelection(elementView);
            }
            puzzle.notifyTreeListeners(listener -> listener.onTreeSelectionChanged(selection));
            puzzle.notifyBoardListeners(listener -> listener.onBoardChanged(elementView.getTreeElement().getBoard()));
        }
    }

    /**
     * Mouse Entered event - no default action
     *
     * @param e MouseEvent object
     */
    @Override
    public void mouseEntered(MouseEvent e)
    {
        TreeView treeView = (TreeView)viewer;
        Point point = null;//treeView.getActualPoint(e.getPoint());
        Tree tree = getInstance().getTree();
        BoardView boardView = getInstance().getLegupUI().getBoardView();
        TreeElementView elementView = treeView.getTreeElementView(point);
        Puzzle puzzle = getInstance().getPuzzleModule();
        if(elementView != null)
        {
            puzzle.notifyBoardListeners(listener -> listener.onBoardChanged(elementView.getTreeElement().getBoard()));
        }
    }

    /**
     * Mouse Exited event - no default action
     *
     * @param e MouseEvent object
     */
    @Override
    public void mouseExited(MouseEvent e)
    {
        TreeView treeView = (TreeView)viewer;
        Point point = null;//treeView.getActualPoint(e.getPoint());
        TreeElementView elementView = treeView.getTreeElementView(point);
        Puzzle puzzle = getInstance().getPuzzleModule();
        TreeViewSelection selection = treeView.getSelection();

        selection.setMousePoint(null);
        if(elementView != null)
        {
            TreeElementView selectedView = selection.getFirstSelection();
            puzzle.notifyBoardListeners(listener -> listener.onBoardChanged(selectedView.getTreeElement().getBoard()));
        }
    }

    /**
     * Mouse Dragged event - adjusts the viewport
     *
     * @param e MouseEvent object
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        super.mouseDragged(e);
    }

    /**
     * Mouse Moved event - no default action
     *
     * @param e MouseEvent object
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        TreeView treeView = (TreeView)viewer;
        Point point = null;//treeView.getActualPoint(e.getPoint());
        TreeElementView treeNodeView = treeView.getTreeElementView(point);
        Puzzle puzzle = getInstance().getPuzzleModule();
        if(puzzle != null)
        {
            TreeViewSelection selection = treeView.getSelection();
            if(treeNodeView != null)
            {
                if(treeNodeView != selection.getHover())
                {
                    puzzle.notifyBoardListeners(listener -> listener.onBoardChanged(treeNodeView.getTreeElement().getBoard()));
                    selection.newHover(treeNodeView);
                    puzzle.notifyTreeListeners(listener -> listener.onTreeSelectionChanged(selection));
                }
                else
                {
                    puzzle.notifyTreeListeners(listener -> listener.onTreeSelectionChanged(selection));
                }
            }
            else
            {
                if(selection.getHover() != null)
                {
                    puzzle.notifyBoardListeners(listener -> listener.onBoardChanged(selection.getFirstSelection().getTreeElement().getBoard()));
                    selection.clearHover();
                    puzzle.notifyTreeListeners(listener -> listener.onTreeSelectionChanged(selection));
                }
            }
        }
    }

    /**
     * Mouse Wheel Moved event - zooms in on the viewport
     *
     * @param e MouseEvent object
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        super.mouseWheelMoved(e);
//        TreeView treeView = (TreeView)viewer;
//        TreeViewSelection selection = treeView.getSelectedViews();
//        Point newPoint = new Point(e.getX() + treeView.getViewport().getX(), e.getY() + treeView.getViewport().getY());
//
//        PointerInfo a = MouseInfo.getPointerInfo();
//        Point b = a.getLocation();
//        SwingUtilities.convertPointFromScreen(b, treeView.getCanvas());
//
//        selection.setMousePoint(b);
    }
}

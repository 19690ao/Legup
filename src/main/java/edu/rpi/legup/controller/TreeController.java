package edu.rpi.legup.controller;

import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.tree.Tree;
import edu.rpi.legup.ui.boardview.BoardView;
import edu.rpi.legup.ui.treeview.*;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.tree.Tree;
import edu.rpi.legup.ui.boardview.BoardView;
import edu.rpi.legup.ui.treeview.*;


import static edu.rpi.legup.app.GameBoardFacade.getInstance;

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
    public void onMouseClicked(MouseEvent e)
    {

    }

    /**
     * Mouse Pressed event - sets the cursor to the move cursor and stores
     * info for possible panning
     *
     * @param e MouseEvent object
     */
    @Override
    public void onMousePressed(MouseEvent e)
    {
        super.onMousePressed(e);
    }

    /**
     * Mouse Released event - sets the cursor back to the default cursor and reset
     * info for panning
     *
     * @param e MouseEvent object
     */
    @Override
    public void onMouseReleased(MouseEvent e)
    {
        super.onMouseReleased(e);

        TreeView treeView = (TreeView)viewer;
        Point2D point = null;//treeView.getActualPoint(e.getPoint());
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
    public void onMouseEntered(MouseEvent e)
    {
        TreeView treeView = (TreeView)viewer;
        Point2D point = null;//treeView.getActualPoint(e.getPoint());
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
    public void onMouseExited(MouseEvent e)
    {
        TreeView treeView = (TreeView)viewer;
        Point2D point = null;//treeView.getActualPoint(e.getPoint());
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
    public void onMouseDragged(MouseEvent e)
    {
        super.onMouseDragged(e);
    }

    /**
     * Mouse Moved event - no default action
     *
     * @param e MouseEvent object
     */
    @Override
    public void onMouseMoved(MouseEvent e)
    {
        TreeView treeView = (TreeView)viewer;
        Point2D point = null;//treeView.getActualPoint(e.getPoint());
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
//                    selection.newHover(treeNodeView);
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
//                    selection.clearHover();
                    puzzle.notifyTreeListeners(listener -> listener.onTreeSelectionChanged(selection));
                }
            }
        }
    }
}

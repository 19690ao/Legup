package edu.rpi.legup.controller;

import edu.rpi.legup.app.GameBoardFacade;
import edu.rpi.legup.history.CaseRuleCommand;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.CaseBoard;
import edu.rpi.legup.model.gameboard.Element;
import edu.rpi.legup.model.tree.Tree;
import edu.rpi.legup.ui.boardview.BoardView;
import edu.rpi.legup.ui.boardview.ElementSelection;
import edu.rpi.legup.ui.boardview.ElementView;
import edu.rpi.legup.ui.treeview.*;
import edu.rpi.legup.history.ICommand;
import edu.rpi.legup.history.EditDataCommand;

import static edu.rpi.legup.app.GameBoardFacade.*;

public class ElementController
{
    protected BoardView boardView;

    /**
     * ElementController - element edu.rpi.legup.controller to handles edu.rpi.legup.ui events
     * associated interacting with a ElementView
     */
    public ElementController()
    {
        this.boardView = null;
    }

    public void setBoardView(BoardView boardView)
    {
        this.boardView = boardView;
    }

//    /**
//     * Invoked when the mouse button has been clicked (pressed
//     * and released) on a component.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void mouseClicked(MouseEvent e)
//    {
//
//    }
//
//    /**
//     * Invoked when a mouse button has been pressed on a component.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void mousePressed(MouseEvent e)
//    {
//
//    }
//
//    /**
//     * Invoked when a mouse button has been released on a component.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void mouseReleased(MouseEvent e)
//    {
//        TreePanel treePanel = GameBoardFacade.getInstance().getLegupUI().getTreePanel();
//        TreeView treeView = treePanel.getTreeView();
//        BoardView boardView = getInstance().getLegupUI().getBoardView();
//        Board board = boardView.getBoard();
//        ElementView elementView = boardView.getElement(e.getPoint());
//        TreeViewSelection selection = treeView.getSelection();
//
//        if(elementView != null)
//        {
//            if(board instanceof CaseBoard)
//            {
//                CaseRuleCommand caseRuleCommand = new CaseRuleCommand(elementView, selection, ((CaseBoard)board).getCaseRule());
//                if(caseRuleCommand.canExecute())
//                {
//                    caseRuleCommand.execute();
//                    getInstance().getHistory().pushChange(caseRuleCommand);
//                }
//                else
//                {
//                    treePanel.updateError(caseRuleCommand.getExecutionError());
//                }
//            }
//            else
//            {
//                ICommand edit = new EditDataCommand(elementView, selection, e);
//                if(edit.canExecute())
//                {
//                    edit.execute();
//                    getInstance().getHistory().pushChange(edit);
//                }
//                else
//                {
//                    treePanel.updateError(edit.getExecutionError());
//                }
//            }
//        }
//    }
//
//    /**
//     * Invoked when the mouse enters a component.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void mouseEntered(MouseEvent e)
//    {
//        BoardView boardView = getInstance().getLegupUI().getBoardView();
//        ElementView element = boardView.getElement(e.getPoint());
//        ElementSelection selection = boardView.getSelection();
//        if(element != null)
//        {
//
//        }
//    }
//
//    /**
//     * Invoked when the mouse exits a component.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void mouseExited(MouseEvent e)
//    {
//        BoardView view = getInstance().getLegupUI().getBoardView();
//        ElementView element = view.getElement(e.getPoint());
//        if(element != null)
//        {
//        }
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e)
//    {
//
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e)
//    {
//        BoardView boardView = getInstance().getLegupUI().getBoardView();
//        ElementView element = boardView.getElement(e.getPoint());
//        ElementSelection selection = boardView.getSelection();
//    }
//
//    /**
//     * Invoked when an action occurs.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void actionPerformed(ActionEvent e)
//    {
//        BoardView boardView = getInstance().getLegupUI().getBoardView();
//        ElementView selectedElement = boardView.getSelection().getFirstSelection();
//        Element element = selectedElement.getElement();
//        int index = selectedElement.getIndex();
//
//        TreeView treeView = GameBoardFacade.getInstance().getLegupUI().getTreePanel().getTreeView();
//        TreeViewSelection selection = treeView.getSelection();
//        TreeElementView selectedView = selection.getFirstSelection();
//        TreeTransitionView transitionView = (TreeTransitionView)selectedView;
//
//        Board prevBord = transitionView.getTreeElement().getParents().get(0).getBoard();
//
//        int value = (Integer) ((SelectionItemView)e.getSource()).getData().getData();
//
//        element.setData(value);
//
//        if(element.equalsData(prevBord.getElementData(element)))
//            element.setModified(false);
//        else
//            element.setModified(true);
//
//        transitionView.getTreeElement().propagateChanges(element);
//
//        boardView.getSelection().clearSelection();
//    }
//
//    public void changeCell(MouseEvent e, Element data)
//    {
//        if(e.getButton() == MouseEvent.BUTTON1)
//        {
//            if(e.isControlDown() && this.boardView.getSelectionPopupMenu() != null)
//            {
//
//            }
//            else
//            {
//                data.setData(((Integer)data.getData() + 1) % 10);
//            }
//        }
//        else if(e.getButton() == MouseEvent.BUTTON3)
//        {
//            data.setData(((Integer)data.getData() + 9) % 10);
//        }
//    }
//
//    /**
//     * Invoked when a key has been typed.
//     * See the class description for {@link KeyEvent} for a definition of
//     * a key typed event.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void keyTyped(KeyEvent e)
//    {
//
//        System.err.println("Key pressed: " + e.getKeyCode());
//    }
//
//    /**
//     * Invoked when a key has been pressed.
//     * See the class description for {@link KeyEvent} for a definition of
//     * a key pressed event.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void keyPressed(KeyEvent e)
//    {
//
//        System.err.println("Key pressed: " + e.getKeyCode());
//    }
//
//    /**
//     * Invoked when a key has been released.
//     * See the class description for {@link KeyEvent} for a definition of
//     * a key released event.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void keyReleased(KeyEvent e)
//    {
//        Board board = getInstance().getBoard();
//        Tree tree = getInstance().getTree();
//        TreeView treeView = GameBoardFacade.getInstance().getLegupUI().getTreePanel().getTreeView();
//        BoardView boardView = getInstance().getLegupUI().getBoardView();
//        TreeViewSelection selection = treeView.getSelection();
//        TreeElementView selectedView = selection.getFirstSelection();
//        System.err.println("Key pressed: " + e.getKeyCode());
//        if(board instanceof CaseBoard)
//        {
//            if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
//            {
//                GameBoardFacade.getInstance().setBoard(selectedView.getTreeElement().getBoard());
//            }
//        }
//    }
}

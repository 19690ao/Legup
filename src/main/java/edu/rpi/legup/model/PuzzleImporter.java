package edu.rpi.legup.model;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.Element;

import edu.rpi.legup.model.rules.Rule;
import edu.rpi.legup.model.tree.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import edu.rpi.legup.save.InvalidFileFormatException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class PuzzleImporter
{
    protected Puzzle puzzle;

    /**
     * PuzzleImporter Constructor - creates the edu.rpi.legup.puzzle object
     */
    public PuzzleImporter(Puzzle puzzle)
    {
        this.puzzle = puzzle;
    }

    /**
     * Initializes the edu.rpi.legup.puzzle attributes
     *
     * @param node xml document node
     * @throws InvalidFileFormatException
     */
    public void initializePuzzle(Node node) throws InvalidFileFormatException
    {
        if(node.getNodeName().equalsIgnoreCase("edu/rpi/legup/puzzle"))
        {
            org.w3c.dom.Element puzzleElement = (org.w3c.dom.Element)node;

            boolean initBoard = false;
            boolean initProof = false;
            NodeList childNodes = puzzleElement.getChildNodes();
            for(int i = 0; i < childNodes.getLength(); i++)
            {
                Node n = childNodes.item(i);
                if(n.getNodeName().equalsIgnoreCase("board"))
                {
                    if(initBoard)
                    {
                        throw new InvalidFileFormatException("Puzzle creation error: duplicate board element found");
                    }
                    initializeBoard(n);
                    initBoard = true;
                }
                else if(n.getNodeName().equalsIgnoreCase("proof"))
                {
                    if(initProof)
                    {
                        throw new InvalidFileFormatException("Puzzle creation error: duplicate proof element found");
                    }
                    if(!initBoard)
                    {
                        throw new InvalidFileFormatException("Puzzle creation error: could not find board element");
                    }
                    initializeProof(n);
                    initProof = true;
                }
                else
                {
                    if(!n.getNodeName().equalsIgnoreCase("#text"))
                    {
                        throw new InvalidFileFormatException("Puzzle creation error: unknown node found in file");
                    }
                }
            }

            if(!initBoard)
            {
                throw new InvalidFileFormatException("Puzzle creation error: could not find board element");
            }
            if(!initProof)
            {
                createDefaultTree();
            }
        }
        else
        {
            throw new InvalidFileFormatException("Invalid file format; does not contain \"edu.rpi.legup.puzzle\" node");
        }
    }

    /**
     * Creates the board for building
     *
     * @param node xml document node
     * @throws InvalidFileFormatException
     */
    public abstract void initializeBoard(Node node) throws InvalidFileFormatException;

    /**
     * Creates the proof for building
     *
     * @param node xml document node
     * @throws InvalidFileFormatException
     */
    public void initializeProof(Node node) throws InvalidFileFormatException
    {
        if(node.getNodeName().equalsIgnoreCase("proof"))
        {
            org.w3c.dom.Element proofElement = (org.w3c.dom.Element)node;
            NodeList treeList = proofElement.getElementsByTagName("tree");

            boolean initTree = false;
            for(int i = 0; i < treeList.getLength(); i++)
            {
                Node n = treeList.item(i);
                if(n.getNodeName().equalsIgnoreCase("tree"))
                {
                    if(initTree)
                    {
                        throw new InvalidFileFormatException("Proof Tree construction error: duplicate tree element");
                    }
                    createTree(n);
                    initTree = true;
                }
                else
                {
                    throw new InvalidFileFormatException("Proof Tree construction error: unknown element found");
                }
            }
            if(!initTree)
            {
                createDefaultTree();
            }
        }
        else
        {
            throw new InvalidFileFormatException("Invalid file format; does not contain \"proof\" node");
        }
    }

    /**
     * Sets an attribute from the xml document nodes
     *
     * @param node xml document node
     * @throws InvalidFileFormatException
     */
    public void setBoardAttribute(Node node) throws InvalidFileFormatException
    {
        if(node.getNodeName().equals("cells"))
        {
            setCells(node);
        }
        else
        {
            throw new InvalidFileFormatException("Unknown node in xml file");
        }
    }

    /**
     * Sets an attribute from the xml document nodes
     *
     * @param node xml document node
     * @throws InvalidFileFormatException
     */
    public void setProofAttribute(Node node) throws InvalidFileFormatException
    {
        if(node.getNodeName().equals("tree"))
        {
            createTree(node);
        }
        else
        {
            throw new InvalidFileFormatException("Unknown node in xml file");
        }
    }

    /**
     * Sets the element from the xml document node
     *
     * @param node xml document node
     */
    protected void setCells(Node node) throws InvalidFileFormatException
    {
        NodeList dataList = ((org.w3c.dom.Element) node).getElementsByTagName("cell");
        Board board = puzzle.getCurrentBoard();
        for(int i = 0; i < dataList.getLength(); i++)
        {
            Element data = puzzle.getFactory().importCell(dataList.item(i), puzzle.getCurrentBoard());
            board.setElementData(data.getIndex(), data);
        }
    }

    /**
     * Creates the tree for the edu.rpi.legup.puzzle
     *
     * @param node
     * @throws InvalidFileFormatException
     */
    protected void createTree(Node node) throws InvalidFileFormatException
    {
        org.w3c.dom.Element treeElement = (org.w3c.dom.Element)node;

        Tree tree = new Tree();
        puzzle.setTree(tree);

        NodeList nodeList = ((org.w3c.dom.Element)node).getElementsByTagName("node");

        HashMap<String, TreeNode> treeNodes = new HashMap<>();
        HashMap<String, TreeTransition> treeTransitions = new HashMap<>();
        HashMap<TreeTransition, Node> nodeChanges = new HashMap<>();

        for(int i = 0; i < nodeList.getLength(); i++)
        {
            org.w3c.dom.Element treeNodeElement = (org.w3c.dom.Element) nodeList.item(i);
            String nodeId = treeNodeElement.getAttribute("id");
            String isRoot = treeNodeElement.getAttribute("root");
            if(nodeId.isEmpty())
            {
                throw new InvalidFileFormatException("Proof Tree construction error: cannot find node id");
            }
            if(treeNodes.containsKey(nodeId))
            {
                throw new InvalidFileFormatException("Proof Tree construction error: duplicate tree node id found");
            }
            TreeNode treeNode = new TreeNode(puzzle.getCurrentBoard().copy());
            if(isRoot.equalsIgnoreCase("true"))
            {
                if(tree.getRootNode() != null)
                {
                    throw new InvalidFileFormatException("Proof Tree construction error: multiple root nodes declared");
                }
                treeNode.setRoot(true);
                tree.setRootNode(treeNode);
            }
            treeNodes.put(nodeId, treeNode);
        }


        for(int i = 0; i < nodeList.getLength(); i++)
        {
            org.w3c.dom.Element treeNodeElement = (org.w3c.dom.Element) nodeList.item(i);
            String nodeId = treeNodeElement.getAttribute("id");
            TreeNode treeNode = treeNodes.get(nodeId);

            NodeList transList = treeNodeElement.getElementsByTagName("transition");
            for(int k = 0; k < transList.getLength(); k++)
            {
                org.w3c.dom.Element trans = (org.w3c.dom.Element)transList.item(k);
                String transId = trans.getAttribute("id");
//                if(treeTransitions.containsKey(transId))
//                {
//                    throw new InvalidFileFormatException("Proof Tree construction error: duplicate transition id found");
//                }

                String childId = trans.getAttribute("child");
                String ruleName = trans.getAttribute("rule");

                TreeNode child = treeNodes.get(childId);

                TreeTransition transition = new TreeTransition(treeNode, treeNode.getBoard().copy());

                Rule rule;
                if(!ruleName.isEmpty())
                {
                    rule = puzzle.getRuleByName(ruleName);
                    if(rule == null)
                    {
                        throw new InvalidFileFormatException("Proof Tree construction error: could not find rule by name");
                    }
                    transition.setRule(rule);
                }

                treeNode.addChild(transition);
                if(child != null)
                {
                    child.setParent(transition);
                    transition.setChildNode(child);
                }

                nodeChanges.put(transition, trans);
                treeTransitions.put(transId, transition);
            }
        }

        //validateTreeStructure(treeNodes, treeTransitions);
        System.err.println("Tree Size: " + treeTransitions.size());
        for(Map.Entry<TreeTransition, Node> entry : nodeChanges.entrySet())
        {
            makeTransitionChanges(entry.getKey(), entry.getValue());
        }
    }

    protected void validateTreeStructure(HashMap<String, TreeNode> nodes, HashMap<String, TreeTransition> transitions) throws InvalidFileFormatException
    {
        Tree tree = puzzle.getTree();

        if(tree == null)
        {
            throw new InvalidFileFormatException("Proof Tree construction error: invalid tree");
        }

        HashMap<TreeNode, Boolean> connectedNodes = new HashMap<>();
        HashMap<TreeTransition, Boolean> connectedTransitions = new HashMap<>();

        for(TreeNode node : nodes.values())
        {
            connectedNodes.put(node, false);
        }

        for(TreeTransition trans : transitions.values())
        {
            connectedTransitions.put(trans, false);
        }

        ArrayList<TreeElement> treeElements = new ArrayList<>();
        treeElements.add(tree.getRootNode());
        while(!treeElements.isEmpty())
        {
            TreeElement element = treeElements.get(treeElements.size() - 1);
            treeElements.remove(element);
            if(element.getType() == TreeElementType.NODE)
            {
                TreeNode treeNode = (TreeNode)element;

                if(connectedNodes.get(treeNode))
                {
//                    for(TreeTransition trans : treeNode.getParents())
//                    {
//                        if(!(trans.getRule() instanceof MergeRule))
//                        {
//                            throw new InvalidFileFormatException("Proof Tree structure validation error: cyclic tree detected");
//                        }
//                    }
                }
                connectedNodes.replace(treeNode, true);

                for(TreeTransition trans : treeNode.getChildren())
                {
                    treeElements.add(trans);
                }
            }
            else
            {
                TreeTransition treeTransition = (TreeTransition)element;

                if(connectedTransitions.get(treeTransition))
                {
                    throw new InvalidFileFormatException("Proof Tree structure validation error: cyclic tree detected");
                }
                connectedTransitions.replace(treeTransition, true);

                if(treeTransition.getChildNode() != null)
                {
                    treeElements.add(treeTransition.getChildNode());
                }
            }
        }

        for(TreeNode node : nodes.values())
        {
            if(!connectedNodes.get(node))
            {
                throw new InvalidFileFormatException("Proof Tree structure validation error: disjoint node detected");
            }
        }

        for(TreeTransition trans : transitions.values())
        {
            if(!connectedTransitions.get(trans))
            {
                throw new InvalidFileFormatException("Proof Tree structure validation error: disjoint transition detected");
            }
        }
    }

    protected void makeTransitionChanges(TreeTransition transition, Node transElement) throws InvalidFileFormatException
    {
        NodeList cellList = transElement.getChildNodes();
        for(int i = 0; i < cellList.getLength(); i++)
        {
            Node node = cellList.item(i);
            if(node.getNodeName().equalsIgnoreCase("cell"))
            {
                Board board = transition.getBoard();
                Element cell = puzzle.getFactory().importCell(node, board);

                board.setElementData(cell.getIndex(), cell);
                board.addModifiedData(cell);
                transition.propagateChanges(cell);
            }
            else
            {
                if(!node.getNodeName().equalsIgnoreCase("#text"))
                {
                    throw new InvalidFileFormatException("Proof Tree construction error: unknown node in transition");
                }
            }
        }
    }

    protected void createDefaultTree()
    {
        TreeNode root = new TreeNode(puzzle.getCurrentBoard());
        root.setRoot(true);
        Tree tree = new Tree();
        tree.setRootNode(root);
        puzzle.setTree(tree);
    }

    /**
     * Gets the result of building the Puzzle
     *
     * @return edu.rpi.legup.puzzle
     */
    public Puzzle getPuzzle()
    {
        return puzzle;
    }
}

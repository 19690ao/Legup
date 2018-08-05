package edu.rpi.legup.puzzle.battleship;

import edu.rpi.legup.model.Puzzle;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.ui.boardview.ElementView;

import java.awt.*;

public class BattleShip extends Puzzle
{
    public BattleShip()
    {
        super();

        this.name = "BattleShip";

        this.importer = null;
        this.exporter = null;

        this.factory = null;

        // ADD RULES ONCE THEY ARE CREATED
    }

    @Override
    public void initializeView()
    {
        boardView = new BattleShipView((BattleShipBoard) currentBoard);
    }

    @Override
    public Board generatePuzzle(int difficulty)
    {
        return null;
    }

    /**
     * Determines if the current board is a valid state
     *
     * @param board board to check for validity
     *
     * @return true if board is valid, false otherwise
     */
    @Override
    public boolean isBoardComplete(Board board)
    {
        return false;
    }

    @Override
    public void onBoardChange(Board board)
    {

    }
//
//    @Override
//    public void onTreeSelectionChange(ArrayList<Selection> newSelection)
//    {
//
//    }

    @Override
    public void importPuzzle(String fileName)
    {
//        if(fileName != null)
//        {
//            InputStream inputStream = new FileInputStream(fileName);
//            DocumentBuilder builder = null;//factory.newDocumentBuilder();
//            Document document = builder.parse(inputStream);
//
//            BattleShipBoard battleShipBoard;
//
//            Element rootNode = document.getDocumentElement();
//            Element puzzleElement = (Element) rootNode.getElementsByTagName("edu.rpi.legup.puzzle").item(0);
//            Element boardElement = (Element) puzzleElement.getElementsByTagName("board").item(0);
//            Element axesElement = (Element) boardElement.getElementsByTagName("axes").item(0);
//            Element shipElement = (Element) boardElement.getElementsByTagName("ships").item(0);
//            Element cellElement = (Element) boardElement.getElementsByTagName("cells").item(0);
//            Element rightElement = (Element) axesElement.getElementsByTagName("right").item(0);
//            Element bottomElement = (Element) axesElement.getElementsByTagName("bottom").item(0);
//            NodeList rightClueList = rightElement.getElementsByTagName("clue");
//            NodeList bottomClueList = bottomElement.getElementsByTagName("clue");
//            NodeList shipList = shipElement.getElementsByTagName("ship");
//            NodeList cells = cellElement.getElementsByTagName("cell");
//
//            int size = Integer.valueOf(boardElement.getAttribute("size"));
//            battleShipBoard = new BattleShipBoard(size);
//
//            ArrayList<Element> battleShipData = new ArrayList<>();
//            for(int i = 0; i < size * size; i++)
//            {
//                battleShipData.add(null);
//            }
//
//            for (int i = 0; i < rightClueList.getLength(); i++) {
//                battleShipBoard.getRight()[i] = Integer.valueOf(rightClueList.item(i).getAttributes().getNamedItem("value").getNodeValue());
//            }
//
//            for (int i = 0; i < bottomClueList.getLength(); i++) {
//                battleShipBoard.getBottom()[i] = Integer.valueOf(bottomClueList.item(i).getAttributes().getNamedItem("value").getNodeValue());
//            }
//
//            for (int i = 0; i < shipList.getLength(); i++) {
//                int length = Integer.valueOf(shipList.item(i).getAttributes().getNamedItem("length").getNodeValue());
//                int count = Integer.valueOf(shipList.item(i).getAttributes().getNamedItem("count").getNodeValue());
//                battleShipBoard.getShips().add(new Ship(length, count));
//            }
//
//            for (int i = 0; i < cells.getLength(); i++) {
//                int x = Integer.valueOf(cells.item(i).getAttributes().getNamedItem("x").getNodeValue());
//                int y = Integer.valueOf(cells.item(i).getAttributes().getNamedItem("y").getNodeValue());
//                String value = cells.item(i).getAttributes().getNamedItem("value").getNodeValue().toUpperCase();
//
//                BattleShipCell cell = new BattleShipCell(BattleShipCellType.valueOf(value).ordinal(), new Point(x, y));
//                battleShipBoard.setCell(x, y, cell);
//                cell.setModifiable(false);
//                cell.setGiven(true);
//            }
//
//            for (int x = 0; x < size; x++) {
//                for (int y = 0; y < size; y++) {
//                    if (battleShipBoard.getCell(x, y) == null) {
//                        BattleShipCell cell = new BattleShipCell(9, new Point(x, y));
//                        cell.setModifiable(true);
//                        battleShipBoard.setCell(x, y, cell);
//                    }
//                }
//            }
//            this.currentBoard = battleShipBoard;
//            this.tree = new Tree(currentBoard);
//        }
    }
}
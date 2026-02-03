package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Matrix {

    private int matrixSize;
    private final String wall = "#";
    private final String telephone = "T";
    private String[][] board;
    private final String empty = " ";
    private String lastBoardSnapshot = "";
    private boolean playerStarted = false;

    public Matrix(int newMatrixSize, int numAgents, int numTelephones){
        this.matrixSize = newMatrixSize;
        this.board = new String[matrixSize][matrixSize];

        for(int i = 0; i < matrixSize; i++){
            for(int j = 0; j < matrixSize; j++){
                board[i][j] = empty;
            }
        }

        placeTelephones(numTelephones);
        placeWalls();
        placeAgents(numAgents);
        board[0][0] = "N";
        generateBoard();
    }

    public synchronized boolean moveEntity(int oldX, int oldY, int newX, int newY, String identifier){
        if(newX < 0 || newX >= matrixSize || newY < 0 || newY >= matrixSize){
            if(identifier.equals("N")) System.out.println("Move out of bounds: " + newX + "," + newY);
            return false;
        }

        if(!canMove(newX, newY, identifier)){
            if(identifier.equals("N")) System.out.println("Invalid move: cell blocked or occupied.");
            return false;
        }

        String targetBefore = board[newX][newY];

        board[oldX][oldY] = empty;
        board[newX][newY] = identifier;

        if(targetBefore.equals(telephone) && identifier.equals("N")){
            generateBoard();
            System.out.println("Neo found the telephone, You Win!!!");
            System.exit(0);
        }

        if(targetBefore.equals("N") && identifier.startsWith("A")){
            generateBoard();
            System.out.println("The agents catched Neo, You Lose :(");
            System.exit(0);
        }

        return true;
    }

    public boolean canMove(int newX, int newY, String identifier){
        if(newX >= 0 && newX < matrixSize && newY >= 0 && newY < matrixSize && identifier.equals("N")){
            return board[newX][newY].equals(empty) || board[newX][newY].equals(telephone);
        } else if(newX >= 0 && newX < matrixSize && newY >= 0 && newY < matrixSize && identifier.startsWith("A")){
            return board[newX][newY].equals(empty) || board[newX][newY].equals("N");
        }

        return false;
    }

    public synchronized int[] getNeoPosition(){
        for(int i = 0; i < matrixSize; i++){
            for(int j = 0; j < matrixSize; j++){
                if(board[i][j].equals("N")) return new int[]{i, j};
            }
        }

        return null;
    }

    public void placeWalls(){
        Random randomWalls = new Random();
        int wallsNum = randomWalls.nextInt(1, matrixSize);
        int placed = 0;
        while(placed < wallsNum){
            int rowRandomPos = randomWalls.nextInt(1, matrixSize);
            int colRandomPos = randomWalls.nextInt(1, matrixSize);

            if(board[rowRandomPos][colRandomPos].equals(empty)){
                board[rowRandomPos][colRandomPos] = wall; 
                placed++;
            }
        }
    }

    public void placeAgents(int numAgents){
        Random randomAgentsPos = new Random();

        for(int i = 0; i < numAgents; i++){
            int rowRandomPos = randomAgentsPos.nextInt(1, Math.max(2, matrixSize - 1));
            int colRandomPos = randomAgentsPos.nextInt(1, Math.max(2, matrixSize - 1));
            
            while(!board[rowRandomPos][colRandomPos].equals(empty)){
                rowRandomPos = randomAgentsPos.nextInt(1, Math.max(2, matrixSize - 1));
                colRandomPos = randomAgentsPos.nextInt(1, Math.max(2, matrixSize - 1));
            }

            board[rowRandomPos][colRandomPos] = "A";
        }
    }

    public synchronized List<int[]> getAgentPositions(){
        List<int[]> positions = new ArrayList<>();
        for(int i = 0; i < matrixSize; i++){
            for(int j = 0; j < matrixSize; j++){
                if(board[i][j].equals("A")){
                    positions.add(new int[]{i, j});
                }
            }
        }
        return positions;
    }

    public synchronized List<int[]> getTelephonePositions(){
        List<int[]> positions = new ArrayList<>();
        for(int i = 0; i < matrixSize; i++){
            for(int j = 0; j < matrixSize; j++){
                if(board[i][j].equals(telephone)){
                    positions.add(new int[]{i, j});
                }
            }
        }
        return positions;
    }

    public void placeTelephones(int numTelephones){
        Random randomTelephones = new Random();

        int placed = 0;

        while(placed < numTelephones){
            int rowRandomPos = randomTelephones.nextInt(2);
            int colRandomPos = randomTelephones.nextInt(2);

            int row = (rowRandomPos == 0) ? 0 : (matrixSize - 1);
            int col;
            if( row == 0 ){
                col = matrixSize - 1;
            } else {
                col = (colRandomPos == 0) ? 0 : (matrixSize - 1);
            }

            if(board[row][col].equals(empty)){
                board[row][col] = telephone;
                placed++;
            }
        }
    }

    public void generateBoard(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < matrixSize; i++){
            sb.append(("+" + "-".repeat(3)).repeat(matrixSize)).append("+\n");
            for(int j = 0; j < matrixSize; j++){
                sb.append("| ").append(board[i][j]).append(empty);
            }
            sb.append("|\n");
        }
        sb.append(("+" + "-".repeat(3)).repeat(matrixSize)).append("+\n");

        String snapshot = sb.toString();
        if(!snapshot.equals(lastBoardSnapshot)){
            System.out.print(snapshot);
            lastBoardSnapshot = snapshot;
        }
    }

    public void setPlayerStarted(boolean started){
        this.playerStarted = started;
    }

    public boolean isPlayerStarted(){
        return this.playerStarted;
    }

    public int getMatrixSize(){
        return matrixSize;
    }
}

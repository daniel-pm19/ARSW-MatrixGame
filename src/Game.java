package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {
    
    public static void main (String [] args){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("// Matrix Game \\");

        System.out.print("Enter matrix size: ");
        int matrixSize = sc.nextInt();

        while(matrixSize <= 4){
            System.out.print("Invalid matrix size, try with a bigger one: ");
            matrixSize = sc.nextInt();
        }

        System.out.print("Enter quantity of Agents (1 - matrix size): ");

        int agents = sc.nextInt();

        while (agents >= matrixSize || agents < 1){
            System.out.print("Agents quantity is unavailable, try with another quantity: ");
            agents = sc.nextInt();
        }

        System.out.print("Enter a quantity of telephones: ");

        int telephones = sc.nextInt();

        while(telephones > 3 || telephones < 1 ){
            System.out.print("Invalid telephones quantity, try again: ");
            telephones = sc.nextInt();
        }

        Matrix matrix = new Matrix(matrixSize, agents, telephones);

        Neo neo = new Neo(matrix, 0, 0);

        List<Agent> agentsList = new ArrayList<>();
        var agentPositions = matrix.getAgentPositions();
        for(int i = 0; i < agentPositions.size(); i++){
            int[] pos = agentPositions.get(i);
            Agent agent = new Agent(matrix, pos[0], pos[1], i);
            agentsList.add(agent);
        }

        Thread renderer = new Thread(() -> {
            while (true) {
                try{
                    matrix.generateBoard();
                    Thread.sleep(400);
                } catch(InterruptedException e){
                    break;
                }
            }
        });

        System.out.println("MATRIX SYSTEM STARTING...");
        try { Thread.sleep(2000); } catch (Exception e) {}

        renderer.start();
        neo.start();
        for(Agent agent: agentsList) agent.start();
        sc.close();
    }
}

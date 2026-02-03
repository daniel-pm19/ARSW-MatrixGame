package src;

import java.util.List;
import java.util.Random;

public class Neo extends Thread {

    private Matrix matrix;
    private int x, y;
    private final String neoIdentifier = "N";
    private final Random random = new Random();

    public Neo(Matrix matrix, int startX, int startY){
        this.matrix = matrix;
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void run(){
        while(!isInterrupted()){
            List<int[]> telephones = matrix.getTelephonePositions();
            int targetX = -1, targetY = -1;
            if(!telephones.isEmpty()){
                int bestDist = Integer.MAX_VALUE;
                for(int[] t: telephones){
                    int dist = Math.abs(t[0] - x) + Math.abs(t[1] - y);
                    if(dist < bestDist){
                        bestDist = dist;
                        targetX = t[0];
                        targetY = t[1];
                    }
                }
            }

            int[] newCoords;
            if(targetX >= 0){
                newCoords = nextMovementTowards(targetX, targetY);
            } else {
                newCoords = randomMove();
            }

            int newX = newCoords[0];
            int newY = newCoords[1];

            boolean moved = matrix.moveEntity(x, y, newX, newY, neoIdentifier);

            if(moved){
                this.x = newX;
                this.y = newY;
                matrix.setPlayerStarted(true);
            }

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }

    private int[] nextMovementTowards(int tx, int ty){
        int nextX = x;
        int nextY = y;

        int diffX = tx - x;
        int diffY = ty - y;

        if(Math.abs(diffX) > Math.abs(diffY)){
            nextX += Integer.compare(diffX, 0);
        } else if(Math.abs(diffY) > 0){
            nextY += Integer.compare(diffY, 0);
        }

        return new int[]{nextX, nextY};
    }

    private int[] randomMove(){
        int[] dirs = new int[]{-1,0,1};
        int nx = x;
        int ny = y;
        int attempts = 0;
        while(attempts < 10){
            int dx = dirs[random.nextInt(3)];
            int dy = dirs[random.nextInt(3)];
            if(dx == 0 && dy == 0){ attempts++; continue; }
            nx = x + dx;
            ny = y + dy;
            if(nx >= 0 && nx < matrix.getMatrixSize() && ny >= 0 && ny < matrix.getMatrixSize()){
                return new int[]{nx, ny};
            }
            attempts++;
        }
        return new int[]{x, y};
    }

}

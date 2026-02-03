package src;

public class Agent extends Thread{
    
    private Matrix matrix;
    private int x, y;
    private final String agentIdentifier;

    public Agent(Matrix matrix, int startX, int startY, int id){
        this.matrix = matrix;
        this.x = startX;
        this.y = startY;
        int max = matrix.getMatrixSize(); 
        this.x = Math.max(0, Math.min(startX, max-1));
        this.y = Math.max(0, Math.min(startY, max-1));
        this.agentIdentifier = "A";
    }

    @Override
    public void run(){
        try {
            while (!matrix.isPlayerStarted()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            interrupt();
            return;
        }

        while(!isInterrupted()){
            int[] neoPos = matrix.getNeoPosition();

            int[] newCoords = nextPosition(neoPos[0], neoPos[1]);

            boolean moved = matrix.moveEntity(x, y, newCoords[0], newCoords[1], agentIdentifier);

            if(moved){
                this.x = newCoords[0];
                this.y = newCoords[1];
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }

        } 
    }

    private int[] nextPosition(int neoPosX, int neoPosY){
        int nextX = x;
        int nextY = y;

        int diffX = neoPosX - x;
        int diffY = neoPosY - y;

        if(Math.abs(diffX) > Math.abs(diffY)){
            nextX += Integer.compare(diffX, 0);
        } else {
            nextY += Integer.compare(diffY, 0);
        }

        return new int[]{nextX, nextY};
    }

}

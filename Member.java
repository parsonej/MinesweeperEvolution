import java.util.ArrayList;
import java.util.Random;

public class Member {
    public char[][] solution;
    public int size;
    public Member(int s){
        this.size = s;
        this.solution = randomSolution();
    }
    public Member(char[][] sol, int s){
        this.solution = sol;
        this.size = s;
    }

    private char[][] randomSolution() {
        double randomNumber;
        char[][] sol = new char[this.size][this.size];
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                randomNumber = Math.random(); 
                if(randomNumber > 0.5){
                    sol[i][j] = 'x';
                } else{
                    sol[i][j] = 'o';
                }
            }
        }
        return sol;
    }
    @Override
    public String toString(){
        String boardString = "-";
        for(int k = 0; k < this.size; k++){
            boardString += "----";
        }
        boardString += "\n";
        for(int i = 0; i < this.size; i++){
            String rowString = "| ";
            for(int j = 0; j < this.size; j++){
                rowString += this.solution[i][j] + " | ";
            }
            rowString += "\n-";
            for(int k = 0; k < this.size; k++){
                rowString += "----";
            }
            rowString += "\n";
            boardString += rowString;
        }
        return boardString;
    }

    public int calculateFitness(Member target){
        int fitnessScore = 0;
        int correctHits = 0;
        int bombHits = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if(this.solution[i][j] == target.solution[i][j]){
                    correctHits += 1;
                }
                else if(this.solution[i][j] == 'o' && target.solution[i][j] == 'x'){
                    bombHits += 1;
                } else{
                    fitnessScore -= 1;
                }
            }
        }
        int tooManyBombPen = (target.getNumBombs() - this.getNumBombs());
        if(tooManyBombPen > 0){
            tooManyBombPen = 0;
        }
        fitnessScore = fitnessScore + (10*correctHits) - (15*bombHits) + (8*tooManyBombPen);
        return fitnessScore;
    }
    private int getNumBombs(){
        int bombCount = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if(this.solution[i][j] == 'x'){
                    bombCount++;
                }
            }
        }
        return bombCount;
    }
    public Member mutate() {
        Random rand = new Random();
		Member copy = new Member(deepCopySolution(), this.size);
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
                if (rand.nextDouble() < 0.03) {
                    if(copy.solution[i][j] == 'x'){
                        copy.solution[i][j] = 'o';
                    } else{
                        copy.solution[i][j] = 'x';
                    }
                }
            }
		}

		return copy;
	}
    public ArrayList<Member> crossover(Member m){
        Random rand = new Random();
        ArrayList<Member> children = new ArrayList<>();
        Member child1 = new Member(deepCopySolution(), this.size);
        Member child2 = new Member(m.deepCopySolution(), m.size);
        double swapType = rand.nextDouble();
        //Row Swap
        if(swapType <= 0.4){
            int rowToSwap = rand.nextInt(this.size);
            // System.out.println("Swapping row " + rowToSwap);
            char[] colM1 = child1.solution[rowToSwap];
            child1.solution[rowToSwap] = child2.solution[rowToSwap];
            child2.solution[rowToSwap] = colM1;
        } else{
            //Column Swap
            int colToSwap = rand.nextInt(this.size);
            // System.out.println("Swapping column " + colToSwap);
            for(int i = 0; i < this.size; i++){
                char temp = child1.solution[i][colToSwap];
                child1.solution[i][colToSwap] = child2.solution[i][colToSwap];
                child2.solution[i][colToSwap] = temp;
            }
        }
        children.add(child1);
        children.add(child2);
        return children;
    }
    public char[][] deepCopySolution(){
        char[][] solCopy = new char[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                solCopy[i][j] = this.solution[i][j];
            }
        }
        return solCopy;
    }
}

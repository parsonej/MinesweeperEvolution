





public class Main {
    public static void main(String[] args) {
        //Create a target with diagonal bombs starting at (0,0)
        // char[][] sol = new char[5][5];
        // for (int i = 0; i < 5; i++) {
        //     for (int j = 0; j < 5; j++) {
        //         if(i == j){
        //             sol[i][j] = 'x';
        //         } else{
        //             sol[i][j] = 'o';
        //         }
        //     }
        // }
        //Create Target with diagonal bombs starting at (0, maxSize)
        // char[][] sol = new char[10][10];
        // int rowNum = 0;
        // int colNum = 9;
        // for (int i = 0; i < 10; i++) {
        //     for (int j = 0; j < 10; j++) {
        //         if(i == rowNum && j == colNum){
        //             sol[i][j] = 'x';
        //         } else{
        //             sol[i][j] = 'o';
        //         }
        //     }
        //     colNum--;
        //     rowNum++;
        // }
        char[][] sol = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(j % 2 == 0 &&  i != 0){
                    sol[i][j] = 'x';
                    sol[i-1][j] = 'x';
                } else{
                    if(j % 2 == 1 && i % 2 == 1){
                        sol[i][j] ='x';
                    }else{
                        sol[i][j] = 'o';
                    }
                    
                }
            }
        }
        // Member target = new Member(sol, sol.length);
        // char[][] sol = new char[10][10];
        // for (int i = 0; i < 10; i++) {
        //     for (int j = 0; j < 10; j++) {
        //         sol[i][j] = 'x';
        //     }
        // }
        Member target = new Member(sol, 10);
        System.out.print("\nSolution: \n" + target);

        MinesweeperEvolution App = new MinesweeperEvolution(target, 100);
        int targetFitness = target.calculateFitness(target);
        System.out.println("Best Fitness: " + targetFitness);
        App.runEvolution(targetFitness);
        // Member mutant = target.mutate();
        // for(int j = 0; j < 10; j++){
        //     mutant = mutant.mutate();
        // }
        // System.out.println("Mutant Parent: \n" + mutant);
        // ArrayList<Member> children = target.crossover(mutant);
        // System.out.println("Child 1: \n" + children.get(0));
        // System.out.println("Child 2: \n" + children.get(1));
    }
}

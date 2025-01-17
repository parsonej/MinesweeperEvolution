
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MinesweeperEvolution {
    private ArrayList<Member> population;
    private Member target;
    private int popSize;
    private int numElites;
    public MinesweeperEvolution(Member t, int s){
        this.target = t;
        this.popSize = s;
        this.numElites = 5;
        // this.population = createPopulation();
    }

    public void runEvolution(int targetFitness){
        Random rand = new Random();
        boolean solutionFound = false;
        int generations = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
            writer.write("time,max,avg,min");
            writer.newLine();
            createPopulation(this.popSize);
            while(solutionFound == false){
                generations++;
                // if(generations > 5000){
                //     System.out.println("Generation: " + generations + "\n" + "Best Member: \n"+ "Fitness: " + this.population.get(0).calculateFitness(target) +"\n" + this.population.get(0));
                //     break;
                // }
                sortPopulation(this.target);
                System.out.println("Population Size: " + this.population.size());
                System.out.println("Generation: " + generations + "\n" + "Best Member: \n"+ "Fitness: " + this.population.get(0).calculateFitness(target));
                if(generations % 10 == 0){
                    writer.write(String.format("%d,%d,%.2f,%d", generations,getMaxFit(),getAvgFit(),getMinFit()));
                    writer.newLine();
                    
                }
                // System.out.println("Generation: " + generations + "\n" + "Best Member: \n"+ "Fitness: " + this.population.get(0).calculateFitness(target) +"\n" + this.population.get(0));
                for (Member m : this.population) {
                    if(m.calculateFitness(this.target) == targetFitness){
                        solutionFound = true;
                        System.out.println("Solution Found in Generation " + generations + ": \n"+ m);
                        break;
                    }
                }
                this.population = this.selectTopHalf();
                sortPopulation(this.target);
                //Select new Population
                ArrayList<Member> newGen = new ArrayList<>();;
                for(int n = 0; n < this.numElites; n++){
                    newGen.add(this.population.get(n));
                    newGen.add(this.population.get(n).mutate());
                }
                while(newGen.size() < this.popSize){
                    int randMember = (int)Math.floor(rand.nextDouble(this.population.size()));
                    int randMember2 = (int)Math.floor(rand.nextDouble(this.population.size()));
                    ArrayList<Member> children = population.get(randMember).crossover(population.get(randMember2));
                    if(this.popSize - newGen.size() == 1){
                        newGen.add(children.get(0).mutate());
                    } else{
                        newGen.add(children.get(0).mutate());
                        newGen.add(children.get(1).mutate());
                    }
                }
                this.population = newGen;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    //Assumes population is sorted
    public int getMaxFit(){
        return this.population.get(0).calculateFitness(this.target);
    }
    public int getMinFit(){
        return this.population.get(popSize-1).calculateFitness(this.target);
    }
    public double getAvgFit(){
        int totalFit = 0;
        for(Member m : this.population){
            totalFit += m.calculateFitness(this.target);
        }
        double result = (1.0*totalFit)/(1.0*this.popSize);
        return result;
    }
    public ArrayList<Member> createPopulation(int popSize) {
        ArrayList<Member> population = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            Member m = new Member(10);
            population.add(m);
        }
        this.population = population;
        return population;
    }
    public void createMemberUntilMaxFit(){
        boolean foundMax = false;
        int numCreated = 0;
        while (foundMax == false){
            Member m = new Member(10);
            numCreated++;
            if(m.calculateFitness(this.target) == target.calculateFitness(this.target)){
                System.out.println("Member " + numCreated + ": \n" +"Max Fitness: " + m.calculateFitness(this.target) + "\n" +  m);
                foundMax = true;
            } 
        }
    }
    public void sortPopulation(Member target) {
		this.population.sort(new Comparator<Member>() {
            @Override
            public int compare(Member o1, Member o2) {
                // Compare individuals based on their fitness values
                return Integer.compare(o2.calculateFitness(target), o1.calculateFitness(target)); // Descending order
            }
        });
	}
    public ArrayList<Member> selectTopHalf(){
        this.sortPopulation(this.target);
        int halfSize = population.size() / 2;
        ArrayList<Member> topHalf = new ArrayList<>(population.subList(0, halfSize));
        return topHalf;

    }
    public void printPopulation(){
        int count = 1;
        for(Member m : this.population){
            System.out.println("Member " + count + ": \n" +"Fitness: " + m.calculateFitness(this.target) + "\n");
            System.out.println(m);
            count++;
        }
    }

}

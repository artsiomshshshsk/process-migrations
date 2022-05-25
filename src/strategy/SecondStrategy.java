package strategy;
import utils.CPU;
import utils.Process;
import java.util.*;

public class SecondStrategy implements Strategy{
    private String name ="Second Strategy";
    @Override
    public void handle(double p, double r, int z,ArrayList<Process> processes, ArrayList<CPU> cpus) {
        for(Process process: processes){
            if(process.getCameTo().getWorkload() < p){
                process.stay();
            }else{
                List<CPU> toTry = new ArrayList<>(cpus);
                toTry.remove(process.getCameTo());
                while(toTry.size() != 0){
                    CPU cpu = toTry.remove(rand.nextInt(toTry.size()));
                    cpu.increaseAsks();
                    if(cpu.getWorkload() < p){
                        process.migrateTo(cpu);
                        break;
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }
}

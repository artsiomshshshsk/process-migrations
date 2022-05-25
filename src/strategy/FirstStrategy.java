package strategy;

import utils.CPU;
import utils.Process;

import java.util.ArrayList;
import java.util.List;

public class FirstStrategy implements Strategy{
    private String name ="First Strategy";
    @Override
    public void handle(double p, double r, int z, ArrayList<Process> processes, ArrayList<CPU> cpus) {
        for(Process process: processes){
            List<CPU> toTry = new ArrayList<>(cpus);
            toTry.remove(process.getCameTo());
            int tries = 0;
            while(toTry.size() != 0 && tries < z ){
                CPU cpu = toTry.remove(rand.nextInt(toTry.size()));
                cpu.increaseAsks();
                if(cpu.getWorkload() < p){
                    process.migrateTo(cpu);
                    break;  // cpu is found++;
                }
                tries++;
            }
            if(!process.isRunning()) process.stay();
        }
    }

    public String getName() {
        return name;
    }
}

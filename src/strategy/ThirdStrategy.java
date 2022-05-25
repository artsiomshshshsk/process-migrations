package strategy;

import utils.CPU;
import utils.Process;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;
import java.util.stream.Collectors;

public class ThirdStrategy extends SecondStrategy{
    private String name = "Third Strategy";

    public void handle(double p, double r, int z, ArrayList<Process> processes, ArrayList<CPU> cpus) {
        super.handle(p,r,z,processes,cpus);

        ArrayList<CPU> looseCPUs = (ArrayList<CPU>) cpus.stream()
                .filter(cpu -> cpu.getWorkload() < r).toList();

        for(int i = 0; i < looseCPUs.size();i++){
            CPU cpu = looseCPUs.get(0);
            List<CPU> toTry = new ArrayList<>(cpus);
            toTry.remove(cpu);
            for(CPU toTryCPU:toTry){
                cpu.increaseAsks();
                if(toTryCPU.getWorkload() > p) {
                    int amountToMigrate =  toTryCPU.getProcesses().size() /2;
                    for(int migrated = 0; i < amountToMigrate;i++){
                        Process process = toTryCPU.getProcesses().remove(0);
                        process.migrateTo(cpu);
                    }
                }
            }
        }

    }

    @Override
    public String getName() {
        return name;
    }
}

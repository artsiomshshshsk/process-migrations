package distributed.system;
import strategy.FirstStrategy;
import strategy.Strategy;
import strategy.ThirdStrategy;
import utils.CPU;
import utils.Generator;
import utils.Process;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class DistributedSystem {
    Random rand = new Random();
    double p;
    double r;
    int z;
    int N;
    int time;
    ArrayList<Process> processes;
    ArrayList<CPU> cpus;
    Strategy strategy;
    ArrayList<Double> workloads = new ArrayList<>();

    public DistributedSystem(double p, double r, int z, int n, int m, Strategy strategy) {
        this.p = p;
        this.r = r;
        this.z = z;
        this.N = n;
        Generator generator = new Generator();
        processes = generator.generate(n,m);
        cpus = generator.getCpus();
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void handle(ArrayList<Process> newProcesses){
        strategy.handle(p,r,z,newProcesses,cpus);
    }

    private ArrayList<Process> newProcesses(ArrayList<Process> pr,int time){
        ArrayList<Process> newProcesses = (ArrayList<Process>) pr.stream()
                .filter(e -> e.getAppearanceTime() <= time)
                .filter(e -> !e.isRunning())
                .collect(Collectors.toList());
        pr.removeAll(newProcesses);
        return newProcesses;
    }

    private void executeProcesses() {
        processes.stream()
                .filter(Process::isRunning)
                .forEach(Process::reduceTimeLeft);
        processes.stream()
                .filter(p -> p.getTimeLeft() == 0)
                .forEach(Process::finished);
    }

    private void addAvgWorkload(){
        double avg = cpus.stream().mapToDouble(CPU::getWorkload).sum();
        workloads.add(avg/cpus.size());
    }

    private double getAvgWorkload(){
        double avg = workloads.stream()
                .reduce((double) 0, Double::sum);
        return avg/workloads.size();
    }

    private void stats(){
        System.out.println("_____________________________________________");
        System.out.println(strategy.getName());
        System.out.println("Migrations:"+CPU.migrations);
        System.out.println("Asks about workload:"+CPU.asksAboutWorkload);
        System.out.println("Average workload:" +  String.format("%.1f",getAvgWorkload() * 100) + "%");
        System.out.println("_____________________________________________");
    }

    private void reset(){
        cpus.forEach(CPU::reset);
        processes.forEach(Process::reset);
    }

    public boolean processAreExecuting(){
        for(CPU cpu : cpus){
            if(cpu.getProcesses().size() != 0) return true;
        }return false;
    }

    public void run(){
        time = 0;
        ArrayList<Process> pr = new ArrayList<>(processes);
        while (pr.size() != 0 || processAreExecuting()){
            ArrayList<Process> newProcesses = newProcesses(pr,time);
            handle(newProcesses);
            executeProcesses();
            if(time % 10 == 5 && pr.size() != 0) addAvgWorkload();
            time++;
        }
        stats();
        reset();
    }
}

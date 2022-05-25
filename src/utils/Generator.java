package utils;

import java.util.*;

public class Generator {

    private ArrayList<CPU> cpus = new ArrayList<>();

    public ArrayList<Process> generate(int n, int m){
        Random r = new Random();
        for(int i = 0; i < n;i++) cpus.add(new CPU(i));
        ArrayList<Process> processes = new ArrayList<>();
        for(int i = 0; i < m;i++){
            int remainingTime = r.nextInt(20,50);
            processes.add(new Process(i, r.nextDouble(0.02,0.05),cpus.get(r.nextInt(n)), r.nextInt(m/7),remainingTime));
        }
        return processes;
    }
    public ArrayList<CPU> getCpus() {
        return cpus;
    }
}

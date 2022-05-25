package utils;

import java.util.ArrayList;
import java.util.List;

public class CPU implements Resetable{
    public static int migrations;
    public static int asksAboutWorkload;
    int CPU_ID;
    List<Process> processes = new ArrayList<>();
    double workload;

    public CPU(int CPU_ID) {
        this.CPU_ID = CPU_ID;
    }


    public int getCPU_ID() {
        return CPU_ID;
    }

    public void setCPU_ID(int CPU_ID) {
        this.CPU_ID = CPU_ID;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void addProcess(Process p){
        workload+= p.getRequiredWorkload();
        processes.add(p);
    }

    public void removeProcess(Process p){
        workload -= p.getRequiredWorkload();
        processes.remove(p);
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {
        this.workload = workload;
    }

    @Override
    public String toString() {
        return "CPU{" +
                "CPU_ID=" + CPU_ID +
                ", workload=" + workload +
                '}';
    }

    public void increaseAsks(){
        asksAboutWorkload++;
    }

    public void increaseMigrations(){
        migrations++;
    }

    public int getMigrations() {
        return migrations;
    }

    public int getAsksAboutWorkload() {
        return asksAboutWorkload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CPU cpu = (CPU) o;

        if (CPU_ID != cpu.CPU_ID) return false;
        if (Double.compare(cpu.workload, workload) != 0) return false;
        return processes != null ? processes.equals(cpu.processes) : cpu.processes == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = CPU_ID;
        result = 31 * result + (processes != null ? processes.hashCode() : 0);
        temp = Double.doubleToLongBits(workload);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public void reset() {
        migrations = 0;
        asksAboutWorkload = 0;
        processes = new ArrayList<>();
        workload = 0;
    }
}

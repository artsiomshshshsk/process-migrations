package strategy;

import utils.CPU;
import utils.Process;

import java.util.ArrayList;
import java.util.Random;

public interface Strategy {
    Random rand = new Random();
    void handle(double p, double r, int z, ArrayList<Process>processes, ArrayList<CPU> cpus);
    String getName();
}

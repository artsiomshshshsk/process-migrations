package utils;

import distributed.system.DistributedSystem;
import strategy.FirstStrategy;
import strategy.SecondStrategy;
import strategy.ThirdStrategy;

public class Test {
    public static void main(String[] args) {

        double p = 0.7; // upper workload boundary
        double r = 0.15; // lower workload boundary
        int z = 3; // number of tries in 1st algoritm
        int N = 10; // number of CPU's in ds
        int processes = 10000;

        DistributedSystem ds = new DistributedSystem(p,r,z,N,processes, new FirstStrategy());
        ds.run();
        ds.setStrategy(new SecondStrategy());
        ds.run();
        ds.setStrategy(new ThirdStrategy());
        ds.run();

    }
}

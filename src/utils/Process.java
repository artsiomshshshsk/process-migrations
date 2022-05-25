package utils;

public class Process implements Resetable{
    private final int ID;
    private final double requiredWorkload;
    private final int timeToComplete;
    private int timeLeft;
    private CPU cameTo;
    private boolean isRunning;
    private int appearanceTime;
    private CPU runningOn;

    public Process(int ID, double requiredWorkload, CPU cpu, int appearanceTime, int timeToComplete) {
        this.ID = ID;
        this.requiredWorkload = requiredWorkload;
        this.cameTo = cpu;
        this.appearanceTime = appearanceTime;
        this.timeToComplete = timeToComplete;
        timeLeft = timeToComplete;
    }

    public int getID() {
        return ID;
    }
    public double getRequiredWorkload() {
        return requiredWorkload;
    }


    public int getTimeToComplete() {
        return timeToComplete;
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getAppearanceTime() {
        return appearanceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        if (ID != process.ID) return false;
        if (requiredWorkload != process.requiredWorkload) return false;
        return timeToComplete == process.timeToComplete;
    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ID;
        temp = Double.doubleToLongBits(requiredWorkload);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + timeToComplete;
        return result;
    }

    public void stay(){
        runningOn = cameTo;
        setRunning(true);
        runningOn.addProcess(this);
    }

    public void finished(){
        CPU runningOn = getRunningOn();
        runningOn.removeProcess(this);
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void reduceTimeLeft() {
        this.timeLeft = timeLeft - 1;
    }

    public CPU getCameTo() {
        return cameTo;
    }

    public void setAppearanceTime(int appearanceTime) {
        this.appearanceTime = appearanceTime;
    }

    public CPU getRunningOn() {
        return runningOn;
    }

    public void setRunningOn(CPU runningOn) {
        this.runningOn = runningOn;
    }

    public void migrateTo(CPU cpu){
        if(runningOn != null)runningOn.removeProcess(this);
        cpu.increaseMigrations();
        runningOn = cpu;
        cpu.addProcess(this);
        setRunning(true);
    }

    @Override
    public String toString() {
        return "Process{" +
                "ID=" + ID +
                ", requiredWorkload=" + requiredWorkload +
                ", timeToComplete=" + timeToComplete +
                ", cameTo=" + cameTo +
                ", isRunning=" + isRunning +
                ", appearanceTime=" + appearanceTime +
                '}';
    }

    @Override
    public void reset() {
        timeLeft = timeToComplete;
        isRunning = false;
        runningOn = null;
    }
}

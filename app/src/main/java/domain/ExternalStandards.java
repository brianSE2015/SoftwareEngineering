package domain;

/**
 * Created by User on 4/11/15.
 */
public class ExternalStandards extends SolutionSet {

    private double volStandard = 0.0;
    private double volStockSolution = 0.0;
    private double molStandard = 0.0;
    private boolean anotherStandard = false;

    public ExternalStandards(Solution solution){

        super("External Standards");

        String[] questions = super.concat(solution.getQuestions(), new String[]{
                "What is the volume of the new standard?",
                "What is the volume of the stock solution you are transferring?",
                "What is the molarity of the new standard?",
                "Would you like create another standard?"
        });

        super.setQUESTIONS(questions);

    }

    @Override
    public void compute() {

    }

    public double getVolStandard() {
        return volStandard;
    }

    public void setVolStandard(double volStandard) {
        this.volStandard = volStandard;
    }

    public double getVolStockSolution() {
        return volStockSolution;
    }

    public void setVolStockSolution(double volStockSolution) {
        this.volStockSolution = volStockSolution;
    }

    public double getMolStandard() {
        return molStandard;
    }

    public void setMolStandard(double molStandard) {
        this.molStandard = molStandard;
    }

    public boolean isAnotherStandard() {
        return anotherStandard;
    }

    public void setAnotherStandard(boolean anotherStandard) {
        this.anotherStandard = anotherStandard;
    }
}
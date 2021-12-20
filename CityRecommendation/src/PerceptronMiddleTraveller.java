class PerceptronMiddleTraveller extends PerceptronTravellerBase{

    public PerceptronMiddleTraveller(String name, int age) {
        super(name, age);
        this.weightsBias = new double[] {0.4, 0.5, -0.1, 0.3, -0.5, 0.7, 0.6, -0.7, 0, -0.8};//just a suggestion for now
    }
}
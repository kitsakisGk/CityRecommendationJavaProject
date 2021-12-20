class PerceptronElderTraveller extends PerceptronTravellerBase{

    public PerceptronElderTraveller(String name, int age) {
        super(name, age);
        this.weightsBias = new double[] {0.9, -0.1, -0.8, -0.3, 0.5, -0.3, 0.6, -0.5, -0.2, -0.8};//just a suggestion for now
    }
}

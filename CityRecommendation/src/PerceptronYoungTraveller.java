class PerceptronYoungTraveller extends PerceptronTravellerBase{

    public PerceptronYoungTraveller(String name, int age) {
        super(name, age);
        this.weightsBias = new double[] {0.9, 0.8, -0.2, 0.3, 0.5, 0.7, -0.6, -0.5, 0, -0.5};//just a suggestion for now
    }
}

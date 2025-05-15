public class Polynomial{
    public double[] coefficients;
    public Polynomial() {
        coefficients = new double[0];
    }
    public Polynomial(double[] coeff) {
        this.coefficients = coeff;
    }
    public Polynomial add(Polynomial p) {
        int MaxLen = Math.max(this.coefficients.length, p.coefficients.length);
        int MinLen = Math.min(this.coefficients.length, p.coefficients.length);
        double[] ans = new double[MaxLen];
        for(int i=0;i<MinLen;i++){
            ans[i] = this.coefficients[i] + p.coefficients[i];
        }
        for(int i=MinLen;i<MaxLen;i++){
            if(this.coefficients.length > p.coefficients.length){
                ans[i] = this.coefficients[i];
            }
            else{
                ans[i] = p.coefficients[i];
            }
        }
        return new Polynomial(ans);
    }
    public double evaluate(double x) {
        double ans = 0;
        double current = 1;
        for(int i=0;i<this.coefficients.length;i++){
            ans += this.coefficients[i] * current;
            current *= x;
        }
        return ans;
    }
    public Boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }
}
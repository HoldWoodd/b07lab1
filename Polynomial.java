
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class Polynomial{
    public double[] coefficients;
    public int[] exponents;

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }
    public Polynomial(double[] coeff, int[] exp) {
        this.coefficients = coeff;
        this.exponents = exp;
    }
    public Polynomial(File file){
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] parts = line.split("(?=[-+])");
            coefficients = new double[parts.length];
            exponents = new int[parts.length];
            int index = 0;
            for(String part : parts) {
                if(!part.contains("x")) {
                    coefficients[index] = Double.parseDouble(part);
                    exponents[index] = 0;
                    index++;
                }
                else{
                    String[] temp = part.split("x");
                    coefficients[index] = Double.parseDouble(temp[0]);
                    exponents[index] = Integer.parseInt(temp[1]);
                    index++;
                }
            }
        }
        catch (FileNotFoundException e) {
            coefficients = new double[0];
            exponents = new int[0];
        }
    }
    public Polynomial add(Polynomial p) {
        Polynomial ans = new Polynomial(this.coefficients.clone(), this.exponents.clone());
        for(int i=0;i<p.coefficients.length;i++){
            int check = 0;
            for(int j=0;j<ans.coefficients.length;++j)
            {
                if(ans.exponents[j] == p.exponents[i]) {
                    ans.coefficients[j] += p.coefficients[i];
                    if(ans.coefficients[j] == 0) {
                        double[] newCoeffs = new double[ans.coefficients.length - 1];
                        int[] newExps = new int[ans.exponents.length - 1];
                        int index = 0;
                        for(int k=0; k<ans.coefficients.length; k++) {
                            if(k != j)
                            {
                                newCoeffs[index] = ans.coefficients[k];
                                newExps[index] = ans.exponents[k];
                                index++;
                            }
                        }
                        ans.coefficients = newCoeffs;
                        ans.exponents = newExps;
                    }
                    check = 1;
                    break;
                }
            }
            if(check == 0)
            {
                double[] newCoeffs = new double[ans.coefficients.length + 1];
                int[] newExps = new int[ans.exponents.length + 1];
                int index = 0;
                for(int k=0; k<ans.coefficients.length; k++) {
                    newCoeffs[k] = ans.coefficients[k];
                    newExps[k] = ans.exponents[k];
                    index++;
                }
                newCoeffs[index] = p.coefficients[i];
                newExps[index] = p.exponents[i];
                ans.coefficients = newCoeffs;
                ans.exponents = newExps;
            }
        }
        return ans;
    }
    public Polynomial multiply(Polynomial p) {
        Polynomial ans = new Polynomial();
        for(int i=0;i<p.coefficients.length;++i)
        {
            Polynomial temp = new Polynomial(this.coefficients.clone(), this.exponents.clone());
            for(int j=0;j<temp.coefficients.length;++j)
            {
                temp.coefficients[j] *= p.coefficients[i];
                temp.exponents[j] += p.exponents[i];
            }
            ans = ans.add(temp);
        }
        return ans;
    }
    public double evaluate(double x) {
        double ans = 0;
        double current = 1;
        for(int i=0;i<this.coefficients.length;i++){
            for(int j=0;j<this.exponents[i];j++) {
                current *= x;
            }
            ans += this.coefficients[i] * current;
            current = 1;
        }
        return ans;
    }
    public Boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }
    public void saveToFile(String filename) {
        String ans = "";
        for(int i = 0; i < this.coefficients.length; i++) {
            if(this.coefficients[i] > 0 && !ans.equals("")){
                ans += "+";
            }
            ans+= this.coefficients[i];
            if(this.exponents[i] != 0) {
                ans += "x";
                ans += this.exponents[i];
            }
        }
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(ans);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
        
}
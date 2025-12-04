import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    // ============================================
    // BASIC STATISTICS
    // ============================================
    public static double mean(List<Double> d) {
        double sum = 0;
        for (double x : d) sum += x;
        return sum / d.size();
    }

    public static double populationVariance(List<Double> d) {
        double m = mean(d);
        double sum = 0;
        for (double x : d) sum += Math.pow(x - m, 2);
        return sum / d.size();
    }

    public static double populationStd(List<Double> d) {
        return Math.sqrt(populationVariance(d));
    }

    // ============================================
    // NORMAL CDF + ERROR FUNCTION
    // ============================================
    public static double erf(double x) {
        double t = 1.0 / (1 + 0.5 * Math.abs(x));
        double tau = t * Math.exp(
                -x*x - 1.26551223 + 1.00002368*t +
                        0.37409196*t*t + 0.09678418*t*t*t -
                        0.18628806*t*t*t*t + 0.27886807*t*t*t*t*t -
                        1.13520398*t*t*t*t*t*t + 1.48851587*t*t*t*t*t*t*t -
                        0.82215223*t*t*t*t*t*t*t*t + 0.17087277*t*t*t*t*t*t*t*t*t
        );
        return x >= 0 ? 1 - tau : tau - 1;
    }

    public static double normalCDF(double z) {
        return 0.5 * (1 + erf(z / Math.sqrt(2)));
    }

    // ============================================
    // REPORT
    // ============================================
    public static void report(String label, double stat, boolean isZ) {
        double p = 2 * (1 - normalCDF(Math.abs(stat)));

        System.out.println("\n=== " + label + " ===");
        System.out.println((isZ ? "Z" : "T") + " Statistic : " + stat);
        System.out.println("P-value      : " + p);
        System.out.println("Conclusion   : " +
                (p < 0.05 ? "Significant (Reject H₀)" : "Not Significant (Fail to Reject H₀)"));
        System.out.println("--------------------------------------");
    }

    // ============================================
    // STATISTICAL TESTS
    // ============================================
    public static double z1(List<Double> s, double mu, double sd) {
        return (mean(s) - mu) / (sd / Math.sqrt(s.size()));
    }

    public static double t1(List<Double> s, double mu) {
        double sd = Math.sqrt(populationVariance(s) * s.size()/(s.size()-1));
        return (mean(s) - mu) / (sd / Math.sqrt(s.size()));
    }

    public static double t2(List<Double> a, List<Double> b) {
        double va = populationVariance(a) * a.size()/(a.size()-1);
        double vb = populationVariance(b) * b.size()/(b.size()-1);
        double se = Math.sqrt(va/a.size() + vb/b.size());
        return (mean(a) - mean(b)) / se;
    }

    public static double fTest(List<Double> a, List<Double> b) {
        double v1 = populationVariance(a) * a.size()/(a.size()-1);
        double v2 = populationVariance(b) * b.size()/(b.size()-1);
        return Math.max(v1, v2) / Math.min(v1, v2);
    }

    public static double zDiff(List<Double> a, List<Double> b, double sd1, double sd2) {
        double se = Math.sqrt(sd1*sd1/a.size() + sd2*sd2/b.size());
        return (mean(a) - mean(b)) / se;
    }

    // ============================================
    // READ MULTI-COLUMN CSV + SELECT COLUMN
    // ============================================
    public static List<List<String>> readFullCSV(String filename) {
        List<List<String>> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                rows.add(Arrays.asList(parts));
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return rows;
    }

    public static List<Double> extractNumericColumn(List<List<String>> csv, int colIndex) {
        List<Double> nums = new ArrayList<>();

        for (List<String> row : csv) {
            if (colIndex >= row.size()) continue;
            try {
                nums.add(Double.parseDouble(row.get(colIndex).trim()));
            } catch (Exception e) {
                // ignore non-numeric
            }
        }

        return nums;
    }

    public static int chooseColumn(List<List<String>> csv, Scanner sc, String fileName) {
        int maxCols = csv.get(0).size();

        System.out.println("\nColumns detected in " + fileName + ":");
        for (int i = 0; i < maxCols; i++) {
            System.out.println((i + 1) + ". Column " + (i + 1));
        }

        System.out.println("\nEnter which column number to use:");
        return sc.nextInt() - 1;
    }

    // ============================================
    // MAIN
    // ============================================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Load first CSV
        System.out.println("Enter FIRST CSV filename:");
        String f1 = sc.nextLine();
        List<List<String>> csv1 = readFullCSV(f1);

        // Load second CSV
        System.out.println("Enter SECOND CSV filename:");
        String f2 = sc.nextLine();
        List<List<String>> csv2 = readFullCSV(f2);

        // Choose columns
        int col1 = chooseColumn(csv1, sc, f1);
        int col2 = chooseColumn(csv2, sc, f2);

        // Extract numeric columns only
        List<Double> s1 = extractNumericColumn(csv1, col1);
        List<Double> s2 = extractNumericColumn(csv2, col2);

        System.out.println("\nSample 1 size: " + s1.size());
        System.out.println("Sample 2 size: " + s2.size());

        // Test menu
        System.out.println("\nChoose Test:");
        System.out.println("1. One-sample Z Test (Sample 1)");
        System.out.println("2. One-sample T Test (Sample 1)");
        System.out.println("3. Two-sample T Test");
        System.out.println("4. F Test");
        System.out.println("5. Z Test (Difference of Means)");
        int ch = sc.nextInt();

        switch (ch) {

            case 1: {
                System.out.println("Auto-detected mean = " + mean(s1));
                System.out.println("Auto-detected SD   = " + populationStd(s1));
                double z = z1(s1, mean(s1), populationStd(s1));
                report("One-Sample Z Test", z, true);
                break;
            }

            case 2: {
                System.out.println("Enter population mean:");
                double m = sc.nextDouble();
                report("One-Sample T Test", t1(s1, m), false);
                break;
            }

            case 3:
                report("Two-Sample T Test", t2(s1, s2), false);
                break;

            case 4:
                System.out.println("\nF Statistic = " + fTest(s1, s2));
                break;

            case 5: {
                double sd1 = populationStd(s1);
                double sd2 = populationStd(s2);
                report("Z Test (Difference of Means)", zDiff(s1, s2, sd1, sd2), true);
                break;
            }

            default:
                System.out.println("Invalid choice.");
        }

        sc.close();
    }
}

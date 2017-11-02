package SAT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class SatSolverTest {
    public static void main(String[] args) {
        String[] Splitup;
        ArrayList<LinkedList<Integer>> MasterForma= new ArrayList<>();
        LinkedList<Integer> Holder = new LinkedList<>();
        Integer NumberofVariables=0;
        Integer NumberofClauses=0;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/Users/User/Desktop/HOASE/LargeUnSat.cnf" ))) {
            //Requires buffered reader for readline function. Then read through substrings
            String View;
            while ((View =reader.readLine())!= null){
                if (View.substring(0,1).equals("p")) {
                    //Assign Splitup's Value
                    Splitup = View.split("\\s+");
                    NumberofVariables=Integer.parseInt(Splitup[2]);
                    NumberofClauses=Integer.parseInt(Splitup[3]);
                }
                //STRICT. will ignore all badly formatted lines that do not start with p or c. ONLY p or c. P and C will NOT be processed.
                else if(   !(View.substring(0,1).equals("c")) ) {
                    //Assign Splitup's Value
                    Splitup = View.split("\\s+");
                    for (String Expression: Splitup) {
                        //If it is not a denotation of end of line.
                        if (!Expression.equals("0")) {
                            //Add to current holder.
                            Holder.add(Integer.parseInt(Expression));
                        }
                        //it is the end of the line. cut and submit to MasterForma. Clear holder.
                        else{
                            MasterForma.add(Holder);
                            Holder= new LinkedList<>();
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (NumberofClauses!=MasterForma.size()){
            System.out.println("ERROR IN INPUT FILE. CLAUSE NUMBER AND CLAUSES FOUND DID NOT MATCH");
        }
        else {
            System.out.println("START SOLVING.");
            SatSolver A = new SatSolver();
            long started = System.nanoTime();
            A.Solve(MasterForma);
            long ended = System.nanoTime();
            Set<Integer> Answers = A.Varlist.FinalisedValues.keySet();
            for(Integer I: Answers){
             //   System.out.print(I+ ":");
             //   System.out.println(A.Varlist.FinalisedValues.get(I).TrueFinalForm);
            }
            System.out.println("TOTAL VARIABLES ANSWERED: "+Answers.size());
            System.out.println("TOTAL NUMBER OF VARIABLES REGISTERED: " + NumberofVariables);
            System.out.println((ended-started)/1000000.0);
            int counter =0;
            for(LinkedList<Integer> a : A.Varlist.Formula){
                System.out.print(counter);
                System.out.print(a);
                counter++;
            }
        }
    }
}

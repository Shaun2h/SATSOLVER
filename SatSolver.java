package SAT;

import java.util.LinkedList;
import java.util.List;

public class SatSolver{
    public List Solve(List<LinkedList> Formula){
        VariablesList A = new VariablesList();
        //CREATE A NEW VARIABLES LIST
        for(int Clausecounter =0; Clausecounter<Formula.size(); Clausecounter++){
            A.Register(Formula.get(Clausecounter),Clausecounter);
            //PROCESS CLAUSES TO OBTAIN LIST OF VARIABLES, AND ALSO SINGLE CLAUSES THAT EXIST FOR QUICK-MOVE
            //From which you can deduce that VariablesList class is actually a misnomer, and actually more like clause and variable list.
        }
        return ;
    }
}

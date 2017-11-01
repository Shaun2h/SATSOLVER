package SAT;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SatSolver{
    public VariablesList Varlist;
    //Note that we are particular on the EXACT type of list.
    //Using Linked list on the outer portion is disastrous.
    // Adding or deleting elements in a linked list however, is better then that of an arraylist. Hence the particularness.
    public Integer Solve(ArrayList<LinkedList<Integer>> Formula){
        this.Varlist = new VariablesList(Formula);
        //CREATE A NEW VARIABLES LIST
        for(int Clausecounter =0; Clausecounter<Formula.size(); Clausecounter++){
            this.Varlist.Register(Formula.get(Clausecounter),Clausecounter);
            //PROCESS CLAUSES TO OBTAIN LIST OF VARIABLES, AND ALSO SINGLE CLAUSES THAT EXIST FOR QUICK-MOVE
            //From which you can deduce that VariablesList class is actually a misnomer, and actually more like clause and variable list.
            if(this.Varlist.is_unsat){
                System.out.println("not satisfiable");
                return null;
                //ie. break the processing.
            }
        }
        Set<Integer> AllVariables = this.Varlist.Lister.keySet();
        for(Integer O: AllVariables){
            //Check Each Variable's HashTable for is_in.
            if(this.Varlist.Lister.get(O).is_in.isEmpty()){
                //If they are empty, then they hold no effective bearing and can be assigned 1.
                this.Varlist.Lister.get(O).TrueFinalForm=1; //Arbitrary Value
            }
        }
        //now that you have ALL possible values that could have been inferred, and one class of reducible clauses reduced to nothing
        // either due to defaulting to true, you're ready to start DPLL.



        return null;
    }
}

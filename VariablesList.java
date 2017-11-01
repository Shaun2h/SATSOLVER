package SAT;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class VariablesList {
    public HashMap<Integer,Variables> Lister = null; // A master List of all set Variables.
    public HashMap<Integer,Variables> SingleClauses = null;
    //Lists out your clauses that are single. might not be too useful.
    public HashMap<Integer,Variables> FinalisedValues = null; //Lists which variables have finalised values.
    /* A bit of explanation required. Finalised values allows you to compare if a variable within the clause has
    * already got a finalised value. Upon determining it has a finalised value, it will automatically evaluate
    * said variable in the clause's context.
    * IE. if A =1 is determined, in another clause, A appears as {A OR B}, this allows us to instantly find that A has
    * already obtained a determined value. As a result, it will evaluate said clause, in this case, instantly marking the
    * clause as TRUE. This will result in B being ignored if it has already been processed as a "Variable" class, because
     * it would remain as an X case. IE there is no known value that either determines B MUST be 1 or MUST be 0.
    * for processing it because B is still unknown.
    * */
    public HashMap<Integer,Integer> Ignoreus= null; //Lists which clauses can be ignored and no longer processed.
    //might not be useful because of finalised values determination method.

    public Boolean is_unsat = false;
    //If any unsat condition is reached, this will become true. Upon completion of clause processing, instantly causes
    //main SatSolver.java to return a UN-SAT response.
    private Boolean clauseissolved = false;
    /*A marker to ensure that if the clause is solved, I will do 2 things:
    *   ONE: Clear out the entire clause in the original input Formula after it has been completely processed.
    *   This is because there is no longer a need to process ANY of the variables in the clause EVER again.
    *   if they are still unknowns, and are never mentioned in another clause. IT CAN BE ASSIGNED AN ARBITRARY VALUE.
    *   TWO: Remove this clause from consideration of ALL variables involved in this clause.
    *   Yes I want every variable instantiated for record keeping purposes, but if it is involved in a clause that will
    *   evaluate to true, it would never require this clause in its value assignment for consideration.
    *   As a result, remove it.
    */

    public ArrayList<LinkedList<Integer>> Formula;
    //Save the formula for future referencing within THIS function. NOTE THAT IT IS A REFERENCE AND IS HENCE A SHALLOW COPY.
    //ANY CHANGES MADE TO this.Formula IS REFLECTED IN THE REAL Formula Input! DO NOT USE REMOVE() on OUTER LIST.
    //ONLY USE REMOVE() OR CLEAR() ON OUTER LIST

    VariablesList(ArrayList<LinkedList<Integer>> Formula) {
        this.Lister = new HashMap<Integer, Variables>();
        this.SingleClauses = new HashMap<Integer, Variables>();
        this.FinalisedValues = new HashMap<Integer, Variables>();
        this.Ignoreus = new HashMap<Integer, Integer>();
        this.Formula = Formula;
    }
    public void Register(LinkedList<Integer> Clause, Integer ClauseNumber){
        for (Integer K : Clause) {


            //Additional check of if it has already obtained a SET value is not yet implemented.


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////    CLAUSE SIZE IS NOT EQUAL TO ONE             ///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            if(Clause.size()!=1){

                //Run a check to see if this variable was never initialised in the list.
                if(this.Lister.get(Math.abs(K))==null) {
                Variableinitnot1.Makevars(this,ClauseNumber,K);
                //run init for variable that appears in not1 clause.
                }


                //If the variable has indeed been initialised, update the existing entry
                else{
                    if(this.Lister.get(Math.abs(K)).TrueFinalForm==null){
                        //ie. this number does not have an assigned value.
                        List<Integer> varsandtype = new ArrayList<Integer>();
                        //Again, create a size = 2 List, First entry being which clause said variable appears in, and Second being variable type.
                        varsandtype.add(ClauseNumber);
                        varsandtype.add(Negativecheck.check(K));
                        this.Lister.get(Math.abs(K)).is_in.put(ClauseNumber,varsandtype);
                        //Taking the reference from within Master Variables hashtable, update variable's is_in hashtable.
                    }
                    else {
                        //This Variable has an assigned Value!
                        if(this.Lister.get(Math.abs(K)).TrueFinalForm.intValue() == SingleClassAssigner.SingleAssign(K).intValue()){
                            //Ie. It satisfies this entire clause instantly
                            this.clauseissolved= true;
                            //Keep the note that the clause is already solved. Continue Processing all variables. Deal
                            //with this later.
                            List<Integer> varsandtype = new ArrayList<Integer>();
                            varsandtype.add(ClauseNumber);
                            varsandtype.add(Negativecheck.check(K));
                            //Taking the reference from within Master Variables hashtable, update variable's is_in hashtable.
                            this.Lister.get(Math.abs(K)).is_in.put(ClauseNumber, varsandtype);

                        }
                        else{
                            //Because this variable does not solve this clause by default, nothing is done.
                            //DPLL that is implemented later will continue to process with this given value.
                            //For later reference, we will of course add this to the is_in of this variable.
                            List<Integer> varsandtype = new ArrayList<Integer>();
                            varsandtype.add(ClauseNumber);
                            varsandtype.add(Negativecheck.check(K));
                            this.Lister.get(Math.abs(K)).is_in.put(ClauseNumber, varsandtype);
                        }

                        List<Integer> varsandtype = new ArrayList<Integer>();
                        varsandtype.add(ClauseNumber);
                        varsandtype.add(Negativecheck.check(K));
                        //Taking the reference from within Master Variables hashtable, update variable's is_in hashtable.
                        this.Lister.get(Math.abs(K)).is_in.put(ClauseNumber, varsandtype);
                    }
                }



            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////    CLAUSE SIZE IS EQUAL TO ONE             ///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            //Else occurs when the clause size is 1. So it's a single variable clause.
            else{
                //IF this variable has never been initialised and is appearing as a singular.
                if(this.Lister.get(Math.abs(K))==null) {
                    Variableinit1.Makevarbut1(this, ClauseNumber,K);
                    }

                    //if finalised value was -1.



                else{
                    //The variable has been initialised. Check if it ever had a true assignment.
                    if(this.Lister.get(Math.abs(K)).TrueFinalForm==null){
                        this.Lister.get(Math.abs(K)).TrueFinalForm = SingleClassAssigner.SingleAssign(K);
                        //Since no true assignment, Assign.
                        FinalisedValues.put(Math.abs(K), Lister.get(Math.abs(K)));
                        //Update Master Assigned Hash
                    }
                    //It has been initialised and had an assignment. Check for unsat condition.
                    else {
                        if (this.Lister.get(Math.abs(K)).TrueFinalForm.intValue() != SingleClassAssigner.SingleAssign(K).intValue()) {
                            //CHECK FOR CONFLICT BETWEEN OLD ASSIGNMENT AND CURRENT ASSIGNMENT.
                            //CONFLICT SETS EQUATION TO IMMEDIATE UN-SAT.
                            this.is_unsat = true;
                            //Upon detection of unsat, because there is no need to care about the correct variables anymore,
                            //the hash of finalised values is not updated.
                        }


                        //If nothing was wrong with the current set value, nothing is done. Move to next processing step.
                        else { //Triggers because its previous assignment was correct
                            //Given that this entire statement is now true, set this.clauseissolved=true.
                            this.clauseissolved = true;
                        }
                    }
                }

            }
        }
        //At this point, ALL variables in the clause have been checked.
        //perform redundant clause removal and possible reduction if the clause was confirmed to be solved.
        if(this.clauseissolved){
            this.clauseissolved = false;
            //reset the clause is solved indicator.
            RedundantClauseRemoval.Remove(this,Clause,ClauseNumber);
            //At this point, All variables in the clause have already been initialised.
            //Now Clear the Clause in the Actual formula, reducing it to an empty LinkedList. It would always evaluate to
            //true anyway. Furthermore, an additional catch-all exists in SatSolver.java, which sets any variable without
            //any weighted clauses to an arbitrary value. ie. 1
            this.Formula.get(ClauseNumber).clear();
        }
    }

}

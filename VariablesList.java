package SAT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    VariablesList() {
        this.Lister = new HashMap<Integer, Variables>();
        this.SingleClauses = new HashMap<Integer, Variables>();
        this.FinalisedValues = new HashMap<Integer, Variables>();
        this.Ignoreus = new HashMap<Integer, Integer>();
    }
    public void Register(List<Integer> Clause, Integer ClauseNumber){
        for (Integer K : Clause) {
            if(Clause.size()!=1){  //IE. for each clause processed, IF CLAUSE IS NOT A SINGLE VARIABLE CLAUSE.
                if(this.Lister.get(Math.abs(K))==null) { //If the variable has never been initialised, Create a new entry
                    Lister.put(Math.abs(K), new Variables(ClauseNumber));
                    // Initialise said Variable's true number representation (negative is negated form)
                    Lister.get(Math.abs(K)).is_in.put(ClauseNumber,);
                }
            }
        }
    }

}

package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.Plan;

/** PlanOutputTO is strictly for bringing back data from client
 * for printing and email purposes
 * 
 * @author jamesstanley
 *
 */
public class PlanOutputTO extends AbstractPlanOutputTO<Plan,PlanTO>{
    
    public PlanOutputTO(){
    	super();
    }
    
    public void setPlan(PlanTO plan){
    	setNonOuputTO(plan);
    }
   
    public PlanTO getPlan() {
    	return getNonOutputTO();
    }
    
}

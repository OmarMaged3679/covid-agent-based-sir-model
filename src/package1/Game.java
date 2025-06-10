package package1;

import sim.engine.*;
import sim.field.network.*;

public class Game implements Steppable{
    
    //public Network pairs = new Network(false);
    
    public void step(SimState state){
        Model model = (Model) state;
        
        int susceptiblesCounter = 0;
        int infectedCounter = 0;
        int recoveredCounter = 0;
        int totalAgentsWearingMask = 0;
        int totalAgentsPracSocialDistance = 0;
        
        int percAgentsInfectedDaily = 0;
        int oldInfected;
        int oldRecoverd;
        
//        pairs.removeAllEdges();
//        pairs.clear();
        
        if(!model.agentArr.isEmpty())
        {
            for(int i = 0; i < model.agentArr.size(); i++){
                
                oldInfected = model.infectedAgents;
                oldRecoverd = model.recoveredAgents;
                
                if(model.agentArr.get(i).status == Status.SUSCEPTIBLE)
                    susceptiblesCounter++;
                if(model.agentArr.get(i).status == Status.INFECTED)
                    infectedCounter++;
                if(model.agentArr.get(i).status == Status.RECOVERED)
                    recoveredCounter++;
                if(model.agentArr.get(i).isWearingMask)
                    totalAgentsWearingMask++;
                if(model.agentArr.get(i).isPractingSocailDistance)
                    totalAgentsPracSocialDistance++;
                
                if(i == 0)
                {
                    //After a certain step, infect a percentage of the population
                    if(model.schedule.getSteps() == model.stepsToStartNewInfection)
                    {
                        model.numbers.clear();
                        for(int j = 0; j < model.numAgents; j++)
                            model.numbers.add(j);
                        
                        
                        //Percentage of the population to start with the infection
                        double agentsToInfect = (model.percAgentsToInfect / 100.0) * model.numAgents;
                        for(int j = 0; j < agentsToInfect; j++)
                        {
                            int randIndex1;
                            int randPlayer1;
                            
                            randIndex1 = model.random.nextInt((model.numbers.size()));
                            randPlayer1 = model.numbers.get(randIndex1);
                            
                            model.numbers.remove(randIndex1);
                            model.agentArr.get(randPlayer1).status = Status.INFECTED;
                        }
                    }
                }
                
                if(i == (model.agentArr.size() - 1))
                {
                    
                    int agentsInfectedDaily = (infectedCounter - oldInfected) + (recoveredCounter - oldRecoverd);
                    int agentsRecoveredDaily = recoveredCounter - oldRecoverd;
                    
                    model.agentsInfectedDaily = agentsInfectedDaily;
                    model.agentsRecoveredDaily = agentsRecoveredDaily;          
                    
                    model.percAgentsInfectedDaily = ((double) agentsInfectedDaily / (double) model.numAgents) * 100.0;
                    model.percAgentsRecoveredDaily = ((double) agentsRecoveredDaily / (double) model.numAgents) * 100.0;
                    
                    model.infectedAgents = infectedCounter;
                    model.recoveredAgents = recoveredCounter;
                    model.susceptibleAgents = susceptiblesCounter;
                    
                    model.maskedAgents = totalAgentsWearingMask;
                    model.socailDistancingAgents = totalAgentsPracSocialDistance;
                    
                    model.infectionRate = ((double) model.infectedAgents / (double) model.numAgents);
                    
                    oldInfected = infectedCounter;
                    oldRecoverd = recoveredCounter;
                }
            }
        }
    }
}
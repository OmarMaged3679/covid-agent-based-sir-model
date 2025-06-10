package package1;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;

public class Agent implements Steppable{
    
    //Notes:
    //Susceptible - Are all of the people that are capable of becoming sick from an infection
    //Infected - People who have the infection
    //Recovered - They can neither get this illness nor can they give it to somebody else
    
    //The higher your immunity the lower the chance of you getting infected
    //Recovery days varies depending on your immunity: (wearing a mask will increase your immunity)
    //  - 3.6% of the population have a high immunity       - Recovery days = 15 days   - Prob. of randomly getting infected < 0.3
    //  - 75% of the population with an average immunity    - Recovery days = 18 days   - Prob. of randomly getting infected < 0.6
    //  - 21.4% of the population with a weak immunity      - Recovery days = 21 days   - Prob. of randomly getting infected < 0.9
    
    public int id;
    //public char action;
    //public int group;
    public int totalDaysToRecover;
    public int recoveryDaysLeft;
    public double probInfection;
    public boolean isPractingSocailDistance;
    public boolean isImmune;
    public boolean isWearingMask = false;
    public boolean isForcedToWearMask = false;
    
    public int awareness;
    public double utility;
    public double immunity;
    public double calcuatedImmunity;
    public double mockingCost;
    public double costOfWearingMask  = 0.75;
    
    public Status status;
    
//    public static int susceptibleAgents;
//    public static int infectedAgents;
//    public static int recoveredAgents;
//    public static int maskedAgents;
//    public static int socailDistancingAgents;
//    public static double infectionRate;
    
    
    public Agent(int id, double immunity, int awareness,int totalDaysToRecover, Status status, double acceptableMocking) {
        this.id = id;
        this.immunity = immunity;
        this.awareness = awareness;
        this.totalDaysToRecover = totalDaysToRecover;
        this.recoveryDaysLeft = totalDaysToRecover;
        this.isImmune = false;
        this.isWearingMask = false;
        this.status = status;
        this.mockingCost = acceptableMocking;
        this.calcuatedImmunity = immunity;
    }
    
//    public double getCalcuatedImmunity() { return calcuatedImmunity; }
    public double getProbInfection() { return probInfection; }
    public double getImmunity() { return immunity; }
    public double getMockingCost() { return mockingCost; }
//    public double getInfectionRate() { return infectionRate; }
//    public int getTotalSusceptibleAgents() { return susceptibleAgents; }
//    public int getTotalInfectedAgents() { return infectedAgents; }
//    public int getTotalRecoveredAgents() { return recoveredAgents; }
//    public int getAgentsWearingMask() { return maskedAgents; }
//    public int getAgentsPracticingSocialDistance() { return socailDistancingAgents; }
    public boolean getIsWearingMask() { return isWearingMask; }
    public double getAwarness() { return awareness; }
    public double getUtility() { return utility; }
    public Status getState() { return status; }
    
    public void step(SimState state) {
        Model model = (Model) state;
        Continuous2D yard = model.yard;
        
        Double2D me = model.yard.getObjectLocation(this);
        MutableDouble2D sumForces = new MutableDouble2D();        
        
        double movementRandomnessMultiplier;;
        
        //Reduce the randomMultiplier - to move less
        if(isPractingSocailDistance)
            movementRandomnessMultiplier = 1;
        else
            movementRandomnessMultiplier = model.randomMultiplier;
        
        //Case agent is infected
        if(status == Status.INFECTED) {
            
            if(isWearingMask)
                isWearingMask = false;
            if(isForcedToWearMask)
                isForcedToWearMask = false;
            
            //Check if the agent has recovered
            recoveryDaysLeft--;
            if(recoveryDaysLeft == 0)
            {
                status = Status.RECOVERED;
                isImmune = true;
            }
            
            //If he didn't recover yet, then he might infect some of his neighbors
            else
            {
                Bag neighbors = yard.getNeighborsWithinDistance(me, model.infectionRadius);
                
                int infectedNeighbors = 0;
                for (int x = 0; x < neighbors.size(); x++) {
                    Agent neighbor = (Agent) neighbors.get(x);
                    double updatedProbOfInfection = neighbor.probInfection;
                    
                    //If a neighbor is really close - then increase his chance of getting infected by a certain %
                    if((model.yard.getObjectLocation(neighbor).x - model.yard.getObjectLocation(this).x) == 0) {
                        updatedProbOfInfection += 0.15;
                    }
                    
                    //To make sure the prob. doesn't exceed 1
                    if(updatedProbOfInfection > 1)
                        updatedProbOfInfection = 1;
                    
                    double randProb = model.random.nextDouble();
                    
                    if(updatedProbOfInfection > randProb && neighbor.status == Status.SUSCEPTIBLE) {
                        infectedNeighbors++;
                        neighbor.status = Status.INFECTED;
                    }
                }
            }
        }
        
        //Case agent is Susceptible and is not Forced to wear a mask
        if(status == Status.SUSCEPTIBLE && model.infectionRate != 0 && !isForcedToWearMask)
        {
            double k1 = model.infectionRateWeight;
            double k2 = model.costOfWearingMaskWeight;
            double k3 = model.mockingCostWeight;
            double k4 = model.awarenessWeight;
            
            Bag neighbors = yard.getNeighborsWithinDistance(me, model.infectionRadius);            
            
            if(model.populationCanWearMask)
            {
                //Decide whether or not the agent should wear based on the utility calculated in the function shouldWearMask()
                shouldWearMask(neighbors, model.maskEffectiveness, model.infectionRate, k1, k2, k3, k4);
            }
        }
        
        //Case agent is still Susceptible and the pandemic ended
        if(status == Status.SUSCEPTIBLE && model.infectionRate == 0 && model.schedule.getSteps() > model.stepsToStartNewInfection)
        {
            if (isPractingSocailDistance)
            {
                calcuatedImmunity -= model.socialDistancingEffectiveness;
                isPractingSocailDistance = false;
                isForcedToWearMask = false;
            }
            //isPractingSocailDistance = false;
            //isForcedToWearMask = false;
            double k1 = model.infectionRateWeight;
            double k2 = model.costOfWearingMaskWeight;
            double k3 = model.mockingCostWeight;
            double k4 = model.awarenessWeight;
            
            Bag neighbors = yard.getNeighborsWithinDistance(me, model.infectionRadius);
            
            if(model.populationCanWearMask)
            {
                shouldWearMask(neighbors, model.maskEffectiveness, model.infectionRate, k1, k2, k3, k4);
            }
        }
        
        //Case agent had Recovered, then he won't wear the mask
        if(status == Status.RECOVERED)
        {
            if(isPractingSocailDistance)
            {
                isPractingSocailDistance = false;
                calcuatedImmunity -= model.socialDistancingEffectiveness;
            }
            if(isWearingMask)
            {
                isWearingMask = false;
                isForcedToWearMask = false;
                calcuatedImmunity -= model.maskEffectiveness;
            }
        }
        
        
        //Initialize the agents position at the beginning
        if(model.schedule.getSteps() == 0)
        {            
            sumForces.addIn(new Double2D(
            ((yard.width - 1) * (model.random.nextDouble() * 1.0 - 0.5)),
            ((yard.height - 1) * (model.random.nextDouble() * 1.0 - 0.5))));

            sumForces.addIn(new Double2D(
            ((model.random.nextDouble() * 1.0 - 0.5)),
            ((model.random.nextDouble() * 1.0 - 0.5))));
        }
        
        //Make sure they don't leave the yard boundaries            
        double distanceFromBoundary = 0.5;

        if(me.x > (yard.width - distanceFromBoundary))
        {
            sumForces.addIn(new Double2D(-model.random.nextDouble(), 0));
        }
        if(me.x < (distanceFromBoundary))
        {
            sumForces.addIn(new Double2D(model.random.nextDouble(),0));
        }
        if(me.y > (yard.height - distanceFromBoundary))
        {
            sumForces.addIn(new Double2D(0, -model.random.nextDouble()));
        }
        if(me.y < (distanceFromBoundary))
        {
            sumForces.addIn(new Double2D(0, model.random.nextDouble()));
        }
        
        
        double controlMovement = model.controlMovement;
        //Add a bit of randomness
        
        sumForces.addIn(new Double2D(
        (movementRandomnessMultiplier * (model.random.nextDouble() * controlMovement - controlMovement / 2.0)),
        (movementRandomnessMultiplier * (model.random.nextDouble() * controlMovement - controlMovement / 2.0))));
        
        
//        sumForces.addIn(new Double2D(
//        (randomMultiplier * (model.random.nextDouble() * 1.0 - 0.5)),
//        (randomMultiplier * (model.random.nextDouble() * 1.0 - 0.5))));

        //instead of the basic range [-0.5, 0.5], I tried [-1.0, 1.0], which allows for more movement,
        //but I have to balance it with the randomMultiplier
//        sumForces.addIn(new Double2D(
//        (randomMultiplier * (model.random.nextDouble() * 2.0 - 1.0)),
//        (randomMultiplier * (model.random.nextDouble() * 2.0 - 1.0))));
        
        sumForces.addIn(me);
        model.yard.setObjectLocation(this, new Double2D(sumForces));
    }
    
    public void shouldWearMask(Bag neighbors, double maskEffectiveness, double infectionRate, double k1, double k2, double k3, double k4) {
        
        boolean wearMask;
        
        int numInfectedNeighbors = 0;
        for (int x = 0; x < neighbors.size(); x++)
        {
            Agent neighbor = (Agent) neighbors.get(x);
            if (neighbor.status == Status.INFECTED)
            {
                numInfectedNeighbors++;
            }
        }
        
        
        //Calculate the probability of infection based on the number of infected neighbors
        double percInfectionFromNeighbors = numInfectedNeighbors / (double) neighbors.size();        
        
        //Calculate the utility of wearing a mask
        //k1, k2, k3, and k4 are constants that can be adjusted to reflect the importance of each factor in determining the utility,
        //this can be done by defining the constants in the Model class
        //Can improve the k values even better
        //double k1 = 1.8, k2 = 1, k3 = 0.7, k4 = 1.1;
        
        utility = (k1 * infectionRate)
                - (k2 * costOfWearingMask)
                - (k3 *  mockingCost)
                + (k4 * (double) awareness / 100.0);
        
        //If the probability of infection is high or the utility of wearing a mask is +ve, then wear a mask
        if (percInfectionFromNeighbors > probInfection || utility > 0)
        //if(utility > 0)
            wearMask = true;
        else
            wearMask = false;
        
        
        if(wearMask)
        {
            if(!isWearingMask)
            {
                isWearingMask = true;
                calcuatedImmunity += maskEffectiveness;

                if(calcuatedImmunity > 100)
                    calcuatedImmunity = 100;

                probInfection = 1.0 - ( (double) calcuatedImmunity / 100.0); 
            }
        }
        
        else
        {
            if(isWearingMask)
            {
                
                calcuatedImmunity -= maskEffectiveness;
                //calcuatedImmunity = immunity;
                probInfection = 1.0 - ( (double) calcuatedImmunity / 100.0);
                isWearingMask = false;
            }
            //isWearingMask = false;
        } 
    }
}

package package1;

import sim.util.*;
import sim.engine.*;
import sim.field.continuous.*;
import java.util.ArrayList;
import java.util.Collections;

public class Model extends SimState {
    
    public Continuous2D yard = new Continuous2D(1.0, 100.0, 100.0);
    public int numAgents = 600;             //If you want to decrease/increase the numger of Agents, it must be balanced with the yard size & infectionDistance
    //double forceToSchoolMultiplier = 1;
    public double controlMovement = 1.25;          //1 - 2 is decent
    public double randomMultiplier = 3.6;          //3 - 4.5 great
    public ArrayList<Agent> agentArr = new ArrayList<>();
    public ArrayList<Integer> numbers = new ArrayList<>();
    
    
    
    public int stepsToStartNewInfection = 1;
    public double maskEffectiveness = 30.0;
    public double percForcedToWearMask = 0;
    
    public double socialDistancingEffectiveness = 20.0;
    public double percSocialDistancing = 0;
    
    public double percAgentsToInfect = 1.5;   //1.5 is great 
    public double infectionRadius = 2;    //2 - 3 is great -> it depends on the yard size
    
    int sameRecDays = 18;
    int highImmunityRecDays = 15;           //9  - 15 is great
    int averageImmunityRecDays = 18;        //15 - 18 is great
    int weakImmunityRecDays = 21;           //18 - 21 is great
    
    double highImmunityPercentage = 3.6;
    double averageImmunityPercentage = 75;
    double weakImmunityPercentage = 21.4;
    
    public Game g;
    public int agentsRecoveredDaily;
    public int agentsInfectedDaily;
    public double percAgentsRecoveredDaily;
    public double percAgentsInfectedDaily;
    
    public int susceptibleAgents;
    public int infectedAgents;
    public int recoveredAgents;
    public int maskedAgents;
    public int socailDistancingAgents;
    public double infectionRate;
    
    public double infectionRateWeight = 1.6;
    public double costOfWearingMaskWeight = 0.9;
    public double mockingCostWeight = 0.7;
    public double awarenessWeight = 1.1;
    
    public boolean sameRecoveryDays = true;
    public boolean populationCanWearMask = true;
    
    
    public double getControlMovement() { return controlMovement; }
    public void setControlMovement(double val) { if (controlMovement >= 0.0) controlMovement = val; }
    public Object domControlMovement() { return new sim.util.Interval(0.0, 2.0); }
    
    public int getTotalNumberOfAgents() { return numAgents; }
    public void setTotalNumberOfAgents(int val) { if (numAgents >= 0) numAgents = val; }
    public Object domTotalNumberOfAgents() { return new sim.util.Interval(0, 3000); }
    
    public double getYardDimensions() { return yard.height; }
    public void setYardDimensions(double val) { if (yard.height >= 0) { yard.height = val; yard.width = val; } }
    public Object domYardDimensions() { return new sim.util.Interval(0.0, 300.0); }
    
    
    
    public boolean getSameRecoveryDaysForAgents() { return sameRecoveryDays; }
    public void setSameRecoveryDaysForAgents(boolean val) { sameRecoveryDays = val; }
    
    public boolean getPopulationCanWearMasks() { return populationCanWearMask; }
    public void setPopulationCanWearMasks(boolean val) { populationCanWearMask = val; }
    
    
    public int getSetRecoveryDays() { return sameRecDays; }
    public void setSetRecoveryDays(int val) { if (sameRecDays >= 0) sameRecDays = val; }
    public Object domSetRecoveryDays() { return new sim.util.Interval(0, 50); }
    
    
    
    public double getMaskEffectiveness() { return maskEffectiveness; }
    public void setMaskEffectiveness(double val) { if (maskEffectiveness >= 0.0) maskEffectiveness = val; }
    public Object domMaskEffectiveness() { return new sim.util.Interval(0.0, 100.0); }
    
    public double getPerc_ForcedToWearMask() { return percForcedToWearMask; }
    public void setPerc_ForcedToWearMask(double val) { if (percForcedToWearMask >= 0.0) percForcedToWearMask = val; }
    public Object domPerc_ForcedToWearMask() { return new sim.util.Interval(0.0, 100.0); }
    
    
    
    public double getSocialDistancingEffectiveness() { return socialDistancingEffectiveness; }
    public void setSocialDistancingEffectiveness(double val) { if (socialDistancingEffectiveness >= 0.0) socialDistancingEffectiveness = val; }
    public Object domSocialDistancingEffectiveness() { return new sim.util.Interval(0.0, 100.0); }
    
    public double getPerc_SocialDistancing() { return percSocialDistancing; }
    public void setPerc_SocialDistancing(double val) { if (percSocialDistancing >= 0.0) percSocialDistancing = val; }
    public Object domPerc_SocialDistancing() { return new sim.util.Interval(0.0, 100.0); }
    
    
    
    public double getInfectionDistance() { return infectionRadius; }
    public void setInfectionDistance(double val) { if (infectionRadius >= 0.0) infectionRadius = val; }
    public Object domInfectionDistance() { return new sim.util.Interval(0.0, 9.0); }
    
    public double getPerc_AgentsToInfect() { return percAgentsToInfect; }
    public void setPerc_AgentsToInfect(double val) { if (percAgentsToInfect >= 0.0) percAgentsToInfect = val; }
    public Object domPerc_AgentsToInfect() { return new sim.util.Interval(0.0, 100.0); }    
    
    
    
    public double getWeight_InfectionRate() { return infectionRateWeight; }
    public void setWeight_InfectionRate(double val) { if (infectionRateWeight >= 0.0) infectionRateWeight = val; }
    public Object domWeight_InfectionRate() { return new sim.util.Interval(0.0, 3.0); }
    
    public double getWeight_CostOfWearingMask() { return costOfWearingMaskWeight; }
    public void setWeight_CostOfWearingMask(double val) { if (costOfWearingMaskWeight >= 0.0) costOfWearingMaskWeight = val; }
    public Object domWeight_CostOfWearingMask() { return new sim.util.Interval(0.0, 3.0); }
    
    public double getWeight_MockingCost() { return mockingCostWeight; }
    public void setWeight_MockingCost(double val) { if (mockingCostWeight >= 0.0) mockingCostWeight = val; }
    public Object domWeight_MockingCost() { return new sim.util.Interval(0.0, 3.0); }
    
    public double getWeight_Awareness() { return awarenessWeight; }
    public void setWeight_Awareness(double val) { if (awarenessWeight >= 0.0) awarenessWeight = val; }
    public Object domWeight_Awareness() { return new sim.util.Interval(0.0, 3.0); }
    
    
    
    public int getSusceptible() { return susceptibleAgents; }
    public int getInfected() { return infectedAgents; }
    public int getRecovered() { return recoveredAgents; }
    public int getTotalAgentsWearingMask() { return maskedAgents; }
    public int getTotalAgentsPracticingSocialDistance() { return socailDistancingAgents; }
    public int getDailyInfected() { return agentsInfectedDaily; }
    public int getDailyRecovered() { return agentsRecoveredDaily; }
    public double getYardHeight() { return yard.height; }
    public double getYardWidth() { return yard.width; }
//    public double getDailyInfectedPerc() { return percAgentsInfectedDaily; }
//    public double getDailyRecoveredPerc() { return percAgentsRecoveredDaily; }
    
//    public double getHighImmunityPercentage() { return highImmunityPercentage; }
//    public void setHighImmunityPercentage(double val) { if (highImmunityPercentage >= 0) highImmunityPercentage = val; }
//    public Object domHighImmunityPercentage() { return new sim.util.Interval(0.0, 100.0); }
//    
//    public double getAverageImmunityPercentage() { return averageImmunityPercentage; }
//    public void setAverageImmunityPercentage(double val) { if (averageImmunityPercentage >= 0) averageImmunityPercentage = val; }
//    public Object domAverageImmunityPercentage() { return new sim.util.Interval(0.0, 100.0); }
//    
//    public double getWeakImmunityPercentage() { return weakImmunityPercentage; }
//    public void setWeakImmunityPercentage(double val) { if (weakImmunityPercentage >= 0) weakImmunityPercentage = val; }
//    public Object domWeakImmunityPercentage() { return new sim.util.Interval(0.0, 100.0); }
//    
//    
//    public double getRandomMultiplier() { return randomMultiplier; }
//    public void setRandomMultiplier(double val) { if (randomMultiplier >= 0.0) randomMultiplier = val; }
//    public Object domRandomMultiplier() { return new sim.util.Interval(0.0, 100.0); }
//    
//    public double getForceToSchoolMultiplier() { return forceToSchoolMultiplier; }
//    public void setForceToSchoolMultiplier(double val) { if (forceToSchoolMultiplier >= 0.0) forceToSchoolMultiplier = val; }
//    public Object domForceToSchoolMultiplier() { return new sim.util.Interval(0.0, 100.0); }
    
    
    
    public Model(long seed){
        super(seed);
    }
    
    public void start() {
        super.start();
        
        int highimmunityRecDays;
        int averageimmunityRecDays;
        int weakimmunityRecDays;
        
        
        if(sameRecoveryDays)
        {
            highimmunityRecDays = sameRecDays;
            averageimmunityRecDays = sameRecDays;
            weakimmunityRecDays = sameRecDays;
        }
        
        else
        {
            highimmunityRecDays = highImmunityRecDays;
            averageimmunityRecDays = averageImmunityRecDays;
            weakimmunityRecDays = weakImmunityRecDays;
        }
        
        
        yard.clear();
        agentArr.clear();
        numbers.clear();
        
        //To fill an array with numbers from 0 -> numAgents
        for(int i = 0; i < numAgents; i++)
        {
            numbers.add(i);
        }
        
        
        //stepsToStartNewInfection = 1;
        susceptibleAgents = numAgents;
        infectedAgents = 0;
        recoveredAgents = 0;
        infectionRate = 0;
        
        //scheduleSeq++;
        int scheduleSeq = 1;
        for(int i = 0; i < numAgents; i++)
        {
            double immunity, mockingCost;
            int awareness, recoveryDays;         
            
            
            double highImmunity = (highImmunityPercentage / 100.0) * ((double) numAgents);
            double averageImmunity = (averageImmunityPercentage / 100.0) * ((double) numAgents);
            //double weakImmunity = (weakImmunityPercentage / 100.0) * numAgents;            
            
            
           if(i < (int) highImmunity)
            {
                immunity = random.nextInt(10) + 70;
                recoveryDays = highimmunityRecDays;
            }
            else if(i < (int) averageImmunity)
            {
                immunity = random.nextInt(20) + 40;
                recoveryDays = averageimmunityRecDays;
            }
            else
            {
                immunity = random.nextInt(10) + 10;
                recoveryDays = weakimmunityRecDays;
            }
            
            awareness = random.nextInt(90) + 10;
            
            //Can set mockingCost to 0 if you don't want it to effect the utility of the Agent
            mockingCost = random.nextDouble();
            
            agentArr.add(new Agent(i,immunity, awareness, 
                    recoveryDays, Status.SUSCEPTIBLE,mockingCost));
            //g.pairs.addNode(agentArr.get(i));
            schedule.scheduleRepeating(agentArr.get(i), scheduleSeq, 1);
        }
        
        scheduleSeq++;
        g = new Game();
        schedule.scheduleRepeating(g, scheduleSeq, 1);
        
        //To randomzie the agents order
        Collections.shuffle(agentArr);
        Collections.shuffle(agentArr);
        Collections.shuffle(agentArr);
        
        
        //To give each agent an ID after shuffling the array
        for(int i = 0; i < numAgents; i++)
            agentArr.get(i).id = i;
        
        
        //FIX THISS -- Should I add this to the Game Class?
        double agentsToWearMask = (percForcedToWearMask / 100.0) * (double) numAgents;
        
        //To set a percentage of the population to wear masks
        for(int j = 0; j < agentArr.size(); j++)
        {
            int randIndex1;
            int randPlayer1;

            randIndex1 = random.nextInt((numbers.size()));
            randPlayer1 = numbers.get(randIndex1);
            numbers.remove(randIndex1);
            
            if(j < agentsToWearMask)
            {
                agentArr.get(randPlayer1).isWearingMask = true;
                agentArr.get(randPlayer1).isForcedToWearMask = true;
                
                //agentArr.get(randPlayer1).immunity += maskEffectiveness;
                agentArr.get(randPlayer1).calcuatedImmunity += maskEffectiveness;
                
                if(agentArr.get(randPlayer1).calcuatedImmunity > 100)
                    agentArr.get(randPlayer1).calcuatedImmunity = 100;
            }
        }
        
        
        numbers.clear();
        for(int i = 0; i < numAgents; i++)
        {
            numbers.add(i);
        }
        
        double agentsPractingSocialDistance = (percSocialDistancing / 100.0) * (double) numAgents;
        
        //To set a percentage of the population to practice social distancing
        for(int j = 0; j < agentArr.size(); j++)
        {
            int randIndex1;
            int randPlayer1;

            randIndex1 = random.nextInt((numbers.size()));
            randPlayer1 = numbers.get(randIndex1);
            numbers.remove(randIndex1);
            
            if(j < agentsPractingSocialDistance)
            {
                agentArr.get(randPlayer1).isPractingSocailDistance = true;
                //agentArr.get(randPlayer1).immunity += socialDistancingEffectiveness;
                agentArr.get(randPlayer1).calcuatedImmunity += socialDistancingEffectiveness;

                if(agentArr.get(randPlayer1).calcuatedImmunity > 100)
                    agentArr.get(randPlayer1).calcuatedImmunity = 100;
            }
            
            //Set the prob. of infection
            agentArr.get(randPlayer1).probInfection = 1.0 - (agentArr.get(randPlayer1).calcuatedImmunity / 100.0);        
        }
        
        
        
        
        //To add agents to the yard
        for (int i = 0; i < agentArr.size(); i++)
        {
            Agent agent = agentArr.get(i);
            
            yard.setObjectLocation(agent,
                new Double2D(yard.getWidth() * 0.5 + random.nextDouble() - 0.5,
                    yard.getHeight() * 0.5 + random.nextDouble() - 0.5));
        }
    }

//    public static void main(String[] args) {
//        doLoop(Model.class, args);
//        System.exit(0);
//    }
}
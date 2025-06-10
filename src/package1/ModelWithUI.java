package package1;

import sim.portrayal.network.*;
import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.*;
import sim.portrayal.simple.*;
import javax.swing.*;
import java.awt.*;

public class ModelWithUI extends GUIState{

    public Display2D display;
    public JFrame displayFrame;
    ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
    NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();
    
    public static void main(String[] args) {
        Console c = new Console(new ModelWithUI());
        c.setVisible(true);
    }

    public ModelWithUI() {
        super(new Model(System.currentTimeMillis()));
    }

    public ModelWithUI(SimState state) {
        super(state);
    }

    public static String getName() {
        return "Agents Yard";
    }
    
    public Object getSimulationInspectedObject() { return state; }
    //public Object getSimulationInspectedObject2() { return state; }
    
    public Inspector getInspector()
    {
        Inspector i = super.getInspector();
        i.setVolatile(true);
        return i;
    }
    
    public void start() {
        super.start();
        setupPortrayals();
    }

    public void load(SimState state) {
        super.load(state);
        setupPortrayals();
    }

    public void setupPortrayals() {
        Model model = (Model) state;
        
        yardPortrayal.setField( model.yard );
        yardPortrayal.setPortrayalForAll(new OvalPortrayal2D());
        
        yardPortrayal.setPortrayalForAll(new OvalPortrayal2D() {
            public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
                Agent agent = (Agent) object;
                
                //Case Agent is SUSCEPTIBLE and is wearing a mask - his color is dark blue
                if (agent.status == Status.SUSCEPTIBLE && agent.isWearingMask) {
                    paint = new Color(0, 0, 126);
                    //0, 0, 255
                    //173, 216, 230
                    super.draw(object, graphics, info);
                }
                //Case Agent is SUSCEPTIBLE and is not wearing a mask - his color is default blue
                if (agent.status == Status.SUSCEPTIBLE && !agent.isWearingMask) {
                    paint = new Color(0, 0, 255);
                    //0, 0, 255
                    //173, 216, 230
                    super.draw(object, graphics, info);
                }
                
                if(agent.status == Status.INFECTED)
                {
                    paint = new Color(255, 0, 0);
                    super.draw(object, graphics, info);
                }
                
                if(agent.status == Status.RECOVERED)
                {
                    paint = new Color(0,255,0);
                    super.draw(object, graphics, info); 
                }
            }
        });
        
        //buddiesPortrayal.setField( new SpatialNetwork2D( model.yard, model.g.pairs ));
        buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
        
        display.reset();
        display.setBackdrop(Color.white);
        display.repaint();
    }

    public void init(Controller c) {
        super.init(c);
        display = new Display2D(750, 750,this);
        display.setClipping(false);
        
        displayFrame = display.createFrame();
        displayFrame.setTitle("Agents Yard Display");
        c.registerFrame(displayFrame); // so the frame appears in the "Display" list
        displayFrame.setVisible(true);
        display.attach( buddiesPortrayal, "Buddies" );
        display.attach( yardPortrayal, "Yard" );
    }

    public void quit() {
        super.quit();
        if (displayFrame != null) {
            displayFrame.dispose();
        }
        displayFrame = null;
        display = null;
    }
}

# COVID-Inspired Agent-Based SIR Model Simulation

This project is an agent-based simulation of the SIR (Susceptible-Infected-Recovered) epidemiological model, developed in Java using the [MASON](https://cs.gmu.edu/~eclab/projects/mason/) multi-agent simulation toolkit.

Agents represent individuals in a population who may become infected, recover, and change behavior (e.g., mask-wearing or social distancing) based on awareness, utility, and peer influence.

---

## 🧠 Features

- Simulation of disease spread using SIR dynamics
- Agents have heterogeneous immunity, awareness, and decision-making
- Mask-wearing and social distancing are dynamically adopted
- Infection probability and recovery are immunity-dependent
- Customizable parameters for:
  - Infection radius
  - Mask effectiveness
  - Social distancing effectiveness
  - Initial infection rate
- Graphical simulation interface using MASON GUI
- Daily statistics tracking (infected, recovered, masked, etc.)

---

## 📸 Screenshots

| Initial Launch | Parameter Settings |
|----------------|--------------------|
| ![Initial UI](screenshots/initial-launch.png) | ![Model Settings](screenshots/model-settings.png) |

> ℹ️ Place your screenshots in a `screenshots/` folder in the project root.

---

## 📦 Run with Pre-built JAR

If you already have the **Java SE Development Kit (JDK)** installed, you can run the simulation using the provided `SIR-Model.jar`.

### 🔧 Prerequisites

- Java SE Development Kit 8 or newer  
  👉 [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)

### ▶️ How to Run

From your terminal or command prompt:

```bash
java -jar SIR-Model.jar
````

You’ll see a window like this:

![Initial Launch](screenshots/initial-launch.png)

This is the **MASON simulation interface**. To begin and control the simulation:

* ▶️ **Play** to start
* ⏸️ **Pause** to temporarily stop
* ⏹️ **Stop** to reset

These buttons are located at the **bottom-left** of the main window.

---

### 🔧 Customizing Simulation Settings

Click on the **“Model”** tab (top right) to configure simulation parameters like:

* Infection radius
* Mask effectiveness
* Social distancing % and effectiveness
* Number of agents
* Weights affecting behavior (e.g., awareness, mocking cost)

Here’s what the configuration panel looks like:

![Model Settings](screenshots/model-settings.png)

All values can be adjusted before or during simulation runs.

---

## 📊 Case Studies & Live Monitoring

This simulation can be used to conduct custom case studies by adjusting parameters such as:

* Infection spread based on mask adoption or social distancing
* Utility-based decision-making under peer pressure (mocking cost)
* Immunity-based recovery timelines
* Initial population infection rates

### 🔍 Live Charting & Monitoring Demo

Here's a quick demonstration of how to enable and observe simulation charts while the model is running:

![Live Monitoring Demo](screenshots/live-monitoring.gif)

> 📝 To do this:
>
> * Click on the **Inspectors** tab
> * Use the 🔍 (magnifying glass) icon to monitor variables like `Infected`, `Recovered`, `Susceptible`, etc.
> * Charts update **live** as the simulation progresses

You can capture screenshots or record data at intervals for further analysis.

---

### ✅ Example Study Ideas

* **Mask Mandates vs. Voluntary Mask Use:** Vary `percForcedToWearMask` and compare infection curves
* **Effectiveness of Social Distancing:** Toggle `percSocialDistancing` and analyze infection suppression
* **Awareness Impact:** Change `Weight_Awareness` to test how risk awareness changes behavior outcomes
* **Cost-Benefit Tradeoffs:** Observe how increasing the `Weight_CostOfWearingMask` or `Weight_MockingCost` affects mask adoption

---

## 🛠 Development Setup (NetBeans)

### 1. Open in NetBeans

1. Open NetBeans.
2. Go to **File > Open Project**.
3. Select the folder containing `nbproject/`.

### 2. Add Required Libraries

1. Right-click the project > **Properties** > **Libraries**.
2. Under the **Compile** tab, click **Add JAR/Folder...**.
3. Add all `.jar` files from the `lib/` directory:

   * `mason.21.jar`
   * `jfreechart-1.0.17.jar`
   * `jcommon-1.0.21.jar`
   * `itext-1.2.jar`
   * `bsh-2.0b4.jar`
   * `jmf.jar`
   * `portfolio.jar`

### 3. Run from Source

Run `ModelWithUI.java` to launch the GUI:

```java
public static void main(String[] args) {
    Console c = new Console(new ModelWithUI());
    c.setVisible(true);
}
```

---

## 🧪 Parameters You Can Tune

* `numAgents`: Number of agents
* `maskEffectiveness`: How effective masks are
* `percForcedToWearMask`: % of agents forced to wear masks
* `percSocialDistancing`: % of agents practicing social distancing
* `infectionRadius`: Range at which infection can spread
* `stepsToStartNewInfection`: When the first infection wave begins

---

## 📚 Technologies Used

* **Java 8+**
* **MASON** (Multi-Agent Simulation Toolkit)
* **JFreeChart**, **iText** (for stats & optional exports)
* **NetBeans IDE** (recommended for dev)

---

## 👤 Author

This project was developed as part of a simulation-based exploration of infectious disease dynamics using agent-based modeling.

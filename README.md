# COVID-Inspired Agent-Based SIR Model Simulation

This project is an agent-based simulation of the SIR (Susceptible-Infected-Recovered) epidemiological model, developed in Java using the [MASON](https://cs.gmu.edu/~eclab/projects/mason/) multi-agent simulation toolkit.

Agents represent individuals in a population who may become infected, recover, and change behavior (e.g., mask-wearing or social distancing) based on awareness, utility, and peer influence.

---

## 🧠 Features

- Simulation of disease spread using SIR dynamics
- Agents have heterogeneous immunity, awareness, and decision-making
- Mask-wearing and social distancing are dynamically adopted
- Infection probability and recovery are immunity-dependent
- Customizable parameters for like:
  - Infection radius
  - Mask effectiveness
  - Social distancing effectiveness
  - Initial infection rate
- Graphical simulation interface using MASON GUI
- Daily statistics tracking (infected, recovered, masked, etc.)

---

## 📂 How to Open This Project in NetBeans

1. **Download or clone this repository**  
   - Click the green **"Code"** button > **Download ZIP**, then extract it  
   - Or use Git:  
     ```bash
     git clone https://github.com/OmarMaged3679/covid-agent-based-sir-model.git
     ```

2. **Open NetBeans IDE**

3. Go to **File > Open Project**

4. Navigate to the extracted `covid-agent-based-sir-model/` folder and open it.

5. **Add required libraries** (if needed):  
   - Right-click the project > **Properties** > **Libraries**.
   - Under the **Compile** tab, click **Add JAR/Folder...**.
   - Add all `.jar` files from the [`libraries`](./libraries/) folder:

     * `mason.21.jar`
     * `jfreechart-1.0.17.jar`
     * `jcommon-1.0.21.jar`
     * `itext-1.2.jar`
     * `bsh-2.0b4.jar`
     * `jmf.jar`
     * `portfolio.jar`

6. **Run from Source**:

    Run `ModelWithUI.java` to launch the GUI

---

## 🚀 Run the App Without NetBeans (with Pre-built JAR)

You can also run the simulation using the provided [`dist/COVID-SIR-Model.jar`](./dist/COVID-SIR-Model.jar).

### 🔧 Prerequisites

- Java SE Development Kit 8 or newer  
  👉 [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)

### ▶ Download & Run

1. Go to [`dist/COVID-SIR-Model.jar`](./dist/COVID-SIR-Model.jar)  
   *(Click the link to download the file)*

2. Open a terminal or command prompt and run:

   ```bash
   java -jar COVID-SIR-Model.jar
   ```


You’ll see a window like this:

| Display Window | Parameter Settings Window |
|----------------|--------------------|
| ![Initial UI](screenshots/initial-launch.png) | ![Model Settings](screenshots/settings.png) |

This is the **MASON simulation interface**. To begin and control the simulation:

* ▶️ **Play** to start
* ⏸️ **Pause** to temporarily stop
* ⏹️ **Stop** to reset

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

## 📚 Technologies Used

* **Java 8+**
* **MASON** (Multi-Agent Simulation Toolkit)
* **JFreeChart**, **iText** (for stats & optional exports)
* **NetBeans IDE** (recommended for dev)

---

## ✍️ Author

This project was developed as part of a simulation-based exploration of infectious disease dynamics using agent-based modeling.
* 💻 Project by: *Omar Maged*

---

## 📜 License

Licensed under the MIT License.

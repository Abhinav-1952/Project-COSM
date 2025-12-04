<div align="center">


#  **ApexStat Engine**
### *Precision Statistical Testing in Pure Java*  
Multi-Column CSV Support • Z / T / F Tests • Auto-Detection • Manual Override

</div>

---

#  **Overview**

ApexStat Engine is a lightweight, transparent statistical testing toolkit written entirely in Java.  
It performs hypothesis testing on CSV datasets using manually implemented formulas—no external libraries.

This updated version supports **multi-column CSV files**, allowing you to pick the exact column to analyze.  
Non-numeric values and text are automatically ignored.

The toolkit performs:

- One-sample Z Test  
- One-sample T Test  
- Two-sample T Test  
- F Test  
- Z Test for Difference Between Two Means  

Designed for students, analysts, researchers, and developers who want accuracy, readability, and total control.

---

#  **Input: Multi-Column CSV Files**

Your CSV may contain:

- Multiple columns  
- Words or text  
- Headers  
- Mixed numeric + non-numeric values  

The program will:

1. Detect how many columns exist  
2. Ask which column you want to use  
3. Extract only numeric entries from that column  

Example:

```
Age,Height,Weight
20,170,65
21,165,59
NaN,180,77
```

You can choose any column (1 = Age, 2 = Height, 3 = Weight).

---

#  **Population Mean and Standard Deviation (Your Dataset)**

These values were computed from the datasets you previously uploaded.

### **Dataset 1 (Sample 1)**
Use these when asked for mean/SD in T-tests or manual Z-tests:

- **Population Mean (μ₁): 51.2568**  
- **Population Standard Deviation (σ₁): 3.5031**

---

### **Dataset 2 (Sample 2)**

- **Population Mean (μ₂): 62.1918**  
- **Population Standard Deviation (σ₂): 2.7729**

---

#  **When to Enter These Values**

### For **One-Sample Z Test (Sample 1)**  
Enter:

```
Mean = 51.2568
SD   = 3.5031
```

### For **One-Sample Z Test (Sample 2)**  
Enter:

```
Mean = 62.1918
SD   = 2.7729
```

### ✔ For **Z Test (Difference Between Means)**  
Enter:

```
SD of Sample 1 = 3.5031
SD of Sample 2 = 2.7729
```

### For T Tests  
Only **mean** is required.  
Enter the same means above unless you want to use another target population mean.

---

# **How to Run**

### 1. Place CSV files in same folder as `Main.java`
### 2. Compile:
```bash
javac Main.java
```

### 3. Run:
```bash
java Main
```

### 4. Select columns when prompted:
```
Columns detected in sample1.csv:
1. Column 1
2. Column 2
3. Column 3
Enter which column number to use:
```

### 5. Choose statistical test:
```
1. One-sample Z Test
2. One-sample T Test
3. Two-sample T Test
4. F Test
5. Z Test (Difference of Means)
```

---

#  **Why ApexStat Engine?**

- Pure Java — no external dependencies  
- Multi-column CSV handling  
- Manual + auto-detected stats  
- Fully transparent computations  
- Ideal for academic and analytic use  
- Easy to extend with new tests  

---

#  **Contributing**

Pull requests welcome — feel free to add:

- ANOVA  
- Chi-square  
- Correlation tests  
- PDF/CSV report export  

---

# License

MIT License — free for personal, educational, and commercial use.


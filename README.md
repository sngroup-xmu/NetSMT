# NetSMT: Guided-SMT solving
This project is a prototype implementation of the SMT formula simplification part of [NetSMT]():
> Xing Fang, Feiyan Ding, Bang Huang, Ziyi Wang, Gao Han, Rulan Yang, Lizhao You, Qiao Xiang,
Linghe Kong, Yutong Liu, Jiwu Shu. "Network Can Help Check Itself: Accelerating SMT-based Network
Configuration Verification Using Network Domain Knowledge", INFOCOM'24

The guided-SMT solving part of NetSMT can be found [here](https://github.com/FangStars/z3_network_guided).


## Introduction
This project builds upon the [Minesweeper](https://github.com/batfish/batfish/releases/tag/2021-03-16-minesweeper).
Our modifications aim to use network domain knowledge to enhance the SMT formula encoding.
- **Pruning Unrelated Configurations:** reduce redundant constraints and variables based on the invariant.
- **Abstracting long-width variables:** narrow the  assignment range by replacing the concrete value with the abstract value.

Besides, we implement a benchmark plugin to test the efficiency of NetSMT.

## Setup
We use IntelliJ IDEA for both development and debugging.

### Prerequisites
- **Linux Operating System** (Path format errors will occur in Windows)
- **glibc >= 2.29** (required by Z3 4.12)
- **Maven:** version 3.6.1 or higher
- **JAVA:** version 1.8
- **Git**

### Run & Debug
1. Clone project.
2. Enter the project directory, and run `mvn –f pom.xml package`.
3. Verify that Maven is set up correctly with `java -jar allinone/target/allinone-bundle-*.jar`.
4. Import the Maven project into IntelliJ IDEA and correctly set up the Maven and JDK versions.
5. Raise the default IDE file size limits.
6. Run/debug `org/batfish/allinone/Main.java` with args `-runmode interactive -loglevel info`.
   Argument `-runmode interactive` starts an interactive CLI, and Argument `-loglevel`
   controls the detail of logs with parameters `debug|info|output|warn|error`.
7. Run/debug various verifications with the command `get $question$ args`, where questions
   can be found at directory `org/batfish/minesweeper/question`. A command example is
   `get smt-reachability-benchmark benchmark=true, guided=true, topology=“/bgptopology”`

### Z3 replacement
NetSMT uses the prototype of [guided-Z3]() as the default Z3 solver. To switch to a different version of Z3, such as
a newer version or one with additional guideline instructions, you can follow these steps:
- **Z3 jar replacement:** replace the Z3 JAR path in the pom.xml file located in the main directory.
- **Z3 bin/lib replacement:** ensure that they are replaced in the actual folders where Z3 runs,
  which by default are /usr/lib for binaries and /lib for libraries. We provide script
  `tools/change_z3.sh` to replace bin/lib, and remember to modify the script with the appropriate paths.

**NOTE**: Lower versions of Z3 are not recommended as they may cause type errors in Minesweeper.

## Code Modification
All codes that we have modified are encapsulated within `ADD_BEGIN` and `ADD_END` markers.
Below, we provide an overview of the functionalities introduced by these modifications.
- **/minesweeper/smt/Encoder.java:** Set parameters for Z3 solver.
- **/minesweeper/smt/EncoderSlice.java:** Implement variable abstraction and fix the bug of Minesweeper.
- **/minesweeper/smt/Optimizations.java:** Implement unrelated configuration pruning and variable abstraction,
  and fix the bug of Minesweeper.
- **/minesweeper/smt/Optimizations.java:** Implement unrelated Configurations pruning and variable abstraction.
- **/minesweeper/question/SmtBenchmarkPlugin.java:** interface to run benchmark function.
- **/minesweeper/question/PropertyChecker.java:** Implement benchmark function.

## Evaluation
### Dataset
### Script

## BiNode source code

## Contact
- Xing Fang (xing.fang.xmu@outlook.com)
- Feiyan Ding (feiyan.ding.xmu@gmail.com)
- Bang Huang (bang.huang.xmu@outlook.com)

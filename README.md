# NetSMT: Guided-SMT solving
This project is a prototype implementation of the SMT formula simplification part of [NetSMT](https://sngroup.org.cn/work/pdf/NetSMT-INFOCOM24.pdf):
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

### Z3 replacement
NetSMT uses the prototype of [guided-Z3]() as the default Z3 solver. To switch to a different version of Z3, such as
a newer version or one with additional guideline instructions, you can follow these steps:
- **Z3 jar replacement:** replace the Z3 JAR path in the pom.xml file located in the main directory.
- **Z3 bin/lib replacement:** ensure that they are replaced in the actual folders where Z3 runs,
  which by default are `/usr/bin` for binaries and `/usr/lib` for libraries. We provide script
  `tools/change_z3.sh` to replace bin/lib, and remember to modify the script with the appropriate paths.

**NOTE**: Lower versions of Z3 are not recommended as they may cause type errors in Minesweeper.

### Run & Debug
1. Clone project.
2. Enter the project directory, and run `mvn –f pom.xml package`.
3. Verify that Maven is set up correctly with `java -jar allinone/target/allinone-bundle-*.jar`.
4. Import the Maven project into IntelliJ IDEA and correctly set up the Maven and JDK versions.
5. Raise the default IDE file size limits.
6. Run/debug `org/batfish/allinone/Main.java` with args `-runmode interactive -loglevel info`.
   Argument `-runmode interactive` starts an interactive CLI, and Argument `-loglevel`
   controls the detail of logs with parameters `debug|info|output|warn|error`.
7. For more details, please refer to [Reproduction](#reproduction).

## Code Modification
All codes that we have modified are encapsulated within `ADD_BEGIN` and `ADD_END` markers.
Below, we provide an overview of the functionalities introduced by these modifications.
- **/minesweeper/smt/Encoder.java:** Set parameters for Z3 solver.
- **/minesweeper/smt/EncoderSlice.java:** Implement variable abstraction and fix the bug of Minesweeper.
- **/minesweeper/smt/Optimizations.java:** Implement unrelated configuration pruning and variable abstraction,
  and fix the bug of Minesweeper.
- **/minesweeper/smt/TransferSSA.java:** Implement unrelated Configurations pruning and variable abstraction.
- **/minesweeper/smt/PropertyChecker.java:** Implement benchmark function.
- **/minesweeper/question/SmtBenchmarkPlugin.java:** interface to run benchmark function.
- **/minesweeper/Graph.java:** generate topology file used by Z3.

## Evaluation
### Dataset
- We provide our experimental networks in the folder `./dataset`.
- There are two types of network: WAN and DCN, and we provide the default and disturbed configurations for both networks. 
- In the default configurations, all the routers are reachable, while in the disturbed configurations, some of them become unreachable.
- Under each network topology directory, there is a configuration folder (`configs`) and a txt file (`node_pairs.txt`) that stores the node pairs to be verified.

### Reproduction
1. Run the Batfish service.
```
java -jar allinone/target/allinone-bundle-*.jar -runmode interactive -loglevel info -batfishmode workservice -coordinatorargs "-templatedirs questions"
```
2. Init a network snapshot. (Let's take `cogentco_lon` under default configurations as an example.)
```
init-snapshot dataset/wan/default/cogentco_lon
```
3. Verify the five variants of the topology.
```
get smt-benchmark benchmark=true, networkType=0, topologyPath="wan/default/cogentco_lon", reduction=true
```
- `benchmark`: whether to record the Z3 solving statistics.
- `networkType`: type of network, 0 for WAN and 1 for DCN.
- `topologyPath`: your topology path in the `./dataset` directory. 
- `reduction`: whether to apply the SMT formula simplification.
4. Check the results under the folder `./result`, the meaning of each file is as follows:

| Filename | Reduction | Meaning |
| ---- | ---- | ---- |
| guided.txt, guided_failure_k.txt | true | the result of `NetSMT` | 
| ori.txt, ori_failure_k.txt | true | the result of `NetSMT w/o guidance` |
| guided.txt, guided_failure_k.txt | false | the result of `NetSMT w/o simplification` |
| ori.txt, ori_failure_k.txt | false | the result of `Minesweeper` |

5. Init and verify other networks, then you can get similar results to those in our paper, which is provided in the `./experiment_result` folder.

**NOTE**: 
- The number of conflits may vary slightly when running on different machines or enviroments.
- We set the verification timeout to 1 hour, thus for some large networks or complex properties, there will be a large number (90+%) of timeouts, and we skip their verification and name the result file with the `.timeout` suffix to save experimental time.
- We skip the forwarding verification of disturbed networks, since its verification in this case is highly similar to reachability. However, you can still verify it with our code.

### BiNode source code
We modify [BiNode](https://github.com/xiaozheshao/BiNode/tree/binode_ebgp) to introduce the guidance techniques and take it as one of the baseline of DCN verification, the source code can be found in `binode` branch.

## Contact
- Xing Fang (xing.fang.xmu@outlook.com)
- Feiyan Ding (feiyan.ding.xmu@gmail.com)
- Bang Huang (bang.huang.xmu@outlook.com)

# BiNode_ebgp

## Introduction
This project Modifies [BiNode_ebgp](https://github.com/xiaozheshao/BiNode/tree/binode_ebgp).
Our modifications aim make BiNode work on our data center network dataset.

## Setup
Same as [minesweeper-guided]()

**NOTE**: the project directory is slightly different from `minesweeper-guided`,
where `minesweeper-guided` only contains directory `projects`.

## Code Modification
All codes that we have modified are encapsulated within `ADD_BEGIN` and `ADD_END` markers.
Below, we provide an overview of the functionalities introduced by these modifications.
- **/minesweeper/smt/EncoderSlice.java:** change customer judgement condition and add default constraint.
- **/minesweeper/smt/Optimizations.java** hard-coded load balance implementation.


## Evaluation
Same as  [minesweeper-guided]()


## Contact
- Xing Fang (xing.fang.xmu@outlook.com)
- Feiyan Ding (feiyan.ding.xmu@gmail.com)
- Bang Huang (bang.huang.xmu@outlook.com)
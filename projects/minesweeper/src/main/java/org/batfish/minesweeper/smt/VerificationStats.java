package org.batfish.minesweeper.smt;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.function.Function;
import org.batfish.common.BatfishException;

/**
 * A simple class to record a few statistics about the network encoding and how long it spends in Z3
 * to solve the instance.
 *
 * @author Ryan Beckett
 */
public class VerificationStats {
  private static final String PROP_AVERAGE_NUM_NODES = "avgNumNodes";
  private static final String PROP_MAX_NUM_NODES = "maxNumNodes";
  private static final String PROP_MIN_NUM_NODES = "minNumNodes";
  private static final String PROP_AVERAGE_NUM_EDGES = "avgNumEdges";
  private static final String PROP_MAX_NUM_EDGES = "maxNumEdges";
  private static final String PROP_MIN_NUM_EDGES = "minNumEdges";
  private static final String PROP_AVERAGE_NUM_VARS = "avgNumVariables";
  private static final String PROP_MAX_NUM_VARS = "maxNumVariables";
  private static final String PROP_MIN_NUM_VARS = "minNumVariables";
  private static final String PROP_AVERAGE_NUM_CONSTRAINTS = "avgNumConstraints";
  private static final String PROP_MAX_NUM_CONSTRAINTS = "maxNumConstraints";
  private static final String PROP_MIN_NUM_CONSTRAINTS = "minNumConstraints";
  private static final String PROP_AVERAGE_SOLVER_TIME = "avgSolverTime";
  private static final String PROP_MAX_SOLVER_TIME = "maxSolverTime";
  private static final String PROP_MIN_SOLVER_TIME = "minSolverTime";
  private static final String PROP_AVERAGE_COMPUTE_EC_TIME = "avgComputeEcTime";
  private static final String PROP_MAX_COMPUTE_EC_TIME = "maxComputeEcTime";
  private static final String PROP_MIN_COMPUTE_EC_TIME = "minComputeEcTime";
  private static final String PROP_AVERAGE_ENCODING_TIME = "avgEncodingTime";
  private static final String PROP_MAX_ENCODING_TIME = "maxEncodingTime";
  private static final String PROP_MIN_ENCODING_TIME = "minEncodingTime";
  private static final String PROP_TIME_CREATE_BDDS = "timeCreateBdds";
  private static final String PROP_TOTAL_TIME = "totalTime";
  private static final String PROP_NUM_ECS = "numEcs";

  private static final String PROP_DECISIONS = "decisions";
  private static final String PROP_CONFLICTS = "conflicts";
  private static final String PROP_AVERAGE_Z3_TIME = "avgZ3Time";
  private static final String PROP_MAX_Z3_TIME = "maxZ3Time";
  private static final String PROP_MIN_Z3_TIME = "minZ3Time";
  private static final String PROP_AVERAGE_Z3_TOTAL_TIME = "avgZ3TotalTime";
  private static final String PROP_MAX_Z3_TOTAL_TIME = "maxZ3TotalTime";
  private static final String PROP_MIN_Z3_TOTAL_TIME = "minZ3TotalTime";
  private double _avgNumNodes;

  private double _maxNumNodes;

  private double _minNumNodes;

  private double _avgNumEdges;

  private double _maxNumEdges;

  private double _minNumEdges;

  private double _avgNumVariables;

  private double _maxNumVariables;

  private double _minNumVariables;

  private double _avgNumConstraints;

  private double _maxNumConstraints;

  private double _minNumConstraints;

  private double _avgSolverTime;

  private double _maxSolverTime;

  private double _minSolverTime;

  private double _avgComputeEcTime;

  private double _maxComputeEcTime;

  private double _minComputeEcTime;

  private double _avgEncodingTime;

  private double _maxEncodingTime;

  private double _minEncodingTime;

  private double _timeCreateBdds;

  private double _totalTime;

  private int _numEcs;

  private int _decisions;

  private int _conflicts;

  private double _avgZ3Time;

  private double _maxZ3Time;

  private double _minZ3Time;

  private double _avgZ3TotalTime;

  private double _maxZ3TotalTime;

  private double _minZ3TotalTime;

  private static double min(List<VerificationStats> stats, Function<VerificationStats, Double> f) {
    double min = -1;
    for (VerificationStats stat : stats) {
      double d = f.apply(stat);
      if (min < 0 || d < min) {
        min = d;
      }
    }
    return min;
  }

  private static double max(List<VerificationStats> stats, Function<VerificationStats, Double> f) {
    double max = 0;
    for (VerificationStats stat : stats) {
      double d = f.apply(stat);
      if (d > max) {
        max = d;
      }
    }
    return max;
  }

  private static double avg(List<VerificationStats> stats, Function<VerificationStats, Double> f) {
    double tot = 0;
    for (VerificationStats stat : stats) {
      double d = f.apply(stat);
      tot = tot + d;
    }
    return (tot / stats.size());
  }

  static VerificationStats combineAll(List<VerificationStats> allStats, long totalTime) {
    if (allStats.isEmpty()) {
      throw new BatfishException("Empty collection of statistics");
    }
    VerificationStats newStats = new VerificationStats();
    newStats.setAvgNumNodes(avg(allStats, VerificationStats::getAvgNumNodes));
    newStats.setMaxNumNodes(max(allStats, VerificationStats::getAvgNumNodes));
    newStats.setMinNumNodes(min(allStats, VerificationStats::getAvgNumNodes));
    newStats.setAvgNumEdges(avg(allStats, VerificationStats::getAvgNumEdges));
    newStats.setMaxNumEdges(max(allStats, VerificationStats::getAvgNumEdges));
    newStats.setMinNumEdges(min(allStats, VerificationStats::getAvgNumEdges));
    newStats.setAvgNumVariables(avg(allStats, VerificationStats::getAvgNumVariables));
    newStats.setMaxNumVariables(max(allStats, VerificationStats::getAvgNumVariables));
    newStats.setMinNumVariables(min(allStats, VerificationStats::getAvgNumVariables));
    newStats.setAvgNumConstraints(avg(allStats, VerificationStats::getAvgNumConstraints));
    newStats.setMaxNumConstraints(max(allStats, VerificationStats::getAvgNumConstraints));
    newStats.setMinNumConstraints(min(allStats, VerificationStats::getAvgNumConstraints));
    newStats.setAvgComputeEcTime(avg(allStats, VerificationStats::getAvgComputeEcTime));
    newStats.setMaxComputeEcTime(max(allStats, VerificationStats::getAvgComputeEcTime));
    newStats.setMinComputeEcTime(min(allStats, VerificationStats::getAvgComputeEcTime));
    newStats.setAvgEncodingTime(avg(allStats, VerificationStats::getAvgEncodingTime));
    newStats.setMaxEncodingTime(max(allStats, VerificationStats::getAvgEncodingTime));
    newStats.setMinEncodingTime(min(allStats, VerificationStats::getAvgEncodingTime));
    newStats.setAvgSolverTime(avg(allStats, VerificationStats::getAvgSolverTime));
    newStats.setMaxSolverTime(max(allStats, VerificationStats::getAvgSolverTime));
    newStats.setMinSolverTime(min(allStats, VerificationStats::getAvgSolverTime));
    newStats.setAvgZ3Time(avg(allStats, VerificationStats::getAvgZ3Time));
    newStats.setMaxZ3Time(max(allStats, VerificationStats::getAvgZ3Time));
    newStats.setMinZ3Time(min(allStats, VerificationStats::getAvgZ3Time));
    newStats.setAvgZ3TotalTime(avg(allStats, VerificationStats::getAvgZ3TotalTime));
    newStats.setMaxZ3TotalTime(max(allStats, VerificationStats::getAvgZ3TotalTime));
    newStats.setMinZ3TotalTime(min(allStats, VerificationStats::getAvgZ3TotalTime));
    newStats.setDecisions(allStats.get(0).getDecisions());
    newStats.setConflicts(allStats.get(0).getConflicts());
    newStats.setTimeCreateBdds(allStats.get(0).getTimeCreateBdds());
    newStats.setNumEcs(allStats.size());
    newStats.setTotalTime(totalTime);
    return newStats;
  }

  @JsonProperty(PROP_AVERAGE_NUM_NODES)
  public double getAvgNumNodes() {
    return _avgNumNodes;
  }

  @JsonProperty(PROP_AVERAGE_NUM_NODES)
  public void setAvgNumNodes(double x) {
    _avgNumNodes = x;
  }

  @JsonProperty(PROP_MAX_NUM_NODES)
  public double getMaxNumNodes() {
    return _maxNumNodes;
  }

  @JsonProperty(PROP_MAX_NUM_NODES)
  public void setMaxNumNodes(double x) {
    _maxNumNodes = x;
  }

  @JsonProperty(PROP_MIN_NUM_NODES)
  public double getMinNumNodes() {
    return _minNumNodes;
  }

  @JsonProperty(PROP_MIN_NUM_NODES)
  public void setMinNumNodes(double x) {
    _minNumNodes = x;
  }

  @JsonProperty(PROP_AVERAGE_NUM_EDGES)
  public double getAvgNumEdges() {
    return _avgNumEdges;
  }

  @JsonProperty(PROP_AVERAGE_NUM_EDGES)
  public void setAvgNumEdges(double x) {
    _avgNumEdges = x;
  }

  @JsonProperty(PROP_MAX_NUM_EDGES)
  public double getMaxNumEdges() {
    return _maxNumEdges;
  }

  @JsonProperty(PROP_MAX_NUM_EDGES)
  public void setMaxNumEdges(double x) {
    _maxNumEdges = x;
  }

  @JsonProperty(PROP_MIN_NUM_EDGES)
  public double getMinNumEdges() {
    return _minNumEdges;
  }

  @JsonProperty(PROP_MIN_NUM_EDGES)
  public void setMinNumEdges(double x) {
    _minNumEdges = x;
  }

  @JsonProperty(PROP_AVERAGE_NUM_VARS)
  public double getAvgNumVariables() {
    return _avgNumVariables;
  }

  @JsonProperty(PROP_AVERAGE_NUM_VARS)
  public void setAvgNumVariables(double x) {
    _avgNumVariables = x;
  }

  @JsonProperty(PROP_MAX_NUM_VARS)
  public double getMaxNumVariables() {
    return _maxNumVariables;
  }

  @JsonProperty(PROP_MAX_NUM_VARS)
  public void setMaxNumVariables(double x) {
    _maxNumVariables = x;
  }

  @JsonProperty(PROP_MIN_NUM_VARS)
  public double getMinNumVariables() {
    return _minNumVariables;
  }

  @JsonProperty(PROP_MIN_NUM_VARS)
  public void setMinNumVariables(double x) {
    _minNumVariables = x;
  }

  @JsonProperty(PROP_AVERAGE_NUM_CONSTRAINTS)
  public double getAvgNumConstraints() {
    return _avgNumConstraints;
  }

  @JsonProperty(PROP_AVERAGE_NUM_CONSTRAINTS)
  public void setAvgNumConstraints(double x) {
    _avgNumConstraints = x;
  }

  @JsonProperty(PROP_MAX_NUM_CONSTRAINTS)
  public double getMaxNumConstraints() {
    return _maxNumConstraints;
  }

  @JsonProperty(PROP_MAX_NUM_CONSTRAINTS)
  public void setMaxNumConstraints(double x) {
    _maxNumConstraints = x;
  }

  @JsonProperty(PROP_MIN_NUM_CONSTRAINTS)
  public double getMinNumConstraints() {
    return _minNumConstraints;
  }

  @JsonProperty(PROP_MIN_NUM_CONSTRAINTS)
  public void setMinNumConstraints(double x) {
    _minNumConstraints = x;
  }

  @JsonProperty(PROP_AVERAGE_SOLVER_TIME)
  public double getAvgSolverTime() {
    return _avgSolverTime;
  }

  @JsonProperty(PROP_AVERAGE_SOLVER_TIME)
  public void setAvgSolverTime(double x) {
    _avgSolverTime = x;
  }

  @JsonProperty(PROP_MAX_SOLVER_TIME)
  public double getMaxSolverTime() {
    return _maxSolverTime;
  }

  @JsonProperty(PROP_MAX_SOLVER_TIME)
  public void setMaxSolverTime(double x) {
    _maxSolverTime = x;
  }

  @JsonProperty(PROP_MIN_SOLVER_TIME)
  public double getMinSolverTime() {
    return _minSolverTime;
  }

  @JsonProperty(PROP_MIN_SOLVER_TIME)
  public void setMinSolverTime(double x) {
    _minSolverTime = x;
  }

  @JsonProperty(PROP_AVERAGE_COMPUTE_EC_TIME)
  public double getAvgComputeEcTime() {
    return _avgComputeEcTime;
  }

  @JsonProperty(PROP_AVERAGE_COMPUTE_EC_TIME)
  public void setAvgComputeEcTime(double x) {
    _avgComputeEcTime = x;
  }

  @JsonProperty(PROP_MAX_COMPUTE_EC_TIME)
  public double getMaxComputeEcTime() {
    return _maxComputeEcTime;
  }

  @JsonProperty(PROP_MAX_COMPUTE_EC_TIME)
  public void setMaxComputeEcTime(double x) {
    _maxComputeEcTime = x;
  }

  @JsonProperty(PROP_MIN_COMPUTE_EC_TIME)
  public double getMinComputeEcTime() {
    return _minComputeEcTime;
  }

  @JsonProperty(PROP_MIN_COMPUTE_EC_TIME)
  public void setMinComputeEcTime(double x) {
    _minComputeEcTime = x;
  }

  @JsonProperty(PROP_TIME_CREATE_BDDS)
  public double getTimeCreateBdds() {
    return _timeCreateBdds;
  }

  @JsonProperty(PROP_TIME_CREATE_BDDS)
  public void setTimeCreateBdds(double x) {
    _timeCreateBdds = x;
  }

  @JsonProperty(PROP_AVERAGE_ENCODING_TIME)
  public double getAvgEncodingTime() {
    return _avgEncodingTime;
  }

  @JsonProperty(PROP_AVERAGE_ENCODING_TIME)
  public void setAvgEncodingTime(double x) {
    _avgEncodingTime = x;
  }

  @JsonProperty(PROP_MAX_ENCODING_TIME)
  public double getMaxEncodingTime() {
    return _maxEncodingTime;
  }

  @JsonProperty(PROP_MAX_ENCODING_TIME)
  public void setMaxEncodingTime(double x) {
    _maxEncodingTime = x;
  }

  @JsonProperty(PROP_MIN_ENCODING_TIME)
  public double getMinEncodingTime() {
    return _minEncodingTime;
  }

  @JsonProperty(PROP_MIN_ENCODING_TIME)
  public void setMinEncodingTime(double x) {
    _minEncodingTime = x;
  }

  @JsonProperty(PROP_TOTAL_TIME)
  public double getTotalTime() {
    return _totalTime;
  }

  @JsonProperty(PROP_TOTAL_TIME)
  public void setTotalTime(double x) {
    _totalTime = x;
  }

  @JsonProperty(PROP_NUM_ECS)
  public int getNumEcs() {
    return _numEcs;
  }

  @JsonProperty(PROP_NUM_ECS)
  public void setNumEcs(int x) {
    _numEcs = x;
  }

  @JsonProperty(PROP_DECISIONS)
  public int getDecisions() {
    return _decisions;
  }
  @JsonProperty(PROP_DECISIONS)
  public void setDecisions(int decisions) {
    _decisions = decisions;
  }

  @JsonProperty(PROP_CONFLICTS)
  public int getConflicts() {
    return _conflicts;
  }
  @JsonProperty(PROP_CONFLICTS)
  public void setConflicts(int conflicts) {
    _conflicts = conflicts;
  }

  @JsonProperty(PROP_AVERAGE_Z3_TIME)
  public double getAvgZ3Time() {
    return _avgZ3Time;
  }

  @JsonProperty(PROP_AVERAGE_Z3_TIME)
  public void setAvgZ3Time(double avgZ3Time) {
    _avgZ3Time = avgZ3Time;
  }

  @JsonProperty(PROP_MAX_Z3_TIME)
  public double getMaxZ3Time() {
    return _maxZ3Time;
  }

  @JsonProperty(PROP_MAX_Z3_TIME)
  public void setMaxZ3Time(double maxZ3Time) {
    _maxZ3Time = maxZ3Time;
  }

  @JsonProperty(PROP_MIN_Z3_TIME)
  public double getMinZ3Time() {
    return _minZ3Time;
  }

  @JsonProperty(PROP_MIN_Z3_TIME)
  public void setMinZ3Time(double minZ3Time) {
    _minZ3Time = minZ3Time;
  }

  @JsonProperty(PROP_AVERAGE_Z3_TOTAL_TIME)
  public double getAvgZ3TotalTime() {
    return _avgZ3TotalTime;
  }

  @JsonProperty(PROP_AVERAGE_Z3_TOTAL_TIME)
  public void setAvgZ3TotalTime(double avgZ3TotalTime) {
    _avgZ3TotalTime = avgZ3TotalTime;
  }

  @JsonProperty(PROP_MAX_Z3_TOTAL_TIME)
  public double getMaxZ3TotalTime() {
    return _maxZ3TotalTime;
  }

  @JsonProperty(PROP_MAX_Z3_TOTAL_TIME)
  public void setMaxZ3TotalTime(double maxZ3TotalTime) {
    _maxZ3TotalTime = maxZ3TotalTime;
  }

  @JsonProperty(PROP_MIN_Z3_TOTAL_TIME)
  public double getMinZ3TotalTime() {
    return _minZ3TotalTime;
  }

  @JsonProperty(PROP_MIN_Z3_TOTAL_TIME)
  public void setMinZ3TotalTime(double minZ3TotalTime) {
    _minZ3TotalTime = minZ3TotalTime;
  }

}

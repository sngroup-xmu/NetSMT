package org.batfish.minesweeper.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.service.AutoService;
import org.batfish.common.Answerer;
import org.batfish.common.NetworkSnapshot;
import org.batfish.common.bdd.BDDPacket;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.plugin.Plugin;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.answers.StringAnswerElement;
import org.batfish.datamodel.questions.Question;
import org.batfish.minesweeper.smt.PropertyChecker;
import org.batfish.question.QuestionPlugin;
import org.batfish.minesweeper.smt.Optimizations;

// ADD_BEGIN
@AutoService(Plugin.class)
public class SmtBenchmarkPlugin extends QuestionPlugin {

    @Override
    protected Answerer createAnswerer(Question question, IBatfish batfish) {
        return new BenchmarAnswerer(question, batfish);
    }
    @Override
    protected Question createQuestion() {
        return new BenchmarkQuestion();
    }

    public static class BenchmarAnswerer extends Answerer {

        public BenchmarAnswerer(Question question, IBatfish batfish) {
            super(question, batfish);
        }

        @Override
        public AnswerElement answer(NetworkSnapshot snapshot) {
            BenchmarkQuestion q = (BenchmarkQuestion) _question;
            PropertyChecker p = new PropertyChecker(new BDDPacket(), _batfish);
            p.benchmarkAllProperty(snapshot, q);
            return new StringAnswerElement("benchmark successfully");
        }
    }

    public static class BenchmarkQuestion extends HeaderLocationQuestion {
        private static final boolean DEFAULT_GUIDED = false;
        // private static final boolean DEFAULT_NETWORK_DISTURBED = false;
        private static final boolean DEFAULT_MODEL_REDUCTION = false;
        private static final String DEFAULT_NETWORK_TYPE  = "";
        private static final String DEFAULT_TOPOLOGY_PATH = "";

        private static final String PROP_Z3_NETWORK_TYPE = "networkType";
        private static final String PROP_TOPOLOGY_PATH = "topologyPath";
        // private static final String PROP_NETWORK_DISTURBED = "disturbed";
        private static final String PROP_MODEL_REDUCTION = "reduction";

        private boolean _guided;
        // private boolean _disturbed;
        private boolean _reduction;
        private String _networkType;
        private String _propType;
        private String _topologyPath;
        
        public BenchmarkQuestion() {
            super();
            _guided = DEFAULT_GUIDED;
            _networkType = DEFAULT_NETWORK_TYPE;
            _topologyPath= DEFAULT_TOPOLOGY_PATH;
            // _disturbed = DEFAULT_NETWORK_DISTURBED;
            _reduction = DEFAULT_MODEL_REDUCTION;
        }

        @JsonProperty(PROP_TOPOLOGY_PATH)
        public String getTopologyPath() {
            return _topologyPath;
        }

        @JsonProperty(PROP_TOPOLOGY_PATH)
        public void setTopologyPath(String topologyPath) {
            _topologyPath = topologyPath;
        }

        public boolean getGuided() {
            return _guided;
        }

        public void setGuided(boolean guided) {
            _guided = guided;
        }

        // @JsonProperty(PROP_NETWORK_DISTURBED)
        // public boolean getDisturbed() {
        //     return _disturbed;
        // }

        // @JsonProperty(PROP_NETWORK_DISTURBED)
        // public void setDisturbed(boolean disturbed) {
        //     _disturbed = disturbed;
        // }

        @JsonProperty(PROP_MODEL_REDUCTION)
        public boolean getReduction() {
            return _reduction;
        }

        @JsonProperty(PROP_MODEL_REDUCTION)
        public void setReduction(boolean reduction) {
            _reduction = reduction;
            if (_reduction) {
                Optimizations.enableReduction();
            }
        }

        @JsonProperty(PROP_Z3_NETWORK_TYPE)
        public String getNetworkType() {
            return _networkType;
        }

        @JsonProperty(PROP_Z3_NETWORK_TYPE)
        public void setNetworkType(String networkType) {
            _networkType = networkType;
        }

        public String getProptype() {
            return _propType;
        }

        public void setProptype(String propType) {
            _propType = propType;
        }

        @Override
        public boolean getDataPlane() {
            return false;
        }

        @Override
        public String getName() {
            return "smt-benchmark";
        }
    }
}
// ADD_END
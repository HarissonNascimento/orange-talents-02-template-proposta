package br.com.zup.propostazup.client.data_analysis.enums;

import br.com.zup.propostazup.model.enums.ProposalStatus;

public enum AnalysisStatus {
    COM_RESTRICAO{
        @Override
        public ProposalStatus toProposalStatus() {
            return ProposalStatus.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        @Override
        public ProposalStatus toProposalStatus() {
            return ProposalStatus.ELEGIVEL;
        }
    };

    public abstract ProposalStatus toProposalStatus();
}

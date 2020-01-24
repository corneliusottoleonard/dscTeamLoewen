package org.vadere.state.psychology;

import org.vadere.state.psychology.cognition.GroupMembership;
import org.vadere.state.psychology.cognition.SelfCategory;
import org.vadere.state.psychology.perception.ThreatMemory;
import org.vadere.state.psychology.perception.types.Stimulus;

public class PsychologyStatus {

    // Member Variables
    private Stimulus mostImportantStimulus;
    private ThreatMemory threatMemory;
    private SelfCategory selfCategory;
    private GroupMembership groupMembership;

    // Constructors
    public PsychologyStatus() {
        this(null, new ThreatMemory(), SelfCategory.TARGET_ORIENTED, GroupMembership.OUT_GROUP);
    }

    public PsychologyStatus(Stimulus mostImportantStimulus, ThreatMemory threatMemory, SelfCategory selfCategory, GroupMembership groupMembership) {
        this.mostImportantStimulus = mostImportantStimulus;
        this.threatMemory = threatMemory;
        this.selfCategory = selfCategory;
        this.groupMembership = groupMembership;
    }

    public PsychologyStatus(PsychologyStatus other) {
        this.mostImportantStimulus = other.getMostImportantStimulus() != null ? other.getMostImportantStimulus().clone() : null;
        this.threatMemory = other.getThreatMemory() != null ? other.getThreatMemory().clone() : null;
        this.selfCategory = other.getSelfCategory();
        this.groupMembership = other.getGroupMembership();
    }

    // Getter
    public Stimulus getMostImportantStimulus() { return mostImportantStimulus; }
    public ThreatMemory getThreatMemory() { return threatMemory; }
    public SelfCategory getSelfCategory() { return selfCategory; }
    public GroupMembership getGroupMembership() { return groupMembership; }

    // Setter
    public void setMostImportantStimulus(Stimulus mostImportantStimulus) {
        this.mostImportantStimulus = mostImportantStimulus;
    }

    public void setThreatMemory(ThreatMemory threatMemory) { this.threatMemory = threatMemory; }

    public void setSelfCategory(SelfCategory selfCategory) {
        this.selfCategory = selfCategory;
    }

    public void setGroupMembership(GroupMembership groupMembership) {
        this.groupMembership = groupMembership;
    }

}

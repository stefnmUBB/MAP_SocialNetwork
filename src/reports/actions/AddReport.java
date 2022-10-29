package reports.actions;

import domain.Entity;
import reports.Report;
import reports.ReportType;

public class AddReport<ID, E extends Entity<ID>> extends Report<ID, E> {
    public AddReport(E target) {
        super(target, ReportType.ADD);
    }

    public String toString() {
        return "Added " + getTarget().toString();
    }
}

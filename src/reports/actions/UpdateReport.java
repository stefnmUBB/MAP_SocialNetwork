package reports.actions;

import domain.Entity;
import reports.Report;
import reports.ReportType;

public class UpdateReport<ID, E extends Entity<ID>> extends Report<ID, E> {
    public UpdateReport(E target) {
        super(target, ReportType.ADD);
    }

    public String toString() {
        return "Updated " + getTarget().toString();
    }
}
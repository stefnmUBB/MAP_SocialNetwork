package reports.actions;

import domain.Entity;
import reports.Report;
import reports.ReportType;

public class RemoveReport<ID, E extends Entity<ID>> extends Report<ID, E> {
    public RemoveReport(E target) {
        super(target, ReportType.ADD);
    }

    public String toString() {
        return "Removed " + getTarget().toString();
    }
}

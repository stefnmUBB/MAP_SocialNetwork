package factory;

import domain.Entity;
import reports.AbstractReport;
import reports.Report;
import reports.ReportType;
import reports.actions.AddReport;
import reports.actions.RemoveReport;
import reports.actions.UpdateReport;

public class ReportFactory {
    private ReportFactory() {}
    private static final ReportFactory instance = new ReportFactory();
    public static ReportFactory getInstance() { return instance; }

    public <ID,E extends  Entity<ID>> AbstractReport createReport(ReportType type, E entity) {
        switch (type)
        {
            case ADD: return new AddReport<>(entity);
            case UPDATE: return new UpdateReport<>(entity);
            case REMOVE: return new RemoveReport<>(entity);
            default: return new Report<>(entity);
        }
    }
}

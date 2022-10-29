package domain;

import java.io.Serializable;

public class Entity<ID> {
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted() {
        deleted = true;
    }

    public void unsetDeleted() {
        deleted = false;
    }
}

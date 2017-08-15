package com.codetinkerhack.persistence.prevayler;

import java.util.Date;
import org.prevayler.Transaction;

/**
 * Created by evgeniys on 15/08/2017.
 */
public class VisitorStorageTransaction implements Transaction<VisitorKeeper> {

    private static final long serialVersionUID = -2023934810496653301L;
    private String _ipToKeep;

    public VisitorStorageTransaction() {
    }

    public VisitorStorageTransaction(String ipToKeep) {
        _ipToKeep = ipToKeep;
    }

    public void executeOn(VisitorKeeper prevalentSystem, Date ignored) {
        prevalentSystem.keep(_ipToKeep);
    }
}
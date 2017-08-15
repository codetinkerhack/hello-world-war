package com.codetinkerhack.persistence;

import com.codetinkerhack.persistence.prevayler.VisitorKeeper;
import com.codetinkerhack.persistence.prevayler.VisitorStorageTransaction;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by evgeniys on 15/08/2017.
 */
public class Storage {

    private final Prevayler<VisitorKeeper> prevayler;

    public Storage() throws Exception {
        this.prevayler = PrevaylerFactory.createPrevayler(new VisitorKeeper(), "visitors");
    }

    public void store(String ip) {
        prevayler.execute(new VisitorStorageTransaction(ip));
    }

    public List<String> getLast20Visitors() {
        VisitorKeeper visitorKeeper = prevayler.prevalentSystem();
        List<String> allIps = visitorKeeper.getIps();

        int last = allIps.size() >= 20 ? 20 : allIps.size();

        return allIps.subList(allIps.size() - last, allIps.size());
    }

    public void close() throws IOException {
        prevayler.close();
    }
}

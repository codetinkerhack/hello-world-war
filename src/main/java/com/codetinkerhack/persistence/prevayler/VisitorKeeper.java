package com.codetinkerhack.persistence.prevayler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeniys on 15/08/2017.
 */
public class VisitorKeeper implements java.io.Serializable {

    private static final long serialVersionUID = 2253937139530882022L;
    private final List<String> ipList = new ArrayList<String>();

    public void keep(String ip) {
        ipList.add(ip);
    }

    public List getIps() {
        return ipList;
    }

    public String lastIp() {
        return ipList.isEmpty() ? "" : ipList.get(ipList.size() - 1);
    }

}

package com.sparkapp.spark.thread;

import java.util.ArrayList;
import java.util.List;

public class PoolTable {

    public static DiscoveryThread discover;
    public static ServerThread server;
    public static List<SocketHandler> sockets = new ArrayList<SocketHandler>();
}

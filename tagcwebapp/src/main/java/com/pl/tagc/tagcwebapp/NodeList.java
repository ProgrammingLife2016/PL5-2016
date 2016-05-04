package com.pl.tagc.tagcwebapp;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class NodeList {
    private static final CopyOnWriteArrayList<Node> nList = new CopyOnWriteArrayList<>();

    static {
        for (int i=0; i<=10; i++) {
            nList.add(
                new Node("HugeNode", i * 50, (int) (25 + Math.random() * 50), 100)
            );
        }

        for (int i=0; i<=20; i++) {
            nList.add(
                new Node("BigNode", i * 25, (int) (25 + Math.random() * 60), 80)
            );
        }

        for (int i=0; i<=50; i++) {
            nList.add(
                new Node("MediumNode", i * 10, (int) (25 + Math.random() * 70), 60)
            );
        }

        for (int i=0; i<=100; i++) {
            nList.add(
                new Node("SmallNode", i * 5, (int) (25 + Math.random() * 80), 40)
            );
        }

        for (int i=0; i<=250; i++) {
            nList.add(
                new Node("TinyNode", i * 2, (int) (25 + Math.random() * 90), 20)
            );
        }

        for (int i=0; i<10; i++) {
            nList.get(i).edges.add(new Edge(nList.get(i+1), 10));
        }

        Collections.sort(nList,
                (n1, n2) -> n1.x - n2.x);

        for (int i=0; i<100; i++) {
            for (int x = 0; x < nList.size(); x++) {
                int y = x + 0;
                x += (int) (Math.random() * 10 + 1);
                if (x >= nList.size()) {
                    break;
                }
                nList.get(y).addEdge(nList.get(x), 1);
            }
        }
    }

    private NodeList(){}

    public static CopyOnWriteArrayList<Node> getInstance(){
        return nList;
    }
}
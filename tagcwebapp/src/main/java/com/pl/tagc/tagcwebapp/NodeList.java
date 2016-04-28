package com.pl.tagc.tagcwebapp;

import java.util.concurrent.CopyOnWriteArrayList;

public class NodeList {
    private static final CopyOnWriteArrayList<Node> nList = new CopyOnWriteArrayList<>();

    static {
        // Create list of customers
//    nList.add(
//        new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//    );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
//    nList.add(
//            new Node("dummyText", (int)(Math.random()*500),(int)(Math.random()*100))
//        );
        for (int i=0; i<=10; i++) {
            nList.add(
                new Node("dummyText", i * 50, 50)
            );
        }

    }

    private NodeList(){}

    public static CopyOnWriteArrayList<Node> getInstance(){
        return nList;
    }

}
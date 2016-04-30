package com.pl.tagc.tagcwebapp;

import java.util.concurrent.CopyOnWriteArrayList;

public class NodeList {
  private static final CopyOnWriteArrayList<Node> nList = new CopyOnWriteArrayList<>();

  static {
    // Create list of customers
    nList.add(
        new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
    );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );
    nList.add(
            new Node("dummyText", (int)(Math.random()*100),(int)(Math.random()*100))
        );

  }
  
  private NodeList(){}
  
  public static CopyOnWriteArrayList<Node> getInstance(){
    return nList;
  }
  
}
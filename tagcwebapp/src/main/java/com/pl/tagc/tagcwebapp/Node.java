package com.pl.tagc.tagcwebapp;



  public class Node {
    public String info;
    public int x;
    public int y;
 

	public Node() {} // JAXB needs this
  
    public Node(String info, int x, int y) {
      this.info = info;
      this.x = x;
      this.y = y;
    }
  }
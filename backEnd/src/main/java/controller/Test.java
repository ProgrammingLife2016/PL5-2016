package controller;

import datatree.DataNode;
import datatree.DataTree;

public class Test {

	public static void main(String[] args) {
		Controller c = Controller.DC;
		DataTree tree = c.getDataTree();
		DataNode node = tree.getRoot();
		System.out.println(node.getStrands().size());
		System.out.println(node.getChildren().get(1).getStrands().size());
		System.out.println(node.getChildren().get(1).getChildren().get(0).getStrands().size());
		System.out.println(node.getChildren().get(1).getChildren().get(0).getChildren().get(0).getStrands().size());
		System.out.println(node.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getStrands().size());
		System.out.println(node.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getStrands().size());
		System.out.println(node.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getStrands().size());
		System.out.println(node.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(1).getStrands().size());




	}

}

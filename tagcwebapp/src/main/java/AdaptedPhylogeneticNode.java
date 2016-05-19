

import java.util.ArrayList;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import phylogenetictree.PhylogeneticNode;

@XmlType(propOrder={"nameLabel","originalChildOrder","children","distance","annotation","id"})
public class AdaptedPhylogeneticNode {
	
    private ArrayList<PhylogeneticNode> children;

    /**
     * The name of this genome, "" if not a leaf.
     */
    private String nameLabel;
    /**
     * The distance to its parent.
     */
    private int id;
    private double distance;
    private String annotation = "";
    private int originalChildOrder;

    /**
     * returns a string of lenght 0 if the node is not a leaf.
     *
     * @return the node name label
     */
    @XmlElement(name = "name")
    public String getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(String nameLabel) {
		this.nameLabel = nameLabel;
	}
    
    /**
     * Get the distance.
     *
     * @return The distance.
     */
    @XmlElement(name = "attribute")
    private String getDistance() {
        return String.format(Locale.US,"%1.11e", distance);
    }	

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public ArrayList<PhylogeneticNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<PhylogeneticNode> children) {
		this.children = children;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public void setOriginalChildOrder(int originalChildOrder) {
		this.originalChildOrder = originalChildOrder; 
		
	}
	@XmlElement(name = "original_child_order")
	public int getOriginalChildOrder() {
		return originalChildOrder;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

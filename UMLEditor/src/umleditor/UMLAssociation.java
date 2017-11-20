/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umleditor;

/**
 *
 * @author huctw
 */
public class UMLAssociation {
    
    private String classA;
    private String classB;
    private byte type;
    private boolean pointingLeft;
    
    public UMLAssociation(String source) {
        String[] tmp = source.split(" ");
        classA = tmp[0];
        classB = tmp[2];
        if (tmp[1].contains("*")) {
            type = 1;
        }
        else if (tmp[1].contains("|")) {
            type = 0;
        }
        else if (tmp[1].contains("o")) {
            type = 2;
        }
        if (tmp[1].contains("*-") || tmp[1].contains("*.") || tmp[1].contains("<")) {
            pointingLeft = true;
        }
    }
    
    /**
     * Returns a byte representation of the type of the ass
     * @return 0 for extension, 1 for composition, 2 for aggregation
     */
    public byte getType() {
        return this.type;
    }
    
    public boolean getPointingLeft() {
        return this.pointingLeft;
    }
    
    public String getClassA() {
        return this.classA;
    }
    
    public String getClassB() {
        return this.classB;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umleditor;

import java.util.LinkedList;

/**
 *
 * @author huctw
 */
public class UMLUtilities {
    /**
     * Locates a UML class in a diagram object based on its name
     * @param name name of class
     * @param diagram Diagram to locate from
     * @return class internal representation
     */
    public static UMLClass indentifyClass(String name, UMLDiagram diagram) {
        LinkedList<UMLClass> list = diagram.getClasses();
        for(UMLClass item : list) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Identifies the visibility of an object in a class
     * @param item plantUML line containing item
     * @return 0 if private, 1 if protected, 2 if package private, 3 if public -1 if error
     */
    public static byte visibility(String item) {
        switch (item.charAt(0)) {
            case '-':
                return 0;
            case '#':
                return 1;
            case '~':
                return 2;
            case '+':
                return 3;
            default:
                return -1;
        }
    }
    
    /**
     * Checks if a line's item is an abstract field/method
     * @param item plantUML line containing item
     * @return true if abstract
     */
    public static boolean isAbstract(String item) {
        if (item.contains("{abstract}")) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a line's item is a static field/method
     * @param item plantUML line containing item
     * @return true if static
     */
    public static boolean isStatic(String item) {
        if (item.contains("{static}")) {
            return true;
        }
        return false;
    }
}

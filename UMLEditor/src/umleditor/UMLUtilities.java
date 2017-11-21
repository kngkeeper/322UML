/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umleditor;

import java.util.ArrayList;

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
        ArrayList<UMLClass> list = diagram.getClasses();
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
    
    public static void changeVisibility(String fieldName, UMLClass classof, byte level) {
            for (String item : classof.getFields()) {
                if (item.equals(fieldName)) {
                    
                }
            }
    }
    
    /**
     * Checks if a line's item is an abstract field/method
     * @param item plantUML line containing item
     * @return true if abstract
     */
    public static boolean isAbstract(String item) {
        return item.contains("{abstract}");
    }
    
    /**
     * Checks if a line's item is a static field/method
     * @param item plantUML line containing item
     * @return true if static
     */
    public static boolean isStatic(String item) {
        return item.contains("{static}");
    }
    
    public static String getClassName(String classSource) {
        String ret = null;
        String[] tmp = classSource.split(" ");
        for(int i=0;i<tmp.length;i++) {
            if (tmp[i].equals("class")) {
                ret = tmp[i+1];
                break;
            }
        }
        return ret;
    }
    
    /**
     * Takes in a plantUML source string and outputs the names of all classes
     * @param source PlantUML source string
     * @return All classes listed in source file
     */
    public static ArrayList<String> classNames(String source) {
        ArrayList<String> ret = new ArrayList();
        String[] lines = source.split("\\n");
        
        for(int i=0;i<lines.length;i++) {
            if(lines[i].contains("class")) {
                String[] words = lines[i].split(" ");
                for (int j=0;j<words.length;j++) {
                    if (words[j].equals("class")) {
                        ret.add(words[j+1]);
                    }
                }
            }
         }
        return ret;
    }
    
    /**
     * Takes in the source string and returns a list of relations in the source
     * @param source PlantUML source string
     * @return list of Strings containing the entire source lines of relations
     */
    public static ArrayList<String> relationNames(String source) {
        final String[] arrows = {"-*","-o",".*",".o","|>","*-","o-","*.","o.","<|"};
        ArrayList<String> ret = new ArrayList();
        String[] lines = source.split("\\n");
        for(int i=0;i<lines.length;i++) {
            for(String arrow : arrows) {
                if (lines[i].contains(arrow)) {
                    ret.add(lines[i]);
                }
            }
        }
        return ret;
    }
    
    /**
     * Returns the PlantUML source of a named class
     * @param source PlantUML source string
     * @param className name of class to extract
     * @return source of class with className
     */
    public static String getClassSource(String source, String className) {
        String ret = "";
        String[] lines = source.split("\\n");
        for(int i=0;i<lines.length;i++) {
            if(lines[i].contains("class") && (lines[i].contains(className + " ") || lines[i].contains(className + "\n"))) {
                if(lines[i].contains("{") && !(lines[i].contains("}"))) {
                    while(lines[i].charAt(0) != '}') {
                        ret = ret + lines[i] + "\n";
                        i++;
                    }
                    return ret;
                }
                else {
                    return lines[i];
                }
            }
        }
        return ret;
    }
    
    /**
     * Takes in plantUML source and a class name and checks if it is abstract
     * @param source full plantUML source string
     * @param className name of class to check abstractness of
     * @return 
     */
    public static boolean classIsAbstract(String source, String className) {
        String[] lines = source.split("\\n");
        for(String line : lines) {
            if(line.contains("abstract ") && (line.contains(className + " ") || line.contains(className + "\n"))) {
                return true;
            }
        }
        return false;
    }
}

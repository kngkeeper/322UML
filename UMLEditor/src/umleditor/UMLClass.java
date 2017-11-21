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
public class UMLClass {
    
    private String name;
    private boolean isAbstract;
    private ArrayList<String> fields = new ArrayList();
    private ArrayList<String> methods = new ArrayList();
    
    public UMLClass(String source) {
        String[] tmp = source.split(" ");
        for(int i=0;i<tmp.length;i++) {
            if (tmp[i].equals("class")) {
                this.name = tmp[i+1];
                break;
            }
        }
        
        isAbstract = tmp[0].equals("abstract");
        
        String contents = source.substring(source.indexOf("{"), source.lastIndexOf("}"));
        String[] contentLines = contents.split("\\n");
        for(String line : contentLines) {
            if(line.contains("()")) {
                methods.add(line);
            }
            else {
                fields.add(line);
            }
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean getAbstract() {
        return isAbstract;
    }
    
    public ArrayList<String> getFields() {
        return fields;
    }
    
    public ArrayList<String> getMethods() {
        return methods;
    }
}

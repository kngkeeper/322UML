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
public class UMLDiagram {
    
    private final String[] arrows = {"-*","-o",".*",".o","|>","*-","o-","*.","o.","<|"};
    
    private ArrayList<UMLClass> classlist = new ArrayList();
    private ArrayList<UMLAssociation> rels = new ArrayList();
    
    public UMLDiagram(String source) {
        String[] lines = source.split("\\n");
        
        for(int i=0;i<lines.length;i++) {
            if(lines[i].contains("class")) {
                if(lines[i].contains("{") && (lines[i].charAt(0) == '}')) {
                    String classSrc = "";
                    while(!lines[i].contains("}")) {
                        classSrc = classSrc + lines[i] + "\n";
                        i++;
                    }
                    classlist.add(new UMLClass(classSrc));
                }
                else {
                    classlist.add(new UMLClass(lines[i]));
                }
            }
            for(String arrow : arrows) {
                if (lines[i].contains(arrow)) {
                    rels.add(new UMLAssociation(lines[i]));
                }
            }
        }
    }
    
    /**
     * Returns the full source string as it would be right now
     * @return source string
     */
    public String getSource() {
        return null;
    }
    
    public UMLClass getUMLClassByName(String name) {
        return null;
    }
    
    public ArrayList<UMLClass> getClasses() {
        return classlist;
    }
}

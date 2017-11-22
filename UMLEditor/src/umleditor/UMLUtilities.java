/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umleditor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author huctw
 */
public class UMLUtilities {
    
    private static final String[] ARROWS = {"-*","-o",".*",".o","|>","*-","o-","*.","o.","<|","--",".."};
    private static final char[] ARROW_SYMBOLS = {'*','o','|'};
    
    /**
     * Identifies the visibility of an object in a class
     * @param item plantUML line containing item
     * @return char of visibility level in plantUML terms, space for none
     */
    public static char getVisibility(String item) {
        char[] line = item.toCharArray();
        for(char c : line) {
            if(c == '-' || c == '#' || c == '~' || c == '+') {
                return c;
            }
        }
        return ' ';
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
        ArrayList<String> ret = new ArrayList();
        String[] lines = source.split("\\n");
        for(int i=0;i<lines.length;i++) {
            for(String arrow : ARROWS) {
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
            if(lines[i].contains("class "+className)) {
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
            if(line.contains("abstract ") && line.contains(className)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Takes in the source string and a class and returns the fields of the class
     * @param source PlantUML source string
     * @param className name of class to extract from
     * @return list of all fields in the class
     */
    public static ArrayList<String> classFields(String source, String className) {
        ArrayList<String> ret = new ArrayList();
        String classSource = getClassSource(source, className);
        String[] lines = classSource.split("\\n");
        for(String line : lines) {
            if(!line.contains("class "+className) && !line.equals("}") && !line.contains("()")) {
                ret.add(line);
            }
        }
        return ret;
    }
    
    /**
     * Takes in the source string and a class and returns the methods of the class
     * @param source PlantUML source string
     * @param className name of class to extract from
     * @return list of all methods in the class in source format
     */
    public static ArrayList<String> classMethods(String source, String className) {
        ArrayList<String> ret = new ArrayList();
        String classSource = getClassSource(source, className);
        String[] lines = classSource.split("\\n");
        for(String line : lines) {
            if(!line.contains("class "+className) && !line.equals("}") && !line.contains("()")) {
                ret.add(line);
            }
        }
        return ret;
    }
    
    /**
     * Returns the name of a field from its source line
     * @param fieldSource source line containing field
     * @return name of field
     */
    public static String getFieldName(String fieldSource) {
        String ret = "";
        String[] words = fieldSource.split(" ");
        for(int i=0;i<words.length;i++) {
            if(!words[i].contains("}")) {
                if(words[i].contains("+") || words[i].contains("~") || words[i].contains("#") || words[i].contains("-")) {
                    return words[i].substring(1);
                }
                else {
                    return words[i];
                }
            }
        }
        return ret;
    }
    
    /**
     * Returns the name of a method from its source line
     * @param methodSource source line containing method
     * @return name of method
     */
    public static String getMethodName(String methodSource) {
        return getFieldName(methodSource);
    }
    
    /**
     * Sets the visibility of a field or method in a given class in a source string
     * @param source plantUML source string
     * @param className name of the class which the field belongs to
     * @param fieldName name of the field or method to change
     * @param visibility char for visibility matching symbols used in pUML language
     * @return plantUML source string with visibility changed
     */
    public static String setVisibility(String source, String className, String fieldName, char visibility) {
        String[] lines = source.split("\\n");
        for(int i=0;i<lines.length;i++) {
            if(lines[i].contains("class "+className)) {
                while(!lines[i].contains(fieldName)) {
                    i++;
                }
                char[] line = lines[i].toCharArray();
                if(getVisibility(lines[i]) != ' ') {
                    for(char c : line) {
                        switch(c) {
                            case '-':
                                c = visibility;
                                break;
                            case '#':
                                c = visibility;
                                break;
                            case '~':
                                c = visibility;
                                break;
                            case '+':
                                c = visibility;
                                break;
                        }
                    }
                }
                else {
                    for(int j=0;j<line.length;j++) {
                        if(line[j] == '{') {
                            while(line[j] != '}') { j++; }
                        }
                        else if(Character.isLetter(line[j])) {
                            line[j-1] = visibility;
                        }
                    }
                }
                lines[i] = Arrays.toString(line);
            }
        }
        String ret = "";
        for(String line : lines) {
            ret = ret + line + "\n";
        }
        return ret;
    }
    
    /**
     * Adds a new class to the UML document
     * @param source plantUML source string
     * @param className name of new class to add
     * @return plantUML source string with new class added
     */
    public static String createClass(String source, String className) {
        ArrayList<String> lines = new ArrayList(Arrays.asList(source.split("\\n")));
        lines.add(1, "class "+className);
        String ret = "";
        for(String line : lines) {
            ret = ret + line + "\n";
        }
        return ret;
    }
    
    /**
     * Deletes a class from the UML document
     * @param source plantUML source string
     * @param className name of class to remove
     * @return plantUML source string with class removed
     */
    public static String deleteClass(String source, String className) {
        ArrayList<String> lines = new ArrayList(Arrays.asList(source.split("\\n")));
        for(int i=0;i<lines.size();i++) {
            if(lines.get(i).contains("class "+className+" {")) {
                while(!lines.get(i).equals("}")) {
                    lines.remove(i);
                }
                lines.remove(i);
            }
            else if(lines.get(i).contains("class "+className)) {
                lines.remove(i);
            }
        }
        String ret = "";
        for(String line : lines) {
            ret = ret + line + "\n";
        }
        return ret;
    }
    
    /**
     * Creates a new field in specified class
     * @param source plantUML source string
     * @param className name of class to add field to
     * @param fieldName name of field to add
     * @param visibility visibility of field as specified by plantUML language
     * @param isStatic true if field is static
     * @param isAbstract true if field is abstract
     * @return plantUML source string with field added
     */
    public static String createField(String source, String className, String fieldName, char visibility, boolean isStatic, boolean isAbstract) {
        ArrayList<String> lines = new ArrayList(Arrays.asList(source.split("\\n")));
        for(int i=0;i<lines.size();i++) {
            if(lines.get(i).contains("class "+className+" {")) {
                if(isAbstract)
                    lines.add(i+1,"{abstract} "+visibility+fieldName);
                else if(isStatic)
                    lines.add(i+1,"{static} "+visibility+fieldName);
                else
                    lines.add(i+1,visibility+fieldName);
                break;
            }
            else {
                 String tmp = lines.get(i) + " {";
                 lines.remove(i);
                 lines.add(i, tmp);
                 if(isAbstract)
                    lines.add(i+1,"{abstract} "+visibility+fieldName);
                else if(isStatic)
                    lines.add(i+1,"{static} "+visibility+fieldName);
                else
                    lines.add(i+1,visibility+fieldName);
                lines.add(i+2, "}");
                break;
            }
        }
        String ret = "";
        for(String line : lines) {
            ret = ret + line + "\n";
        }
        return ret;
    }
    
    /**
     * Creates a new method in specified class
     * @param source plantUML source string
     * @param className name of class to add field to
     * @param methodName name of method to add
     * @param visibility visibility of field as specified by plantUML language
     * @param isStatic true if field is static
     * @param isAbstract true if field is abstract
     * @return plantUML source string with field added
     */
    public static String createMethod(String source, String className, String methodName, char visibility, boolean isStatic, boolean isAbstract) {
        return createField(source,className,methodName,visibility,isStatic,isAbstract);
    }
    
    /**
     * Checks the direction of an association
     * @param source plantUML source file
     * @param classA first class in association
     * @param classB second class in association
     * @return true if association is A to B or directionless, false if B to A
     */
    public static boolean assAtoB(String source, String classA, String classB) {
        ArrayList<String> assList = relationNames(source);
        for(String ass : assList) {
            if(ass.contains(classA) && ass.contains(classB)) {
                int i = 0;
                while(ass.charAt(i) != '-' || ass.charAt(i) != '.' || i<ass.length()) {
                    for(char symb : ARROW_SYMBOLS) {
                        if(symb == ass.charAt(i))
                            return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

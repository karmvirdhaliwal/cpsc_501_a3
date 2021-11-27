import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Array;

/**
 * CPSC 501
 * Inspector starter class
 *
 * @author Jonathan Hudson
 */
//Karmvir Singh Dhaliwal
//30025474
//CPSC 501 T05


public class Inspector {
	
	private static final int DEPTH_INCREASE = 1;

    public void inspect(Object obj, boolean recursive) {
        Class<?> c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class<?> c, Object obj, boolean recursive, int depth) {
		// note: depth will be needed to capture the output indentation level
    	try {
    		
    		//for indentation
    		String tab = "	";
        	String indent = tab.repeat(depth);
        	
        	System.out.println(indent + "CLASS");
        	
        	//printing name of declaring class 
        	String decName = c.getName();
        	System.out.println(indent + "Class: "+ decName);
        	
        	//finding immediate superclass
        	Class<?> immediateSuper = c.getSuperclass();
        	String superName;
        	
        	if(immediateSuper != null) {
        		
        		superName = immediateSuper.getName();
        	}
        	else {
        		superName = "NONE";
        	}
        	
        	//printing name of immediate super class 
        	System.out.println(indent + "SUPERCLASS -> Recursively inspect");
        	System.out.println(indent + "SuperClass: " + superName);
        	
        	//recursively inspecting superclasses 
        	while(immediateSuper != null) {
        		
        		inspectClass(immediateSuper, obj, recursive, depth+DEPTH_INCREASE);
        		break;
        	}
        	//finding and printing immediate interface
        	System.out.println(indent + "INTERFACES( " + decName + ")");
        	Class<?>[] interfaces = c.getInterfaces();
        	
        	if(interfaces.length == 0) {
        		System.out.println(indent+"Interfaces -> NONE");
        	}
        	else {//recursively printing interfaces
        		for(int i = 0; i < interfaces.length; i++) {
        			System.out.println(indent+"INTERFACE -> Recursively Inspect");
        			String tempName = interfaces[i].getName();
        			Class<?> tempClass = interfaces[i];
        			System.out.println(indent + "Interface: " + tempName);
        			inspectClass(tempClass, obj, recursive, depth+DEPTH_INCREASE);
        		}
        	}
        	//getting constructors
        	Constructor<?>[] constructors = c.getDeclaredConstructors();
        	
        	System.out.println(indent + "CONSTRUCTORS( " + decName + ")");
        	
        	if(constructors.length == 0) {
        		System.out.println(indent + "Constructors -> NONE");
        	}
        	else {
        		for(int i = 0; i < constructors.length; i++) {
        			System.out.println(indent+"Constructor");
        			String tempName = constructors[i].getName();
        			System.out.println(indent + "Name: " + tempName);
        			
        			Class<?>[] tempExceptions = constructors[i].getExceptionTypes();
        			
        			if(tempExceptions.length == 0) {
        				System.out.println(indent + "Exceptions: NONE ");
        			}
        			else {
        				for(int j = 0; j < tempExceptions.length; j++) {
        					
        					String tempName2 = tempExceptions[j].getName();
        					System.out.println(indent + "Exception: " + tempName2);
        				}
        			}
        			Class<?>[] params = constructors[i].getParameterTypes();
        			
        			if(params.length == 0) {
        				System.out.println(indent+ "Parameters: NONE");
        			}
        			else {
        				for(int k = 0; k < params.length; k++) {
        					String tempName3 = params[k].getName();
        					System.out.println(indent + "Parameter: " + tempName3);
        				}
        			}
        			
        			int mods = constructors[i].getModifiers();
        			String modsString = Modifier.toString(mods);
        			System.out.println(indent + "Modifiers: " + modsString);
        		}
        	}
        	
        	//getting methods
        	Method[] methods = c.getDeclaredMethods();
        	
        	System.out.println(indent + "METHODS( " + decName + ")");
        	
        	if(methods.length == 0) {
        		System.out.println(indent + "Methods -> NONE");
        	}
        	else {
        		for(int i = 0; i < methods.length; i++) {
        			System.out.println(indent+"Method");
        			String tempName = methods[i].getName();
        			System.out.println(indent + "Name: " + tempName);
        			
        			Class<?>[] tempExceptions = methods[i].getExceptionTypes();
        			
        			if(tempExceptions.length == 0) {
        				System.out.println(indent + "Exceptions: NONE ");
        			}
        			else {
        				for(int j = 0; j < tempExceptions.length; j++) {
        					
        					String tempName2 = tempExceptions[j].getName();
        					System.out.println(indent + "Exception: " + tempName2);
        				}
        			}
        			Class<?>[] params = methods[i].getParameterTypes();
        			
        			if(params.length == 0) {
        				System.out.println(indent+ "Parameters: NONE");
        			}
        			else {
        				for(int k = 0; k < params.length; k++) {
        					String tempName3 = params[k].getName();
        					System.out.println(indent + "Parameter: " + tempName3);
        				}
        			}
        			
        			int mods = methods[i].getModifiers();
        			String modsString = Modifier.toString(mods);
        			System.out.println(indent + "Modifiers: " + modsString);
        			
        			Class<?> returnType = methods[i].getReturnType();
        			String returnTypeName = returnType.getName();
        			
        			System.out.println(indent + "Return type: " + returnTypeName);
        		}
        	}
        	
        	//getting fields
        	Field[] fields = c.getDeclaredFields();
        	System.out.println(indent + "FIELDS( " + decName + ")");
        	
        	if(fields.length == 0) {
        		System.out.println(indent + "Fields -> NONE");
        	}
        	else {
        		for(int i = 0; i < fields.length; i++) {
        			
        			System.out.println(indent+"Field");
        			String tempName = fields[i].getName();
        			System.out.println(indent + "Name: " + tempName);
        			
        			Class<?> type = fields[i].getType();
        			String typeName = type.getName();
        			System.out.println(indent + "Type: " + typeName);
        			
        			int mods = fields[i].getModifiers();
        			String modsString = Modifier.toString(mods);
        			System.out.println(indent + "Modifiers: " + modsString);
        			
        			if(type.isPrimitive()) {
        				fields[i].setAccessible(true);
        				Object fieldVal = fields[i].get(obj);
        				//String val = fieldVal.toString();
        				System.out.println(indent + "Value: " + fieldVal);
        			}
        			else {
        				if(!recursive) {
        					fields[i].setAccessible(true);
            				Object fieldVal = fields[i].get(obj);
            				//String val = fieldVal.toString();
            				System.out.println(indent + "Value (ref): " + fieldVal + System.identityHashCode(fieldVal));
        				}
        				else {
        					fields[i].setAccessible(true);
            				Object fieldVal = fields[i].get(obj);
            				//String val = fieldVal.toString();
            				if(fieldVal != null) {
            					Class<?> cl = fieldVal.getClass();
                				System.out.println(indent + "Value: " + fieldVal + System.identityHashCode(fieldVal));
                				System.out.println(indent+ "Recursively inspect: ");
                				inspectClass(cl,fieldVal,recursive,depth+DEPTH_INCREASE);
            				}
            				else{
            					System.out.println(indent + "Value: " + fieldVal);
            				}
            				
        				}
        			}
        			if(type.isArray()) {
        				
        				Class<?> compType = type.getComponentType();
        				String compTypeName = compType.getName();
        				System.out.println(indent + "Component type: " + compTypeName);
        				
        				int length = Array.getLength(fields[i].get(obj));
        				System.out.println(indent + "Array length: " + length);
        				
        				System.out.println(indent + "Entries ->");
        				
        				for(int j = 0; j < length; j++) {
        					Object temp = Array.get(fields[i].get(obj), j);
        					//String val = temp.toString();
        					System.out.println(indent+ "Value: " + temp);
        				}
        			}
        		}
        	}
        	if(c.isArray()) {
        		
        		Class<?> compType = c.getComponentType();
				String compTypeName = compType.getName();
				System.out.println(indent + "Component type: " + compTypeName);
				
				int length = Array.getLength(obj);
				System.out.println(indent + "Array length: " + length);
				
				System.out.println(indent + "Entries ->");
				
				for(int j = 0; j < length; j++) {
					Object temp = Array.get(obj, j);
					//String val = temp.toString();
					System.out.println(indent+ "Value: " + temp);
				}
        	}
        	
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    	
    }

}

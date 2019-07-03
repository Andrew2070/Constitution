package Constitution.Commands.Engine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation providing metadata for a command.
 * Each {@link StandardCommand} implementation should have
 * this annotation. It is mainly used by the help command to
 * provide information on a command to the user.
 * 
 * @author Andrew2070
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

	String name();

	String description();
	
	String syntax();
	
	String[] alias() default {};

	String permission();

	String parentName() default "ROOT";
	
	boolean console() default true;
	
	String[] completionKeys() default {};
	   
}
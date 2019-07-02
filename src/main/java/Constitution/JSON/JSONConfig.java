package Constitution.JSON;



import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Constitution.ConstitutionMain;

/**
 * An abstract class for all JSON configs.
 * Methods are usually overrided but called inside their overrided method.
 */
public abstract class JSONConfig<T, L extends List<T>> {

    /**
     * The path to the file used.
     */
    protected final String path, name;
	protected static Gson gson;
    protected Type gsonType;

    public JSONConfig(String path, String name) {
        this.path = path;
        this.name = name;
    }

    protected abstract L newList();

    public void init() {
        init(newList());
    }

    public static void setExclusion(GsonBuilder builder) {
    	gson = builder.excludeFieldsWithoutExposeAnnotation().create();
    }
    
    /**
     * Initializes everything.
     */
    public void init(L items) {
        File file = new File(path);

        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        if (!file.exists() || file.isDirectory()) {
            create(items);
        } else {
            read();
        }
    }

    /**
     * Creates the file if it doesn't exist with the initial given items
     */
    public void create(L initialItems) {
        try {
            Writer writer = new FileWriter(path);
            gson.toJson(initialItems, gsonType, writer);
            writer.close();
            ConstitutionMain.logger.info("Created new " + name + " file successfully!");
        } catch (IOException ex) {
            ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
            ConstitutionMain.logger.info("Failed to create " + name + " file!");
            
        }
    }
    
    //test1
    
    
//test
    /**
     * Writes the given list to the file, completely overwriting it
     */
    public void write(L items) {
        try {
            Writer writer = new FileWriter(path);
            gson.toJson(items, gsonType, writer);
            writer.close();
            ConstitutionMain.logger.info("Updated the " + name + " file successfully!");
        } catch (IOException ex) {
            ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
            ConstitutionMain.logger.info("Failed to update " + name + " file!");
        }
    }

    /**
     * Reads and returns the validated items.
     */
    public L read() {
        L items = null;
        try {
            Reader reader = new FileReader(path);
            items = gson.fromJson(reader, gsonType);
            reader.close();
            ConstitutionMain.logger.info(path);
            ConstitutionMain.logger.info(reader.toString());
            ConstitutionMain.logger.info(gsonType.toString());
            
            ConstitutionMain.logger.info("Loaded " + name + " successfully!");
        } catch (IOException ex) {
            ConstitutionMain.logger.info(ExceptionUtils.getStackTrace(ex));
            ConstitutionMain.logger.info("Failed to read from " + name + " file!");
        }
        	if (!validate(items)) {
        		write(items);
        	}
        return items;
    }

    /**
     * Checks for validity and modifies the given list so that is valid.
     */
    public boolean validate(L items) {
        return true;
    }

    public String getName() {
        return this.name;
    }
}
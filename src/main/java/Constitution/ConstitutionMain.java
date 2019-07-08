package constitution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import constitution.commands.engine.CommandManager;
import constitution.commands.servercommands.administrative.channel;
import constitution.commands.servercommands.permissions.PermissionCommands;
import constitution.configuration.Config;
import constitution.configuration.json.JSONConfig;
import constitution.events.ChatEvent;
import constitution.events.LoginEvent;
import constitution.events.LogoutEvent;
import constitution.localization.Localization;
import constitution.localization.LocalizationManager;
import constitution.permissions.ConstitutionBridge;
import constitution.permissions.PermissionProxy;
import constitution.utilities.ClassUtilities;
import constitution.utilities.LogFormatter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = ConstitutionMain.MODID, name = "Constitution", version = ConstitutionMain.VERSION, dependencies = "after:worldedit", updateJSON = ConstitutionMain.UPDATEURL, acceptableRemoteVersions = "*", acceptedMinecraftVersions = ConstitutionMain.MCVERSIONS)
public class ConstitutionMain
{
	@Mod.Instance(ConstitutionMain.MODID)
	public static 						   ConstitutionMain 	   instance;
    public 								   Localization		   LOCAL;
    public static final 				   String              MODID              = "constitution";
    public static final 				   String              VERSION            = "1.12.2";
    public static final 				   String              UPDATEURL          = "";
    public final static 				   String              MCVERSIONS         = "[1.9.4, 1.13]";
    public static 						   File                configFile         = null;
    public static 						   File                jsonFile           = null;
    public static 						   String 			   CONFIG_FOLDER 	  = "";
	public static						   String 			   DATABASE_FOLDER 	  = "";
	public static						   String			   COMMAND_FOLDER     = "constitution.commands.executable";
    public static 					       Logger              logger             = Logger.getLogger(MODID);
    public static boolean                  debug             					  = false;
    private final List<JSONConfig> 		   jsonConfigs 							  = new ArrayList<JSONConfig>();

    static ExclusionStrategy               exclusion          = new ExclusionStrategy()
                                                              {
                                                                  @Override
                                                                  public boolean shouldSkipField(FieldAttributes f)
                                                                  {
                                                                      String name = f.getName();
                                                                      return name.startsWith("_");
                                                                  }

                                                                  @Override
                                                                  public boolean shouldSkipClass(Class<?> clazz)
                                                                  {
                                                                      return false;
                                                                  }
                                                              };

    public ConstitutionMain() {
        initLogger();
    }

    private void initLogger() {
        FileHandler logHandler = null;
        logger.setLevel(Level.ALL);
        try
        {
            File logs = new File("." + File.separator + "logs");
            logs.mkdirs();
            File logfile = new File(logs, MODID + ".log");
            if ((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null)
            {
                logHandler = new FileHandler(logfile.getPath());
                logHandler.setFormatter(new LogFormatter());
                logger.addHandler(logHandler);
            }
        }
        catch (SecurityException | IOException e)
        {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void preInitializationEvent(FMLPreInitializationEvent event) {
    	//CONFIGURATION:
    	CONFIG_FOLDER = event.getModConfigurationDirectory().getPath() + "/Constitution/";
		DATABASE_FOLDER = event.getModConfigurationDirectory().getParent() + "/databases/";
    	Config.instance.init(CONFIG_FOLDER + "/Constitution.cfg", "Constitution");
    	LOCAL = new Localization(CONFIG_FOLDER + "/Localization/", Config.instance.localization.get(),
				"/Constitution/Localization/", ConstitutionMain.class);
    	LocalizationManager.register(LOCAL, "constitution");
		MinecraftForge.EVENT_BUS.register(ChatEvent.instance);
		MinecraftForge.EVENT_BUS.register(LoginEvent.instance);
		MinecraftForge.EVENT_BUS.register(LogoutEvent.instance);
		
    }
 
    @EventHandler
    public void postInitializationEvent(FMLPostInitializationEvent event) {
    	
    }

    @EventHandler
    public void serverLoadEvent(FMLServerStartingEvent event) {
    	loadConfig();
    	logger.info("Constitution Started");
    	if (PermissionProxy.getPermissionManager() instanceof ConstitutionBridge) {
			CommandManager.registerCommands(PermissionCommands.class, "constitution.cmd.perm", ConstitutionMain.instance.LOCAL, null);
			CommandManager.registerCommands(channel.class, "constitution.cmd.channel", ConstitutionMain.instance.LOCAL, null);
			//registerCommands();
		}
    }
    
    @EventHandler
    public void serverStoppingEvent(FMLServerStoppingEvent event) {
    	
    }

    public static void loadPerms() {
    
    }

    public static void savePerms() {
       
    }
    
    public static void registerCommands() {
    	//Needs Work (CommandManager is persistent on each Command Class Having A Root Command);
    	List<Class<?>> commandClazzes = (ClassUtilities.getClassesInPackage(COMMAND_FOLDER));
    	for (Class<?> clazz : commandClazzes) {
    		//String rootPerm = 
    		//logger.info("Class: " + clazz.getSimpleName()); 
    		//CommandManager.registerCommands(clazz, rootPerm, ConstitutionMain.instance.LOCAL, null);
    	}
    }
    public void loadConfig() {
		Config.instance.reload();
		PermissionProxy.init();
		LOCAL.load();
		for (JSONConfig jsonConfig : jsonConfigs) {
			jsonConfig.init();
		}
	}
}

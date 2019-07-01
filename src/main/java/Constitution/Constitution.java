package Constitution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.Maps;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import Constitution.Commands.CommandManager;
import Constitution.Commands.Executable.CoreCommands;
import Constitution.Configuration.Config;
import Constitution.Events.PlayerEventHandler;
import Constitution.JSON.JSONConfig;
import Constitution.Localization.Localization;
import Constitution.Localization.LocalizationManager;
import Constitution.Permissions.ConstitutionBridge;
import Constitution.Permissions.PermissionManager;
import Constitution.Permissions.PermissionProxy;
import Constitution.Utilities.LogFormatter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = Constitution.MODID, name = "Constitution", version = Constitution.VERSION, dependencies = "after:worldedit", updateJSON = Constitution.UPDATEURL, acceptableRemoteVersions = "*", acceptedMinecraftVersions = Constitution.MCVERSIONS)
public class Constitution
{
	@Mod.Instance(Constitution.MODID)
	public static 						   Constitution 		   instance;
    public static final 				   String              MODID              = "constitution";
    public static final 				   String              VERSION            = "1.12.2";
    public static final 				   String              UPDATEURL          = "";
    public final static 				   String              MCVERSIONS         = "[1.9.4, 1.13]";
    public 								   Localization		   LOCAL;
    public static boolean                  allCommandUse      					  = false;
    public static 						   File                configFile         = null;
    public static 						   File                jsonFile           = null;
    public static 						   String 			   CONFIG_FOLDER 	  = "";
	public static						   String 			   DATABASE_FOLDER 	  = "";
    public static 					       Logger              logger             = Logger.getLogger(MODID);
    public static boolean                  debug             					  = false;
    public static Map<String, String>      customCommandPerms 					  = Maps.newHashMap();
    public static boolean                  wrapOpCommands     					  = true;
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

    public Constitution() {
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
				"/Constitution/Localization/", Constitution.class);
    	LocalizationManager.register(LOCAL, "Constitution");
		MinecraftForge.EVENT_BUS.register(PlayerEventHandler.instance);
    }
 
    @EventHandler
    public void postInitializationEvent(FMLPostInitializationEvent event) {
    	
    }

    @EventHandler
    public void serverLoadEvent(FMLServerStartingEvent event) {
    	loadConfig();
    	logger.info("Constitution Started");
    	if (PermissionProxy.getPermissionManager() instanceof ConstitutionBridge) {
			CommandManager.registerCommands(CoreCommands.class, "Constitution.perm.cmd", Constitution.instance.LOCAL, null);

		}
    	
    	for (JSONConfig jsonConfig : jsonConfigs) {
			jsonConfig.init();
		}
    }
    
    @EventHandler
    public void serverStoppingEvent(FMLServerStoppingEvent event) {
    	
    }

    public static void loadPerms() {
    
    }

    public static void savePerms() {
       
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

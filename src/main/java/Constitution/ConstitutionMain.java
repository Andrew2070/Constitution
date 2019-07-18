/*******************************************************************************
 * Copyright (C) July/14/2019, Andrew2070
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement:
 *    This product includes software developed by Andrew2070.
 * 
 * 4. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package constitution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import constitution.commands.engine.CommandManager;
import constitution.commands.servercommands.administrative.channel;
import constitution.commands.servercommands.permissions.PermissionCommands;
import constitution.configuration.Config;
import constitution.configuration.json.JSONConfig;
import constitution.events.ChatEvent;
import constitution.events.LoginEvent;
import constitution.events.LogoutEvent;
import constitution.events.SendCommandEvent;
import constitution.localization.Localization;
import constitution.localization.LocalizationManager;
import constitution.permissions.PermissionManager;
import constitution.utilities.ClassUtilities;
import constitution.utilities.LogFormatter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = ConstitutionMain.MODID, name = "Constitution Mod", version = ConstitutionMain.VERSION, dependencies = "after:worldedit", updateJSON = ConstitutionMain.UPDATEURL, acceptableRemoteVersions = "*", acceptedMinecraftVersions = ConstitutionMain.MCVERSIONS)
public class ConstitutionMain
{
	@Mod.Instance(ConstitutionMain.MODID)
	public static 						   ConstitutionMain    instance;
	public 								   Localization		   LOCAL;
	public static final 				   String              MODID              = Constants.MODID;
	public static final 				   String              VERSION            = Constants.VERSION;
	public static final 				   String              UPDATEURL          = "";
	public final static 				   String              MCVERSIONS         = "[1.9.4, 1.13]";
	public static 						   File                configFile         = null;
	public static 						   File                jsonFile           = null;
	public static 						   String 			   CONFIG_FOLDER 	  = "";
	public static						   String 			   DATABASE_FOLDER 	  = "";
	public static						   String			   COMMAND_FOLDER     = "constitution.commands.executable";
	public static 					       Logger              logger             = Logger.getLogger(MODID);
	public static boolean                  debug             					  = false;
	private final                          List<JSONConfig>    jsonConfigs 		  = new ArrayList<JSONConfig>();
	public static                          PermissionManager   permissionManager  = new PermissionManager();
	
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
		MinecraftForge.EVENT_BUS.register(SendCommandEvent.instance);
		permissionManager.preInitialization();

	}

	@EventHandler
	public void postInitializationEvent(FMLPostInitializationEvent event) {

	}

	@EventHandler
	public void serverLoadEvent(FMLServerStartingEvent event) {
		loadConfig();
		permissionManager.serverLoad();
		logger.info("Constitution Started");
		CommandManager.registerCommands(PermissionCommands.class, "constitution.cmd.perm", ConstitutionMain.instance.LOCAL, permissionManager);
		CommandManager.registerCommands(channel.class, "constitution.cmd.channel", ConstitutionMain.instance.LOCAL, permissionManager);
		//registerCommands();
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
		}
	}
	public void loadConfig() {
		Config.instance.reload();
		LOCAL.load();
		for (JSONConfig jsonConfig : jsonConfigs) {
			jsonConfig.init();
		}
	}

	public static PermissionManager getPermissionManager() {
		return permissionManager;
	}
}

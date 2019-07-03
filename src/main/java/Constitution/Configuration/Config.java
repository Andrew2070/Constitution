package constitution.configuration;

/**
 * @author Andrew2070
 *
 *   Configuration Template:
 *   
 *   [EX] Configuration Example:
 *
 *	 public ConfigProperty<String/Int/Double/Long/Boolean> Name = new ConfigProperty<String/Int/Double/Long/Boolean>(
 *				"Configuration Display Name", "Main Category",
 *				"Description of Configuration Property.",
 *				"Default Value");
 *
 *	Steps:
 *	1. Specify Object Type
 *	2. Specify Name
 *	3. Initialize ConfigProperty, Specify Object Type Again
 *	4. Specify Name t appears in configuration
 *	5. Specify Main Category
 *  6. Specify Description
 *  7. Specify Default Value (if Boolean then true/false, if String then "String", if Double then "0.0")
 *
 *
 *
 */
public class Config extends ConfigTemplate {

    public static final Config instance = new Config();
      
    public ConfigProperty<String> localization = new ConfigProperty<String>(
            "localization", "localization",
            "The localization file used, currently we only support English.",
            "en_US");
    
    public ConfigProperty<String> Database = new ConfigProperty<String>(
            "dbName", "database",
            "The Name Of The Database",
            "ConstitutionDB");
    
    public ConfigProperty<String> DBType = new ConfigProperty<String>(
            "dbType", "database",
            "Database Type, Supported Options: MySQL, SQLite, SQL",
            "SQLite");
    
    public ConfigProperty<String> DBHost = new ConfigProperty<String>(
            "dbHost", "database",
            "Database Host",
            "Host");
    
    public ConfigProperty<String> DBPath = new ConfigProperty<String>(
            "dbPath", "database",
            "Alternative Database Path",
            "dbPath");
    
    public ConfigProperty<String> DBUsername = new ConfigProperty<String>(
            "dbUsername", "database",
            "Database Username",
            "username");
    
    public ConfigProperty<String> DBPassword = new ConfigProperty<String>(
            "dbPassword", "database",
            "Database Password",
            "password");
    
    public ConfigProperty<String> permissionSystem = new ConfigProperty<String>(
    		"permissionSystem", "Permissions",
    		"Specify Permission System To Be Used, Currently no other modular support implemented, changing will crash, don't touch unless you know what you're doing.",
    		"$Constitution");

	public ConfigProperty<Boolean> fullAccessForOPS = new ConfigProperty<Boolean>(
			"fullAccessForOps", "Permissions",
			"Specify Whether Ops get Full Access",
			true);
	
	public ConfigProperty<String> defaultGroupName = new ConfigProperty<String>(
			"defaultGroupName", "Permissions",
			"Specify Default Group Name",
			"default");
	
	public ConfigProperty<Integer> defaultGroupRank = new ConfigProperty<Integer>(
			"defaultGroupRank", "Permissions",
			"Specify Default Group Rank",
			10);
	
	public ConfigProperty<String> defaultGroupDesc = new ConfigProperty<String>(
			"defaultGroupDesc", "Permissions",
			"Specify Default Group Description",
			"This Is The Default Group");
	
	public ConfigProperty<String> defaultGroupPrefix = new ConfigProperty<String>(
			"defaultGroupPrefix", "Permissions",
			"Specify Default Group prefix",
			"[Default]");
	
	public ConfigProperty<String> defaultGroupSuffix = new ConfigProperty<String>(
			"defaultGroupSuffix", "Permissions",
			"Specify Default Group suffix",
			"");
	
	public ConfigProperty<Boolean> setLastGameMode = new ConfigProperty<Boolean>(
			"setLastGameMode", "Users",
			"Changes The Game Mode of The User Back To Last Recorded Mode (ie: before they logged off)",
			true);
	public ConfigProperty<Boolean> forceSurvivalMode= new ConfigProperty<Boolean>(
			"forceSurvivalMode", "Users",
			"Controls Whether or Not To Force All Players To Survival Mode on Succesful Login",
			false);
	
	public ConfigProperty<String> ChatModifications = new ConfigProperty<String>(
			"chatModifications", "Chat",
			"Allows editing chat messages, includes different Mod support",
			"[ExampleMod][GroupPrefix][UserPrefix] PlayerName [UserSuffix][GroupSuffix]: message");
	
	public ConfigProperty<String> defaultWarnMessage= new ConfigProperty<String>(
			"defaultWarnDuration", "BanManager",
			"When no warn message is specified, what message should be sent to the warned player?",
			"You Have Been Warned!");
	
	public ConfigProperty<String> defaultBanMessage = new ConfigProperty<String>(
			"defaultBanMessage", "BanManager",
			"When no ban message is specified, what message should be sent to the banned player?",
			"The Ban Hammer Has Spoken!");
	
	public ConfigProperty<String> defaultBanDuration = new ConfigProperty<String>(
			"defaultBanDuration", "BanManager",
			"When no tempban duration is specified, how long should the player be banned by default?",
			"1h");
	
	
			
			
    
    
}
package net.Zahryan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.Zahryan.game.GameManager;
import net.Zahryan.game.GameState;
import net.Zahryan.listeners.JoinQuitListeners;
import net.Zahryan.listeners.PropertiesListeners;
import net.Zahryan.listeners.PvPListeners;

public class KillTheKing extends JavaPlugin{
	
	/*---Variables---*/
	public HashMap<UUID, IGPlayer> playerData = new HashMap<>();
	public ArrayList<UUID> equipeRouge = new ArrayList<>();
	public ArrayList<UUID> equipeBleue = new ArrayList<>();
	public int preGameTimer = 30;
	public int gameTimer = 300;
	/*---------------*/

	/*---Constructeur---*/
	public KillTheKing(){
		//https://github.com/Sentrance/MiniShootCraft
	}
	/*------------------*/
	
	/*---Méthodes---*/
	public void onEnable(){

		this.getConfig().addDefault("Spawn Rouge X", 5.0F);
		this.getConfig().addDefault("Spawn Rouge Y", 5.0F);
		this.getConfig().addDefault("Spawn Rouge Z", 5.0F);
		
		this.getConfig().addDefault("Spawn Bleu X", 10.0F);
		this.getConfig().addDefault("Spawn Bleu Y", 10.0F);
		this.getConfig().addDefault("Spawn Bleu Z", 10.0F);
		
		this.getConfig().addDefault("Taille des equipes", 10);
		
		this.getConfig().options().copyDefaults(true);
		saveConfig();
		
		Bukkit.broadcastMessage("[KillTheKing]Enabling...");
		
		super.onEnable();
		
		this.playerData = new HashMap<UUID,IGPlayer>();
		
		GameState.setState(GameState.EN_ATTENTE);
		
		Bukkit.getWorld("world").setPVP(false);
		
		getServer().getPluginManager().registerEvents(new JoinQuitListeners(this), this);
		getServer().getPluginManager().registerEvents(new PropertiesListeners(this), this);
		getServer().getPluginManager().registerEvents(new PvPListeners(this), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
	        {
	            @Override
	            public void run()
	            {
	                int currentTimer = 0;
	                if(GameState.isState(GameState.EN_ATTENTE)){
		                preGameTimer--;
		                currentTimer=preGameTimer;
		                if(preGameTimer<=0){
		                	GameState.setState(GameState.EN_JEU);
		                	GameManager.startGame(KillTheKing.this);
		                }
	                }
	                
	                if(GameState.isState(GameState.EN_JEU)){
		                gameTimer--;
		                currentTimer=gameTimer;
		                if(gameTimer<=0){
		                	GameState.setState(GameState.FIN);
		                	GameManager.endGame(KillTheKing.this);
		                	// Cancel task Bukkit.getScheduler().;
		                	Bukkit.getScheduler().cancelTasks(KillTheKing.this);
		                }
	                }
	                
	                for (IGPlayer p : playerData.values()){
	                    p.updateTimer(currentTimer);
	                }
	                
	            }
	        }, 0, 20);
		
		Bukkit.broadcastMessage("[KillTheKing]Enabling : done.");
		
	}
	
	public void onDisable(){
		 
		Bukkit.broadcastMessage("[KillTheKing]Disabling...");
		
		super.onDisable();
		
		Bukkit.broadcastMessage("[KillTheKing]Disabling : done.");
    
	}
	/*--------------*/
}

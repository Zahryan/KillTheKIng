package net.Zahryan.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.Zahryan.IGPlayer;
import net.Zahryan.KillTheKing;
import net.Zahryan.game.GameState;
import net.Zahryan.util.Titles;

public class JoinQuitListeners implements Listener {
	
	/*---Variables---*/
	public KillTheKing ktk;
	/*---------------*/

	/*---Constructeur---*/
	public JoinQuitListeners(KillTheKing killTheKing) {
		this.ktk = killTheKing;
	}
	/*------------------*/
	
	/*---M�thodes---*/
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		if(GameState.isState(GameState.EN_ATTENTE) && this.ktk.playerData.size() < (this.ktk.getConfig().getInt("Taille des equipes"))*2){
			
			//Initialisation du joueur
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setHealth(20.0D);
			p.setFoodLevel(20);
			this.ktk.playerData.put(p.getUniqueId(), new IGPlayer(p));
			
			Titles.sendTitle(p, ChatColor.RED + "KillTheKing", ChatColor.GREEN + "Selectionnez votre Equipe !", 20);
			
			//Give des laines pour le choix des �quipes
			ItemStack redWool = new ItemStack(Material.WOOL, 1, (short)14);
		    ItemMeta iRedWool = redWool.getItemMeta();
		    iRedWool.setDisplayName(ChatColor.RED + "Rejoindre l'�quipe ROUGE !");
		    redWool.setItemMeta(iRedWool);
		    p.getInventory().addItem(redWool);
		    
		    ItemStack blueWool = new ItemStack(Material.WOOL, 1, (short)11);
		    ItemMeta iBlueWool = blueWool.getItemMeta();
		    iBlueWool.setDisplayName(ChatColor.BLUE + "Rejoindre l'�quipe BLEUE !");
		    blueWool.setItemMeta(iBlueWool);
		    p.getInventory().addItem(blueWool);
			
		}else{
			
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage("La partie a d�j� commenc�e ou a atteint le nombre max de joueurs.");
			
		}
	
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		
		if(this.ktk.playerData.containsKey(p.getUniqueId())){
			this.ktk.playerData.remove(p.getUniqueId());
		}
		if(this.ktk.equipeBleue.contains(p.getUniqueId())){
			this.ktk.equipeBleue.remove(p.getUniqueId());
		}
		if(this.ktk.equipeRouge.contains(p.getUniqueId())){
			this.ktk.equipeRouge.remove(p.getUniqueId());
		}
			
	}
	
	@EventHandler
	public void teamJoin(PlayerInteractEvent e){
		if (GameState.isState(GameState.EN_ATTENTE) && ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))){
			Player p = e.getPlayer();
		    ItemStack i = e.getItem();
		    
		    ItemStack redWool = new ItemStack(Material.WOOL, 1, (short)14);
		    ItemStack blueWool = new ItemStack(Material.WOOL, 1, (short)11);
		    
		    if (i.getData().equals(redWool.getData()) && this.ktk.equipeBleue.size()<this.ktk.getConfig().getInt("Taille des equipes")){
		    	//this.ktk.playerData.get(p.getUniqueId()).setTeam("Rouge");
		    	if(this.ktk.equipeBleue.contains(p.getUniqueId())){
		    		this.ktk.equipeBleue.remove(p.getUniqueId());
		    	}
		    	this.ktk.equipeRouge.add(p.getUniqueId());
		    	p.sendMessage(ChatColor.RED + "Vous rejoignez l'�quipe Rouge");
		    }else if (i.getData().equals(blueWool.getData())  && this.ktk.equipeRouge.size()<this.ktk.getConfig().getInt("Taille des equipes")){
		    	//this.ktk.playerData.get(p.getUniqueId()).setTeam("Bleu");
		    	if(this.ktk.equipeRouge.contains(p.getUniqueId())){
		    		this.ktk.equipeRouge.remove(p.getUniqueId());
		    	}
		    	this.ktk.equipeBleue.add(p.getUniqueId());
		    	p.sendMessage(ChatColor.BLUE + "Vous rejoignez l'�quipe Bleue");
		    }
		}
	}
	/*--------------*/

}

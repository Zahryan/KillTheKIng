package net.Zahryan.game;

import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.Zahryan.IGPlayer;
import net.Zahryan.KillTheKing;
import net.Zahryan.util.Titles;


public class GameManager {
	
	/*---Variables---*/
	/*---------------*/
	
	/*---Constructeur---*/
	/*------------------*/

	/*---M�thodes---*/
	public static void startGame(KillTheKing ktk){
		//Stockage des �quipes dans des listes
		/*ArrayList<IGPlayer> equipeBleue = new ArrayList<IGPlayer>();
		ArrayList<IGPlayer> equipeRouge = new ArrayList<IGPlayer>();
		for (Entry<UUID, IGPlayer> entry : ktk.playerData.entrySet()){
			if(entry.getValue().getTeam() == "Rouge"){
				equipeRouge.add(entry.getValue());
			}else if(entry.getValue().getTeam() == "Bleu"){
				equipeBleue.add(entry.getValue());
			}else{
				if(equipeRouge.size() < 10){
					equipeRouge.add(entry.getValue());
				}else{
					equipeBleue.add(entry.getValue());
				}
			}
		}*/
		
		for (Entry<UUID, IGPlayer> entry : ktk.playerData.entrySet()){
			if((!ktk.equipeBleue.contains(entry.getKey())) && (!ktk.equipeRouge.contains(entry.getKey()))){
				if(ktk.equipeBleue.size() <= ktk.equipeRouge.size()){
					ktk.equipeBleue.add(entry.getKey());
					Bukkit.getPlayer(entry.getKey()).sendMessage("BLEU");
				}else{
					ktk.equipeRouge.add(entry.getKey());
					Bukkit.getPlayer(entry.getKey()).sendMessage("ROUGE");
				}
			}
		}
		
		//G�n�ration des deux rois
		Random r = new Random();
		
		int i = r.nextInt(ktk.equipeBleue.size());
		UUID kingBleu = ktk.equipeBleue.get(i);
		ktk.playerData.get(kingBleu).setKing(true);
		Bukkit.broadcastMessage(ChatColor.BLUE + "Le roi Bleu est "+Bukkit.getPlayer(kingBleu).getDisplayName());
		
		i = r.nextInt(ktk.equipeRouge.size());
		UUID kingRouge = ktk.equipeRouge.get(i);
		ktk.playerData.get(kingRouge).setKing(true);
		Bukkit.broadcastMessage(ChatColor.RED + "Le roi Rouge est "+Bukkit.getPlayer(kingRouge).getDisplayName());
		
		//T�l�portation des joueurs et give des kits
		for (UUID id : ktk.equipeBleue){
			Player p = Bukkit.getPlayer(id);
			p.teleport(new Location(Bukkit.getWorld("world"),ktk.getConfig().getInt("Spawn Bleu X"),ktk.getConfig().getInt("Spawn Bleu Y"),ktk.getConfig().getInt("Spawn Bleu Z")));
			if(!ktk.playerData.get(id).isKing()){
				p.getInventory().clear();
				p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1, (byte)0));
				p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1, (byte)0));
				p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1, (byte)0));
				p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1, (byte)11));
			}else{
				p.getInventory().clear();
				p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1, (byte)0));
				p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1, (byte)0));
				p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1, (byte)0));
				p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1, (byte)0));
			}
		}
		
		for (UUID id : ktk.equipeRouge){
			Player p = Bukkit.getPlayer(id);
			p.teleport(new Location(Bukkit.getWorld("world"),ktk.getConfig().getInt("Spawn Rouge X"),ktk.getConfig().getInt("Spawn Rouge Y"),ktk.getConfig().getInt("Spawn Rouge Z")));
			if(!ktk.playerData.get(id).isKing()){
				p.getInventory().clear();
				p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1, (byte)0));
				p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1, (byte)0));
				p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1, (byte)0));
				p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1, (byte)14));
			}else{
				p.getInventory().clear();
				p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1, (byte)0));
				p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1, (byte)0));
				p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1, (byte)0));
				p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1, (byte)0));
			}
		}
	
		Bukkit.getWorld("world").setPVP(true);
	}

	public static void endGame(KillTheKing ktk, Player p) {
		//On passe tous les joueurs perdants en spectateur
		if(ktk.equipeBleue.contains(p.getUniqueId())){
			Bukkit.broadcastMessage("L'equipe rouge a gagn� !");
			for(UUID id : ktk.equipeBleue){
				Player player = Bukkit.getPlayer(id);
				player.setGameMode(GameMode.SPECTATOR);
			}
		}else{
			Bukkit.broadcastMessage("L'equipe bleue a gagn� !");
			for(UUID id : ktk.equipeRouge){
				Player player = Bukkit.getPlayer(id);
				player.setGameMode(GameMode.SPECTATOR);
			}
		}
		Bukkit.getScheduler().cancelTasks(ktk);
	}
	
	public static void endGame(KillTheKing ktk) {
		//On passe tous les joueurs en spectateur
		for (Entry<UUID, IGPlayer> entry : ktk.playerData.entrySet()){
			Player p = Bukkit.getPlayer(entry.getKey());
			p.setGameMode(GameMode.SPECTATOR);
			Titles.sendTitle(p, "", "Pas de gagnant !", 40);
		}	
	}
	/*--------------*/
}

package net.Zahryan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class IGPlayer {
	
	/*---Variables---*/
    private ScoreboardManager sManager = Bukkit.getScoreboardManager();
    private Scoreboard board = sManager.getNewScoreboard();
    private Objective objective = board.registerNewObjective("The ShootCraft", "dummy");
    private int kills = 0;
    private Score sKills = objective.getScore(ChatColor.DARK_AQUA + "Kills: " + kills);
    private Score sTimeLeft = objective.getScore("Temps : Attente");
    private boolean king = false;
	/*---------------*/

	
	/*---Constructeur---*/
    public IGPlayer(Player p){
    	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("Kill The King");
        objective.getScore(ChatColor.DARK_AQUA+"").setScore(5);
        sTimeLeft.setScore(4);
        objective.getScore(ChatColor.BLACK+"").setScore(3);
        sKills.setScore(2);
        objective.getScore("§f").setScore(1);
        p.setScoreboard(board);
    }
	/*------------------*/
	
	/*---Méthodes---*/
    public void kill(){
    	this.kills++;
    	this.board.resetScores(this.sKills.getEntry());
    	this.sKills= this.objective.getScore(ChatColor.DARK_AQUA + "Kills: " + kills);
    	this.sKills.setScore(2);
    }
	
	public void setKing(boolean b){
		this.king=b;
	}
	
	public boolean isKing(){
		return this.king;
	}
	
	public void updateTimer(int timer) {
        board.resetScores(sTimeLeft.getEntry());
        int minutes = timer/60;
        int dizainesSecondes = (timer - minutes * 60)/10;
        int uniteSeconde = (timer - minutes * 60)%10;
        sTimeLeft = objective.getScore("Temps : " + minutes+":"+dizainesSecondes+uniteSeconde);
        sTimeLeft.setScore(4);
	}
	/*--------------*/

}

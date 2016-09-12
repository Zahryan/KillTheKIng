package net.Zahryan.util;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Titles
{
  public static void sendTitle(Player p, String title, String subTitle, int ticks)
  {
    IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
    IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");

    PacketPlayOutTitle titre = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
    PacketPlayOutTitle sousTitre = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);

    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(titre);
    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(sousTitre);

    time(p, ticks);
  }

  private static void time(Player p, int ticks) {
    PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(time);
  }
}
package net.Zahryan.game;

public enum GameState
{
  EN_ATTENTE, EN_JEU, FIN;

  private static GameState etatActuel;

  private GameState() {
	  
  }

  public static void setState(GameState state) {
    GameState.etatActuel = state;
  }

  public static boolean isState(GameState state) {
    return etatActuel == state;
  }

  public static GameState getState() {
    return etatActuel;
  }
}
import java.util.*;

public class AdventureGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        game.start();
    }
}

class Game {
    private Scanner scanner;
    private Player player;
    private Map map;
    private boolean isGameOver;

    public Game(Scanner scanner) {
        this.scanner = scanner;
        this.isGameOver = false;
    }

    public void start() {
        System.out.println("Welcome to the Adventure Game!");
        System.out.println("Enter your character's name: ");
        String name = scanner.nextLine();
        player = new Player(name);
        map = new Map();
        player.setCurrentRoom(map.getStartingRoom());
        
        while (!isGameOver) {
            displayStatus();
            String command = scanner.nextLine();
            processCommand(command);
        }
    }

    private void displayStatus() {
        System.out.println("\n" + player.getName() + " is in the " + player.getCurrentRoom().getName());
        System.out.println("Health: " + player.getHealth());
        System.out.println("Inventory: " + player.getInventory());
        System.out.println("What would you like to do?");
    }

    private void processCommand(String command) {
        if (command.equalsIgnoreCase("look")) {
            look();
        } else if (command.equalsIgnoreCase("inventory")) {
            showInventory();
        } else if (command.startsWith("go")) {
            go(command);
        } else if (command.startsWith("take")) {
            take(command);
        } else if (command.startsWith("fight")) {
            fight(command);
        } else if (command.equalsIgnoreCase("quit")) {
            quit();
        } else {
            System.out.println("Invalid command.");
        }
    }

    private void look() {
        System.out.println(player.getCurrentRoom().getDescription());
    }

    private void showInventory() {
        System.out.println("You have the following items:");
        for (String item : player.getInventory()) {
            System.out.println(item);
        }
    }

    private void go(String command) {
        String direction = command.substring(3).trim().toLowerCase();
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println("You go " + direction + " to the " + nextRoom.getName());
        } else {
            System.out.println("You can't go that way.");
        }
    }

    private void take(String command) {
        String itemName = command.substring(5).trim();
        if (player.getCurrentRoom().hasItem(itemName)) {
            player.addItem(itemName);
            player.getCurrentRoom().removeItem(itemName);
            System.out.println("You take the " + itemName);
        } else {
            System.out.println("There is no such item here.");
        }
    }

    private void fight(String command) {
        String enemyName = command.substring(5).trim();
        if (player.getCurrentRoom().hasEnemy(enemyName)) {
            Enemy enemy = player.getCurrentRoom().getEnemy(enemyName);
            System.out.println("You engage in a fight with " + enemy.getName() + "!");
            int damage = new Random().nextInt(10) + 1;
            enemy.takeDamage(damage);
            System.out.println("You dealt " + damage + " damage.");
            if (enemy.getHealth() <= 0) {
                System.out.println("You have defeated " + enemy.getName() + "!");
                player.getCurrentRoom().removeEnemy(enemy);
            }
        } else {
            System.out.println("There is no enemy named " + enemyName + " here.");
        }
    }

    private void quit() {
        System.out.println("You have quit the game.");
        isGameOver = true;
    }
}

class Player {
    private String name;
    private int health;
    private List<String> inventory;
    private Room currentRoom;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void addItem(String item) {
        inventory.add(item);
    }

    public void removeItem(String item) {
        inventory.remove(item);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            System.out.println("You have died.");
        }
    }
}

class Map {
    private Room startingRoom;
    private Room forest;
    private Room cave;
    private Room village;

    public Map() {
        startingRoom = new Room("Starting Room", "You are in a small, dimly lit room.");
        forest = new Room("Forest", "You are in a dense forest. The trees are tall and the air smells fresh.");
        cave = new Room("Cave", "You enter a dark cave. The air is damp and cold.");
        village = new Room("Village", "You stand in a small village with stone buildings and dirt roads.");
        
        startingRoom.addExit("north", forest);
        forest.addExit("south", startingRoom);
        forest.addExit("east", cave);
        cave.addExit("west", forest);
        cave.addExit("south", village);
        village.addExit("north", cave);

        startingRoom.addItem("Map");
        forest.addItem("Wooden Sword");
        cave.addItem("Torch");

        startingRoom.addEnemy(new Enemy("Goblin", 30));
        forest.addEnemy(new Enemy("Wolf", 50));
    }

    public Room getStartingRoom() {
        return startingRoom;
    }
}

class Room {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private List<String> items;
    private List<Enemy> enemies;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addExit(String direction, Room room) {
        exits.put(direction, room);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void addItem(String item) {
        items.add(item);
    }

    public boolean hasItem(String item) {
        return items.contains(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public boolean hasEnemy(String name) {
        for (Enemy enemy : enemies) {
            if (enemy.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Enemy getEnemy(String name) {
        for (Enemy enemy : enemies) {
            if (enemy.getName().equalsIgnoreCase(name)) {
                return enemy;
            }
        }
        return null;
    }
}

class Enemy {
    private String name;
    private int health;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            System.out.println(name + " has been defeated.");
        }
    }
}

class Item {
    private String name;
    private String description;
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class Weapon extends Item {
    private int attackDamage;
    
    public Weapon(String name, String description, int attackDamage) {
        super(name, description);
        this.attackDamage = attackDamage;
    }
    
    public int getAttackDamage() {
        return attackDamage;
    }
}

class Armor extends Item {
    private int defenseValue;
    
    public Armor(String name, String description, int defenseValue) {
        super(name, description);
        this.defenseValue = defenseValue;
    }
    
    public int getDefenseValue() {
        return defenseValue;
    }
}

class Potion extends Item {
    private int healingAmount;
    
    public Potion(String name, String description, int healingAmount) {
        super(name, description);
        this.healingAmount = healingAmount;
    }
    
    public int getHealingAmount() {
        return healingAmount;
    }
}

class Quest {
    private String name;
    private String description;
    private boolean isComplete;

    public Quest(String name, String description) {
        this.name = name;
        this.description = description;
        this.isComplete = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void complete() {
        isComplete = true;
        System.out.println("Quest " + name + " completed!");
    }

    public boolean isComplete() {
        return isComplete;
    }
}

class QuestManager {
    private List<Quest> quests;

    public QuestManager() {
        quests = new ArrayList<>();
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public void listQuests() {
        for (Quest quest : quests) {
            System.out.println(quest.getName() + ": " + quest.getDescription());
            if (quest.isComplete()) {
                System.out.println("Completed");
            } else {
                System.out.println("Not completed");
            }
        }
    }

    public void completeQuest(String questName) {
        for (Quest quest : quests) {
            if (quest.getName().equals(questName)) {
                quest.complete();
                return;
            }
        }
        System.out.println("Quest not found.");
    }
}

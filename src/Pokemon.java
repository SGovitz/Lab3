public class Pokemon {
    private int pokedex;
    private String name;
    private int height;
    private int weight;
    private int hp;
    private int attack;
    private int defense;
    private int sAttack;
    private int sDefense;
    private int speed;
    private String type;
    private int evo;

    public Pokemon(String[] data) {
        // CSV order: pokedex, name, height, weight, hp, attack, defense, s_attack, s_defense, speed, type, evo_set, info
        // We ignore data[12] (info)
        this.pokedex = Integer.parseInt(data[0]);
        this.name = data[1].replace("\"", "");
        this.height = Integer.parseInt(data[2]);
        this.weight = Integer.parseInt(data[3]);
        this.hp = Integer.parseInt(data[4]);
        this.attack = Integer.parseInt(data[5]);
        this.defense = Integer.parseInt(data[6]);
        this.sAttack = Integer.parseInt(data[7]);
        this.sDefense = Integer.parseInt(data[8]);
        this.speed = Integer.parseInt(data[9]);
        this.type = data[10].replaceAll("[{}]", "");
        this.evo = Integer.parseInt(data[11]);
    }

    public Object[] toObjectArray() {
        return new Object[]{
                pokedex, name, height, weight, hp, attack, defense, sAttack, sDefense, speed, type, evo
        };
    }

    public int getPokedex() {
        return pokedex;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSAttack() {
        return sAttack;
    }

    public int getSDefense() {
        return sDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getEvo() {
        return evo;
    }

    public String toString() {
        return name + " - HP: " + hp + ", Attack: " + attack + ", Defense: " + defense + ", Speed: " + speed + ", Type: " + type;
    }
}

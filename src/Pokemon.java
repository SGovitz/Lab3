public class Pokemon {
    private String name;
    private String type;
    private String evoSet;
    private int height;
    private int weight;
    private int hp;
    private int attack;
    private int defense;
    private int sAttack;
    private int sDefense;
    private int speed;

    public Pokemon(String[] data) {
        // CSV order: id, name, height, weight, hp, attack, defense, s_attack, s_defense, speed, type, evo_set, info
        // We ignore data[0] (id) and data[12] (info)
        this.name = data[1].replace("\"", "");
        this.height = Integer.parseInt(data[2]);
        this.weight = Integer.parseInt(data[3]);
        this.hp = Integer.parseInt(data[4]);
        this.attack = Integer.parseInt(data[5]);
        this.defense = Integer.parseInt(data[6]);
        this.sAttack = Integer.parseInt(data[7]);
        this.sDefense = Integer.parseInt(data[8]);
        this.speed = Integer.parseInt(data[9]);
        this.type = data[10].replace("\"", "");
        this.evoSet = data[11].replace("\"", "");
    }

    public Object[] toObjectArray() {
        return new Object[]{
                name, height, weight, hp, attack, defense, sAttack, sDefense, speed, type, evoSet
        };
    }

    @Override
    public String toString() {
        return name + " - HP: " + hp + ", Attack: " + attack + ", Defense: " + defense;
    }
}

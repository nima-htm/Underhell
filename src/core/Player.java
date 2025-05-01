package core;

public class Player {

    private String name;
    private int hp;

    public Player(String name, int hp)
    {
        this.name = name;
        this.hp = hp;
    }

    public void Attack(){
        System.out.println(name + "just attacked !");

    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public void takeDamage(int damage){
        hp -= damage;
        if (hp<0) hp =0;
    }
    public boolean isAlive(){
        return hp > 0;
    }
}

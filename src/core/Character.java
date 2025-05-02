package core;

public abstract class Character {

    protected String name;
    protected int hp;

    public Character(String name, int hp){
        this.name = name;
        this.hp = hp;
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

    public abstract void weakAttack();
    public abstract void mediumAttack();
    public abstract void strongAttack();

    public void randomAttack()
    {
        int rand = (int) (Math.random() * 3);
        switch (rand)
        {
            case 0 : weakAttack();
            case 1 : mediumAttack();
            case 2 : strongAttack();
        }
    }
}

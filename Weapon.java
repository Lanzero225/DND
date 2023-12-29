import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Weapon {
    private String name, mastery, damageType, currency;
    private int diceNumber, damageDie, versatileDamage, cost;
    private float weight;
    private boolean versatile, throwable, twoHanded, ammo;
    private String ammunition;
    private ArrayList<String> properties;

    public static Weapon findWeapon(String name){
        for(Weapon x : Main.weaponList){
            if(x.getName().equals(name)){
                return x;
            }
        }
        return null;
    }
    //One Weapon Roll for Attack
    public static int[] rollForAttack(Character player, Weapon weapon,
                                      AtomicBoolean advantage, AtomicBoolean disadvantage){

        int additionalDieRoll = 0;
        int usedModifier;
        //Declare
        //INITIALIZE LOCAL WEAPON VARIABLES
        //Ask for Versatile (GUI) --
        //Ask for Throwable (GUI) --

        //BARBARIAN
        if(player.barbLevels.getBarbLevel() != 0){

            //Reckless attack check. --
            //If activated, must bypass advantage and disadvantage ask
            //ROLL HERE
            if(player.barbLevels.isRecklessCheck()){
                advantage.set(true);
                System.out.println("SUUUUUUUUUUUUU");
                player.barbLevels.setRecklessCheck(false);
            }
        }

        //Check Proficiency (AUTO) --
        if(player.isWeaponProficient(weapon)){
            additionalDieRoll += player.getProficiency();

        }

        //Check if finesse weapon
        //if yes, modifier is either strength or dex
        //Else, use strength only
        usedModifier = player.getStatModifier(0);
        if(weapon.isFinesse()){
            //Uses dexterity instead
            if(player.getStatModifier(1) > usedModifier){
                usedModifier = player.getStatModifier(1);
            }
        }
        additionalDieRoll += usedModifier;

        //Dueling check
        if(player.findSkill("Dueling")){
            additionalDieRoll += 2;
        }
        //ADVANTAGE DISADVANTAGE ASK (GUI) --

        //ROLL HERE
        int initialRoll;
        Random rand = new Random();

        int firstRoll = rand.nextInt(20) + 1;
        initialRoll = firstRoll;
        int secondRoll = rand.nextInt(20) + 1;

        if (Boolean.parseBoolean(advantage.toString()) && secondRoll > firstRoll) {
            initialRoll = secondRoll;
        }
        if (Boolean.parseBoolean(disadvantage.toString()) && secondRoll < firstRoll) {
            initialRoll = secondRoll;
        }
        int finalRoll = initialRoll + additionalDieRoll;

        return new int[]{initialRoll, finalRoll, usedModifier};
    }

    //Rolling for damage, single weapon
    public static int[] rollForDamage(Character player, Weapon finalWeapon,
                                      AtomicBoolean thrown, AtomicBoolean oneHandedWield, int[] attackRoll) {
        int additionalDamageRoll = 0;

        //Proficiency
        if(player.isWeaponProficient(finalWeapon)){
            additionalDamageRoll += attackRoll[2];
            System.out.println(additionalDamageRoll);
        }
        //Add for rage (AUTO) --
        if (player.barbLevels.getBarbLevel() != 0 && player.barbLevels.isRageChecker()) {
            additionalDamageRoll += player.barbLevels.getRageDmg();
            System.out.println(additionalDamageRoll);
            System.out.println("HEYYYYYn dfdfdfdf\n\n\nddfdff");
        }

        //Thrown Weapon Fighting Style
        if (Boolean.parseBoolean(thrown.toString()) && player.findSkill("Thrown-Weapon")) {
            additionalDamageRoll += 2;
        }

        //ROLL FOR DAMAGE
        int maximumRoll = finalWeapon.getDamageDie();
        if(oneHandedWield.get() && finalWeapon.isVersatile()){
            maximumRoll = finalWeapon.getVersatileDamage();
        }

        int initialDamageRoll = 0;
        Random rand = new Random();
        int roll = 0;
        while(finalWeapon.getDiceNumber() > roll){
            initialDamageRoll += rand.nextInt(maximumRoll) + 1;
            roll++;
        }

        int finalDamageRoll;
        finalDamageRoll = initialDamageRoll + additionalDamageRoll;

        //IF NAT ROLL IS 1
        if(attackRoll[0] == 1){
            //DISABLE RAGE
            if(player.barbLevels.isRageChecker()){
                new JOptionPane("MAN, you suck");
                player.barbLevels.setRageChecker(false);
            }
        }
        //ELSE FOR NAT 20
        else if(attackRoll[0] == 20) {
            //DOUBLE ROLL
            finalDamageRoll += initialDamageRoll;
        }


        return new int[]{initialDamageRoll, finalDamageRoll, additionalDamageRoll};
    }

    //Rolling for damage, two weapon version


    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public int getDamageDie() {
        return damageDie;
    }

    public void setDamageDie(int damageDie) {
        this.damageDie = damageDie;
    }

    public String getMastery() {
        return mastery;
    }

    public void setMastery(String mastery) {
        this.mastery = mastery;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getVersatileDamage() {
        return versatileDamage;
    }

    public void setVersatileDamage(int versatileDamage) {
        this.versatileDamage = versatileDamage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<String> properties) {
        this.properties = properties;
    }


    public boolean isVersatile() {
        return versatile;
    }

    public void setVersatile(boolean versatile) {
        this.versatile = versatile;
    }

    public boolean isThrowable() {
        return throwable;
    }

    public void setThrowable(boolean throwable) {
        this.throwable = throwable;
    }
    public boolean isFinesse(){
        return this.properties.contains("Finesse");
    }

    public boolean isTwoHanded() {
        return twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        this.twoHanded = twoHanded;
    }

    public boolean isAmmo() {
        return ammo;
    }

    public void setAmmo(boolean ammo) {
        this.ammo = ammo;
    }

    public String getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(String ammunition) {
        this.ammunition = ammunition;
    }
    public boolean isLight(){
        return this.properties.contains("Light");
    }
}

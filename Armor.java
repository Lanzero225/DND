import java.util.ArrayList;

public class Armor {
    private String name, type;
    private int cost, armorClass, weight;
    private boolean dexterityModifier, stealthDisadvantage;
    private int modifierMaximum, requiredStrength;
//Armor,Type,Cost (in gp),Armor Class,
// Modifier (1 uses modifiers),Maximum (0 means no limit),
// Strength (0 means not required,Stealth (0 means no disadvantage),Weight in pounds
    public Armor(String[] temp){
        name = temp[0];
        type = temp[1];
        cost = Integer.parseInt(temp[2]);
        armorClass = Integer.parseInt(temp[3]);
        dexterityModifier = Integer.parseInt(temp[4]) != 0;
        modifierMaximum = Integer.parseInt(temp[5]);
        requiredStrength = Integer.parseInt(temp[6]);
        stealthDisadvantage = Integer.parseInt(temp[7]) != 0;
        weight = Integer.parseInt(temp[8]);
    }
    public static Armor findArmor(String armor){
        Armor toReturn = null;
        for(Armor x : Main.armorList){
            if(x.getName().equals(armor)){
                toReturn = x;
            }
        }
        return toReturn;
    }
    public static ArrayList<Armor> getSpecificType(String type){
        ArrayList<Armor> toReturn = new ArrayList<>();
        for(Armor x : Main.armorList) {
            if (x.getType().equals(type)) {
                toReturn.add(x);
            }
        }
        return toReturn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isDexterityModifier() {
        return dexterityModifier;
    }

    public void setDexterityModifier(boolean dexterityModifier) {
        this.dexterityModifier = dexterityModifier;
    }

    public boolean isStealthDisadvantage() {
        return stealthDisadvantage;
    }

    public void setStealthDisadvantage(boolean stealthDisadvantage) {
        this.stealthDisadvantage = stealthDisadvantage;
    }

    public int getModifierMaximum() {
        return modifierMaximum;
    }

    public void setModifierMaximum(int modifierMaximum) {
        this.modifierMaximum = modifierMaximum;
    }

    public int getRequiredStrength() {
        return requiredStrength;
    }

    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }
}

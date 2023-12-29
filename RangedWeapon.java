import java.util.ArrayList;

public class RangedWeapon extends Weapon{
    private int rangeMinimum, rangeMaximum;


    public RangedWeapon(String[] weaponProperties){
        this.setName(weaponProperties[0]);
        this.setMastery(weaponProperties[1]);
        this.setDamageType(weaponProperties[3]);
        this.setWeight(Float.parseFloat(weaponProperties[4]));

        ArrayList<String> additionalProperties = new ArrayList<>();
        for(int i = 5; i <= 8; i++){
            if(!weaponProperties[i].equals("0")){
                additionalProperties.add(weaponProperties[i]);
            }
        }
        this.setProperties(additionalProperties);

        this.setDiceNumber(Integer.parseInt(weaponProperties[9]));
        this.setDamageDie(Integer.parseInt(weaponProperties[10]));
        this.setVersatileDamage(Integer.parseInt(weaponProperties[11]));

        this.setVersatile(false);
        if(this.getVersatileDamage() > this.getDamageDie()){
            this.setVersatile(true);
        }

        this.setTwoHanded(false);
        if(additionalProperties.contains("Two-Handed")){
            this.setTwoHanded(true);
        }

        this.rangeMinimum = Integer.parseInt(weaponProperties[12]);
        this.rangeMaximum = Integer.parseInt(weaponProperties[13]);
        this.setCost(Integer.parseInt(weaponProperties[15]));
        this.setCurrency(weaponProperties[16]);

        this.setAmmo(true);
        if(weaponProperties[14].equals("None")){
            this.setAmmo(false);
        }
        this.setAmmunition(weaponProperties[14]);

        this.setThrowable(false);
    }

    public int getRangeMinimum() {
        return rangeMinimum;
    }

    public void setRangeMinimum(int rangeMinimum) {
        this.rangeMinimum = rangeMinimum;
    }

    public int getRangeMaximum() {
        return rangeMaximum;
    }

    public void setRangeMaximum(int rangeMaximum) {
        this.rangeMaximum = rangeMaximum;
    }


}

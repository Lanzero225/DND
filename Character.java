import java.util.Random;

public class Character extends DNDCharacter {
    protected BarbarianClass barbLevels = new BarbarianClass();
    Character(String charName, String playerName, String race,
              String background, String alignment, int[] stats){
        this.setPlayerName(playerName);
        this.setCharName(charName);
        this.setAlignment(alignment);
        this.setBackground(background);
        this.setRace(race);
        this.initializeClassLevels();
        this.setHpMax(0);
        this.setHp(0);
        this.setHpCap(0);
        this.setProficiency(2);
        this.setWalkSpeed(30); //SUBJECT TO CHANGE
        this.setSwimSpeed(0); //SUBJECT TO CHANGE
        this.setClimbSpeed(0); //SUBJECT TO CHANGE
        this.setFlySpeed(0); //SUBJECT TO CHANGE
        this.setInspiration(6);

        this.setStats(stats);
        this.setArmorClass(10);
        this.setArmorDon(null);
        this.setShield(false);

    }
    public void levelUP(String chosenClass){
        this.addTotalLevel();
        if(chosenClass.equals("Barbarian")){
            barbLevels.barbarianLevelUp(this);
            this.addClassLevel(1);
        }

        this.updateModifers();


    }
    public int rollStat(int stat){
        Random rand = new Random();
        int modifier = this.getSkillModifier(stat);

        return rand.nextInt(20) + 1 + modifier;
    }
    public static int calculateArmorClass(Character character){
        Armor currentArmor = character.getArmorDon();
        if(character.findFeature("Unarmored Defense") && currentArmor == null){
            return 10 + character.getStatModifier(1) + character.getStatModifier(2);
        }
        if(currentArmor == null){
            return 10 + character.getStatModifier(1);
        }


        int armorClass = 0;
        if(currentArmor.isDexterityModifier()){
            armorClass = currentArmor.getArmorClass();
            if(currentArmor.getModifierMaximum() != 0){
                armorClass += (Math.min(character.getStatModifier(1), currentArmor.getModifierMaximum()));
            }
        }
        return armorClass;
    }
    public int addExp(int exp){
        this.addExperiencePoints(exp);
        if(this.getTotalLevel() == 1 && this.getExperiencePoints() >= 300) return 2;
        else if(this.getTotalLevel() == 2 && this.getExperiencePoints() >= 900) return 3;
        else if(this.getTotalLevel() == 3 && this.getExperiencePoints() >= 1800) return 4;
        else if(this.getTotalLevel() == 4 && this.getExperiencePoints() >= 4500) return 5;
        return 0;
    }
}

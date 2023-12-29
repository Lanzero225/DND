import java.util.ArrayList;
import java.util.Scanner;

public class BarbarianClass implements LevelUp{
    Scanner input = new Scanner(System.in);
    private int barbLevel;
    private String subclass;
    private int rages, rageDmg, maxRage;
    private int rageTurns;
    private boolean rageChecker, recklessCheck;
    private int bruteDie, maxBruteDie;
    private int frenzy, rageSkip, frenzySkip, extraAttacks;
    private String[] totemList = new String[3];
    private int totemCount;
    BarbarianClass(){
        barbLevel = 0;
        totemCount = 0;
    }

    public int getBarbLevel(){
        return this.barbLevel;
    }
    public void barbarianLevelUp(Character playerChar){
        if(barbLevel > 20){
            return;
        }

        barbLevel++;
        switch (barbLevel) {
            case 1 -> firstLevel(playerChar);
            case 2 -> secondLevel(playerChar);
            case 3 -> thirdLevel(playerChar);
            case 4 -> fourthLevel(playerChar);
            case 5 -> fifthLevel(playerChar);
            case 6 -> sixthLevel(playerChar);
            case 7 -> seventhLevel(playerChar);
            case 8 -> eightLevel(playerChar);
            case 9 -> ninthLevel(playerChar);
            case 10 -> tenthLevel(playerChar);
            case 11 -> eleventhLevel(playerChar);
            case 12 -> twelfthLevel(playerChar);
            case 13 -> thirteenthLevel(playerChar);
            case 14 -> fourteenthLevel(playerChar);
            case 15 -> fifteenthLevel(playerChar);
            case 16 -> sixteenthLevel(playerChar);
            case 17 -> seventeenthLevel(playerChar);
            case 18 -> eighteenthLevel(playerChar);
            case 19 -> nineteenthLevel(playerChar);
            case 20 -> twentiethLevel(playerChar);
        }
    }

    @Override
    public void firstLevel(Character playerChar) {
        playerChar.addHitDie(12);
        playerChar.addFeature("Unarmored Defense");
        playerChar.addFeature("Rage");
        playerChar.addBonusAction("Rage");
        playerChar.addBonusAction("Disable Rage");

        playerChar.barbLevels.setRageChecker(false);

        playerChar.addArmorProficiency(Armor.findArmor("Shield"));
        for(String x : Main.martialWeaponList){
            playerChar.addWeaponProficiency(Weapon.findWeapon(x));
        }
        for(String x : Main.simpleWeaponList){
            playerChar.addWeaponProficiency(Weapon.findWeapon(x));
        }
        System.out.println("You have unlocked Rage and Unarmored Defense!");
        if(playerChar.getTotalLevel() > 1){
            playerChar.addHpCap(7 + playerChar.getStatModifier(2)/*Constitution Modifier*/);
            playerChar.setHpMax(playerChar.getHpCap() * 2);
            playerChar.setHp(playerChar.getHpCap());
        }
        else{
            playerChar.addWeapon(Weapon.findWeapon("Javelin"));
            playerChar.addWeapon(Weapon.findWeapon("Javelin"));
            playerChar.addWeapon(Weapon.findWeapon("Hand Crossbow"));
            playerChar.addWeapon(Weapon.findWeapon("Hand Crossbow"));
            playerChar.addAmmo("Arrow", 2);
            playerChar.addWeapon(Weapon.findWeapon("Javelin"));
            playerChar.addWeapon(Weapon.findWeapon("Javelin"));
            playerChar.addWeapon(Weapon.findWeapon("Unarmed Strike"));
            playerChar.addSavingThrow("Strength");
            playerChar.addSavingThrow("Constitution");
            playerChar.setHp(12 + playerChar.getStatModifier(2));/*Constitution Modifier*/
            playerChar.setHpCap(12 + playerChar.getStatModifier(2)/*Constitution Modifier*/);
            playerChar.setHpMax(playerChar.getHpCap() * 2);
            for(Armor x : Armor.getSpecificType("Light Armor")){
                playerChar.addArmorProficiency(x);
            }
            for(Armor x : Armor.getSpecificType("Medium Armor")){
                playerChar.addArmorProficiency(x);
            }
            //Starting Skills
            String[] barbarianStartingSkills = {"Animal Handling", "Athletics",
                    "Intimidation", "Nature", "Perception", "Survival"};
            String[] barbarianStartingEquipment = {"Greataxe", "Martial Melee Weapon"};
            new GUI(barbarianStartingSkills, barbarianStartingEquipment, playerChar);
        }
        this.maxRage = 2;
        this.rages = 2;
        this.rageDmg = 2;
    }
    @Override
    public void secondLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Reckless Attack");
        playerChar.addBonusAction("Reckless Attack");
        playerChar.addFeature("Danger Sense");
        System.out.println("You have unlocked Reckless Attack and Danger Sense");
        this.rages = 2;
        new GUI(playerChar);
    }
    @Override
    public void thirdLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        char subclassPick;
        int setter = 0;
        System.out.println("[1] - Berserker");
        System.out.println("[2] - Totem Warrior");
        System.out.println("You have unlocked the Primal Path, please choose your subclass: ");
        subclassPick = input.next().charAt(0);
        do{
            switch(subclassPick){
                case '1':
                    System.out.println("You are now a Barbarian Berserker!");
                    System.out.println("You have obtained Berserker!");
                    playerChar.barbLevels.setSubclass("Berserker");
                    playerChar.addFeature("Frenzy");
                    playerChar.addBonusAction("Frenzy");
                    setter++;
                    break;
                case '2':
                    System.out.println("You are now a Barbarian Totem Warrior!");
                    System.out.println("You have obtained Spirit Seeker!");
                    System.out.println("You can now cast Beast Sense and Speak with Animals!");
                    playerChar.barbLevels.setSubclass("Totem Warrior");
                    playerChar.addFeature("Spirit Seeker");
                    playerChar.addSpell("Beast Sense");
                    playerChar.addSpell("Speak with Animals");
                    playerChar.barbLevels.totemSpirit();
                    setter++;
                    break;
            }
        }while(setter == 0);
        this.rages = 3;
        this.maxRage = 3;
    }
    @Override
    public void fourthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.abilityScoreImprovement();
        this.rages = 3;
    }
    @Override
    public void fifthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Extra Attack (Barbarian)");
        playerChar.addFeature("Fast Movement");
        System.out.println("You have unlocked Extra Attack and Fast Movement");
        this.rages = 3;
    }
    @Override
    public void sixthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        if(playerChar.barbLevels.getSubclass().equals("Berserker")){
            playerChar.addFeature("Mindless Rage");
            System.out.println("You have obtained Mindless Rage!");
        }
        else if(playerChar.barbLevels.getSubclass().equals("Totem Warrior")){
            playerChar.addFeature("Aspect of the Beast");
            System.out.println("You have obtained Aspect of the Beast!");
            playerChar.barbLevels.totemSpirit();
            if(playerChar.barbLevels.getTotem(1).equals("Tiger")){
                int tts;
                ArrayList<String> tigerAspectSkills = playerChar.barbLevels.tigerAspect(playerChar.getSkillList());
                tts = tigerAspectSkills.size();
                if(tts == 0){
                    System.out.println("You already have skills for Tiger Aspect!");
                }
                else{
                    for(String x : tigerAspectSkills){
                        playerChar.addFeature(x);
                    }
                }
            }
        }
        this.rages = 4;
        this.maxRage = 4;
    }
    @Override
    public void seventhLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Feral Instinct");
        System.out.println("You have unlocked Feral Instinct!");
        this.rages = 4;
    }
    @Override
    public void eightLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.abilityScoreImprovement();
        this.rages = 4;
        this.rageDmg = 3;
    }
    @Override
    public void ninthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Brutal Critical");
        System.out.println("You have unlocked Brutal Critical!");
        this.rages = 4;
        this.maxBruteDie = 1;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void tenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        if(playerChar.barbLevels.getSubclass().equals("Berserker")){
            playerChar.addFeature("Intimidating Presence");
            playerChar.addAction("Intimidating Presence");
            System.out.println("You have obtained Intimidating Presence!");
        }
        else if(playerChar.barbLevels.getSubclass().equals("Totem Warrior")){
            playerChar.addFeature("Spirit Walker");
            playerChar.addSpell("Commune with Nature");
            System.out.println("You have obtained Spirit Walker!");
        }
        this.rages = 4;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void eleventhLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Relentless Rage");
        System.out.println("You have obtained Relentless Rage!");
        this.rages = 4;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void twelfthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.abilityScoreImprovement();
        this.rages = 5;
        this.maxRage = 5;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void thirteenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        this.rages = 5;
        this.maxBruteDie = 2;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void fourteenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        if(playerChar.barbLevels.getSubclass().equals("Berserker")){
            playerChar.addFeature("Retaliation");
            System.out.println("You have obtained Retaliation!");
        }
        else if(playerChar.barbLevels.getSubclass().equals("Totem Warrior")){
            playerChar.addFeature("Totemic Attunement");
            System.out.println("You have obtained Totemic Attunement!");
            playerChar.barbLevels.totemSpirit();
        }
        this.bruteDie = maxBruteDie;
        this.rages = 5;
    }
    @Override
    public void fifteenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Persistent Rage");
        System.out.println("You have obtained Persistent Rage!");
        this.rages = 5;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void sixteenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.abilityScoreImprovement();
        this.rages = 5;
        this.rageDmg = 4;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void seventeenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        this.rages = 6;
        this.maxRage = 6;
        this.maxBruteDie = 3;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void eighteenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Indomitable Might");
        System.out.println("You have unlocked Indomitable Might!");
        this.rages = 6;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void nineteenthLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.abilityScoreImprovement();
        this.rages = 6;
        this.bruteDie = maxBruteDie;
    }
    @Override
    public void twentiethLevel(Character playerChar){
        playerChar.hpLevelUp(12, 7);
        playerChar.addFeature("Primal Champion");
        System.out.println("You have unlocked Primal Champion!");
        playerChar.statUpdate('1', 4);
        playerChar.statUpdate('3', 4);
        this.maxRage = -1;
        this.bruteDie = maxBruteDie;
    }
    public void totemSpirit(){
        String[] Totem_Animals = {"Bear", "Eagle", "Elk", "Tiger", "Wolf"};
        for(int v = 0; v < 5; v++){
            System.out.println("[" + (v + 1) + "] - " + Totem_Animals[v]);
        }
        if(barbLevel == 3){
            totemPicker(1);
        }
        else if(barbLevel == 6){
            totemPicker(2);
        }
        else if(barbLevel == 14){
            totemPicker(3);
        }
    }
    public void totemPicker(int lvl){
        int setter = 0;
        char pick;
        do{
            System.out.println("Please choose a totem animal: ");
            pick = input.next().charAt(0);
            switch(pick){
                case '1':
                    totemSet(lvl, "Bear");
                    setter++;
                    break;
                case '2':
                    totemSet(lvl, "Eagle");
                    setter++;
                    break;
                case '3':
                    totemSet(lvl, "Elk");
                    setter++;
                    break;
                case '4':
                    totemSet(lvl, "Tiger");
                    setter++;
                    break;
                case '5':
                    totemSet(lvl, "Wolf");
                    setter++;
                    break;
                default:
                    System.out.println("Please try again!");
            }
        }while(setter == 0);
    }
    public void totemSet(int lvl, String animal){
        totemCount++;
        switch(lvl){
            case 1:
                totemList[0] = animal; break;
            case 2:
                totemList[1] = animal; break;
            case 3:
                totemList[2] = animal; break;
        }
    }
    public String getTotem(int pick){
        return totemList[pick];
    }
    public ArrayList<String> tigerAspect(ArrayList<String> charSkills){
        //Starting Skills
        ArrayList<String> skillToReturn = new ArrayList<String>();
        String[] tigerSkills = {"Athletics", "Acrobatics",
                "Stealth", "Survival"};

        //Checks if there are valid choices/enough choices for the users
        int skillsFound = 0, counter;
        for (String x : tigerSkills) {
            if(charSkills.contains(x)){
                skillsFound++;
            }
        }
        if(skillsFound == 0 || skillsFound == 1 || skillsFound == 2){
            counter = 2;
        }
        else if(skillsFound == 3){
            counter = 1;
        }
        else {
            counter = 0;
            return skillToReturn;
        }

        int numSkills = 0;
        int pick;
        for (int z = 0; z < 4; z++){
            System.out.println("[" + (z + 1) + "] - " + tigerSkills[z]);
        }
        while (numSkills < counter){
            System.out.println("Choose a skill: ");
            pick = input.nextInt();

            //Input out of range
            if(pick < 1 || pick > 4){
                System.out.println("Please enter again!");
                continue;
            }
            pick--;

            //Input Found In List
            if(charSkills.contains(tigerSkills[pick])){
                System.out.println("Skill already there!");
            }
            //Input Not Found In List
            else{
                skillToReturn.add(tigerSkills[pick]);
                numSkills++;
            }
        }
        return skillToReturn;

    }
    public int getRages() {
        return rages;
    }
    public void setRages(int rages) {
        this.rages = rages;
    }
    public int getRageDmg() {
        return rageDmg;
    }
    public void setRageDmg(int rageDmg) {
        this.rageDmg = rageDmg;
    }
    public int getMaxRage() {
        return maxRage;
    }
    public void setMaxRage(int maxRage) {
        this.maxRage = maxRage;
    }
    public String getSubclass() {
        return subclass;
    }
    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public int getRageTurns() {
        return rageTurns;
    }

    public void setRageTurns(int rageTurns) {
        this.rageTurns = rageTurns;
    }

    public boolean isRageChecker() {
        return rageChecker;
    }

    public void setRageChecker(boolean rageChecker) {
        this.rageChecker = rageChecker;
    }

    public boolean isRecklessCheck() {
        return recklessCheck;
    }

    public void setRecklessCheck(boolean recklessCheck) {
        this.recklessCheck = recklessCheck;
    }

    public int getBruteDie() {
        return bruteDie;
    }

    public void setBruteDie(int bruteDie) {
        this.bruteDie = bruteDie;
    }
    public void decrementTurns(){
        this.rageTurns--;
    }
    public void nextTurnBarbarian(Character player) {
        if(player.barbLevels.isRageChecker()){
            player.barbLevels.decrementTurns();
            if(player.barbLevels.rageTurns == 0){
                player.barbLevels.setRageChecker(false);
            }
        }
    }
}

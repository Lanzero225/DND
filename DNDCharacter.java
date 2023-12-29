import java.util.ArrayList;
import java.util.Scanner;

public abstract class DNDCharacter {
    static Scanner input = new Scanner(System.in);
    private String charName;
    private String playerName;
    private int totalLevel = 0;
    private int[] classLevels = new int[13];
    private String race;
    private int raceNum;
    private String subRace;
    private int subRaceNum;
    private String background;
    private String alignment;
    private int hpMax, hp,hpCap;
    private int armorClass, initiative;
    private Armor armorDon;
    private boolean shield;
    private int walkSpeed, swimSpeed, climbSpeed, flySpeed;
    private int inspiration;
    private int[] stats = new int[6];
    private int[] modifiers = new int[6];
    private int proficiency;
    private int experiencePoints = 0;
    private int[] ammunition = new int[3];
    //0 is for arrow
    //1 is for rocks
    //2 is for blowgun darts
    private ArrayList<String> features = new ArrayList<>();
    private ArrayList<String> bonusAction = new ArrayList<>();
    private ArrayList<String> reaction = new ArrayList<>();
    private ArrayList<Armor> armorProficiency = new ArrayList<>();
    private ArrayList<Weapon> weaponProficiency = new ArrayList<>();
    private ArrayList<Integer> hitDieList = new ArrayList<>();
    private ArrayList<Weapon> weaponList = new ArrayList<>();
    private ArrayList<Armor> armorList = new ArrayList<>();
    private ArrayList<String> savingThrowList = new ArrayList<>();
    private ArrayList<String> skillList = new ArrayList<>();
    private ArrayList<String> spellList = new ArrayList<>();
    private ArrayList<String> actionList = new ArrayList<>();

    public void addFeature(String feature){
        this.features.add(feature);
    }
    public void addBonusAction(String bonusAction){
        this.bonusAction.add(bonusAction);
    }
    public void addReaction(String reaction){
        this.reaction.add(reaction);
    }
    public void addArmorProficiency(Armor armor){
        if(!this.armorProficiency.contains(armor)){
            this.armorProficiency.add(armor);
        }
    }
    public void addWeaponProficiency(Weapon weapon){
        if(!this.weaponProficiency.contains(weapon)){
            this.weaponProficiency.add(weapon);
        }
    }
    public void addHitDie(int hitDie){
        this.hitDieList.add(hitDie);
    }
    public void addWeapon(Weapon weapon){
        this.weaponList.add(weapon);
    }
    public void addSavingThrow(String stat){
        this.savingThrowList.add(stat);
    }
    public void addSpell(String spell){
        this.spellList.add(spell);
    }
    public void addSkill(String skill){
        if(this.findSkill(skill) == false){
            skillList.add(skill);
        }
    }
    public boolean findSkill(String skill){
        if(this.skillList.contains(skill)){
            return true;
        }
        return false;
    }
    public boolean findFeature(String feature){
        if (this.features.contains(feature)) {
            return true;
        }
        return false;
    }
    public void addAction(String action){
        actionList.add(action);
    }
    public boolean isWeaponProficient(Weapon weapon){
        return weaponProficiency.contains(weapon);
    }
    public void removeWeapon(Weapon weapon){
        this.weaponList.remove(weapon);
    }
    public void removeAmmo(String ammunition){

        switch (ammunition) {
            case "Arrow" -> this.ammunition[0]--;
            case "Rocks" -> this.ammunition[1]--;
            case "Blowgun Dart" -> this.ammunition[2]--;
        }
        System.out.println("ARROW " + this.ammunition[0]);
    }
    public void addAmmo(String ammunition, int amount){
        switch (ammunition) {
            case "Arrow" -> this.ammunition[0] += amount;
            case "Rocks" -> this.ammunition[1] += amount;
            case "Blowgun Dart" -> this.ammunition[2] += amount;
        }
    }
    public boolean hasAmmo(String ammunition){
        int totalAmmo = 0;
        switch (ammunition) {
            case "Arrow" -> totalAmmo = this.ammunition[0];
            case "Rocks" -> totalAmmo = this.ammunition[1]++;
            case "Blowgun Dart" -> totalAmmo = this.ammunition[2]++;
        }
        return totalAmmo != 0;
    }
    public int getAmmo(String ammunition){
        int totalAmmo = 0;
        switch (ammunition) {
            case "Arrow" -> totalAmmo = this.ammunition[0];
            case "Rocks" -> totalAmmo = this.ammunition[1]++;
            case "Blowgun Dart" -> totalAmmo = this.ammunition[2]++;
        }
        return totalAmmo;
    }

    public void abilityScoreImprovement(){
        char abPick, statup;
        int setter1 = 0, setter2 = 0;
        System.out.println("Do you want to add [A] a feat or [B] 2 ability scores: ");
        abPick = input.next().charAt(0);
        do{
            switch(abPick){
                case 'A': case 'a':
                    System.out.println("Feat Stuff");
                    //FEAT
                    setter1++;
                    break;
                case 'B': case 'b':
                    System.out.println("[1] - Strength");
                    System.out.println("[2] - Dexterity");
                    System.out.println("[3] - Constitution");
                    System.out.println("[4] - Intelligence");
                    System.out.println("[5] - Wisdom");
                    System.out.println("[6] - Charisma");
                    do{
                        System.out.println("Where would you like to add 1: ");
                        statup = input.next().charAt(0);
                        if (statup == '1' || statup == '2' || statup == '3' ||
                                statup == '4' || statup == '5' || statup == '6'){
                            this.statUpdate(statup, 1);
                            setter2++;
                        }
                        else{
                            System.out.println("Invalid input.");
                        }
                    }while(setter2 < 2);
                    updateModifers();
                    setter1++;
                    break;
            }
        }while(setter1 == 0);
    }
    public void updateModifers(){
        int modifier;
        for(int x = 0; x < 6; x++){
            modifier = (this.stats[x] - 10) / 2;
            this.modUpdate((x + 1), modifier);
        }
    }

    public void modUpdate(int x, int y){
        modifiers[x - 1] = y;
    }
    public void statUpdate(char aStat, int addStat){
        switch(aStat){
            case '1': stats[0] += addStat; break;
            case '2': stats[1] += addStat; break;
            case '3': stats[2] += addStat; break;
            case '4': stats[3] += addStat; break;
            case '5': stats[4] += addStat; break;
            case '6': stats[5] += addStat; break;
            default:
                throw new IllegalStateException("Unexpected value: " + aStat);
        }
    }
    public String getCharName(){
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getRaceNum() {
        return raceNum;
    }

    public void setRaceNum(int raceNum) {
        this.raceNum = raceNum;
    }

    public String getSubRace() {
        return subRace;
    }

    public void setSubRace(String subRace) {
        this.subRace = subRace;
    }

    public int getSubRaceNum() {
        return subRaceNum;
    }

    public void setSubRaceNum(int subRaceNum) {
        this.subRaceNum = subRaceNum;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAlignment(){
        return this.alignment;
    }
    public void setAlignment(String alignment){
        this.alignment = alignment;
    }
    public void setHpMax(int hpMax){
        this.hpMax = hpMax;
    }
    public int getHpMax(){
        return this.hpMax;
    }
    public void setHp(int hp){
        this.hp = hp;
    }
    public int getHp(){
        return this.hp;
    }
    public void setHpCap(int hpCap){
        this.hpCap = hpCap;
    }
    public int getHpCap(){
        return this.hpCap;
    }
    public void addHpCap(int hp){
        this.hpCap += hp;
    }
    public void hpLevelUp(int hpDie, int classHp){
        this.addHitDie(hpDie);
        this.addHpCap(classHp + this.stats[2]);
        this.setHpMax(this.getHpCap() * 2);
        this.setHp(this.getHpCap());
    }
    public void addTotalLevel(){
        this.totalLevel++;
    }
    public int getTotalLevel(){
        return this.totalLevel;
    }
    public void initializeClassLevels(){
        for (int x = 0; x < this.classLevels.length; x++){
            this.classLevels[x] = 0;
        }
    }
    public void addClassLevel(int pick){
        System.out.println(this.charName);
        this.classLevels[pick]++;
    }
    public boolean checkSavingThrowProficiency(String stat){
        if(savingThrowList.contains(stat)){
            return true;
        }
        return false;
    }
    public boolean checkSkillsProficiency(String skill){
        System.out.println(this.skillList + "  - " + skill + " - " + this.skillList.contains(skill));
        if(skillList.contains(skill)){
            return true;
        }
        return false;
    }
    public int getSkillModifier(int i) {
        int toReturn = 0;
        switch(i){
            case 3: toReturn = this.getStatModifier(0); break; //Strength, Athle
            case 0, 15, 16: toReturn = this.getStatModifier(1); break; //Dexterity, Acro
            case 4, 7, 12, 13: toReturn = this.getStatModifier(5); break; //Charisma
            case 2, 5, 8, 10, 14: toReturn = this.getStatModifier(3); break; //Intelligence
            case 1, 6, 9, 11, 17: toReturn = this.getStatModifier(4); break; //Wisdom
        }
        return toReturn;
    }
    public ArrayList<String> getSkillList(){
        return this.skillList;
    }
    public void displayFeatures(){
        for (String x:
             features) {
            System.out.println(x);
        }
    }
    public void displaySavingThrows(){
        for (String x:
             savingThrowList) {
            System.out.println(x);
        }
    }
    public void displayModifiers(){
        for(int x : modifiers){
            System.out.println(x);
        }
    }
    public void displayWeapons(){
        for(Weapon x : weaponList){
            System.out.println(x.getName());
        }
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }
    public int[] getClassLevels() {
        return classLevels;
    }

    public int[] getStats() {
        return stats;
    }
    public void setStats(int[] statArray) {
        for(int x = 0; x < 6; x++){
            stats[x] = statArray[x];
        }
    }
    public int getStat(int index) {
        return stats[index];
    }

    public int[] getModifiers() {
        return modifiers;
    }
    public int getStatModifier(int chosenStat) {
        return modifiers[chosenStat];
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }
    public void addExperiencePoints(int exp){
        this.experiencePoints += exp;
    }
    public int[] getClasses(){
        return classLevels;
    }
    public ArrayList<Weapon> getWeaponList(){
        return weaponList;
    }


    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(int walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public int getSwimSpeed() {
        return swimSpeed;
    }

    public void setSwimSpeed(int swimSpeed) {
        this.swimSpeed = swimSpeed;
    }

    public int getClimbSpeed() {
        return climbSpeed;
    }

    public void setClimbSpeed(int climbSpeed) {
        this.climbSpeed = climbSpeed;
    }

    public int getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(int flySpeed) {
        this.flySpeed = flySpeed;
    }

    public int getInspiration() {
        return inspiration;
    }

    public void setInspiration(int inspiration) {
        this.inspiration = inspiration;
    }
    public boolean hasInspiration(){
        return inspiration > 0;
    }
    public ArrayList<String> getBonusAction(){
        return this.bonusAction;
    }
    public ArrayList<String> getReaction(){
        return this.reaction;
    }

    public Armor getArmorDon() {
        return armorDon;
    }

    public void setArmorDon(Armor armorDon) {
        this.armorDon = armorDon;
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }
}

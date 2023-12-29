import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int[] standardArray = new int[]{15, 14, 13, 12, 10, 8};
    public static String[] statNames = new String[]{"Strength", "Dexterity",
        "Constitution", "Intelligence", "Wisdom", "Charisma"};
    public static String[] skillNames = new String[]{"Acrobatics","Animal Handling","Arcana","Athletics",
            "Deception","History","Insight","Intimidation","Investigation","Medicine","Nature","Perception",
            "Performance","Persuasion","Religion","Sleight of Hand","Stealth","Survival"};
    public static ArrayList<String> strengthSkills = new ArrayList<>(Arrays.asList("Athletics"));
    public static ArrayList<String> dexteritySkills = new ArrayList<>(Arrays.asList(
            "Acrobatics","Sleight of Hand", "Stealth"));
    public static ArrayList<String> charismaSkills = new ArrayList<>(Arrays.asList(
            "Deception","Intimidation","Performance","Persuasion"
    ));
    public static ArrayList<String> intelligenceSkills = new ArrayList<>(Arrays.asList(
            "Arcana","History","Investigation","Nature","Religion"
    ));
    public static ArrayList<String> wisdomSkills = new ArrayList<>(Arrays.asList(
            "Animal Handling","Insight","Medicine","Perception","Survival"
    ));
    public static String[] martialWeaponList = {"Battleaxe", "Flail", "Glaive", "Greataxe",
            "Greatsword", "Halberd", "Lance", "Longsword", "Maul", "Morningstar", "Pike",
            "Rapier", "Scimitar", "Shortsword", "Trident", "War Pick", "Warhammer", "Whip",
            "Blowgun", "Hand Crossbow", "Heavy Crossbow", "Longbow", "Net"};
    public static String[] simpleWeaponList = {"Club", "Dagger", "Greatclub",
            "Handaxe", "Javelin", "Light Hammer", "Mace", "Quarterstaff", "Sickle", "Spear",
            "Light Crossbow", "Dart", "Shortbow", "Sling"};
    public static String[] martialMeleeWeaponList = {"Battleaxe", "Flail", "Glaive", "Greataxe",
            "Greatsword", "Halberd", "Lance", "Longsword", "Maul", "Morningstar", "Pike",
            "Rapier", "Scimitar", "Shortsword", "Trident", "War Pick", "Warhammer", "Whip"};
    public static String[] classes = {"Artificer", "Barbarian", "Bard", "Cleric", "Druid",
            "Fighter", "Monk", "Paladin", "Ranger", "Rogue",
            "Sorcerer", "Warlock", "Wizard"};
    public static String[] races = {"Human", "Elf", "Halfling"};
    public static String[] backgrounds = {"Entertainer", "Hermit", "Noble"};
    static Scanner input = new Scanner(System.in);
    static int charCount = 0;
    public static ArrayList<Character> charList = new ArrayList<>();
    public static ArrayList<Weapon> weaponList = new ArrayList<>();
    public static ArrayList<Armor> armorList = new ArrayList<>();
    public static String[] alignmentList = {"Lawful Good", "Lawful Neutral",
        "Lawful Evil", "Neutral Good", "True Neutral", "Neutral Evil",
        "Chaotic Good", "Chaotic Neutral", "Chaotic Evil"};

    public static void main(String[] args) throws FileNotFoundException{
        loadWeapons();
        loadArmor();
        for(Weapon x : weaponList){
            System.out.println(x.getName());
        }
        for(Armor x : armorList){
            System.out.println(x.getName());
        }
        new GUI("Main Menu", 0);

    }

    public static void characterCreation(String charName, String playerName, String race,
                                         String background, String charClass, String alignment,
                                         int[] statArray){

        Character newChar = new Character(charName, playerName, race,
                background, alignment, statArray);
        charList.add(newChar);

        charList.get(charCount).levelUP(charClass);

        charCount++;
        for(Character x : charList){
            System.out.println(x.getCharName());
            for(int y : x.getClasses()){
                System.out.print(y);
            }
        }
    }
    public static void loadWeapons() throws FileNotFoundException {
        String line;
        File file;
        file = new File("src/Weapon.csv");

        Scanner scan = new Scanner(file);
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            //Splits all values in weapons csv and puts it in the values array
            String[] values = line.split(",");

            Weapon newWeapon;
            //Range weapon
            if(values[3].equals("Ranged")){
                newWeapon = new RangedWeapon(values);
            }
            //Melee weapon
            else{
                newWeapon = new MeleeWeapon(values);
            }
            weaponList.add(newWeapon);
        }
        scan.close();

    }
    private static void loadArmor() throws FileNotFoundException {
        String line;
        File file;
        file = new File("src/Armor.csv");

        Scanner scan = new Scanner(file);
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            //Splits all values in weapons csv and puts it in the values array
            String[] values = line.split(",");

            Armor newArmor = new Armor(values);
            //Range weapon

            armorList.add(newArmor);
        }
        scan.close();
    }

}
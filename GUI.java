import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GUI extends JFrame {
    //MAIN MENU
    JButton newCharacterButton, exitButton, playButton;
    JFrame mainMenu;
    public GUI(String menu, int option){
        mainMenu = new JFrame();
        mainMenu.setTitle("DND");
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setResizable(false);
        mainMenu.setLayout(new BoxLayout(mainMenu.getContentPane(), BoxLayout.Y_AXIS));

        JLabel mainMenuPrompt = new JLabel("MAIN MENU");
        mainMenuPrompt.setBounds(0,0,200,20);

        newCharacterButton = new JButton("New Character");
        newCharacterButton.setBounds(50,25,100,30);

        playButton = new JButton("Play");
        playButton.setBounds(50,60,100,30);

        exitButton = new JButton("Exit");
        exitButton.setBounds(50, 95, 100, 30);

        mainMenuPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        newCharacterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainMenu.add(Box.createVerticalGlue());

        mainMenu.add(mainMenuPrompt);

        mainMenu.add(Box.createVerticalGlue());

        mainMenu.pack();
        mainMenu.setSize(180, 200);

        mainMenu.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        mainMenu.add(newCharacterButton);
        mainMenu.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        mainMenu.add(playButton);
        mainMenu.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        mainMenu.add(exitButton);
        mainMenu.add(Box.createVerticalStrut(10)); // Add some vertical spacing

        exitButton.addActionListener(e -> {
            //SAVE
            System.exit(0);
        });
        newCharacterButton.addActionListener(e -> {
            new GUI("", 0.0);
            mainMenu.setVisible(false);
        });
        playButton.addActionListener(e -> {
            for(Character x : Main.charList){
                System.out.println(x.getWeaponList());
                new GUI("", 0f);
                mainMenu.setVisible(false);
            }
        });
        mainMenu.setVisible(true);

    }
    //CHOOSE CHARACTER
    private JComboBox<String> characterNames;
    public GUI(String menu, float option){
        AtomicBoolean trueValue = new AtomicBoolean(true);

        JFrame characterChoose = new JFrame();
        characterChoose.setTitle("Choose Character");
        characterChoose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterChoose.setResizable(false);
        characterChoose.setLayout(null);
        characterChoose.setSize(500, 200);

        JLabel mainMenuPrompt = new JLabel("Choose Character: ");
        mainMenuPrompt.setBounds(20,20,120,30);

        int totalCharacter = Main.charList.size();
        String[] characterList = new String[totalCharacter];
        for(int i = 0; i < totalCharacter; i++){
            characterList[i] = Main.charList.get(i).getCharName();
        }

        characterNames = new JComboBox<>(characterList);
        characterNames.setBounds(150, 20, 140, 30);

        characterChoose.add(mainMenuPrompt);
        characterChoose.add(characterNames);

        JLabel chosenCharacterLabel = new JLabel("Chosen Character: ");
        chosenCharacterLabel.setBounds(20, 55, 170, 30);
        characterChoose.add(chosenCharacterLabel);

        characterNames.addActionListener(e -> {
            String selectedCharacter = (String) characterNames.getSelectedItem();
            chosenCharacterLabel.setText("Chosen Character: " + selectedCharacter);
        });

        playButton = new JButton("Play");
        playButton.setBounds(50, 95, 100, 30);
        characterChoose.add(playButton);

        //Button to select character to play
        playButton.addActionListener(e -> {
            //Declares player character
            Character chosenPlayer = null;
            //Initializes picked name
            String name = Objects.requireNonNull(characterNames.getSelectedItem()).toString();
            //Iterates through character list to find the player
            for(Character x : Main.charList){
                if(name.equals(x.getCharName())){
                    System.out.println("FSFF");
                    chosenPlayer = x;
                    break;
                }
            }
            assert chosenPlayer != null;
            AtomicInteger movementInteger = new AtomicInteger(chosenPlayer.getWalkSpeed());

            new GUI(chosenPlayer);
            characterChoose.setVisible(false);
        });

        characterChoose.setVisible(true);

    }
    //CHARACTER PLAY
    //CHARACTER SHEET
    public GUI(Character player){
        JFrame characterSheet = new JFrame();
        characterSheet.setTitle("Character Sheet");
        characterSheet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterSheet.setResizable(false);
        characterSheet.setLayout(null);
        characterSheet.setSize(900, 800);

        //HEADER
        JLabel characterNameLabel = new JLabel("Character Name: " + player.getCharName());
        characterNameLabel.setBounds(50, 50, 150, 20);
        JLabel playerNameLabel = new JLabel("Player Name: " + player.getPlayerName());
        playerNameLabel.setBounds(50, 75, 150, 20);
        JLabel raceLabel = new JLabel("Race: " + player.getRace());
        raceLabel.setBounds(210, 50, 150, 20);
        JLabel backgroundLabel = new JLabel("Background: " + player.getBackground());
        backgroundLabel.setBounds(210, 75, 150, 20);
        JLabel alignmentNameLabel = new JLabel("Alignment: " + player.getAlignment());
        alignmentNameLabel.setBounds(370, 50, 150, 20);
        JLabel experienceLabel = new JLabel("Experience Points: " + player.getExperiencePoints());
        experienceLabel.setBounds(370, 75, 150, 20);
        characterSheet.add(characterNameLabel);
        characterSheet.add(playerNameLabel);
        characterSheet.add(raceLabel);
        characterSheet.add(backgroundLabel);
        characterSheet.add(alignmentNameLabel);
        characterSheet.add(experienceLabel);

        //STATS
        int yStatBound = 105;
        for(int i = 0; i < 6; i++){
            JLabel newLabel = new JLabel(Main.statNames[i] + ": " + player.getStat(i) + " (" + player.getStatModifier(i) + ")");
            newLabel.setBounds(50, yStatBound, 130, 20);
            characterSheet.add(newLabel);
            yStatBound += 25;
        }

        //Inspiration and Proficiency and Passive
        JLabel inspirationLabel = new JLabel("Inspiration: ");
        inspirationLabel.setBounds(200, 105, 150, 20);
        characterSheet.add(inspirationLabel);
        JLabel proficiencyBonusLabel = new JLabel("Proficiency Bonus: " + player.getProficiency());
        proficiencyBonusLabel.setBounds(200, 130, 150, 20);
        characterSheet.add(proficiencyBonusLabel);
        JLabel passivePerceptionLabel = new JLabel("Passive Wisdom (Perception): " + (10 + player.getSkillModifier(11)));
        passivePerceptionLabel.setBounds(50, 795, 250, 20);
        characterSheet.add(passivePerceptionLabel);

        //Modifiers
        //Saving Throws
        JLabel savingThrowLabel = new JLabel("Saving Throws");
        savingThrowLabel.setBounds(200, 165, 150, 20);
        characterSheet.add(savingThrowLabel);
        int ySavingThrowBound = 195;

        for(int i = 0; i < 6; i++){
            JLabel newLabel = new JLabel(Main.statNames[i] + ": " +
                    (player.getStatModifier(i)
                    + (player.checkSavingThrowProficiency(Main.statNames[i]) ? player.getProficiency() : 0)
                    ));
            newLabel.setBounds(200, ySavingThrowBound, 150, 20);
            characterSheet.add(newLabel);
            ySavingThrowBound += 25;
        }

        JLabel skillLabel = new JLabel("Skills");
        skillLabel.setBounds(50, 320, 150, 20);
        characterSheet.add(skillLabel);
        int ySkillBound = 330;


        for(int i = 0; i < 9; i++){
            JLabel newLabel = new JLabel(Main.skillNames[i] + ": " +
                    (player.getSkillModifier(i)
                            + (player.checkSkillsProficiency(Main.skillNames[i]) ? player.getProficiency() : 0)
                    ) );
            newLabel.setBounds(50, ySkillBound, 150, 20);
            characterSheet.add(newLabel);
            ySkillBound += 25;
        }
        ySkillBound = 330;
        for(int i = 9; i < 18; i++){
            JLabel newLabel = new JLabel(Main.skillNames[i] + ": " +
                    (player.getSkillModifier(i)
                            + (player.checkSkillsProficiency(Main.skillNames[i]) ? player.getProficiency() : 0)
                    ) );
            newLabel.setBounds(210, ySkillBound, 150, 20);
            characterSheet.add(newLabel);
            ySkillBound += 25;
        }

        //Armor class, initiative, speed
        int armorTemp = 0;
        armorTemp = Character.calculateArmorClass(player);
        JLabel armorClassLabel = new JLabel("Armor Class: " + armorTemp);
        armorClassLabel.setBounds(300, 450, 150, 20);
        characterSheet.add(armorClassLabel);

        AtomicInteger movement = new AtomicInteger(player.getWalkSpeed());

        JLabel playerMovementLabel = new JLabel("Speed: " + player.getWalkSpeed());
        playerMovementLabel.setBounds(300, 400, 150, 20);
        characterSheet.add(playerMovementLabel);
        JLabel playerCurrentMovementLabel = new JLabel("Current Speed: " + movement);
        playerCurrentMovementLabel.setBounds(300, 400, 150, 20);
        characterSheet.add(playerCurrentMovementLabel);

        //Health

        //Weapons

        //COMBAT
        JButton combatButton = new JButton("Combat");
        combatButton.setBounds(550, 230, 100, 30);
        characterSheet.add(combatButton);
        combatButton.addActionListener(e -> {
            AtomicBoolean trueValue = new AtomicBoolean(true);
            AtomicBoolean[] temp = new AtomicBoolean[]{trueValue, trueValue, trueValue};
            new CombatGUI(player, temp, movement);
            characterSheet.setVisible(false);
        });

        JTextField expText = new JTextField();
        expText.setToolTipText("Add EXP");
        expText.setBounds(550, 270, 100, 20);
        characterSheet.add(expText);
        JButton expButton = new JButton("Add EXP");
        expButton.setBounds(550, 295, 100, 30);
        characterSheet.add(expButton);
        expButton.addActionListener(e -> {
            try{
                int providedExpAmount = Integer.parseInt(expText.getText());
                int result = player.addExp(providedExpAmount);
                if(result != 0){
                    new GUI(player, result);
                    characterSheet.setVisible(false);
                }
                else{
                    experienceLabel.setText("Experience: " + player.getExperiencePoints());
                }
            }
            catch(NumberFormatException ime){
                System.out.println("HEY");
                JOptionPane.showMessageDialog(characterSheet, "Invalid Input!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(600, 600, 100, 20);
        characterSheet.add(returnButton);
        returnButton.addActionListener(e -> {
            new GUI("", 0);
            characterSheet.setVisible(false);
        });

        characterSheet.setVisible(true);
    }
    //LEVEL UP GUI
    //choose class
    //can multiclass
    public GUI(Character player, int level){
        JFrame levelUpFrame = new JFrame();
        levelUpFrame.setTitle("Level Up");
        levelUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelUpFrame.setResizable(false);
        levelUpFrame.setLayout(null);
        levelUpFrame.setSize(500, 500);

        //HEADER
        JLabel levelLabel = new JLabel("Level: " + level);
        levelLabel.setBounds(20, 20, 100, 20);
        levelUpFrame.add(levelLabel);
        JLabel chooseClassLabel = new JLabel("Choose Class:");
        chooseClassLabel.setBounds(20, 45, 120, 20);
        levelUpFrame.add(chooseClassLabel);
        JComboBox classComboBox = new JComboBox<>(Main.classes);
        classComboBox.setBounds(150, 45, 150, 20);
        levelUpFrame.add(classComboBox);

        //COMBAT
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(20, 70, 90, 20);
        levelUpFrame.add(confirmButton);

        confirmButton.addActionListener(e -> {
            player.levelUP(classComboBox.getSelectedItem().toString());
            levelUpFrame.setVisible(false);
        });
        levelUpFrame.setVisible(true);

    }

    //CHARACTER CREATION
    JTextField name, playerName;
    private final JButton[] increaseButtons = new JButton[6];
    JLabel statPrompt;
    JComboBox<String> raceComboBox, backgroundComboBox,
        classComboBox, alignmentComboBox;
    JLabel[] mainStatPrompt = new JLabel[6];
    int[] counters = new int[6];
    int pointBuyCounter;
    private GUI(String menu, double option){
        pointBuyCounter = 27;
        for(int x = 0; x < 6; x++){
            counters[x] = 8;
        }
        JFrame characterCreation = new JFrame();
        characterCreation.setTitle("Character Creation");
        characterCreation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterCreation.setResizable(false);
        characterCreation.setLayout(null);
        characterCreation.setSize(600, 440);

        JLabel mainMenuPrompt = new JLabel("NEW CHARACTER MENU");

        characterCreation.add(Box.createVerticalGlue());
        characterCreation.add(mainMenuPrompt);

        JLabel namePrompt = new JLabel("Name: ");
        namePrompt.setBounds(50, 50, 50, 20);
        name = new JTextField();
        name.setBounds(140, 50, 150, 20);
        characterCreation.add(namePrompt);
        characterCreation.add(name);

        JLabel playerNamePrompt = new JLabel("Player Name: ");
        playerNamePrompt.setBounds(50, 80, 80, 20);
        playerName = new JTextField();
        playerName.setBounds(140, 80, 150, 20);
        characterCreation.add(playerNamePrompt);
        characterCreation.add(playerName);

        JLabel racePrompt = new JLabel("Race: ");
        racePrompt.setBounds(50, 110, 50, 20);
        raceComboBox = new JComboBox<>(Main.races);
        raceComboBox.setBounds(140, 110, 150, 20);
        characterCreation.add(racePrompt);
        characterCreation.add(raceComboBox);

        JLabel classPrompt = new JLabel("Class: ");
        classPrompt.setBounds(50, 140, 50, 20);
        classComboBox = new JComboBox<>(Main.classes);
        classComboBox.setBounds(140, 140, 150, 20);
        characterCreation.add(classPrompt);
        characterCreation.add(classComboBox);

        JLabel backgroundPrompt = new JLabel("Background: ");
        backgroundPrompt.setBounds(50, 170, 80, 20);
        backgroundComboBox = new JComboBox<>(Main.backgrounds);
        backgroundComboBox.setBounds(140, 170, 150, 20);
        characterCreation.add(backgroundPrompt);
        characterCreation.add(backgroundComboBox);

        JLabel alignmentPrompt = new JLabel("Alignment: ");
        alignmentPrompt.setBounds(50, 200, 80, 20);
        alignmentComboBox = new JComboBox<>(Main.alignmentList);
        alignmentComboBox.setBounds(140, 200, 150, 20);
        characterCreation.add(alignmentPrompt);
        characterCreation.add(alignmentComboBox);

        statPrompt = new JLabel("Point Buy: " + pointBuyCounter);
        statPrompt.setBounds(50, 260, 80, 20);
        characterCreation.add(statPrompt);

        int[] boundIncreaseX = {30,30, 30, 275, 275, 275};
        int[] boundY = {300,330, 360, 300, 330, 360};

        for(int i = 0; i < 6; i++){
            increaseButtons[i] = new JButton("Up");
            increaseButtons[i].setBounds(boundIncreaseX[i], boundY[i], 70, 20);
            mainStatPrompt[i] = new JLabel(Main.statNames[i] + ": " + counters[i]);
            mainStatPrompt[i].setBounds(boundIncreaseX[i] + 75, boundY[i], 90, 20);
            JButton[] decreaseButtons = new JButton[6];
            decreaseButtons[i] = new JButton("Down");
            decreaseButtons[i].setBounds(boundIncreaseX[i] + 170, boundY[i], 70, 20);
            characterCreation.add(increaseButtons[i]);
            characterCreation.add(mainStatPrompt[i]);
            characterCreation.add(decreaseButtons[i]);

            final int categoryIndex = i;

            increaseButtons[i].addActionListener(e -> {
                if (counters[categoryIndex] < 20 && pointBuyCounter > 0) {
                    counters[categoryIndex]++;
                    pointBuyCounter--;
                    updateUI();
                }
            });
            decreaseButtons[i].addActionListener(e -> {
                if (counters[categoryIndex] > 1) {
                    counters[categoryIndex]--;
                    pointBuyCounter++;
                    updateUI();
                }
            });
        }

        characterCreation.setLocationRelativeTo(null);
        characterCreation.setVisible(true);

        JButton createCharacter = new JButton("Create Character");
        createCharacter.setBounds(400, 100, 100, 20);
        characterCreation.add(createCharacter);

        createCharacter.addActionListener(e -> {
            if(name.getText().equals("") || playerName.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Name cannot be empty!", "Invalid Input!", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(pointBuyCounter != 0){
                JOptionPane.showMessageDialog(rootPane, "Must spend all points!", "Cannot Go!", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Character " + name.getText() + " Created!", "Character Created Successfully!", JOptionPane.INFORMATION_MESSAGE);
                characterCreation.setVisible(false);

                System.out.println("ESF");


                Main.characterCreation(name.getText(), playerName.getText(),
                        Objects.requireNonNull(raceComboBox.getSelectedItem()).toString(),
                        Objects.requireNonNull(backgroundComboBox.getSelectedItem()).toString(),
                        Objects.requireNonNull(classComboBox.getSelectedItem()).toString(),
                        Objects.requireNonNull(alignmentComboBox.getSelectedItem()).toString(),
                        counters
                );
            }
        });


    }

    private DefaultListModel<String> selectedSkillsModel;
    private JComboBox<String> equipmentComboBox;
    private JComboBox<String> weaponComboBox;
    private JLabel chosenWeapon;
    String selectedWeapon;
    public GUI(String[] skills, String[] equipment, Character character){
        JFrame characterCreation = new JFrame();
        characterCreation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterCreation.setResizable(false);
        characterCreation.setLayout(null);
        characterCreation.setSize(600, 440);

        DefaultListModel<String> availableSkillsModel = new DefaultListModel<>();
        selectedSkillsModel = new DefaultListModel<>();

        for (String skill : skills) {
            availableSkillsModel.addElement(skill);
        }

        JList<String> availableSkillsList = new JList<>(availableSkillsModel);
        JList<String> selectedSkillsList = new JList<>(selectedSkillsModel);


        JButton addButton = new JButton("Add Skill");
        addButton.addActionListener(e -> addSelectedSkill(availableSkillsList.getSelectedValue()));
        JButton removeButton = new JButton("Remove Skill");
        removeButton.addActionListener(e -> removeSelectedSkill(selectedSkillsList.getSelectedValue()));

        addButton.setBounds(55, 160, 120, 40);
        removeButton.setBounds(195, 160, 120, 40);
        characterCreation.add(addButton);
        characterCreation.add(removeButton);

        JScrollPane availableSkills = new JScrollPane(availableSkillsList);
        JScrollPane selectedSkills = new JScrollPane(selectedSkillsList);

        availableSkills.setBounds(50, 50, 130, 100);
        selectedSkills.setBounds(190, 50, 130, 100);

        characterCreation.add(availableSkills);
        characterCreation.add(selectedSkills);

        equipmentComboBox = new JComboBox<>(equipment);
        weaponComboBox = new JComboBox<>(Main.martialMeleeWeaponList);
        weaponComboBox.setVisible(false);

        equipmentComboBox.addActionListener(e -> handleEquipmentSelection());
        weaponComboBox.addActionListener(e -> {
            selectedWeapon = (String) weaponComboBox.getSelectedItem();
            chosenWeapon.setText("Chosen Weapon: " + selectedWeapon);
        });

        characterCreation.add(new JLabel("Select starting equipment: "));

        equipmentComboBox.setBounds(50, 220, 180, 30);
        weaponComboBox.setBounds(50, 260, 180, 30);

        characterCreation.add(equipmentComboBox);
        characterCreation.add(weaponComboBox);

        chosenWeapon = new JLabel("Chosen Weapon: ");
        chosenWeapon.setBounds(50, 300, 200, 30);
        characterCreation.add(chosenWeapon);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if(selectedWeapon != null && selectedSkillsModel.getSize() == 2){
                ArrayList<String> values = getStartingItems();
                System.out.println(values);

                character.addSkill(values.get(0));
                character.addSkill(values.get(1));
                character.addWeapon(Weapon.findWeapon(values.get(2)));
                characterCreation.setVisible(false);
                new GUI("", 0);
            }

        });
        submit.setBounds(50, 340, 80, 40);
        characterCreation.add(submit);


        characterCreation.setVisible(true);


    }
    public ArrayList<String> getStartingItems(){
        ArrayList<String> toReturn = new ArrayList<>();
        int size = selectedSkillsModel.getSize();

        for (int i = 0; i < size; i++) {
            toReturn.add(selectedSkillsModel.getElementAt(i));
        }
        toReturn.add(selectedWeapon);
        return toReturn;
    }
    private void addSelectedSkill(String skill) {
        if (skill != null && selectedSkillsModel.size() < 2 && !selectedSkillsModel.contains(skill)) {
            selectedSkillsModel.addElement(skill);
        }
    }
    private void removeSelectedSkill(String skill) {
        if (skill != null) {
            selectedSkillsModel.removeElement(skill);
        }
    }
    private void handleEquipmentSelection() {
        String selectedEquipment = (String) equipmentComboBox.getSelectedItem();
        selectedWeapon = (String) weaponComboBox.getSelectedItem();

        if (selectedEquipment != null && selectedEquipment.equals("Martial Melee Weapon")) {
            chosenWeapon.setText("Chosen Weapon: " + selectedWeapon);
            weaponComboBox.setVisible(true);
        }
        else {
            chosenWeapon.setText("Chosen Weapon: " + selectedEquipment);
            selectedWeapon = selectedEquipment;
            weaponComboBox.setVisible(false);
        }
    }
    private void updateUI() {
        for (int i = 0; i < 6; i++) {
            String line = Main.statNames[i] + ": " + counters[i];
            mainStatPrompt[i].setText(line);
        }

        statPrompt.setText("Total Tally: " + pointBuyCounter);
        statPrompt.setEnabled(pointBuyCounter > 0);

        // Check if all counters have reached the maximum value
        boolean allMaxed = true;
        for (int counter : counters) {
            if (counter < 20) {
                allMaxed = false;
                break;
            }
        }

        // Disable all increase buttons if total tally is zero or all counters are maxed
        for (JButton button : increaseButtons) {
            button.setEnabled(pointBuyCounter > 0 && !allMaxed);
        }
    }

}


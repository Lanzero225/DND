import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CombatGUI {
    AtomicBoolean trueValue = new AtomicBoolean(true);
    AtomicBoolean falseValue = new AtomicBoolean(false);
    //COMBAT GUI START
    public CombatGUI(Character player, AtomicBoolean[] turnValues, AtomicInteger movement){
        JFrame combatSheet = new JFrame();
        combatSheet.setTitle("Combat Sheet");
        combatSheet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        combatSheet.setResizable(false);
        combatSheet.setLayout(null);
        combatSheet.setSize(900, 800);

        //HEADER
        JLabel characterNameLabel = new JLabel("Character Name: " + player.getCharName());
        characterNameLabel.setBounds(50, 50, 150, 20);
        JLabel playerNameLabel = new JLabel("Player Name: " + player.getPlayerName());
        playerNameLabel.setBounds(50, 75, 150, 20);
        combatSheet.add(characterNameLabel);
        combatSheet.add(playerNameLabel);

        //STATS
        int yStatBound = 105;
        for(int i = 0; i < 6; i++){
            JLabel newLabel = new JLabel(Main.statNames[i] + ": " + player.getStat(i) + " (" + player.getStatModifier(i) + ")");
            newLabel.setBounds(50, yStatBound, 130, 20);
            combatSheet.add(newLabel);
            yStatBound += 25;
        }

        //Inspiration and Proficiency and Passive
        JLabel inspirationLabel = new JLabel("Inspiration: ");
        inspirationLabel.setBounds(200, 105, 150, 20);
        combatSheet.add(inspirationLabel);
        JLabel proficiencyBonusLabel = new JLabel("Proficiency Bonus: " + player.getProficiency());
        proficiencyBonusLabel.setBounds(200, 130, 150, 20);
        combatSheet.add(proficiencyBonusLabel);
        JLabel passivePerceptionLabel = new JLabel("Passive Wisdom (Perception): " + (10 + player.getSkillModifier(11)));
        passivePerceptionLabel.setBounds(50, 795, 250, 20);
        combatSheet.add(passivePerceptionLabel);

        //Modifiers
        //Saving Throws
        JLabel savingThrowLabel = new JLabel("Saving Throws");
        savingThrowLabel.setBounds(200, 165, 150, 20);
        combatSheet.add(savingThrowLabel);
        int ySavingThrowBound = 195;

        for(int i = 0; i < 6; i++){
            JLabel newLabel = new JLabel(Main.statNames[i] + ": " +
                    (player.getStatModifier(i)
                            + (player.checkSavingThrowProficiency(Main.statNames[i]) ? player.getProficiency() : 0)
                    ));
            newLabel.setBounds(200, ySavingThrowBound, 150, 20);
            combatSheet.add(newLabel);
            ySavingThrowBound += 25;
        }

        JLabel skillLabel = new JLabel("Skills");
        skillLabel.setBounds(50, 320, 150, 20);
        combatSheet.add(skillLabel);
        int ySkillBound = 330;


        for(int i = 0; i < 9; i++){
            JLabel newLabel = new JLabel(Main.skillNames[i] + ": " +
                    (player.getSkillModifier(i)
                            + (player.checkSkillsProficiency(Main.skillNames[i]) ? player.getProficiency() : 0)
                    ) );
            newLabel.setBounds(50, ySkillBound, 150, 20);
            combatSheet.add(newLabel);
            ySkillBound += 25;
        }
        ySkillBound = 330;
        for(int i = 9; i < 18; i++){
            JLabel newLabel = new JLabel(Main.skillNames[i] + ": " +
                    (player.getSkillModifier(i)
                            + (player.checkSkillsProficiency(Main.skillNames[i]) ? player.getProficiency() : 0)
                    ) );
            newLabel.setBounds(210, ySkillBound, 150, 20);
            combatSheet.add(newLabel);
            ySkillBound += 25;
        }

        //Armor class, initiative, speed
        int armorTemp = Character.calculateArmorClass(player);
        JLabel armorClassLabel = new JLabel("Armor Class: " + armorTemp);
        armorClassLabel.setBounds(300, 450, 150, 20);
        combatSheet.add(armorClassLabel);

        JLabel playerMovementLabel = new JLabel("Speed: " + player.getWalkSpeed());
        playerMovementLabel.setBounds(300, 400, 150, 20);
        combatSheet.add(playerMovementLabel);
        JLabel playerCurrentMovementLabel = new JLabel("Current Speed: " + movement);
        playerCurrentMovementLabel.setBounds(300, 400, 150, 20);
        combatSheet.add(playerCurrentMovementLabel);

        //Health

        //Weapons

        //Actions
        String[] actionList = {"One-Weapon Attack", "Two-Weapon Attack", "Cast a Spell", "Dash", "Dodge",
                "Help", "Hide", "Ready", "Search", "Use an Object"};
        JComboBox<String> actionComboBox = new JComboBox<>(actionList);
        actionComboBox.setBounds(550, 20, 150, 20);
        combatSheet.add(actionComboBox);
        JButton actionButton = new JButton("Action");
        actionButton.setBounds(550, 45, 130, 30);
        combatSheet.add(actionButton);

        //Bonus Actions
        String[] bonusActionList = player.getBonusAction().toArray(new String[0]);
        for(String x : bonusActionList){
            System.out.println(x);
        }
        JComboBox<String> bonusActionComboBox = new JComboBox<>(bonusActionList);
        bonusActionComboBox.setBounds(550, 80, 150, 20);
        combatSheet.add(bonusActionComboBox);
        JButton bonusActionButton = new JButton("Bonus Action");
        bonusActionButton.setBounds(550, 105, 130, 30);
        combatSheet.add(bonusActionButton);

        //Reactions
        String[] reactionList = player.getReaction().toArray(new String[0]);
        for(String x : reactionList){
            System.out.println(x);
        }

        JComboBox<String> reactionComboBox = new JComboBox<>(reactionList);
        reactionComboBox.setBounds(550, 140, 150, 20);
        combatSheet.add(reactionComboBox);

        JButton reactionButton = new JButton("Reaction");
        reactionButton.setBounds(550, 165, 130, 30);
        combatSheet.add(reactionButton);

        //ACTION BUTTON
        //Select action to do
        actionButton.addActionListener(e -> {
            //Initializes picked action
            String chosenAction = Objects.requireNonNull(actionComboBox.getSelectedItem()).toString();
            combatSheet.setVisible(false);

            switch (chosenAction) {
                case "One-Weapon Attack" -> new CombatGUI(player, player.getWeaponList(), movement, turnValues);
                case "Two-Weapon Attack" -> {
                    int lightWeaponCount = 0;
                    for (Weapon x : player.getWeaponList()) {
                        if (x.isLight()) {
                            lightWeaponCount++;
                        }
                    }
                    if (lightWeaponCount >= 2) {
                        new CombatGUI(player, movement, turnValues);
                    } else {
                        JOptionPane.showMessageDialog(combatSheet, "Insufficient Light Weapons", "Lack of Weapons", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case "Dash" -> {
                    playerCurrentMovementLabel.setText("Current Movement: " + (movement.get() + player.getWalkSpeed()));
                    movement.set(movement.get() + player.getWalkSpeed());
                }
            }
        });

        //BONUS ACTION BUTTON
        //Select action to do
        bonusActionButton.addActionListener(e -> {
            //Initializes picked action
            String chosenBonusAction = Objects.requireNonNull(bonusActionComboBox.getSelectedItem()).toString();
            //characterSheet.setVisible(false);

            switch (chosenBonusAction) {
                case "Rage" ->{
                    //Stops them from raging again
                    if(player.barbLevels.isRageChecker()){
                        JOptionPane.showMessageDialog(combatSheet, "No More Rages", "Invalid Option", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //Alternative for unlimited rages
                    else if(player.barbLevels.getMaxRage() == -1){
                        turnValues[1].set(false);
                        bonusActionButton.setEnabled(false);
                        player.barbLevels.setRageChecker(true);
                        player.barbLevels.setRageTurns(10);
                    }
                    //Does not allow for rages
                    else if(player.barbLevels.getRages() == 0){
                        JOptionPane.showMessageDialog(combatSheet, "No More Rages", "Invalid Option", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //Default rage
                    else{
                        turnValues[1].set(false);
                        bonusActionButton.setEnabled(false);
                        player.barbLevels.setRageChecker(true);
                        player.barbLevels.setRages(player.barbLevels.getRages() - 1);
                        player.barbLevels.setRageTurns(10);
                    }
                }
                case "Disable Rage" -> {
                    //Not raging
                    if(!player.barbLevels.isRageChecker()){
                        JOptionPane.showMessageDialog(combatSheet, "Not Raging", "Invalid Option", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        bonusActionButton.setEnabled(false);
                        player.barbLevels.setRageChecker(false);
                        player.barbLevels.setRageTurns(0);
                    }
                }
                case "Reckless Attack" -> {
                    player.barbLevels.setRecklessCheck(true);
                }

            }
        });


        JButton moveButton = new JButton("Move");
        moveButton.setBounds(550, 230, 100, 30);
        combatSheet.add(moveButton);

        //Move Button
        moveButton.addActionListener(e -> {
            if(movement.get() != 0){
                combatSheet.setVisible(false);
                new CombatGUI(player, turnValues, movement, "");
            }
            else{
                JOptionPane.showMessageDialog(combatSheet, "Insufficient Movement", "Lack of Speed", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        combatSheet.setVisible(true);
        if(!turnValues[0].get()){
            actionButton.setEnabled(false);
        }
        if(!turnValues[1].get()){
            bonusActionButton.setEnabled(false);
        }
        if(!turnValues[2].get()){
            reactionButton.setEnabled(false);
        }

        //ADD FUNCTIONALITY
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(600, 600, 100, 20);
        combatSheet.add(returnButton);
        returnButton.addActionListener(e -> {
            new GUI(player);
            combatSheet.setVisible(false);
        });

        JButton nextTurnButton = new JButton("Next Turn");
        nextTurnButton.setBounds(550, 270, 100, 30);
        combatSheet.add(nextTurnButton);
        nextTurnButton.addActionListener(e -> {
            actionButton.setEnabled(true);
            turnValues[0].set(true);
            bonusActionButton.setEnabled(true);
            turnValues[1].set(true);
            reactionButton.setEnabled(true);
            turnValues[2].set(true);
            movement.set(player.getWalkSpeed());
            playerCurrentMovementLabel.setText("Current Movement: " + movement.get());
            if(player.barbLevels.getBarbLevel() != 0){
                player.barbLevels.nextTurnBarbarian(player);
            }
        });

        //next turn
    }

    //SINGLE ATTACKS
    private JComboBox<String> weaponNamesComboBox;
    JButton rollButton;
    public CombatGUI(Character player, ArrayList<Weapon> weapons, AtomicInteger movement, AtomicBoolean[] turnValues){
        AtomicBoolean thrown = new AtomicBoolean(false);
        AtomicBoolean oneHandedWield = new AtomicBoolean(false);
        AtomicBoolean advantage = new AtomicBoolean(false);
        AtomicBoolean disadvantage = new AtomicBoolean(false);

        JFrame singleWeaponAttack = new JFrame();
        singleWeaponAttack.setTitle("Single Weapon Attack");
        singleWeaponAttack.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        singleWeaponAttack.setResizable(false);
        singleWeaponAttack.setLayout(null);
        singleWeaponAttack.setSize(600, 370);

        JLabel chooseWeaponPrompt = new JLabel("Choose Weapon: ");
        chooseWeaponPrompt.setBounds(20,20,120,30);
        singleWeaponAttack.add(chooseWeaponPrompt);

        int totalWeapons = weapons.size();
        String[] weaponList = new String[totalWeapons];
        for(int i = 0; i < totalWeapons; i++){
            weaponList[i] = weapons.get(i).getName();
        }

        weaponNamesComboBox = new JComboBox<>(weaponList);
        weaponNamesComboBox.setBounds(150, 20, 140, 30);
        singleWeaponAttack.add(weaponNamesComboBox);

        //Versatile Buttons
        JRadioButton oneHandedVersatile = new JRadioButton("Use One Hand");
        oneHandedVersatile.setBounds(50, 60, 150, 30);
        JRadioButton twoHandedVersatile = new JRadioButton("Use Two Hands");
        twoHandedVersatile.setBounds(210, 60, 150, 30);
        singleWeaponAttack.add(oneHandedVersatile);
        singleWeaponAttack.add(twoHandedVersatile);
        oneHandedVersatile.setVisible(false);
        twoHandedVersatile.setVisible(false);

        ButtonGroup versatileGroup = new ButtonGroup();
        versatileGroup.add(oneHandedVersatile);
        versatileGroup.add(twoHandedVersatile);

        //Throwable Buttons
        JRadioButton enableThrowButton = new JRadioButton("Throw");
        enableThrowButton.setBounds(50, 100, 150, 30);
        JRadioButton disableThrowButton = new JRadioButton("Don't Throw");
        disableThrowButton.setBounds(210, 100, 150, 30);
        singleWeaponAttack.add(enableThrowButton);
        singleWeaponAttack.add(disableThrowButton);
        enableThrowButton.setVisible(false);
        disableThrowButton.setVisible(false);

        ButtonGroup throwOption = new ButtonGroup();
        throwOption.add(enableThrowButton);
        throwOption.add(disableThrowButton);

        //Advantage Disadvantage
        JRadioButton advantageButton = new JRadioButton("Advantage");
        advantageButton.setBounds(50, 140, 120, 30);
        JRadioButton disadvantageButton = new JRadioButton("Disadvantage");
        disadvantageButton.setBounds(50, 175, 120, 30);
        JRadioButton noAdvantageButton = new JRadioButton("None");
        noAdvantageButton.setBounds(50, 210, 120, 30);
        singleWeaponAttack.add(advantageButton);
        singleWeaponAttack.add(disadvantageButton);
        singleWeaponAttack.add(noAdvantageButton);
        advantageButton.setVisible(false);
        disadvantageButton.setVisible(false);
        noAdvantageButton.setVisible(false);

        ButtonGroup advantageOption = new ButtonGroup();
        advantageOption.add(noAdvantageButton);
        advantageOption.add(advantageButton);
        advantageOption.add(disadvantageButton);

        //ROLL BUTTON
        rollButton = new JButton("Roll");
        rollButton.setBounds(60, 250, 80, 30);
        singleWeaponAttack.add(rollButton);
        rollButton.setEnabled(false);

        JLabel initialAttackLabel = new JLabel();
        initialAttackLabel.setBounds(360, 20, 120, 20);
        JLabel finalAttackLabel = new JLabel();
        finalAttackLabel.setBounds(360, 45, 120, 20);
        JLabel initialDamageLabel = new JLabel();
        initialDamageLabel.setBounds(360, 75, 130, 20);
        JLabel finalDamageLabel = new JLabel();
        finalDamageLabel.setBounds(360, 100, 130, 20);
        singleWeaponAttack.add(initialAttackLabel);
        singleWeaponAttack.add(finalAttackLabel);
        singleWeaponAttack.add(initialDamageLabel);
        singleWeaponAttack.add(finalDamageLabel);
        initialAttackLabel.setVisible(false);
        finalAttackLabel.setVisible(false);
        initialDamageLabel.setVisible(false);
        finalDamageLabel.setVisible(false);

        //POST ROLL BUTTON
        JButton rerollButton = new JButton("Reroll Low Dice");
        rerollButton.setBounds(360, 140, 140, 30);
        singleWeaponAttack.add(rerollButton);
        rerollButton.setVisible(false);

        JButton bruteButton = new JButton("Roll Brute Die");
        bruteButton.setBounds(360, 140, 140, 30);
        singleWeaponAttack.add(bruteButton);
        bruteButton.setVisible(false);

        //Return Button
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(160, 250, 80, 30);
        singleWeaponAttack.add(returnButton);

        //Ammo Label
        JLabel ammoRemainingLabel = new JLabel();
        ammoRemainingLabel.setBounds(260, 250, 140, 20);
        singleWeaponAttack.add(ammoRemainingLabel);
        ammoRemainingLabel.setVisible(false);

        weaponNamesComboBox.addActionListener(e -> {
            rollButton.setEnabled(false);
            String selectedWeapon = (String) weaponNamesComboBox.getSelectedItem();
            Weapon currentWeapon = Weapon.findWeapon(selectedWeapon);
            thrown.set(false);
            oneHandedWield.set(false);

            initialAttackLabel.setVisible(false);
            finalAttackLabel.setVisible(false);
            initialDamageLabel.setVisible(false);
            finalDamageLabel.setVisible(false);
            rerollButton.setVisible(false);

            if(currentWeapon.isAmmo()){
                ammoRemainingLabel.setText(currentWeapon.getAmmunition() + " Remaining: " +
                        player.getAmmo(currentWeapon.getAmmunition()));
                ammoRemainingLabel.setVisible(true);
            }

            if((currentWeapon.isTwoHanded())){
                thrown.set(false);
                oneHandedWield.set(false);

                oneHandedVersatile.setVisible(false);
                twoHandedVersatile.setVisible(false);

                enableThrowButton.setVisible(false);
                disableThrowButton.setVisible(false);

                advantageButton.setVisible(true);
                disadvantageButton.setVisible(true);
                noAdvantageButton.setVisible(true);
            }
            else if((currentWeapon.isVersatile() || currentWeapon.isVersatile() && currentWeapon.isThrowable()) || !currentWeapon.isThrowable()){
                oneHandedVersatile.setVisible(true);
                twoHandedVersatile.setVisible(true);

                enableThrowButton.setVisible(false);
                disableThrowButton.setVisible(false);

                advantageButton.setVisible(false);
                disadvantageButton.setVisible(false);
                noAdvantageButton.setVisible(false);

                oneHandedVersatile.setSelected(false);
                twoHandedVersatile.setSelected(false);
            }
            else if(currentWeapon.isThrowable()){
                oneHandedVersatile.setVisible(false);
                twoHandedVersatile.setVisible(false);

                enableThrowButton.setVisible(true);
                disableThrowButton.setVisible(true);

                advantageButton.setVisible(false);
                disadvantageButton.setVisible(false);
                noAdvantageButton.setVisible(false);

                enableThrowButton.setSelected(false);
                disableThrowButton.setSelected(false);
            }

            rollButton.setEnabled(false);

            rollButton.setEnabled(
                    (advantageButton.isSelected() || disadvantageButton.isSelected() || noAdvantageButton.isSelected()) &&
                            noAdvantageButton.isVisible());

        });

        oneHandedVersatile.addActionListener(e -> {
            rollButton.setEnabled(false);
            String selectedWeapon = (String) weaponNamesComboBox.getSelectedItem();
            if(oneHandedVersatile.isSelected()){
                if(Weapon.findWeapon(selectedWeapon).isThrowable()){
                    //Weapon is throwable and enables throw buttons
                    oneHandedWield.set(true);
                    enableThrowButton.setVisible(true);
                    disableThrowButton.setVisible(true);

                    enableThrowButton.setSelected(false);
                    disableThrowButton.setSelected(false);

                    advantageButton.setVisible(false);
                    disadvantageButton.setVisible(false);
                    noAdvantageButton.setVisible(false);
                }
                else{
                    //Weapon cannot be thrown and disables throw buttons
                    thrown.set(false);
                    oneHandedWield.set(true);

                    enableThrowButton.setVisible(false);
                    disableThrowButton.setVisible(false);
                    advantageButton.setVisible(true);
                    disadvantageButton.setVisible(true);
                    noAdvantageButton.setVisible(true);

                    advantageButton.setSelected(false);
                    disadvantageButton.setSelected(false);
                    noAdvantageButton.setSelected(false);
                }
            }
            else if(twoHandedVersatile.isSelected()){
                //Ensure Versatile
                oneHandedWield.set(false);
                enableThrowButton.setVisible(false);
                disableThrowButton.setVisible(false);
                advantageButton.setVisible(true);
                disadvantageButton.setVisible(true);
                noAdvantageButton.setVisible(true);

                advantageButton.setSelected(false);
                disadvantageButton.setSelected(false);
                noAdvantageButton.setSelected(false);

            }
        });
        twoHandedVersatile.addActionListener(e -> {
            rollButton.setEnabled(false);
            if(oneHandedVersatile.isSelected()){
                thrown.set(false);
                oneHandedWield.set(true);

                enableThrowButton.setVisible(true);
                disableThrowButton.setVisible(true);

                enableThrowButton.setSelected(false);
                disableThrowButton.setSelected(false);

                rollButton.setEnabled(false);
            }
            else if(twoHandedVersatile.isSelected()){
                //Versatile append
                oneHandedWield.set(true);
                thrown.set(false);

                enableThrowButton.setVisible(false);
                disableThrowButton.setVisible(false);
                advantageButton.setVisible(true);
                disadvantageButton.setVisible(true);
                noAdvantageButton.setVisible(true);

                advantageButton.setSelected(false);
                disadvantageButton.setSelected(false);
                noAdvantageButton.setSelected(false);
            }
        });


        enableThrowButton.addActionListener(e -> {
            if(enableThrowButton.isSelected()){
                //Throw counter append
                thrown.set(true);
                oneHandedWield.set(true);

                enableThrowButton.setVisible(true);
                disableThrowButton.setVisible(true);
            }
            else if(disableThrowButton.isSelected()){
                thrown.set(false);
                oneHandedWield.set(true);

                enableThrowButton.setVisible(false);
                disableThrowButton.setVisible(false);
            }
            advantageButton.setVisible(true);
            disadvantageButton.setVisible(true);
            noAdvantageButton.setVisible(true);
            rollButton.setEnabled(false);

            advantageButton.setSelected(false);
            disadvantageButton.setSelected(false);
            noAdvantageButton.setSelected(false);
        });
        disableThrowButton.addActionListener(e -> {
            if(enableThrowButton.isSelected()){
                //Throw counter append
                thrown.set(true);
                oneHandedWield.set(true);

                enableThrowButton.setVisible(true);
                disableThrowButton.setVisible(true);
            }
            else if(disableThrowButton.isSelected()){
                thrown.set(false);
                oneHandedWield.set(true);
            }
            advantageButton.setVisible(true);
            disadvantageButton.setVisible(true);
            noAdvantageButton.setVisible(true);
            rollButton.setEnabled(false);

            advantageButton.setSelected(false);
            disadvantageButton.setSelected(false);
            noAdvantageButton.setSelected(false);
        });

        advantageButton.addActionListener(e -> {
            rollButton.setEnabled(advantageButton.isSelected());
            advantage.set(true);
            disadvantage.set(false);
        });
        disadvantageButton.addActionListener(e -> {
            rollButton.setEnabled(disadvantageButton.isSelected());
            advantage.set(false);
            disadvantage.set(true);
        });
        noAdvantageButton.addActionListener(e -> {
            rollButton.setEnabled(noAdvantageButton.isSelected());
            advantage.set(false);
            disadvantage.set(false);
        });

        singleWeaponAttack.setVisible(true);

        //GUI PART
        //If it has versatile property, add combobox that asks for versatile property
        //If it has throwable property, user must choose not to use versatile for this to view
            //Remove throwable pick if user goes to versatile
        //Asks for advantage, disadvantage, or none
        //Button for ROLL

        //DO BELOW AFTER ROLL
        rollButton.addActionListener(e -> {
            Weapon finalWeapon = Weapon.findWeapon(weaponNamesComboBox.getSelectedItem().toString());

            if(!player.hasAmmo(finalWeapon.getAmmunition()) && finalWeapon.isAmmo()){
                JOptionPane.showMessageDialog(singleWeaponAttack, "You have no more ammo!", "Ammo Insufficient", JOptionPane.INFORMATION_MESSAGE);
            }
            else{

                //RETURNS ATTACK ROLLS
                int[] attackRoll = Weapon.rollForAttack(player, finalWeapon, advantage, disadvantage);
                int[] damageRoll = Weapon.rollForDamage(player, finalWeapon, thrown, oneHandedWield, attackRoll);

                initialAttackLabel.setText("Natural Roll: " + attackRoll[0]);
                finalAttackLabel.setText("Final Attack Roll: " + attackRoll[1]);
                initialDamageLabel.setText("Initial Damage Roll: " + damageRoll[0]);
                finalDamageLabel.setText("Final Damage Roll: " + damageRoll[1]);

                initialAttackLabel.setVisible(true);
                finalAttackLabel.setVisible(true);
                initialDamageLabel.setVisible(true);
                finalDamageLabel.setVisible(true);
                rerollButton.setVisible(false);

                //AFTER BACKEND WORKS
                //For nat 1
                if(attackRoll[0] == 1){
                    JOptionPane.showMessageDialog(singleWeaponAttack, "Critical Fail!", "You Rolled a Natural 1!", JOptionPane.INFORMATION_MESSAGE);
                }
                //For non-nat 20
                else if(attackRoll[0] != 20){
                    if((damageRoll[0] == 1 || damageRoll[0] == 2) && oneHandedWield.get()){
                        rerollButton.setVisible(true);
                    }
                }
                //For nat 20
                else{
                    if(player.barbLevels.getBruteDie() != 0){
                        bruteButton.setVisible(true);

                    }
                }
                rollButton.setEnabled(false);

                weaponNamesComboBox.setEnabled(false);
                if(thrown.get()){
                    player.removeWeapon(finalWeapon);
                }
                if(player.hasAmmo(finalWeapon.getAmmunition()) && finalWeapon.isAmmo()){
                    player.removeAmmo(finalWeapon.getAmmunition());
                    ammoRemainingLabel.setText(finalWeapon.getAmmunition() + " Remaining: " +
                            player.getAmmo(finalWeapon.getAmmunition())
                    );
                }
            }
        });
        rerollButton.addActionListener(e ->{
            Weapon finalWeapon = Weapon.findWeapon(weaponNamesComboBox.getSelectedItem().toString());
            //Reroll if 1 or 2
            //Asks for damage reroll
            //Updates final damage
            //If they click the reroll button, then
            Random rand = new Random();
            int damageReroll = 0;
            int roll = 0;
            while(finalWeapon.getDiceNumber() > roll){
                damageReroll += rand.nextInt(finalWeapon.getDamageDie()) + 1;
                roll++;
            }

            int[] attackRoll = Weapon.rollForAttack(player, finalWeapon, advantage, disadvantage);
            int[] damageRoll = Weapon.rollForDamage(player, finalWeapon, thrown, oneHandedWield, attackRoll);
            initialDamageLabel.setText("Initial Damage Roll: " + damageReroll);
            finalDamageLabel.setText("Final Damage Roll: " + (damageReroll + damageRoll[2]));

            initialAttackLabel.setVisible(true);
            finalAttackLabel.setVisible(true);
            initialDamageLabel.setVisible(true);
            finalDamageLabel.setVisible(true);

            rerollButton.setEnabled(false);
        });
        bruteButton.addActionListener(e ->{
            Weapon finalWeapon = Weapon.findWeapon(weaponNamesComboBox.getSelectedItem().toString());
            //Ask for brutal critical
            //Updates final damage
            Random rand = new Random();
            int damageReroll = 0;
            int roll = 0;
            while(finalWeapon.getDiceNumber() > roll){
                damageReroll += rand.nextInt(finalWeapon.getDamageDie()) + 1;
                roll++;
            }

            int[] attackRoll = Weapon.rollForAttack(player, finalWeapon, advantage, disadvantage);
            int[] damageRoll = Weapon.rollForDamage(player, finalWeapon, thrown, oneHandedWield, attackRoll);
            initialDamageLabel.setText("Initial Damage Roll: " + damageReroll);
            finalDamageLabel.setText("Final Damage Roll: " + (damageReroll + damageRoll[2]));

            initialAttackLabel.setVisible(true);
            finalAttackLabel.setVisible(true);
            initialDamageLabel.setVisible(true);
            finalDamageLabel.setVisible(true);

            rerollButton.setEnabled(false);
        });
        returnButton.addActionListener(e -> {
            AtomicBoolean[] temp = new AtomicBoolean[]{falseValue, turnValues[1], trueValue};
            new CombatGUI(player, temp, movement);
            singleWeaponAttack.setVisible(false);
        });


    }

    //TWO WEAPON FIGHTING
    public CombatGUI(Character player, AtomicInteger movement, AtomicBoolean[] turnValues) {
        AtomicBoolean advantage = new AtomicBoolean(false);
        AtomicBoolean disadvantage = new AtomicBoolean(false);

        JFrame doubleWeaponAttack = new JFrame();
        doubleWeaponAttack.setTitle("Two Weapon Attack");
        doubleWeaponAttack.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        doubleWeaponAttack.setResizable(false);
        doubleWeaponAttack.setLayout(null);
        doubleWeaponAttack.setSize(950, 600);

        JLabel chooseFirstWeaponPrompt = new JLabel("Choose First Weapon: ");
        chooseFirstWeaponPrompt.setBounds(20,20,120,30);
        doubleWeaponAttack.add(chooseFirstWeaponPrompt);

        JLabel chooseSecondWeaponPrompt = new JLabel("Choose Second Weapon: ");
        chooseSecondWeaponPrompt.setBounds(170,20,120,30);
        doubleWeaponAttack.add(chooseSecondWeaponPrompt);

        //Weapon Scroll Pane
        DefaultListModel<String> availableLightWeapons = new DefaultListModel<>();
        DefaultListModel<String> selectedLightWeapons = new DefaultListModel<>();

        for (Weapon weapon : player.getWeaponList()) {
            if(weapon.isLight()){
                availableLightWeapons.addElement(weapon.getName());
            }
        }


        JList<String> availableWeaponList = new JList<>(availableLightWeapons);
        JList<String> selectedWeaponList = new JList<>(selectedLightWeapons);

        JScrollPane availableWeapons = new JScrollPane(availableWeaponList);
        JScrollPane selectedWeapons = new JScrollPane(selectedWeaponList);

        availableWeapons.setBounds(20, 50, 130, 100);
        selectedWeapons.setBounds(170, 50, 130, 100);

        doubleWeaponAttack.add(availableWeapons);
        doubleWeaponAttack.add(selectedWeapons);

        //Selecting Weapon
        JButton addButton = new JButton("Select Weapon");
        JButton removeButton = new JButton("Remove Weapon");
        addButton.setBounds(20, 160, 120, 30);
        removeButton.setBounds(170, 160, 120, 30);
        doubleWeaponAttack.add(addButton);
        doubleWeaponAttack.add(removeButton);
        removeButton.setEnabled(false);

        //Confirm two selected Weapons
        JButton confirmButton = new JButton("Confirm Weapons");
        confirmButton.setBounds(20, 200, 120, 30);
        confirmButton.setEnabled(false);
        doubleWeaponAttack.add(confirmButton);

        //Advantage Disadvantage
        JRadioButton advantageButton = new JRadioButton("Advantage");
        advantageButton.setBounds(20, 225, 120, 20);
        JRadioButton disadvantageButton = new JRadioButton("Disadvantage");
        disadvantageButton.setBounds(20, 250, 120, 20);
        JRadioButton noAdvantageButton = new JRadioButton("None");
        noAdvantageButton.setBounds(20, 275, 120, 20);
        doubleWeaponAttack.add(advantageButton);
        doubleWeaponAttack.add(disadvantageButton);
        doubleWeaponAttack.add(noAdvantageButton);
        advantageButton.setVisible(false);
        disadvantageButton.setVisible(false);
        noAdvantageButton.setVisible(false);


        ButtonGroup advantageOption = new ButtonGroup();
        advantageOption.add(noAdvantageButton);
        advantageOption.add(advantageButton);
        advantageOption.add(disadvantageButton);

        //ROLL BUTTON
        rollButton = new JButton("Roll");
        rollButton.setBounds(20, 300, 80, 30);
        doubleWeaponAttack.add(rollButton);
        rollButton.setEnabled(false);

        //First Attack
        JLabel firstInitialAttackLabel = new JLabel();
        firstInitialAttackLabel.setBounds(320, 20, 120, 20);
        JLabel firstFinalAttackLabel = new JLabel();
        firstFinalAttackLabel.setBounds(320, 45, 120, 20);
        JLabel firstInitialDamageLabel = new JLabel();
        firstInitialDamageLabel.setBounds(320, 75, 120, 20);
        JLabel firstFinalDamageLabel = new JLabel();
        firstFinalDamageLabel.setBounds(320, 100, 120, 20);
        doubleWeaponAttack.add(firstInitialAttackLabel);
        doubleWeaponAttack.add(firstFinalAttackLabel);
        doubleWeaponAttack.add(firstInitialDamageLabel);
        doubleWeaponAttack.add(firstFinalDamageLabel);
        firstInitialAttackLabel.setVisible(false);
        firstFinalAttackLabel.setVisible(false);
        firstInitialDamageLabel.setVisible(false);
        firstFinalDamageLabel.setVisible(false);
        //Second Attack
        JLabel secondInitialAttackLabel = new JLabel();
        secondInitialAttackLabel.setBounds(450, 20, 120, 20);
        JLabel secondFinalAttackLabel = new JLabel();
        secondFinalAttackLabel.setBounds(450, 45, 120, 20);
        JLabel secondInitialDamageLabel = new JLabel();
        secondInitialDamageLabel.setBounds(450, 75, 120, 20);
        JLabel secondFinalDamageLabel = new JLabel();
        secondFinalDamageLabel.setBounds(450, 100, 120, 20);
        doubleWeaponAttack.add(secondInitialAttackLabel);
        doubleWeaponAttack.add(secondFinalAttackLabel);
        doubleWeaponAttack.add(secondInitialDamageLabel);
        doubleWeaponAttack.add(secondFinalDamageLabel);
        secondInitialAttackLabel.setVisible(false);
        secondFinalAttackLabel.setVisible(false);
        secondInitialDamageLabel.setVisible(false);
        secondFinalDamageLabel.setVisible(false);

        //POST ROLL BUTTON
        JButton bruteButtonFirst = new JButton("Roll Brute Die");
        bruteButtonFirst.setBounds(360, 140, 140, 30);
        doubleWeaponAttack.add(bruteButtonFirst);
        bruteButtonFirst.setVisible(false);

        JButton bruteButtonSecond = new JButton("Roll Brute Die");
        bruteButtonSecond.setBounds(450, 140, 140, 30);
        doubleWeaponAttack.add(bruteButtonSecond);
        bruteButtonSecond.setVisible(false);

        //Return Button
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(160, 250, 80, 30);
        doubleWeaponAttack.add(returnButton);


        addButton.addActionListener(e -> {
            Weapon currentWeapon = Weapon.findWeapon(availableWeaponList.getSelectedValue());
            if (currentWeapon != null) {
                selectedLightWeapons.addElement(currentWeapon.getName());
                availableLightWeapons.removeElement(currentWeapon.getName());
                if(selectedLightWeapons.getSize() == 2){
                    confirmButton.setEnabled(true);
                    addButton.setEnabled(false);
                }
                removeButton.setEnabled(true);
            }
        });
        removeButton.addActionListener(e -> {
            Weapon currentWeapon = Weapon.findWeapon(selectedWeaponList.getSelectedValue());
            if (currentWeapon != null) {
                availableLightWeapons.addElement(currentWeapon.getName());
                selectedLightWeapons.removeElement(currentWeapon.getName());
                confirmButton.setEnabled(false);
                addButton.setEnabled(true);
                if(selectedLightWeapons.size() == 0){
                    removeButton.setEnabled(false);
                }
            }
        });

        confirmButton.addActionListener(e ->{
            Weapon firstWeapon = Weapon.findWeapon(selectedLightWeapons.get(0));
            Weapon secondWeapon = Weapon.findWeapon(selectedLightWeapons.get(1));
            if(player.getAmmo(firstWeapon.getAmmunition()) == 0|| player.getAmmo(secondWeapon.getAmmunition()) == 0){
                JOptionPane.showMessageDialog(doubleWeaponAttack, "Insufficient Ammo", "You lack ammo!", JOptionPane.INFORMATION_MESSAGE);
            }
            else{

                addButton.setEnabled(false);
                removeButton.setEnabled(false);

                advantageButton.setVisible(true);
                disadvantageButton.setVisible(true);
                noAdvantageButton.setVisible(true);
            }
        });

        advantageButton.addActionListener(e -> {
            rollButton.setEnabled(advantageButton.isSelected());
            advantage.set(true);
            disadvantage.set(false);
        });
        disadvantageButton.addActionListener(e -> {
            rollButton.setEnabled(disadvantageButton.isSelected());
            advantage.set(false);
            disadvantage.set(true);
        });
        noAdvantageButton.addActionListener(e -> {
            rollButton.setEnabled(noAdvantageButton.isSelected());
            advantage.set(false);
            disadvantage.set(false);
        });

        //DO BELOW AFTER ROLL
        rollButton.addActionListener(e -> {
            Weapon firstWeapon = Weapon.findWeapon(selectedLightWeapons.get(0));
            Weapon secondWeapon = Weapon.findWeapon(selectedLightWeapons.get(1));

            if(!player.hasAmmo(firstWeapon.getAmmunition()) && firstWeapon.isAmmo()){
                JOptionPane.showMessageDialog(doubleWeaponAttack, "You have no more ammo for your first weapon!!", "Ammo Insufficient", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(!player.hasAmmo(secondWeapon.getAmmunition()) && secondWeapon.isAmmo()){
                JOptionPane.showMessageDialog(doubleWeaponAttack, "You have no more ammo for your second weapon!", "Ammo Insufficient", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                //RETURNS ATTACK ROLLS
                AtomicBoolean falseVariable = new AtomicBoolean(false);
                int[] firstAttackRoll = Weapon.rollForAttack(player, firstWeapon, advantage, disadvantage);
                int[] firstDamageRoll = Weapon.rollForDamage(player, firstWeapon, falseVariable, falseVariable, firstAttackRoll);

                int[] secondAttackRoll = Weapon.rollForAttack(player, secondWeapon, advantage, disadvantage);
                int[] secondDamageRoll = Weapon.rollForDamage(player, secondWeapon, falseVariable, falseVariable, secondAttackRoll);

                firstInitialAttackLabel.setText("Natural Roll: " + firstAttackRoll[0]);
                firstFinalAttackLabel.setText("Final Attack Roll: " + firstAttackRoll[1]);
                firstInitialDamageLabel.setText("Initial Damage Roll: " + firstDamageRoll[0]);
                firstFinalDamageLabel.setText("Final Damage Roll: " + firstDamageRoll[1]);

                firstInitialAttackLabel.setVisible(true);
                firstFinalAttackLabel.setVisible(true);
                firstInitialDamageLabel.setVisible(true);
                firstFinalDamageLabel.setVisible(true);

                secondInitialAttackLabel.setText("Natural Roll: " + secondAttackRoll[0]);
                secondFinalAttackLabel.setText("Final Attack Roll: " + secondAttackRoll[1]);
                secondInitialDamageLabel.setText("Initial Damage Roll: " + secondDamageRoll[0]);
                secondFinalDamageLabel.setText("Final Damage Roll: " + secondDamageRoll[1]);

                secondInitialAttackLabel.setVisible(true);
                secondFinalAttackLabel.setVisible(true);
                secondInitialDamageLabel.setVisible(true);
                secondFinalDamageLabel.setVisible(true);

                //AFTER BACKEND WORKS
                //For nat 1
                if(firstAttackRoll[0] == 1){
                    JOptionPane.showMessageDialog(doubleWeaponAttack, "Critical Fail!", "You Rolled a Natural 1 on your first attack!", JOptionPane.INFORMATION_MESSAGE);
                }
                //For non-nat 20
                else if(firstAttackRoll[0] == 20){
                    if(player.barbLevels.getBruteDie() != 0){
                        bruteButtonFirst.setVisible(true);
                    }
                }

                if(firstAttackRoll[0] == 1){
                    JOptionPane.showMessageDialog(doubleWeaponAttack, "Critical Fail!", "You Rolled a Natural 1 on your second attack!", JOptionPane.INFORMATION_MESSAGE);
                }
                //For non-nat 20
                else if (secondAttackRoll[0] == 20){
                    if(player.barbLevels.getBruteDie() != 0){
                        bruteButtonSecond.setVisible(true);
                    }
                }
                rollButton.setEnabled(false);
                if(firstWeapon.isAmmo()){
                    player.removeAmmo(firstWeapon.getAmmunition());
                }
                if(secondWeapon.isAmmo()){
                    player.removeAmmo(secondWeapon.getAmmunition());
                }
            }
        });
        bruteButtonFirst.addActionListener(e ->{
            AtomicBoolean falseVariable = new AtomicBoolean(false);
            Weapon firstWeapon = Weapon.findWeapon(selectedLightWeapons.get(0));

            //Ask for brutal critical
            //Updates final damage
            Random rand = new Random();
            int damageReroll = 0;
            int roll = 0;
            while(firstWeapon.getDiceNumber() > roll){
                damageReroll += rand.nextInt(firstWeapon.getDamageDie()) + 1;
                roll++;
            }

            int[] attackRoll = Weapon.rollForAttack(player, firstWeapon, advantage, disadvantage);
            int[] damageRoll = Weapon.rollForDamage(player, firstWeapon, falseVariable, falseVariable, attackRoll);
            firstInitialDamageLabel.setText("Initial Damage Roll: " + damageReroll);
            firstFinalDamageLabel.setText("Final Damage Roll: " + (damageReroll + damageRoll[2]));
        });
        bruteButtonSecond.addActionListener(e ->{
            AtomicBoolean falseVariable = new AtomicBoolean(false);
            Weapon secondWeapon = Weapon.findWeapon(selectedLightWeapons.get(0));

            //Ask for brutal critical
            //Updates final damage
            Random rand = new Random();
            int damageReroll = 0;
            int roll = 0;
            while(secondWeapon.getDiceNumber() > roll){
                damageReroll += rand.nextInt(secondWeapon.getDamageDie()) + 1;
                roll++;
            }

            int[] attackRoll = Weapon.rollForAttack(player, secondWeapon, advantage, disadvantage);
            int[] damageRoll = Weapon.rollForDamage(player, secondWeapon, falseVariable, falseVariable, attackRoll);
            secondInitialDamageLabel.setText("Initial Damage Roll: " + damageReroll);
            secondFinalDamageLabel.setText("Final Damage Roll: " + (damageReroll + damageRoll[2]));
        });
        returnButton.addActionListener(e -> {
            AtomicBoolean[] temp = new AtomicBoolean[]{falseValue, falseValue, turnValues[2]};
            new CombatGUI(player, temp, movement);
            doubleWeaponAttack.setVisible(false);
        });


        doubleWeaponAttack.setVisible(true);

    }

    JRadioButton walkButton, walkRoughButton;
    JRadioButton flyButton, flyRoughButton;
    JRadioButton specialMovementButton;
    JRadioButton specialMovementRoughButton;
    JButton specialMovementAthletics;
    JRadioButton highJumpButton, longJumpButton;
    JRadioButton runningHighJumpButton, runningLongJumpButton;
    JButton highJumpAthletics, longJumpAthletics, longJumpAcrobatics;
    JButton confirmMovement;
    JLabel athleticsLabel, acrobaticsLabel;
    JButton inspirationAthletics, inspirationAcrobatics;

    public CombatGUI(Character player, AtomicBoolean[] options, AtomicInteger movement, String opt) {
        JFrame movementFrame = new JFrame();
        movementFrame.setTitle("Movement");
        movementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        movementFrame.setResizable(false);
        movementFrame.setLayout(null);
        movementFrame.setSize(950, 600);

        JLabel moveLabel = new JLabel("Choose Movement Option:");
        moveLabel.setBounds(20, 20, 150, 20);
        movementFrame.add(moveLabel);

        String[] availableMovementArray = {"Walk", "Climb", "Swim", "Fly", "Crawl", "Long Jump", "High Jump"};
        JComboBox movementOptions = new JComboBox<>(availableMovementArray);
        movementOptions.setBounds(20, 45, 100, 20);
        movementFrame.add(movementOptions);

        JLabel currentSpeed = new JLabel("Current Speed: " + movement);
        currentSpeed.setBounds(180, 20, 130, 20);
        movementFrame.add(currentSpeed);

        JLabel movementAmountLabel = new JLabel("Enter Movement Distance:");
        movementAmountLabel.setBounds(20, 70, 150, 20);
        movementFrame.add(movementAmountLabel);
        movementAmountLabel.setVisible(false);

        JTextField movementAmountText = new JTextField();
        movementAmountText.setBounds(20, 90, 150, 20);
        movementFrame.add(movementAmountText);
        movementAmountText.setVisible(false);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(20, 115, 80, 20);
        movementFrame.add(calculateButton);

        confirmMovement = new JButton("Confirm");
        confirmMovement.setBounds(200, 115, 80, 20);
        movementFrame.add(confirmMovement);
        confirmMovement.setEnabled(false);

        walkButton = new JRadioButton();
        walkButton.setBounds(20, 140, 150, 20);
        walkButton.setVisible(false);
        movementFrame.add(walkButton);

        walkRoughButton = new JRadioButton();
        walkRoughButton.setBounds(20, 170, 150, 20);
        walkRoughButton.setVisible(false);
        movementFrame.add(walkRoughButton);

        flyButton = new JRadioButton();
        flyButton.setBounds(20, 140, 150, 20);
        flyButton.setVisible(false);
        movementFrame.add(flyButton);

        flyRoughButton = new JRadioButton();
        flyRoughButton.setBounds(20, 170, 150, 20);
        flyRoughButton.setVisible(false);
        movementFrame.add(flyRoughButton);

        specialMovementButton = new JRadioButton();
        specialMovementButton.setBounds(20, 140, 150, 20);
        specialMovementButton.setVisible(false);
        movementFrame.add(specialMovementButton);

        specialMovementRoughButton = new JRadioButton();
        specialMovementRoughButton.setBounds(20, 170, 150, 20);
        specialMovementRoughButton.setVisible(false);
        movementFrame.add(specialMovementRoughButton);


        specialMovementAthletics = new JButton("Roll For Athletics");
        specialMovementAthletics.setBounds(20, 200, 180, 20);
        specialMovementAthletics.setVisible(false);
        movementFrame.add(specialMovementAthletics);


        highJumpButton = new JRadioButton();
        highJumpButton.setBounds(20, 140, 180, 20);
        highJumpButton.setVisible(false);
        movementFrame.add(highJumpButton);

        runningHighJumpButton = new JRadioButton();
        runningHighJumpButton.setBounds(20, 170, 180, 20);
        runningHighJumpButton.setVisible(false);
        movementFrame.add(runningHighJumpButton);

        longJumpButton = new JRadioButton();
        longJumpButton.setBounds(20, 140, 180, 20);
        longJumpButton.setVisible(false);
        movementFrame.add(longJumpButton);
        runningLongJumpButton = new JRadioButton();
        runningLongJumpButton.setBounds(20, 170, 180, 20);
        runningLongJumpButton.setVisible(false);
        movementFrame.add(runningLongJumpButton);


        highJumpAthletics = new JButton("Roll For Athletics");
        highJumpAthletics.setBounds(20, 200, 180, 20);
        highJumpAthletics.setVisible(false);
        movementFrame.add(highJumpAthletics);

        longJumpAthletics = new JButton("Roll For Athletics");
        longJumpAthletics.setBounds(20, 200, 180, 20);
        longJumpAthletics.setVisible(false);
        movementFrame.add(longJumpAthletics);

        longJumpAcrobatics = new JButton("Roll For Acrobatics");
        longJumpAcrobatics.setBounds(20, 230, 180, 20);
        longJumpAcrobatics.setVisible(false);
        movementFrame.add(longJumpAcrobatics);

        ButtonGroup walkMovementGroup = new ButtonGroup();
        walkMovementGroup.add(walkButton);
        walkMovementGroup.add(walkRoughButton);

        ButtonGroup flyMovementGroup = new ButtonGroup();
        flyMovementGroup.add(flyButton);
        flyMovementGroup.add(flyRoughButton);

        ButtonGroup specialMovementGroup = new ButtonGroup();
        specialMovementGroup.add(specialMovementButton);
        specialMovementGroup.add(specialMovementRoughButton);

        ButtonGroup longJumpGroup = new ButtonGroup();
        longJumpGroup.add(longJumpButton);
        longJumpGroup.add(runningLongJumpButton);

        ButtonGroup highJumpGroup = new ButtonGroup();
        highJumpGroup.add(highJumpButton);
        highJumpGroup.add(runningHighJumpButton);

        athleticsLabel = new JLabel();
        athleticsLabel.setBounds(20, 220, 150, 20);
        athleticsLabel.setVisible(false);
        acrobaticsLabel = new JLabel();
        acrobaticsLabel.setBounds(20, 240, 150, 20);
        acrobaticsLabel.setVisible(false);
        inspirationAthletics = new JButton("Use Inspiration");
        inspirationAthletics.setBounds(210, 200, 150, 20);
        inspirationAthletics.setVisible(false);
        inspirationAcrobatics = new JButton("Use Inspiration");
        inspirationAcrobatics.setBounds(210, 230, 150, 20);
        inspirationAcrobatics.setVisible(false);

        movementFrame.add(acrobaticsLabel);
        movementFrame.add(athleticsLabel);
        movementFrame.add(inspirationAthletics);
        movementFrame.add(inspirationAcrobatics);

        AtomicInteger usedInspiration = new AtomicInteger(0);


        movementOptions.addActionListener(e -> {
            usedInspiration.set(0);
            inspirationAcrobatics.setEnabled(true);
            inspirationAthletics.setEnabled(true);
            disableAllMovement();
            String providedMovement = movementOptions.getSelectedItem().toString();
            switch(providedMovement){
                case "Long Jump" -> {
                    longJumpButton.setText("Standing Jump: " + player.getStat(0)/2);
                    runningLongJumpButton.setText("Running Jump: " + player.getStat(0));
                    longJumpAthletics.setVisible(true);
                    longJumpAcrobatics.setVisible(true);
                    disableMovementText("Long Jump");
                    confirmMovement.setEnabled(false);
                    movementAmountLabel.setVisible(false);
                    movementAmountText.setVisible(false);
                    calculateButton.setVisible(false);
                }
                case "High Jump" -> {
                    highJumpButton.setText("Standing Jump: " + player.getStat(0)/2);
                    runningHighJumpButton.setText("Running Jump: " + player.getStat(0));
                    highJumpAthletics.setVisible(true);
                    disableMovementText("High Jump");
                    confirmMovement.setEnabled(false);
                    movementAmountLabel.setVisible(false);
                    movementAmountText.setVisible(false);
                    calculateButton.setVisible(false);
                }
                case "Walk", "Crawl", "Swim", "Climb", "Fly" -> {
                    movementAmountLabel.setVisible(true);
                    movementAmountText.setVisible(true);
                    calculateButton.setVisible(true);
                    highJumpAthletics.setVisible(false);
                    longJumpAthletics.setVisible(false);
                    longJumpAcrobatics.setVisible(false);
                    confirmMovement.setEnabled(false);
                }
            }
        });

        AtomicReference<String> chosenMovement = new AtomicReference<>("");
        AtomicInteger finalDistance = new AtomicInteger();
        calculateButton.addActionListener(e -> {
            try{
                int providedMovementAmount = Integer.parseInt(movementAmountText.getText());
                String providedMovement = movementOptions.getSelectedItem().toString();
                if(movementAmountText.getText().equals("")){
                    System.out.println("SSADASD");
                    JOptionPane.showMessageDialog(movementFrame, "Insufficient Movement", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(providedMovementAmount > movement.get()){
                    JOptionPane.showMessageDialog(movementFrame, "Exceeds Current Movement!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);

                }
                else{
                    int resultant = calculateSpeed(player, providedMovement, providedMovementAmount);
                    switch (providedMovement) {
                        case "Walk" -> {
                            walkButton.setText("Normal Terrain: " + resultant);
                            walkRoughButton.setText("Rough Terrain: " + resultant / 2);

                        }
                        case "Fly" -> {
                            flyButton.setText("Normal Terrain: " + resultant);
                            flyRoughButton.setText("Rough Terrain: " + resultant / 2);

                        }
                        case "Swim", "Climb", "Crawl" -> {
                            specialMovementButton.setText("Normal Terrain: " + resultant);
                            specialMovementRoughButton.setText("Rough Terrain: " + resultant / 2);

                            specialMovementAthletics.setVisible(true);
                        }

                    }
                    finalDistance.set(resultant);
                    disableMovementText(providedMovement);
                    confirmMovement.setVisible(true);
                    chosenMovement.set(providedMovement);
                }
            }
            catch(NumberFormatException ime){
                System.out.println("HEY");
                JOptionPane.showMessageDialog(movementFrame, "Invalid Input!", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //Long jump, radio for half or not
        //Honesty
        //Athletics and Acrobatics buttons if needed

        //High jump, radio for half or not
        //Honesty
        //Athletics

        walkButton.addActionListener(e -> confirmMovement.setEnabled(true));
        walkRoughButton.addActionListener(e -> confirmMovement.setEnabled(true));
        flyButton.addActionListener(e -> confirmMovement.setEnabled(true));
        flyRoughButton.addActionListener(e -> confirmMovement.setEnabled(true));
        specialMovementButton.addActionListener(e -> confirmMovement.setEnabled(true));
        specialMovementRoughButton.addActionListener(e -> confirmMovement.setEnabled(true));
        longJumpButton.addActionListener(e -> confirmMovement.setEnabled(true));
        runningLongJumpButton.addActionListener(e -> confirmMovement.setEnabled(true));
        highJumpButton.addActionListener(e -> confirmMovement.setEnabled(true));
        runningHighJumpButton.addActionListener(e -> confirmMovement.setEnabled(true));



        movementFrame.setVisible(true);
        specialMovementAthletics.addActionListener(e -> {
            if(player.hasInspiration()){
                inspirationAthletics.setVisible(true);
            }
            else{
                athleticsLabel.setText("Athletics Roll: " + player.rollStat(3));
                athleticsLabel.setVisible(true);
            }
        });
        longJumpAcrobatics.addActionListener(e -> {
            if(player.hasInspiration()){
                inspirationAcrobatics.setVisible(true);
            }
            else{
                acrobaticsLabel.setText("Athletics Roll: " + player.rollStat(3));
                acrobaticsLabel.setVisible(true);
            }
        });
        longJumpAthletics.addActionListener(e -> {
            if(player.hasInspiration()){
                inspirationAthletics.setVisible(true);
            }
            else{
                athleticsLabel.setText("Athletics Roll: " + player.rollStat(3));
                athleticsLabel.setVisible(true);
            }
        });
        highJumpAthletics.addActionListener(e -> {
            if(player.hasInspiration()){
                inspirationAthletics.setVisible(true);
            }
            else{
                athleticsLabel.setText("Athletics Roll: " + player.rollStat(3));
                athleticsLabel.setVisible(true);
            }
        });

        Random rand = new Random();
        inspirationAthletics.addActionListener(e -> {
            usedInspiration.getAndIncrement();
            inspirationAcrobatics.setEnabled(false);
            inspirationAthletics.setEnabled(false);
            athleticsLabel.setText("Athletics Roll: " + (player.rollStat(3) + rand.nextInt(player.getInspiration()) + 1));
            athleticsLabel.setVisible(true);
        });
        inspirationAcrobatics.addActionListener(e -> {
            usedInspiration.getAndIncrement();
            inspirationAcrobatics.setEnabled(false);
            inspirationAthletics.setEnabled(false);
            acrobaticsLabel.setText("Acrobatics Roll: " + (player.rollStat(0) + rand.nextInt(player.getInspiration()) + 1));
            acrobaticsLabel.setVisible(true);
        });


        confirmMovement.addActionListener(e -> {
            String finalMovement = movementOptions.getSelectedItem().toString();
            int movementLeft = movement.get();
            switch (finalMovement){
                case "Walk", "Fly" -> movementLeft -= finalDistance.get();
                case "Swim", "Climb", "Crawl" -> movementLeft -= Integer.parseInt(movementAmountText.getText());
                case "Long Jump", "High Jump" -> movementLeft -= player.getStat(0);
            }

            AtomicInteger movementRemaining = new AtomicInteger(movementLeft);
            new CombatGUI(player, options, movementRemaining);
            movementFrame.setVisible(false);
            if(usedInspiration.getAndIncrement() != 0){
                player.setInspiration(0);
            }
        });
    }

    private void disableMovementText(String providedMovement){
        switch (providedMovement) {
            case "Walk" -> {
                walkButton.setVisible(true);
                walkRoughButton.setVisible(true);

                flyButton.setVisible(false);
                flyRoughButton.setVisible(false);
                specialMovementButton.setVisible(false);
                specialMovementRoughButton.setVisible(false);
                specialMovementAthletics.setVisible(false);
                longJumpButton.setVisible(false);
                runningLongJumpButton.setVisible(false);
                highJumpButton.setVisible(false);
                runningHighJumpButton.setVisible(false);
            }
            case "Fly" -> {
                flyButton.setVisible(true);
                flyRoughButton.setVisible(true);

                walkButton.setVisible(false);
                walkRoughButton.setVisible(false);
                specialMovementButton.setVisible(false);
                specialMovementRoughButton.setVisible(false);
                specialMovementAthletics.setVisible(false);
                longJumpButton.setVisible(false);
                runningLongJumpButton.setVisible(false);
                highJumpButton.setVisible(false);
                runningHighJumpButton.setVisible(false);
            }
            case "Swim", "Climb", "Crawl" -> {
                specialMovementButton.setVisible(true);
                specialMovementRoughButton.setVisible(true);
                specialMovementAthletics.setVisible(true);

                walkButton.setVisible(false);
                walkRoughButton.setVisible(false);
                flyButton.setVisible(false);
                flyRoughButton.setVisible(false);
                longJumpButton.setVisible(false);
                runningLongJumpButton.setVisible(false);
                highJumpButton.setVisible(false);
                runningHighJumpButton.setVisible(false);
            }
            case "Long Jump" -> {
                longJumpAthletics.setVisible(true);
                longJumpAcrobatics.setVisible(true);
                longJumpButton.setVisible(true);
                runningLongJumpButton.setVisible(true);

                walkButton.setVisible(false);
                walkRoughButton.setVisible(false);
                flyButton.setVisible(false);
                flyRoughButton.setVisible(false);
                specialMovementButton.setVisible(false);
                specialMovementRoughButton.setVisible(false);
                specialMovementAthletics.setVisible(false);
                highJumpButton.setVisible(false);
                runningHighJumpButton.setVisible(false);
            }
            case "High Jump" -> {
                highJumpAthletics.setVisible(true);
                highJumpButton.setVisible(true);
                runningHighJumpButton.setVisible(true);

                walkButton.setVisible(false);
                walkRoughButton.setVisible(false);
                flyButton.setVisible(false);
                flyRoughButton.setVisible(false);
                specialMovementButton.setVisible(false);
                specialMovementRoughButton.setVisible(false);
                specialMovementAthletics.setVisible(false);
                longJumpButton.setVisible(false);
                runningLongJumpButton.setVisible(false);
            }
        }
    }
    private void disableAllMovement(){
        flyButton.setVisible(false);
        flyRoughButton.setVisible(false);
        specialMovementButton.setVisible(false);
        specialMovementRoughButton.setVisible(false);
        specialMovementAthletics.setVisible(false);
        longJumpButton.setVisible(false);
        runningLongJumpButton.setVisible(false);
        highJumpButton.setVisible(false);
        runningHighJumpButton.setVisible(false);
        walkButton.setVisible(false);
        walkRoughButton.setVisible(false);
        athleticsLabel.setVisible(false);
        acrobaticsLabel.setVisible(false);
        inspirationAthletics.setVisible(false);
        inspirationAcrobatics.setVisible(false);
    }
    private int calculateSpeed(Character player, String providedMovement, int providedMovementAmount) {
        int toReturn = 0;
        switch (providedMovement) {
            case "Walk" -> toReturn = providedMovementAmount;
            case "Swim" -> {
                if (player.getSwimSpeed() != 0) {
                    toReturn = providedMovementAmount;
                } else {
                    toReturn = providedMovementAmount / 2;
                }
            }
            case "Climb" -> {
                if (player.getClimbSpeed() != 0) {
                    toReturn = providedMovementAmount;
                } else {
                    toReturn = providedMovementAmount / 2;
                }
            }
            case "Fly" -> {
                if (player.getFlySpeed() == 0) {
                } else {
                    toReturn = providedMovementAmount;
                }
            }
            case "Crawl" -> toReturn = providedMovementAmount/2;
            case "Long Jump" -> toReturn = player.getStat(0);
            case "High Jump" -> toReturn = player.getModifiers()[0] + 3;
        }

        return toReturn;
    }


    //SPELLCASTING

    //MOVEMENT

}


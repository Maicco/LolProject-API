package com.example.android.leagueoflegends_api;

public class SingleChampion
{
    private String title;
    private int attack, defense, magic, difficult;
    private String passiveName, passiveDescription;
    private String tags;
    private String skillName0, skillDescription0, skillName1, skillDescription1, skillName2, skillDescription2, skillName3, skillDescription3;
    private String costBurn0, cooldownBurn0, costBurn1, cooldownBurn1, costBurn2, cooldownBurn2, costBurn3, cooldownBurn3;
    private String lore, key, name;

    public SingleChampion(String title, int attack, int defense, int magic, int difficult, String passiveName,
                          String passiveDescription, String tags, String skillName0, String skillDescription0,
                          String skillName1, String skillDescription1, String skillName2, String skillDescription2,
                          String skillName3, String skillDescription3,
                          String costBurn0, String cooldownBurn0, String costBurn1, String cooldownBurn1, String costBurn2, String cooldownBurn2,
                          String costBurn3, String cooldownBurn3, String lore, String key, String name)
    {
        this.title = title;
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;
        this.difficult = difficult;
        this.passiveName = passiveName;
        this.passiveDescription = passiveDescription;
        this.tags = tags;
        this.skillName0 = skillName0;
        this.skillDescription0 = skillDescription0;
        this.skillName1 = skillName1;
        this.skillDescription1 = skillDescription1;
        this.skillName2 = skillName2;
        this.skillDescription2 = skillDescription2;
        this.skillName3 = skillName3;
        this.skillDescription3 = skillDescription3;
        this.costBurn0 = costBurn0;
        this.cooldownBurn0 = cooldownBurn0;
        this.costBurn1 = costBurn1;
        this.cooldownBurn1 = cooldownBurn1;
        this.costBurn2 = costBurn2;
        this.cooldownBurn2 = cooldownBurn2;
        this.costBurn3 = costBurn3;
        this.cooldownBurn3 = cooldownBurn3;
        this.lore = lore;
        this.key = key;
        this.name = name;
    }

    public String getTitle() {
        return title.toUpperCase();
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagic() {
        return magic;
    }

    public int getDifficult() {
        return difficult;
    }

    public String getPassiveName() {
        return passiveName;
    }

    public String getPassiveDescription() {
        return passiveDescription;
    }

    public String getTags() {
        return tags;
    }

    public String getSkillName0() {
        return skillName0;
    }

    public String getSkillDescription0() {
        return skillDescription0;
    }

    public String getCostBurn0() {
        return costBurn0;
    }

    public String getCooldownBurn0() {
        return cooldownBurn0;
    }

    public String getCostBurn1() {
        return costBurn1;
    }

    public String getCooldownBurn1() {
        return cooldownBurn1;
    }

    public String getCostBurn2() {
        return costBurn2;
    }

    public String getCooldownBurn2() {
        return cooldownBurn2;
    }

    public String getCostBurn3() {
        return costBurn3;
    }

    public String getCooldownBurn3() {
        return cooldownBurn3;
    }

    public String getSkillName1() {
        return skillName1;
    }

    public String getSkillDescription1() {
        return skillDescription1;
    }

    public String getSkillName2() {
        return skillName2;
    }

    public String getSkillDescription2() {
        return skillDescription2;
    }

    public String getSkillName3() {
        return skillName3;
    }

    public String getSkillDescription3() {
        return skillDescription3;
    }

    public String getLore() {
        return lore;
    }

    public String getKey()
    {
        return key.toLowerCase()+"_splash_0";
    }

    public String getPassiveImage()
    {
        return key.toLowerCase()+"_passive";
    }

    public String getSkill0Image()
    {
        return key.toLowerCase()+"_q";
    }

    public String getSkill1Image()
    {
        return key.toLowerCase()+"_w";
    }

    public String getSkill2Image()
    {
        return key.toLowerCase()+"_e";
    }

    public String getSkill3Image()
    {
        return key.toLowerCase()+"_r";
    }

    public String getName()
    {
        return name.toUpperCase();
    }
}

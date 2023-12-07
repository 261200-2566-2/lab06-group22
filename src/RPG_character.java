import java.util.Objects;
interface Character_Setting{
    String name();
    int level();
    void LevelUp();
    void ShowStat();
}
interface Job_Setting extends Character_Setting {
    String Job_type();
    double[] Stat();
    void SetStat();
    void LevelUp();
    void Equip(Sword Thing);
    void Equip(Shield Thing);
    void Equip(Armor Thing);
    void Equip(Ring Thing);
    void UnEquip();
    void UnEquip(Sword Thing);
    void UnEquip(Shield Thing);
}
interface Accessories_Setting{
    String name();
    int Level();
    void LevelUp();
}
class RPG_character implements Character_Setting{
    protected String name;
    protected int level;
    protected double MaxHp,MaxMana,Hp,Mana,Atk,Def,BaseSpeed,MaxSpeed;
    protected Sword sword;
    protected Shield shield;
    protected Armor[] armor;
    protected Ring ring;
    public RPG_character(String name,int level){
        this.name = name;
        this.level = level;
        sword = new Sword();
        shield = new Shield();
        armor = new Armor[3];
        for(int i=0;i<3;i++){
            armor[i] = new Armor();
        }
        ring = new Ring();
    }
    @Override
    public String name() {
        return this.name;
    }
    @Override
    public int level() {
        return this.level;
    }
    /** Character's level Up
     * effects: Character's level increase by 1.
     */
    @Override
    public void LevelUp() {
        this.level++;
    }
    /** Printed all Character's Stat.
     *  effects: Show Character's name and level
     */
    public void ShowStat(){
        System.out.println("----------------------------------------------------------------");
        System.out.println("Name : " + name);
        System.out.println("level : " + level);
    }
}
class Job extends RPG_character implements Job_Setting{
    protected double[] Stat;
    protected String Job_type;
    public Job(String name, int level) {
        super(name, level);
        Job_type = "None";
        this.Stat = new double[]{50, 23, 6};
        SetStat();
    }
    @Override
    public String Job_type() {
        return Job_type;
    }
    @Override
    public double[] Stat() {
        return Stat;
    }
    /** Set a Default Character's Stat.
     *  effects: character's stats ( Atk, Def, MaxHp, Hp, MaxMana, Mana, BaseSpeed and MaxSpeed) are set based on the job type's stat and level.
    * */
    @Override
    public void SetStat() {
        this.Atk  = 10+2*level;
        this.Def = 10+level;
        this.MaxHp = Stat[0]+10*level;
        this.Hp = MaxHp;
        this.MaxMana = Stat[1]+2*level;
        this.Mana = MaxMana;
        this.BaseSpeed =Stat[2];
        this.MaxSpeed = BaseSpeed*(0.1+0.03*level);
    }
    /** Calculate Character's attack power.
     *  Returns the calculated attack power from the sword damage and character's base attack.
     */
    public  double attack(){
        if(!Objects.equals(sword.name, "None")){
            return sword.SwordDamage+Atk;
        }
        return Atk;
    }
    /** Decrease Character's Hp from damage this Character received.
     *  effects: calculated how much damage effects on Character's Hp
     *  form Character's defence and Shield defense and decrease Character's Hp
     * @param damage value of damage this character received
     */
    public void beAttacked(double damage){
        if(!Objects.equals(shield.name, "none")){

            damage -= (Def + shield.ShieldDefense)/10;
        }else{
            damage -= Def/10;
        }
        Hp = Math.max((Hp - damage), 0);
    }
    /** Character's level Up
     *  effects: Character's level increase by 1.
     *  effects: If the character's current HP is equal to the maximum HP, increase HP by 10.
     *  effects: If the character's current Mana is equal to the maximum Mana, increase Mana by 2.
     *  effects: Increase character's stats ( Atk, Def, MaxHp, MaxMana and MaxSpeed).
     */
    @Override
    public void LevelUp() {
        level++;
        if(Hp == MaxHp){
            Hp += 10;
        }
        if(Mana == MaxMana){
            Mana += 2;
        }
        this.MaxHp +=10;
        this.MaxMana += 2;
        Atk += 4;
        Def += 1;
        MaxSpeed = BaseSpeed*(0.1+0.03*level);
    }
    /** Give character a sword
     *  effects: If the provided Sword is a valid sword type and the character doesn't have a sword equipped, equip the sword
     *  or print "U cant equip " + sword's name +"!" if no
     *  @param Thing Object from Sword class
     */
    @Override
    public void Equip(Sword Thing) {
        if(Objects.equals(Thing.Type, "Weapon-Sword") && Objects.equals(sword.name, "None")){
            sword = Thing;
        }else{
            System.out.println("U cant equip " + Thing.name +"!");
        }
    }
    /** Give character a Shield
     *  effects: If the provided Shield is a valid Shield type and the character doesn't have a Shield equipped, equip the Shield
     *  or print "U cant equip " + Shield's name +"!" if no
     *  @param Thing Object from Shield class
     */
    public void Equip(Shield Thing) {
        if(Objects.equals(Thing.Type, "Weapon-Shield") && Objects.equals(shield.name, "None")){
            shield = Thing;
        }else{
            System.out.println("U cant equip " + Thing.name +"!");
        }
    }
    /** Give character an Armor
     *  effects: If the provided Armor is a valid Armor type and the character doesn't have an Armor equipped, equip the Armor
     *  or print "U cant equip " + Armor's name +"!" if no
     *  effects: Update character's stat based on Armor's stat
     *  @param Thing Object from Armor class
     */
    public void Equip(Armor Thing) {
        switch (Thing.Type){
            case "Helmet":
                if(Objects.equals(armor[0].name, "None")){
                    armor[0] = Thing;
                    UpdateStat("Plus",armor[0].Stat);
                }else{System.out.println("U cant equip " + Thing.name +"!");}
                break;
            case "Chest" :
                if(Objects.equals(armor[1].name, "None")){
                    armor[1] = Thing;
                    UpdateStat("Plus",armor[1].Stat);
                }else{System.out.println("U cant equip " + Thing.name +"!");}
                break;
            case "Pant" :
                if(Objects.equals(armor[2].name, "None")){
                    armor[2] = Thing;
                    UpdateStat("Plus",armor[2].Stat);
                }else{System.out.println("U cant equip " + Thing.name +"!");}
                break;
        }
    }
    /** Give character a Ring
     *  effects: If the provided Ring is a valid Ring type and the character doesn't have a Ring equipped, equip the Ring
     *  or print "U cant equip " + Ring's name +"!" if no
     *  effects: Update character's stat based on ring's stat
     *  @param Thing Object from Ring class
     */
    public void Equip(Ring Thing){
        if(Objects.equals(Thing.Type, "Ring") && Objects.equals(ring.name, "None")){
            ring = Thing;
            UpdateStat("Plus",ring.Rise,ring.Stat);
        }else{
            System.out.println("U cant equip " + Thing.name +"!");
        }
    }
    public void UnEquip(Sword Thing) {
        if (Objects.equals(sword.name, Thing.name)) {
            sword = new Sword();
        } else {
            System.out.println("You don't have " + Thing.name + " equipped!");
        }
    }

    @Override
    public void UnEquip(Shield Thing) {
        if (Objects.equals(shield.name, Thing.name)) {
            shield = new Shield();
        } else {
            System.out.println("You don't have " + Thing.name + " equipped!");
        }
    }

    public void UpdateStat(String A, int[] stat){
        if (Objects.equals(A, "Plus")){
            if(Hp == MaxHp){
                Hp += stat[0];
            }
            MaxHp += stat[0];
            MaxSpeed -= stat[1];
            Def += stat[2];
        }else if(Objects.equals(A, "minus")){
            if(Hp == MaxHp){
                Hp -= stat[0];
            }
            MaxHp -= stat[0];
            MaxSpeed += stat[1];
            Def -= stat[2];
        }
    }
    public void UpdateStat(String A,String B, int stat) {
        if (Objects.equals(A, "Plus")){
            switch (B){
                case "MaxHp" : if(Hp == MaxHp){Hp += stat;}MaxHp += stat;break;
                case "Atk" : Atk += stat;break;
                case "MaxSpeed" : MaxSpeed += stat;break;
                case "Defense" : Def += stat;break;
            }
        }else if(Objects.equals(A, "minus")){
            switch (B){
                case "MaxHp" : if(Hp == MaxHp){Hp -= stat;}MaxHp -= stat;break;
                case "Atk" : Atk -= stat;break;
                case "MaxSpeed" : MaxSpeed -= stat;break;
                case "Defense" : Def -= stat;break;
            }
        }
    }
    public void UnEquip(){
        sword = new Sword();
        shield = new Shield();
    }
    public void UnEquipAllArmor(){
        UpdateStat("minus",armor[0].Stat);
        armor[0] = new Armor();
        UpdateStat("minus",armor[1].Stat);
        armor[1] = new Armor();
        UpdateStat("minus",armor[2].Stat);
        armor[2] = new Armor();
        UpdateStat("minus",ring.Rise,ring.Stat);
        ring = new Ring();
    }
    /** Printed all Character's Stat.
     *  effects: Show Character's stat ( name, level, Hp, MaxHp, Mana, MaxMana, Defence, Job, Attack power ) and every all Character's accessories
     */
    public void ShowStat(){
        System.out.println("----------------------------------------------------------------");
        System.out.println("Name : " + name + "          level : " + level);
        System.out.println("Hp : " + Hp + "/" + MaxHp + "     Mp : " + Mana + "/" + MaxMana + "     Def : " + Def);
        System.out.println("Job : " + Job_type + "     Atk : " + attack());
        if(!Objects.equals(sword.name, "None")){System.out.println(sword.Type + " : " + sword.name + " lv." + sword.Level + " Damage :" + sword.SwordDamage); }
        if(!Objects.equals(shield.name, "None")){System.out.println(shield.Type + " : " + shield.name + " lv." + shield.Level + " Defense :" + shield.ShieldDefense); }
        if(!Objects.equals(armor[0].name, "None")){System.out.println(armor[0].Type + " : " + armor[0].name + " lv." + armor[0].Level);}
        if(!Objects.equals(armor[1].name, "None")){System.out.println(armor[1].Type + " : " + armor[1].name + " lv." + armor[1].Level);}
        if(!Objects.equals(armor[2].name, "None")){System.out.println(armor[2].Type + " : " + armor[2].name + " lv." + armor[2].Level);}
        if(!Objects.equals(ring.name, "None")){System.out.println(ring.Type + " : " + ring.name + " lv." + ring.Level);}
    }
}
class Wizard extends Job{
    public Wizard(String name, int level) {
        super(name,level);
        this.Job_type = "Wizard";
        this.Stat = new double[]{22,46, 4};
        SetStat();
    }

    @Override
    public void SetStat() {
        super.SetStat();
        this.MaxHp = Stat[0]+8*level;
        this.Hp = MaxHp;
        this.MaxMana = Stat[1]+4*level;
        this.Mana = MaxMana;
    }

    @Override
    public void LevelUp() {
        super.LevelUp();
        if(Hp == MaxHp){
            Hp -= 3;
        }
        if(Mana == MaxMana){
            Mana += 4;
        }
        this.MaxHp -= 3;
        this.MaxMana += 4;
    }

    public void Healing(){
        if(Mana >= 30){
            if((Hp+20) >= MaxHp){
                Hp = MaxHp;
            }else{
                Hp += 20;
            }
        }else{
            System.out.println("Not Enough Mp");
        }
    }
}
class Warrior extends Job{

    public Warrior(String name, int level) {
        super(name,level);
        this.Job_type = "Warrior";
        this.Stat = new double[]{68,14, 7};
        SetStat();
    }

    @Override
    public void SetStat() {
        super.SetStat();
        this.MaxHp = Stat[0]+12*level;
        this.Hp = MaxHp;
        this.MaxMana = Stat[1]+level;
        this.Mana = MaxMana;
    }

    @Override
    public void LevelUp() {
        super.LevelUp();
        if(Hp == MaxHp){
            Hp += 12;
        }
        if(Mana == MaxMana){
            Mana += 1;
        }
        this.MaxHp += 12;
        this.MaxMana += 1;
        this.Def += 1;
    }
    public void BoostSpeed(){
        if(Mana >= 20){
            BaseSpeed *= 2;
            Mana -= 20;
        }else{
            System.out.println("Not Enough Mp");
        }
    }
}
class Accessories implements Accessories_Setting{
    protected String name;
    protected String Type;
    protected int Level;
    public Accessories() {
        this.name = "None";
        this.Level = 0;
        this.Type = "None";
    }
    public Accessories(String name,int Level){
        this.name = name;
        this.Level = Level;
        this.Type = "None";
    }
    @Override
    public String name() {
        return this.name;
    }
    @Override
    public int Level() {
        return this.Level;
    }
    @Override
    public void LevelUp() {
        Level++;
    }
}
class Weapon extends Accessories{
    protected int Stat;
    public Weapon() {
        super();
        this.Type = "Weapon";
    }
    public Weapon(String name, int Level,int Stat) {
        super(name,Level);
        this.Type = "Weapon";
        this.Stat = Stat;
    }
}
class Sword extends Weapon{
    private int BaseDamage;
    protected double SwordDamage;
    public Sword() {
        super();
        this.Type = "Weapon-Sword";
    }
    public Sword(String name, int Level, int Stat) {
        super(name,Level,Stat);
        BaseDamage = Stat;
        SwordDamage = BaseDamage*(1+0.1*Level);
        this.Type = "Weapon-Sword";
    }
    @Override
    public void LevelUp() {
        super.LevelUp();
        SwordDamage = BaseDamage*(1+0.1*Level);
    }
}
class Shield extends Weapon{
    private int BaseDefense;
    protected double ShieldDefense;
    public Shield() {
        super();
        this.Type = "Weapon-Shield";
    }
    public Shield(String name, int Level, int Stat) {
        super(name,Level,Stat);
        BaseDefense = Stat;
        ShieldDefense = BaseDefense*(1+0.05*Level);
        this.Type = "Weapon-Shield";
    }
    @Override
    public void LevelUp() {
        super.LevelUp();
        ShieldDefense = BaseDefense*(1+0.05*Level);
    }
}
class Armor extends Accessories{
    protected int[] Stat;
    public Armor() {
        super();
        this.Type = "Armor";
    }
    public Armor(String name,String type,int Level,int[] Stat) {
        super(name,Level);
        switch (type){
            case "Helmet" : this.Type = "Helmet";break;
            case "Chest" : this.Type = "Chest";break;
            case "Pant" : this.Type = "Pant";break;
        }
        this.Stat = Stat;
    }
}
class Ring extends Accessories{
    protected int Stat;
    protected String Rise;
    public Ring() {
        super();
        this.Type = "Ring";
    }
    public Ring(String name,String Rise,int Level,int Stat){
        super(name,Level);
        this.Type = "Ring";
        this.Stat = Stat;
        this.Rise = Rise;
    }
}
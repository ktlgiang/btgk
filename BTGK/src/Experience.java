public class Experience extends Employee {
    private int expInYear;
    private String proSkill;

    public Experience(String ID, String fullName, String birthDay, String phone, String email, int expInYear, String proSkill) {
        super(ID, fullName, birthDay, phone, email, "Experience");
        this.expInYear = expInYear;
        this.proSkill = proSkill;
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Experience In Year: " + expInYear);
        System.out.println("Professional Skill: " + proSkill);
    }
}

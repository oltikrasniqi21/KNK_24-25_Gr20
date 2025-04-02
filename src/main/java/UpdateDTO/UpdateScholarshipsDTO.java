package UpdateDTO;

import java.time.LocalDate;

public class UpdateScholarshipsDTO {
    private String name;
    private String provider;
    private double amount;
    private LocalDate deadlineDate;
    private double requirementsGpa;
    private int requirementsYear;
    private String requirementsMajor;

    public UpdateScholarshipsDTO() {};

    public UpdateScholarshipsDTO(String name, String provider, double amount, LocalDate deadlineDate,
                                 double requirementsGpa, int requirementsYear, String requirementsMajor) {
        this.name = name;
        this.provider = provider;
        this.amount = amount;
        this.deadlineDate = deadlineDate;
        this.requirementsGpa = requirementsGpa;
        this.requirementsYear = requirementsYear;
        this.requirementsMajor = requirementsMajor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public double getRequirementsGpa() {
        return requirementsGpa;
    }

    public void setRequirementsGpa(double requirementsGpa) {
        this.requirementsGpa = requirementsGpa;
    }

    public int getRequirementsYear() {
        return requirementsYear;
    }

    public void setRequirementsYear(int requirementsYear) {
        this.requirementsYear = requirementsYear;
    }

    public String getRequirementsMajor() {
        return requirementsMajor;
    }

    public void setRequirementsMajor(String requirementsMajor) {
        this.requirementsMajor = requirementsMajor;
    }
}

package seedu.address.model.module.personal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.module.exceptions.DateTimeException;

//@@author wanxuanong
/**
 * Represents a Deadline in Personal.
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS = "Date and time field of deadline should be in the format "
            + "YYYY-MM-DD and HH:mm respectively.\n" + "Dates should be valid Gregorian calendar dates"
            + " and time should be in 24-hour format.\n"
            + "Example: 2020-04-25 18:54";

    protected String moduleCode;
    protected String description;
    protected LocalDate date;
    protected LocalTime time;
    private String tag;

    private DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Deadline(String moduleCode, String description, LocalDate date, LocalTime time) {
        this.moduleCode = moduleCode;
        this.description = description;
        this.date = date;
        this.time = time;

        LocalDate today = LocalDate.now();
        if (this.date.isBefore(today.plusDays(5))) {
            tag = "RED";
        } else if (this.date.isBefore(today.plusDays(10))) {
            tag = "YELLOW";
        } else {
            tag = "GREEN";
        }
    }

    public Deadline(String moduleCode, String description) {
        this.moduleCode = moduleCode;
        this.description = description;
        this.date = null;
        this.time = null;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStringDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    public String getStringTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedString = time.format(formatter);
        return formattedString;
    }

    public String getTag() {
        return tag;
    }

    public void setDescription(String newTask) {
        this.description = newTask;
    }

    public void setDateTime(String newDate, String newTime) throws DateTimeException {
        try {
            this.date = LocalDate.parse(newDate);
            this.time = LocalTime.parse(newTime, inputTimeFormatter);
        } catch (Exception e) {
            throw new DateTimeException("Try: d/YYYY-MM-DD HH:mm");
        }
    }

    public DateTimeFormatter getInputTimeFormatter() {
        return inputTimeFormatter;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    /**
     * Adds tag to new Deadline so that UI changes colour according to date.
     */
    public void addTag() {
        LocalDate today = LocalDate.now();
        if (this.date.isBefore(today.plusDays(5))) {
            tag = "RED";
        } else if (this.date.isBefore(today.plusDays(10))) {
            tag = "YELLOW";
        } else {
            tag = "GREEN";
        }
    }

    @Override
    public String toString() {
        String result = this.moduleCode + ": " + this.description;
        if (date != null && time != null) {
            result += " by " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ", "
                    + LocalTime.parse(time.toString(), inputTimeFormatter);
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Deadline) {
            Deadline otherDeadline = (Deadline) other;
            return this.moduleCode.equals(otherDeadline.getModuleCode())
                    && this.description.toUpperCase().equals(otherDeadline.getDescription().toUpperCase());
        }
        return false;
    }
}

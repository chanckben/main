package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.module.personal.Deadline;

//@@author gyant6

/**
 * A utility class to help with building Deadline objects.
 */
public class DeadlineBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS1101S";
    public static final String DEFAULT_DESCRIPTION = "Assignment 1";
    public static final String DEFAULT_DATE = "2020-05-30";
    public static final String DEFAULT_TIME = "18:00";

    private static final String inputTimePattern = "HH:mm";
    private static final DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(inputTimePattern);

    private String moduleCode;
    private String description;
    private LocalDate date;
    private LocalTime time;

    public DeadlineBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        description = DEFAULT_DESCRIPTION;
        date = LocalDate.parse(DEFAULT_DATE);
        time = LocalTime.parse(DEFAULT_TIME, inputTimeFormatter);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code profileToCopy}.
     */
    public DeadlineBuilder(Deadline deadlineToCopy) {
        moduleCode = deadlineToCopy.getModuleCode();
        description = deadlineToCopy.getDescription();
        date = deadlineToCopy.getDate();
        time = deadlineToCopy.getTime();
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    /**
     * Sets the description of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withDate(String date) {
        this.date = LocalDate.parse(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withTime(String time) {
        this.time = LocalTime.parse(time, inputTimeFormatter);
        return this;
    }

    public Deadline build() {
        return new Deadline(moduleCode, description, date, time);
    }

}


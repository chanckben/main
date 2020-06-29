package seedu.address.testutil;

//@@author gyant6

import seedu.address.model.module.personal.Deadline;

/**
 * A utility class containing a list of {@code Deadline} objects to be used in tests.
 */
public class TypicalDeadlines {

    public static final Deadline ASSIGNMENT_ONE = new DeadlineBuilder().withModuleCode("CS1101S")
            .withDescription("ASSIGNMENT ONE").withDate("2020-05-30")
            .withTime("18:00").build();

}

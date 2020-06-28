package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.NameContainsKeywordsPredicate;
import seedu.address.model.profile.Profile;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_MODCODE_AMY = "CS1231";
    public static final String VALID_SEMESTER_AMY = "2.1";
    public static final String VALID_GRADE_AMY = "A-";
    public static final String VALID_TASK_AMY = "homework";
    public static final String VALID_NEW_TASK_AMY = "new homework";
    public static final String VALID_COURSE_AMY = "Computer Science";
    public static final String VALID_FOCUS_AREA_AMY = "Software Engineering";
    public static final String VALID_DEADLINE_DATE_AMY = "2020-05-23";
    public static final String VALID_DEADLINE_TIME_AMY = "23:59";
    public static final String VALID_DEADLINE_AMY = "2020-05-23 23:59";

    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_MODCODE_BOB = "MA1521";
    public static final String VALID_SEMESTER_BOB = "1.1";
    public static final String VALID_GRADE_BOB = "C+";
    public static final String VALID_TASK_BOB = "assignment";
    public static final String VALID_NEW_TASK_BOB = "new assignment";
    public static final String VALID_COURSE_BOB = "Business Analytics";
    public static final String VALID_FOCUS_AREA_BOB = "Financial Analytics";
    public static final String VALID_DEADLINE_DATE_BOB = "2020-05-05";
    public static final String VALID_DEADLINE_TIME_BOB = "12:35";
    public static final String VALID_DEADLINE_BOB = "2020-05-05 12:35";

    public static final String VALID_COURSE_CS = "Computer Science";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String MODCODE_DESC_AMY = " " + PREFIX_MODULE + VALID_MODCODE_AMY;
    public static final String SEMESTER_DESC_AMY = " " + PREFIX_YEAR + VALID_SEMESTER_AMY;
    public static final String GRADE_DESC_AMY = " " + PREFIX_GRADE + VALID_GRADE_AMY;
    public static final String TASK_DESC_AMY = " " + PREFIX_TASK + VALID_TASK_AMY;
    public static final String NEW_TASK_DESC_AMY = " " + PREFIX_NEW_TASK + VALID_NEW_TASK_AMY;
    public static final String DEADLINE_DESC_AMY = "  " + PREFIX_DEADLINE + VALID_DEADLINE_AMY;
    public static final String COURSE_DESC_AMY = " " + PREFIX_COURSE_NAME + VALID_COURSE_AMY;
    public static final String FOCUS_AREA_DESC_AMY = " " + PREFIX_FOCUS_AREA + VALID_FOCUS_AREA_AMY;

    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String MODCODE_DESC_BOB = " " + PREFIX_MODULE + VALID_MODCODE_BOB;
    public static final String SEMESTER_DESC_BOB = " " + PREFIX_YEAR + VALID_SEMESTER_BOB;
    public static final String GRADE_DESC_BOB = " " + PREFIX_GRADE + VALID_GRADE_BOB;
    public static final String TASK_DESC_BOB = " " + PREFIX_TASK + VALID_TASK_BOB;
    public static final String NEW_TASK_DESC_BOB = " " + PREFIX_NEW_TASK + VALID_NEW_TASK_BOB;
    public static final String DEADLINE_DESC_BOB = " " + PREFIX_DEADLINE + VALID_DEADLINE_BOB;
    public static final String COURSE_DESC_BOB = " " + PREFIX_COURSE_NAME + VALID_COURSE_BOB;
    public static final String FOCUS_AREA_DESC_BOB = " " + PREFIX_FOCUS_AREA + VALID_FOCUS_AREA_BOB;

    public static final String INVALID_COURSE = "Random Course";
    public static final String INVALID_FOCUS_AREA = "Random Focus Area";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_MODCODE_DESC = " " + PREFIX_MODULE + "123ABC";
    public static final String INVALID_SEMESTER_DESC = " " + PREFIX_YEAR + "a";
    public static final String INVALID_GRADE_DESC = " " + PREFIX_GRADE + "1";
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "2021-15-30 23:59";
    public static final String INVALID_COURSE_DESC = " " + PREFIX_COURSE_NAME + "Random Course";
    public static final String INVALID_FOCUS_AREA_DESC = " " + PREFIX_FOCUS_AREA + "Random Focus Area";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show only the profile at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Profile profile = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = profile.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}

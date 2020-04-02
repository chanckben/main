package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_SEMESTER = "Semester is not a valid integer.";


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (name.equals("")) {
            throw new ParseException(MESSAGE_MISSING_NAME);
        }
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (moduleCode.equals("")) {
            throw new ParseException(MESSAGE_MISSING_MODULE);
        }
        if (!ModuleCode.isValidCode(moduleCode)) {
            throw new ParseException(MESSAGE_INVALID_MODULE);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Checks that input is an integer
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a {@code String semester}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code semester} is invalid.
     */
    public static int parseSemester(String semester) throws ParseException {
        String trimmedSemester = semester.trim();
        if (semester.equals("")) {
            throw new ParseException(MESSAGE_MISSING_SEMESTER);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedSemester)) {
            throw new ParseException(MESSAGE_INVALID_SEMESTER);
        }
        return Integer.parseInt(trimmedSemester);
    }

    /**
     * Parses a {@code String grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static String parseGrade(String grade) throws ParseException {
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return trimmedGrade;
    }

    /**
     * Parses a {@code String deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static String parseDeadline(String deadline) throws ParseException {
        String trimmedDeadline = deadline.trim();
        String[] dateTime = trimmedDeadline.split(" ");
        if (!Deadline.isValidDeadline(dateTime[0], dateTime[1])) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return trimmedDeadline;
    }

    /**
     * Parses a {@code String courseName} into a {@code CourseName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CourseName parseCourseName(String courseName) throws ParseException {
        requireNonNull(courseName);
        String trimmedSemester = courseName.trim();
        if (trimmedSemester.equals("")) {
            throw new ParseException(MESSAGE_MISSING_COURSE);
        }
        return new CourseName(trimmedSemester);
    }

    public static String parseFocusArea(String focusArea) throws ParseException {
        requireNonNull(focusArea);
        String trimmedFocusArea = focusArea.trim();
        if (trimmedFocusArea.equals("")) {
            throw new ParseException(MESSAGE_MISSING_COURSE_FOCUS_AREA);
        }
        return trimmedFocusArea;
    }
}

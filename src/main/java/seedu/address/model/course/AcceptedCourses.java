package seedu.address.model.course;

//@@author joycelynteo
/**
 * Accepted courses stored as enumerations.
 */
public enum AcceptedCourses {
    COMPUTER_SCIENCE("COMPUTER SCIENCE"),
    BUSINESS_ANALYTICS("BUSINESS ANALYTICS"),
    INFORMATION_SYSTEMS("INFORMATION SYSTEMS"),
    INFORMATION_SECURITY("INFORMATION SECURITY"),
    COMPUTER_ENGINEERING("COMPUTER ENGINEERING");

    private String name;

    AcceptedCourses(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true if enum contains given course.
     */
    public static boolean contains(String course) {
        for (AcceptedCourses c: AcceptedCourses.values()) {
            if (c.name.equals(course)) {
                return true;
            }
        }
        return false;
    }
}

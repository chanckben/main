package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;
import seedu.address.model.profile.course.module.personal.Personal;
import seedu.address.model.profile.course.module.personal.Status;

/**
 * Jackson-friendly version of {@link Profile}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonProfile {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Profile's %s field is missing!";

    private String name;
    private String courseName;
    private String specialisation;
    private String currentSemester;
    private List<JsonSemesterRecord> records;

    @JsonCreator
    public JsonProfile(@JsonProperty("name") String name,
            @JsonProperty("courseName") String courseName,
            @JsonProperty("specialisation") String specialisation,
            @JsonProperty("currentSemester") String currentSemester,
            @JsonProperty("records") List<JsonSemesterRecord> records) {
        this.name = name;
        this.courseName = courseName;
        this.specialisation = specialisation;
        this.currentSemester = currentSemester;
        this.records = records;
    }

    public JsonProfile(Profile source) {
        name = source.getName().toString();
        courseName = source.getCourseName().toString();
        specialisation = source.getSpecialisation();
        currentSemester = source.getCurrentSemester();
        records = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<Module>> entry: source.getMappings()) {
            String sem = entry.getKey().toString();
            ArrayList<Module> modules = entry.getValue();
            records.add(new JsonSemesterRecord(
                    sem, modules.stream().map(JsonPersonalModule::new).collect(Collectors.toList())));
        }
    }

    /**
     * Converts this Jackson-friendly profile object into a {@code Profile} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the profile.
     */
    public Profile toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        } else if (courseName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CourseName.class.getSimpleName()));
        } else if (currentSemester == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "current semester"));
        } else if (records == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "records"));
        }

        // Handle invalid values
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        } else if (!currentSemester.matches("\\d+")) {
            throw new IllegalValueException("Semester number should be a positive integer");
        }
        // TODO: Validation for course - Completely alphabetical

        Name profileName = new Name(name);
        CourseName profileCourse = new CourseName(courseName);
        Profile profile = new Profile(profileName, profileCourse, currentSemester, specialisation);

        for (JsonSemesterRecord record : records) {
            int semester = Integer.parseInt(record.getSemester());
            for (JsonPersonalModule module : record.getModules()) {
                Module mod = module.toModelType();
                profile.addModule(semester, mod);
            }
        }

        return profile;
    }
}

/**
 * Jackson-friendly version of a semester.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonSemesterRecord {
    private String semester;
    private List<JsonPersonalModule> modules;

    @JsonCreator
    public JsonSemesterRecord(@JsonProperty("semester") String semester,
            @JsonProperty("modules") List<JsonPersonalModule> modules) {
        this.semester = semester;
        this.modules = modules;
    }

    public String getSemester() throws IllegalValueException {
        if (!semester.matches("\\d+")) {
            throw new IllegalValueException("Semester number should be a positive integer");
        }
        return semester;
    }

    public List<JsonPersonalModule> getModules() {
        return modules;
    }
}

/**
 * Jackson-friendly version of {@link Module}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonPersonalModule extends JsonModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private String status;
    private String grade;
    private List<JsonDeadline> deadlines;

    @JsonCreator
    public JsonPersonalModule(@JsonProperty("moduleCode") String moduleCode,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("moduleCredit") String moduleCredit,
            @JsonProperty("prerequisite") String prerequisite,
            @JsonProperty("semesterData") List<JsonSemesterData> semesterData,
            @JsonProperty("status") String status,
            @JsonProperty("grade") String grade,
            @JsonProperty("deadlines") List<JsonDeadline> deadlines) {
        super(moduleCode, title, description, moduleCredit, prerequisite, semesterData);
        this.status = status;
        this.grade = grade;
        this.deadlines = deadlines;
    }

    public JsonPersonalModule(Module module) {
        super(module.getModuleCode().toString(), module.getTitle().toString(), module.getDescription().toString(),
                module.getModularCredits().toString(), module.getPrereqList().toString(),
                module.getSemesterData().getSemesters().stream()
                        .map(JsonSemesterData::new).collect(Collectors.toList()));
        status = module.getStatus();
        grade = module.getGrade();
        if (module.getDeadlines().size() == 0) {
            deadlines = null;
        } else {
            deadlines = module.getDeadlines().stream().map(JsonDeadline::new).collect(Collectors.toList());
        }
    }

    @Override
    public Module toModelType() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        } else if (grade != null && !Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }

        Module module = super.toModelType();
        Personal personal = new Personal();
        if (grade != null) {
            personal.setGrade(grade);
        }
        personal.setStatus(status);
        if (deadlines != null) {
            for (JsonDeadline deadline : deadlines) {
                personal.addDeadline(deadline.toModelType());
            }
        }
        module.setPersonal(personal);
        return module;
    }
}

/**
 * Jackson-friendly version of {@link Deadline}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonDeadline {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deadline's %s field is missing!";

    private String description;
    private String date;
    private String time;

    @JsonCreator
    public JsonDeadline(@JsonProperty("description") String description,
            @JsonProperty("date") String date,
            @JsonProperty("time") String time) {
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public JsonDeadline(Deadline deadline) {
        description = deadline.getDescription();
        if (deadline.getDate() == null) {
            date = null;
        } else {
            date = deadline.getDate().toString();

        }
        if (deadline.getTime() == null) {
            time = null;
        } else {
            time = deadline.getTime().toString();
        }
    }

    /**
     * Converts this Jackson-friendly deadline object into a {@code Deadline} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the deadline.
     */
    public Deadline toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "description"));
        }

        if (date != null && time != null) {
            try {
                LocalDate.parse(date);
                LocalTime.parse(time);
            } catch (DateTimeParseException | NullPointerException e) {
                throw new IllegalValueException("Deadline's date field should be a valid date in the format YYYY-MM-DD "
                        + "and time field should be a valid time in the format HH:mm");
            }

            try {
                return new Deadline(description, date, time);
            } catch (DateTimeException e) {
                throw new IllegalValueException("Unknown error occurred");
            }
        }

        return new Deadline(description);
    }
}

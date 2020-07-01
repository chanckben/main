package seedu.address.model.profile;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SEMESTER;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModuleList;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FocusArea;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.module.personal.Deadline;
import seedu.address.model.profile.exceptions.MaxModsException;

//@@author chanckben
/**
 * Represents a Profile in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Profile {

    // Identity fields
    private TreeMap<Integer, ModuleList> semModTreeMap;
    private int currentSemester = 0;
    private FocusArea focusArea;
    private Name name;
    private CourseName courseName;
    private List<Deadline> deadlineList;

    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, CourseName courseName, int currentSemester, FocusArea focusArea) {
        requireAllNonNull(name);
        requireAllNonNull(courseName);
        requireAllNonNull(currentSemester);
        this.name = name;
        this.courseName = courseName;
        this.currentSemester = currentSemester;
        this.focusArea = focusArea;
        this.semModTreeMap = new TreeMap<>();
    }


    /**
     * Adds a module to the TreeMap with the key being the semester
     */
    public void addModule(Integer semester, Module module) throws MaxModsException {
        if (!semModTreeMap.isEmpty() && semModTreeMap.containsKey(semester)) {
            if (semModTreeMap.get(semester).size() == 10) {
                throw new MaxModsException();
            }
            semModTreeMap.get(semester).addModule(module);
        } else {
            ModuleList moduleList = new ModuleList();
            moduleList.addModule(module);
            semModTreeMap.put(semester, moduleList);
        }

        int id = semModTreeMap.get(semester).getModuleList().size();
        module.setTag(id);

    }

    public Name getName() {
        return name;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public int getOverallSemester() {
        return this.currentSemester;
    }

    public int getCurrentSemester() {
        if (currentSemester % 2 == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    public int getCurrentYear() {
        if (currentSemester >= 1 && currentSemester <= 16) {
            return (currentSemester + 1) / 2;
        } else {
            return 9;
        }
    }

    public FocusArea getFocusArea() {
        return focusArea;
    }

    public String getFocusAreaString() {
        return focusArea.toString();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setCourse(CourseName courseName) {
        this.courseName = courseName;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public void setFocusArea(FocusArea focusArea) {
        this.focusArea = focusArea;
    }

    public ModuleList getModules(Integer semester) throws ParseException {
        if (!semModTreeMap.containsKey(semester)) {
            throw new ParseException(MESSAGE_INVALID_SEMESTER);
        }
        return semModTreeMap.get(semester);
    }

    public List<ModuleCode> getAllModuleCodesBefore(int semester) {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        for (int sem: semModTreeMap.keySet()) {
            if (sem < semester) {
                moduleCodes.addAll(semModTreeMap.get(sem).getModuleCodes());
            }
            //moduleCodes.addAll(moduleList.getModuleCodes());
        }
        return moduleCodes;
    }

    public TreeMap<Integer, ModuleList> getAllModules() {
        return semModTreeMap;
    }

    public Set<Map.Entry<Integer, ModuleList>> getMappings() {
        return semModTreeMap.entrySet();
    }

    public TreeMap<Integer, ModuleList> getSemModTreeMap() {
        return semModTreeMap;
    }

    public List<Deadline> getDeadlines() {
        ModuleList modules = semModTreeMap.get(currentSemester); // Deadlines should only be from the current semester
        deadlineList = new ArrayList<>();

        if (modules != null) {
            for (Module module: modules) {
                deadlineList.addAll(module.getDeadlines());
            }
        }
        return deadlineList;
    }

    public String getCap() {
        for (ModuleList semMods: semModTreeMap.values()) {
            for (Module mod: semMods) {
                if (mod.hasGrade()) {
                    DecimalFormat df = new DecimalFormat("#.##"); // Convert cap to 2 d.p.
                    return df.format(Cap.getCap(semModTreeMap));
                }
            }
        }
        return "No grades added";
    }

    public ModuleList getCurModules() {
        return semModTreeMap.get(currentSemester);
    }

    public int getModuleSemester(ModuleCode moduleCode) {
        for (int semester: semModTreeMap.keySet()) {
            for (Module module: semModTreeMap.get(semester)) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return semester;
                }
            }
        }
        throw new NoSuchElementException(name.toString() + " is not taking " + moduleCode.toString());
    }

    public Module getModule(ModuleCode moduleCode) throws ModuleNotFoundException {
        for (ModuleList moduleList: semModTreeMap.values()) {
            for (Module module: moduleList) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return module;
                }
            }
        }
        throw new ModuleNotFoundException(name.toString() + " is not taking " + moduleCode.toString());
    }

    /**
     * Returns true if a module with module code {@code moduleCode} exists in {@code moduleHash}.
     */
    public boolean hasModule(ModuleCode moduleCode) {
        for (ModuleList moduleList: semModTreeMap.values()) {
            if (moduleList.hasModuleWithModuleCode(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a module with module code {@code moduleCode}.
     */
    public void deleteModule(ModuleCode moduleCode) throws ModuleNotFoundException {
        if (hasModule(moduleCode)) {
            int semester = getModuleSemester(moduleCode);
            semModTreeMap.get(semester).removeModuleWithModuleCode(moduleCode);
            return;
        }
        throw new ModuleNotFoundException(name.toString() + " is not taking " + moduleCode.toString());
    }

    /**
     * Returns key of the given value
     */
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProfile(Profile otherProfile) {
        if (otherProfile == this) {
            return true;
        }

        return otherProfile != null
                && otherProfile.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Profile)) {
            return false;
        }

        Profile otherProfile = (Profile) other;
        return otherProfile.getName().equals(getName())
                && otherProfile.getCourseName().equals(getCourseName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }
}

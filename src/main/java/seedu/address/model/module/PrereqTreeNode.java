package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

import seedu.address.storage.JsonPrereqTreeNode;

//@@author chanckben
/**
 * An object representing the node of the prerequisite tree of a module.
 */
public class PrereqTreeNode {
    private ModuleCode moduleCode;
    private String type;
    private List<PrereqTreeNode> prereqTreeNodes;

    public PrereqTreeNode(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }
    public PrereqTreeNode() {
        this.prereqTreeNodes = new ArrayList<>();
    }

    public void addPrereqTreeNode(PrereqTreeNode prereqTreeNode) {
        this.prereqTreeNodes.add(prereqTreeNode);
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Checks if the user has fulfilled all the prerequisites under this prerequisite tree.
     * Recursive function, to be called for every node while traversing down the tree.
     */
    public boolean hasFulfilledPrereqs(List<ModuleCode> modulesTaken) {
        if (moduleCode != null) {
            // Kept the first condition mainly as precaution
            return modulesTaken.contains(moduleCode)
                    || modulesTaken.stream()
                    .map(ModuleCode::removeSuffix)
                    .anyMatch(modCode->modCode.equals(moduleCode));
        }
        boolean check;
        if (type.equals("or")) {
            check = false;
            // As long as 1 module fulfils prereq, requirement is fulfilled
            for (PrereqTreeNode node: prereqTreeNodes) {
                if (node.hasFulfilledPrereqs(modulesTaken)) {
                    check = true;
                }
            }
        } else if (type.equals("and")) {
            check = true;
            // As long as 1 module does not fulfil prereq, requirement is not fulfilled
            for (PrereqTreeNode node: prereqTreeNodes) {
                if (!node.hasFulfilledPrereqs(modulesTaken)) {
                    check = false;
                }
            }
        } else {
            throw new IllegalArgumentException("Wrong type in PrereqTreeNode.java, fix code");
        }
        return check;
    }

    /**
     * Converts this PrereqTreeNode to a JsonPrereqTreeNode object.
     */
    public JsonPrereqTreeNode toJson() {
        if (moduleCode != null) {
            return new JsonPrereqTreeNode(moduleCode.toString());
        }
        List<JsonPrereqTreeNode> listNodes = new ArrayList<>();
        for (PrereqTreeNode node: prereqTreeNodes) {
            listNodes.add(node.toJson());
        }
        if (type.equals("or")) {
            return new JsonPrereqTreeNode(listNodes, null);
        } else if (type.equals("and")) {
            return new JsonPrereqTreeNode(null, listNodes);
        }
        throw new IllegalArgumentException("Error parsing prereqTreeNode object in PrereqTreeNode.java, to fix");
    }

    @Override
    public String toString() { // For debugging purposes
        if (moduleCode != null) {
            return moduleCode.toString();
        }
        String toReturn = "{";
        toReturn += type + ": [";
        for (PrereqTreeNode node: prereqTreeNodes) {
            toReturn += node.toString() + ", ";
        }
        toReturn += "]}";
        return toReturn;
    }
}

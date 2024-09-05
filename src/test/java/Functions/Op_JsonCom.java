package Functions;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import Utilities.ReportManager;

public class Op_JsonCom {
    private static final List<String> SECTIONS = Arrays.asList("Data", "Before", "AfterData", "payload.before", "payload.after");

    public void compareJsonFiles(ReportManager reportManager) {
        String filePath1 = "src/test/resources/Cosmos.json";
        String filePath2 = "src/test/resources/Dummy_Cosmos.json";

        try {
            String content1 = new String(Files.readAllBytes(Paths.get(filePath1)));
            String content2 = new String(Files.readAllBytes(Paths.get(filePath2)));

            JSONObject jsonObject1 = new JSONObject(content1);
            JSONObject jsonObject2 = new JSONObject(content2);

            compareObjects(jsonObject1, jsonObject2, "Cosmos.json", "Dummy_Cosmos.json", reportManager);
            reportManager.logPass("JSON comparison completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            reportManager.logFail("JSON comparison failed due to an exception: " + e.getMessage());
        }
    }

    private void compareObjects(JSONObject json1, JSONObject json2, String fileName1, String fileName2, ReportManager reportManager) {
        for (String section : SECTIONS) {
            reportManager.logInfo("Comparing objects in " + section + " section:");
            JSONObject obj1 = getNestedObject(json1, section);
            JSONObject obj2 = getNestedObject(json2, section);

            if (obj1 != null && obj2 != null) {
                if (isOfficeNumMatching(obj1, obj2, reportManager)) {
                    compareFullObjects(obj1, obj2, fileName1, fileName2, reportManager);
                } else {
                    reportManager.logFail("Error: iOfficeNum does not match between " + fileName1 + " and " + fileName2 + " in section " + section);
                    throw new RuntimeException("Error: iOfficeNum does not match.");
                }
            } else {
                handleMissingSection(obj1, obj2, fileName1, fileName2, section, reportManager);
            }
        }
    }

    private JSONObject getNestedObject(JSONObject json, String path) {
        String[] parts = path.split("\\.");
        JSONObject current = json;
        for (String part : parts) {
            current = current.optJSONObject(part);
            if (current == null) return null;
        }
        return current;
    }

    private boolean isOfficeNumMatching(JSONObject obj1, JSONObject obj2, ReportManager reportManager) {
        List<String> keysToCompare = Arrays.asList("iOfficeNum", "sOfficeDept", "iIndCtlNum");

        for (String key : keysToCompare) {
            String value1 = obj1.optString(key, "Not Found");
            String value2 = obj2.optString(key, "Not Found");

            if (!value1.equals(value2)) {
                reportManager.logFail("<span style='color:red'>" + key + " does not match: " + value1 + " vs " + value2 + "</span>");
                return false;
            }
        }
        return true;
    }

    private void compareFullObjects(JSONObject obj1, JSONObject obj2, String fileName1, String fileName2, ReportManager reportManager) {
        if (obj1.similar(obj2)) {
            reportManager.logPass("The full objects match.");
        } else {
            reportManager.logFail("The full objects do not match:");
            reportManager.logInfo("Details from " + fileName1 + ":\n" + obj1.toString(4));
            reportManager.logInfo("Details from " + fileName2 + ":\n" + obj2.toString(4));
        }
    }

    private void handleMissingSection(JSONObject obj1, JSONObject obj2, String fileName1, String fileName2, String section, ReportManager reportManager) {
        if (obj1 == null) {
            reportManager.logInfo("<span style='color:red'>Section '" + section + "' is missing in " + fileName1 + ".</span>");
            reportManager.logInfo("Details from " + fileName2 + ":\n" + formatJsonForReport(obj2));
        } else if (obj2 == null) {
            reportManager.logInfo("<span style='color:red'>Section '" + section + "' is missing in " + fileName2 + ".</span>");
            reportManager.logInfo("Details from " + fileName1 + ":\n" + formatJsonForReport(obj1));
        } else {
            reportManager.logInfo("Section not found in both files.");
        }
    }
    private String formatJsonForReport(JSONObject jsonObject) {
        String jsonString = jsonObject.toString(4);
        return escapeHtml(jsonString);

    }
    // This is for Json format
    private String escapeHtml(String jsonString) {
        return jsonString
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;")
                .replace(" ", "&nbsp;")
                .replace("\n", "<br>");
    }
}


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

public class Task3 {
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonReader testsFile = new JsonReader(new FileReader("tests.json"));
        Structure testsData = gson.fromJson(testsFile, Structure.class);

        JsonReader valuesFile = new JsonReader(new FileReader("values.json"));
        Values valuesData = gson.fromJson(valuesFile, Values.class);

        HashMap<Integer, String> valuesMap = new HashMap<>();
        for (ValuesUnit valuesUnit : valuesData.values) {
            valuesMap.put(valuesUnit.id, valuesUnit.value);
        }

        List<StructureUnit> tests = testsData.tests;
        for (StructureUnit structureUnit : tests) {
            structureUnitParse(structureUnit, valuesMap);
        }

        try (Writer writer = new FileWriter("report.json")) {
            gson.toJson(testsData, writer);
        }
    }

    private static void structureUnitParse(StructureUnit structureUnit, HashMap<Integer, String> valuesMap) {
        structureUnit.value = valuesMap.getOrDefault(structureUnit.id, "");

        List<StructureUnit> structureUnits = structureUnit.values;
        if (structureUnits != null) {
            for (StructureUnit structureUnitChild : structureUnits) {
                structureUnitParse(structureUnitChild, valuesMap);
            }
        }
    }

    private static class Structure {
        List<StructureUnit> tests;
    }

    private static class StructureUnit {
        int id;
        String title;
        String value;
        List<StructureUnit> values;
    }

    private static class Values {
        List<ValuesUnit> values;
    }

    private static class ValuesUnit {
        int id;
        String value;
    }
}

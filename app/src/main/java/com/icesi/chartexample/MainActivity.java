package com.icesi.chartexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.PertDataEntry;
import com.anychart.charts.Pert;
import com.anychart.core.pert.Milestones;
import com.anychart.core.pert.Tasks;
import com.anychart.core.ui.Tooltip;
import com.anychart.enums.TreeFillingMethod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnyChartView anyChartView = findViewById(R.id.chart);

        Pert pert = AnyChart.pert();

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomPertDataEntry("1", "== zero", "College", "College"));
        data.add(new CustomPertDataEntry("2", "== one", "Collegex", "Collegex"));
        //data.add(new CustomPertDataEntry("3", ">= 30000", "Income", new String[]{"2"}, "Income"));
//        data.add(new CustomPertDataEntry("5", 60, "5", new String[]{"2"}, "Build Prototype"));
//        data.add(new CustomPertDataEntry("6", 40, "6", new String[]{"3"}, "Control & Stability"));
//        data.add(new CustomPertDataEntry("7", 20, "7", new String[]{"4"}, "Wind Tunnel"));
//        data.add(new CustomPertDataEntry("8", 20, "8", new String[]{"6"}, "Computation"));
//        data.add(new CustomPertDataEntry("9", 45, "9", new String[]{"7"}, "Review"));
//        data.add(new CustomPertDataEntry("10", 30, "10", new String[]{"8"}, "Flight Simulation"));
//        data.add(new CustomPertDataEntry("11", 50, "11", new String[]{"9"}, "Research flights"));
//        data.add(new CustomPertDataEntry("12", 45, "12", new String[]{"10"}, "Revise & Review"));
//        data.add(new CustomPertDataEntry("13", 25, "13", new String[]{"5"}, "Finalize"));

        pert.data(data, TreeFillingMethod.AS_TREE);

        pert.padding(50d, 0d, 0d, 50d);

        pert.title().enabled(true);
        pert.cancelMarquee();



        pert.title().useHtml(true);
        pert.title()
                .padding(new Double[]{ 0d, 50d, 35d, 0d })
                .text("Decision tree Churn dataset");

        Tasks tasks = pert.tasks();
        tasks.upperLabels().format(
                "function() {\n" +
                        "    return this.item.get('fullName');\n" +
                        "  }");

        tasks.lowerLabels().format("{%condition} ");

        Tooltip tooltip = tasks.tooltip();
        tooltip.separator(true)
                .titleFormat(
                        "function() {\n" +
                                "      return this.item.get('fullName');\n" +
                                "    }");
        tooltip.title().useHtml(true);

        Milestones milestones = pert.milestones();

        milestones.color("#2C81D5")
                .size("6.5%");
        milestones.hovered().fill("#2C81D5", 0.75d);
        milestones.tooltip().format("" +
                "function() {\n" +
                "  var result = '';\n" +
                "  var i = 0;\n" +
                "  if (this['successors'] && this['successors'].length) {\n" +
                "    result += 'Successors:';\n" +
                "    for (i = 0; i < this['successors'].length-1; i++) {\n" +
                "      result += '\\n - ' + this['successors'][i].get('fullName');\n" +
                "    }\n" +
                "    if (this['predecessors'] && this['predecessors'].length)\n" +
                "      result += '\\n\\n';\n" +
                "  }\n" +
                "  if (this['predecessors'] && this['predecessors'].length) {\n" +
                "    result += 'Predecessors:';\n" +
                "    for (i = 0; i < this['predecessors'].length-1; i++) {\n" +
                "      result += '\\n - ' + this['predecessors'][i].get('fullName');\n" +
                "    }\n" +
                "  }\n" +
                "  return result;\n" +
                "}");

        Milestones critMilestones = pert.criticalPath().milestones();
        critMilestones.labels().format(
                "function() {\n" +
                        "    return this['creator'] ? this['creator'].get('name') : this['isStart'] ? 'Start' : 'Finish';\n" +
                        "  }");
        critMilestones.color("#E24B26");



        anyChartView.setChart(pert);

    }



    private class CustomPertDataEntry extends PertDataEntry {
        CustomPertDataEntry(String id, String duration, String name, String fullName) {
            super(id, name, fullName);
            setValue("condition", duration);
        }

        CustomPertDataEntry(String id, String duration, String name, String[] dependsOn, String fullName) {
            super(id, name, fullName, dependsOn);
            setValue("condition", duration);
        }


    }
}

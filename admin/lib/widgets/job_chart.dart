import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';

class JobChart extends StatefulWidget {
  JobChart({super.key, required this.chartData});
  final List chartData;
  final Color barBackgroundColor = Colors.white.withOpacity(0.3);
  final Color barColor = Colors.white;
  final Color touchedBarColor = Colors.blue;

  @override
  State<StatefulWidget> createState() => _JobChartState();
}

class _JobChartState extends State<JobChart> {
  final Duration animDuration = const Duration(milliseconds: 250);
  int touchedIndex = -1;

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: AspectRatio(
        aspectRatio: 2.4,
        child: BarChart(mainBarData()),
      ),
    );
  }


  BarChartGroupData makeGroupData(
      int x,
      double y, {
        bool isTouched = false,
        Color? barColor,
        double width = 14,
        List<int> showTooltips = const [],
      }) {
    barColor ??= widget.barColor;
    return BarChartGroupData(
      x: x,
      barRods: [
        BarChartRodData(
          toY: isTouched ? y + 1 : y,
          color: isTouched ? widget.touchedBarColor : barColor,
          width: width,
          borderSide: isTouched
              ? BorderSide(color: widget.touchedBarColor)
              : const BorderSide(color: Colors.white, width: 0),
          backDrawRodData: BackgroundBarChartRodData(
            show: true,
            toY: 20,
            color: widget.barBackgroundColor,
          ),
        ),
      ],
      showingTooltipIndicators: showTooltips,
    );
  }

  BarChartGroupData generateGroupData(int x, double pilates) {
    return BarChartGroupData(
      x: x,
      groupVertically: true,
      barRods: [
        BarChartRodData(
            fromY: 0,
            toY: pilates,
            color: pilates > 0 ? Colors.black87 : Colors.amber,
            width: 7,
            borderRadius: pilates > 0 ? const BorderRadius.only(topLeft: Radius.circular(15.0), topRight: Radius.circular(15.0)) : const BorderRadius.only(bottomLeft: Radius.circular(15.0), bottomRight: Radius.circular(15.0))
        ),
      ],
    );
  }


  BarChartData mainBarData() {
    List<BarChartGroupData> dataChart = [];
    for(var i = 0; i < widget.chartData.length; i++) {
      double val = widget.chartData[i].toDouble();
      BarChartGroupData barChartGroupData = makeGroupData(i, val, isTouched: i == touchedIndex);
      dataChart.add(barChartGroupData);
    }
    return BarChartData(
      barTouchData: BarTouchData(
        touchTooltipData: BarTouchTooltipData(
          getTooltipColor: (_) => Colors.blueGrey,
          tooltipHorizontalAlignment: FLHorizontalAlignment.right,
          tooltipMargin: -10,
          getTooltipItem: (group, groupIndex, rod, rodIndex) {
            String month;
            switch (group.x) {
              case 0:
                month = 'Jan';
                break;
              case 1:
                month = 'Feb';
                break;
              case 2:
                month = 'Mar';
                break;
              case 3:
                month = 'Apr';
                break;
              case 4:
                month = 'May';
                break;
              case 5:
                month = 'Jun';
                break;
              case 6:
                month = 'Jul';
                break;
              case 7:
                month = 'Aug';
                break;
              case 8:
                month = 'Sep';
                break;
              case 9:
                month = 'Oct';
                break;
              case 10:
                month = 'Nov';
                break;
              case 11:
                month = 'Dec';
                break;
              default:
                throw Error();
            }
            return BarTooltipItem(
              '$month\n',
              const TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.bold,
                fontSize: 18,
              ),
              children: <TextSpan>[
                TextSpan(
                  text: (rod.toY - 1).toString(),
                  style: const TextStyle(
                    color: Colors.white, //widget.touchedBarColor,
                    fontSize: 16,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ],
            );
          },
        ),
        touchCallback: (FlTouchEvent event, barTouchResponse) {
          setState(() {
            if (!event.isInterestedForInteractions ||
                barTouchResponse == null ||
                barTouchResponse.spot == null) {
              touchedIndex = -1;
              return;
            }
            touchedIndex = barTouchResponse.spot!.touchedBarGroupIndex;
          });
        },
      ),
      titlesData: FlTitlesData(
        show: true,
        rightTitles: const AxisTitles(
          sideTitles: SideTitles(showTitles: false),
        ),
        topTitles: const AxisTitles(
          sideTitles: SideTitles(showTitles: false),
        ),
        bottomTitles: AxisTitles(
          sideTitles: SideTitles(
            showTitles: true,
            getTitlesWidget: getTitles,
            reservedSize: 38,
          ),
        ),
        leftTitles: const AxisTitles(
          sideTitles: SideTitles(
            showTitles: false,
          ),
        ),
      ),
      borderData: FlBorderData(
        show: false,
      ),
      barGroups: dataChart,
      gridData: const FlGridData(show: false),
    );
  }

  Widget getTitles(double value, TitleMeta meta) {
    const style = TextStyle(
      color: Colors.white,
      fontWeight: FontWeight.bold,
      fontSize: 14,
    );
    Widget text;
    switch (value.toInt()) {
      case 0:
        text = const Text('1', style: style);
        break;
      case 1:
        text = const Text('2', style: style);
        break;
      case 2:
        text = const Text('3', style: style);
        break;
      case 3:
        text = const Text('4', style: style);
        break;
      case 4:
        text = const Text('5', style: style);
        break;
      case 5:
        text = const Text('6', style: style);
        break;
      case 6:
        text = const Text('7', style: style);
        break;
      case 7:
        text = const Text('8', style: style);
        break;
      case 8:
        text = const Text('9', style: style);
        break;
      case 9:
        text = const Text('10', style: style);
        break;
      case 10:
        text = const Text('11', style: style);
        break;
      case 11:
        text = const Text('12', style: style);
        break;
      default:
        text = const Text('', style: style);
        break;
    }
    return SideTitleWidget(
      axisSide: meta.axisSide,
      space: 16,
      child: text,
    );
  }
}
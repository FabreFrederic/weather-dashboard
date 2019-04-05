import {Component, Input, OnChanges, OnInit, SimpleChange} from '@angular/core';
import {Chart} from 'angular-highcharts';
import * as Highcharts from 'highcharts';
import {ReadingService} from "../service/reading.service";
import {ChartConfig} from "./chartConfig";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit, OnChanges, OnChanges {
  @Input()
  public lastValue: number;
  @Input()
  public lastDate: number;
  @Input()
  public config: ChartConfig;

  private chart: Chart;
  private chartOptions: any;

  constructor(private readingService: ReadingService) {
  }

  ngOnInit(): void {
    this.setOptions();
    this.chart = new Chart(this.chartOptions);
    Highcharts.setOptions({
      global: {
        timezoneOffset: new Date().getTimezoneOffset()
      }
    });

    this.readingService.getTodayReadings(this.config.url).subscribe((readings) => {
      let values: any[] = [];
      readings.forEach(reading => {
        values.push({
          x: +new Date(reading.date),
          y: +reading.value
        });
      });

      this.chart.ref.series[0].setData(values, true);
    });
  }

  private setOptions() {
    this.chartOptions = {
      global: {
        timezoneOffset: new Date().getTimezoneOffset()
      },
      title: {
        text: null
      },
      xAxis: {
        type: this.config.xAxisType
      },
      yAxis: {
        title: {
          text: this.config.yAxisTitleText
        }
      },
      credits: {
        enabled: false
      },
      navigator: {
        enabled: true
      },
      rangeSelector: {
        enabled: false
      },
      series: [{
        type: 'areaspline',
        name: this.config.seriesName,
        fillColor: {
          linearGradient: {x1: 0, y1: 1, x2: 0, y2: 0},
          stops: [
            [0, '#BBE2DE'],
            [1, '#f4f4f4']
          ]
        },
        color: '#BBE2DE'
      }]
    };
  }

  ngOnChanges(changes: { [propertyName: string]: SimpleChange }) {
    if (changes['lastDate'] && this.lastDate) {
      if (this.chart) {
        this.chart.ref.series[0].addPoint([+new Date(this.lastDate),
          +this.lastValue], true, false);
      }
    }
  }

}

import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-temperature',
  templateUrl: './temperature.component.html',
  styleUrls: ['./temperature.component.css']
})
export class TemperatureComponent implements OnInit {

  public temperatureValue: number = 20;
  public periods = [
    {value: 'today-0', viewValue: 'Today'},
    {value: 'week-1', viewValue: 'This week'},
    {value: 'month-2', viewValue: 'This month'}
  ];

  constructor() {
  }

  ngOnInit() {
  }
}

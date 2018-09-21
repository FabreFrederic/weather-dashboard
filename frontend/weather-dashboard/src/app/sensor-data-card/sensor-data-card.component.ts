import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-sensor-data-card',
  templateUrl: './sensor-data-card.component.html',
  styleUrls: ['./sensor-data-card.component.css']
})
export class SensorDataCardComponent {

  @Input()
  public title: string;
  @Input()
  public subtitle: string;
  @Input()
  public value: number;

  constructor() {}

 }

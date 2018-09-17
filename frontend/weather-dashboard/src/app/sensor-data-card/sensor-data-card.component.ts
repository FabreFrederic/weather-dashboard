import {Component, Input, OnInit} from '@angular/core';
import {VertXEventBusService} from "../vertx/vertXEventBus.service";

@Component({
  selector: 'app-sensor-data-card',
  templateUrl: './sensor-data-card.component.html',
  styleUrls: ['./sensor-data-card.component.css']
})
export class SensorDataCardComponent implements OnInit {

  @Input()
  public title: string;
  @Input()
  public subtitle: string;
  @Input()
  public value: number;

  constructor(private vertXEventBusService: VertXEventBusService) {
    vertXEventBusService.initialize(this.newValueCallback);
  }

  ngOnInit() {
  }

  /**
   * Vert.X event bus new value callback
   * @param {*} error Error message
   * @param {*} message Response message
   */
  public newValueCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else {
      console.log('message : ' + message);
      this.value = message.body.value;
    }
  }

}

/**
 * a reading temperature
 */
export class Temperature {
  temperature: number;
  date: string;

  constructor(temperature: number, date: string) {
    this.temperature = temperature;
    this.date = date;
  }
}

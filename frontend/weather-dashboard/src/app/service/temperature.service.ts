import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import {Temperature} from "../business/temperature";
import {Observable} from "rxjs";

// consume observable :
// https://upgradetoangular.com/angular-news/rxjs-6-examples-in-angular-6-unsubscribe-from-observables/

// best practice
// https://blog.strongbrew.io/rxjs-best-practices-in-angular/#Angular-embraces-RxJS


/*
Angular 6 provides a way to register services/providers directly in the @Injectable() decorator by using
the new providedIn attribute. This attribute accepts any module of your application or 'root' for the main app module.
Now you don't have to include your service in the providers array of your module.
 */
@Injectable({
  providedIn: 'root'
})
export class TemperatureService {

  constructor(public http: HttpClient) {
  }

  /**
   * Get the today temperatures, from 00:00 am to now
   * @return {Observable<Temperature[]>}
   */
  // public getTodayTemperatures(): Observable<Temperature[]> {
  //   return this.http
  //     .get(environment.temperatureRestUrl + '/temperature/today').pipe(
  //       map((response: Response) => {
  //         // console.log('getTodayTemperatures');
  //         const temperatures = response.json();
  //         return temperatures.map((temperature, date) => new Temperature(temperature, date));
  //       }), catchError(error => of(`Bad Promise: ${error}`)))
  // }

  /**
   * Handle and throw error when a rest service is called
   * @param  {Response | any}         error [description]
   * @return {[type]}        [description]
   */
  private handleError(error: Response | any) {
    console.error('TemperatureService::handleError', error);
    return Observable.throw(error);
  }

}

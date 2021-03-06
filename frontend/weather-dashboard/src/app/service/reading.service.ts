import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { Reading } from '../business/reading';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

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
export class ReadingService {

  constructor(public http: HttpClient) {
  }

  public getTodayReadings(url: string): Observable<Reading[]> {
    const todayRestUrl: string = environment.restUrl + url;
    return this.http.get<Reading[]>(todayRestUrl)
    .pipe(catchError(this.handleError<Reading[]>('getTodayReadings', [])));
  }

  public getSingleReading(url: string): Observable<Reading> {
    const todayRestUrl: string = environment.restUrl + url;
    return this.http.get<Reading>(todayRestUrl)
    .pipe(catchError(this.handleError<Reading>('getSingleReading')));
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}

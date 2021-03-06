import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule, MatIconModule, MatListModule, MatSidenavModule, MatToolbarModule} from '@angular/material';
import {MatCardModule} from '@angular/material/card';
import {MatSelectModule} from '@angular/material/select';
import {SensorDataCardComponent} from './sensor-data-card/sensor-data-card.component';
import {VertXEventBusService} from './vertx/vertXEventBus.service';
import {DashboardComponent} from './dashboard/dashboard.component';
import {ChartModule} from 'angular-highcharts';
import {ChartComponent} from './chart/chart.component';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    SensorDataCardComponent,
    DashboardComponent,
    ChartComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatSelectModule,
    ChartModule,
    HttpClientModule
  ],
  providers: [
    VertXEventBusService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

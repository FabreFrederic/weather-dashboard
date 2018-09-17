import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {TemperatureComponent} from './temperature/temperature.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavbarComponent} from './navbar/navbar.component';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule, MatIconModule, MatListModule, MatSidenavModule, MatToolbarModule} from '@angular/material';
import {MatCardModule} from '@angular/material/card';
import {MatSelectModule} from '@angular/material/select';
import {SensorDataCardComponent} from './sensor-data-card/sensor-data-card.component';
import {VertXEventBusService} from './vertx/vertXEventBus.service';

@NgModule({
  declarations: [
    AppComponent,
    TemperatureComponent,
    NavbarComponent,
    SensorDataCardComponent
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
    MatSelectModule
  ],
  providers: [
    VertXEventBusService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

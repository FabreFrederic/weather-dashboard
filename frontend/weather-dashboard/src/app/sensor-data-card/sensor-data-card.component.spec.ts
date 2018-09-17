import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SensorDataCardComponent} from './sensor-data-card.component';

describe('SensorDataCardComponent', () => {
  let component: SensorDataCardComponent;
  let fixture: ComponentFixture<SensorDataCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SensorDataCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SensorDataCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

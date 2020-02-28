import {inject, TestBed} from '@angular/core/testing';

import {ReadingService} from './reading.service';

describe('ReadingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReadingService]
    });
  });

  it('should be created', inject([ReadingService], (service: ReadingService) => {
    expect(service).toBeTruthy();
  }));
});

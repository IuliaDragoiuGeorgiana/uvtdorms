import { TestBed } from '@angular/core/testing';

import { DryerService } from '../dryer.service';

describe('DryerService', () => {
  let service: DryerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DryerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { WashingMachineService } from '../washing-machine.service';

describe('WashingMachineService', () => {
  let service: WashingMachineService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WashingMachineService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

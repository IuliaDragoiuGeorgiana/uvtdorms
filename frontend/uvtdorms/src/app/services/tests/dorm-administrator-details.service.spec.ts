import { TestBed } from '@angular/core/testing';

import { DormAdministratorDetailsService } from '../dorm-administrator-details.service';

describe('DormAdministratorDetailsService', () => {
  let service: DormAdministratorDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DormAdministratorDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

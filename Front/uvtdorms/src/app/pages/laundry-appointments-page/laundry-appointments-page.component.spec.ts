import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LaundryAppointmentsPageComponent } from './laundry-appointments-page.component';

describe('LaundryAppointmentsPageComponent', () => {
  let component: LaundryAppointmentsPageComponent;
  let fixture: ComponentFixture<LaundryAppointmentsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LaundryAppointmentsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LaundryAppointmentsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

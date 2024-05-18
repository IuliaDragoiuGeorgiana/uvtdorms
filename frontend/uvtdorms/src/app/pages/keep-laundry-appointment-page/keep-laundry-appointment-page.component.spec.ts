import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KeepLaundryAppointmentPageComponent } from './keep-laundry-appointment-page.component';

describe('KeepLaundryAppointmentPageComponent', () => {
  let component: KeepLaundryAppointmentPageComponent;
  let fixture: ComponentFixture<KeepLaundryAppointmentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [KeepLaundryAppointmentPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(KeepLaundryAppointmentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

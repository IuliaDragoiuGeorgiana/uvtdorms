import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterRequestDetailsPopupComponent } from './register-request-details-popup.component';

describe('RegisterRequestDetailsPopupComponent', () => {
  let component: RegisterRequestDetailsPopupComponent;
  let fixture: ComponentFixture<RegisterRequestDetailsPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterRequestDetailsPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegisterRequestDetailsPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

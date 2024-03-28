import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewRegisterRequestDialogComponent } from './new-register-request-dialog.component';

describe('NewRegisterRequestDialogComponent', () => {
  let component: NewRegisterRequestDialogComponent;
  let fixture: ComponentFixture<NewRegisterRequestDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewRegisterRequestDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewRegisterRequestDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

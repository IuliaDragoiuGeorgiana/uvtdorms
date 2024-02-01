import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterConfirmDialogComponent } from './register-confirm-dialog.component';

describe('RegisterConfirmDialogComponent', () => {
  let component: RegisterConfirmDialogComponent;
  let fixture: ComponentFixture<RegisterConfirmDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterConfirmDialogComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterConfirmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

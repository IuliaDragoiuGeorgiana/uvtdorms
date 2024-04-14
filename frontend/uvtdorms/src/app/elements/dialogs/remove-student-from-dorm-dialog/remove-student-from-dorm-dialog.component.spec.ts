import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveStudentFromDormDialogComponent } from './remove-student-from-dorm-dialog.component';

describe('RemoveStudentFromDormDialogComponent', () => {
  let component: RemoveStudentFromDormDialogComponent;
  let fixture: ComponentFixture<RemoveStudentFromDormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RemoveStudentFromDormDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RemoveStudentFromDormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDormDialogComponent } from './edit-dorm-dialog.component';

describe('EditDormDialogComponent', () => {
  let component: EditDormDialogComponent;
  let fixture: ComponentFixture<EditDormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditDormDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditDormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
